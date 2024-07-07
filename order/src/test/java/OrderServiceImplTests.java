import com.vr61v.ordermicroservice.OrderRepository;
import com.vr61v.ordermicroservice.OrderService;
import com.vr61v.ordermicroservice.OrderServiceImpl;
import com.vr61v.ordermicroservice.exceptions.IllegalOrderStateChangeException;
import com.vr61v.ordermicroservice.exceptions.NotEnoughMoneyException;
import com.vr61v.ordermicroservice.model.order.Order;
import com.vr61v.ordermicroservice.model.order.OrderState;
import com.vr61v.ordermicroservice.model.product.Detail;
import com.vr61v.ordermicroservice.model.product.Product;
import com.vr61v.ordermicroservice.model.product.ProductMapper;
import com.vr61v.ordermicroservice.model.request.CreateOrderRequest;
import com.vr61v.ordermicroservice.model.request.UpdateOrderRequest;
import com.vr61v.product.ProductClient;
import com.vr61v.product.ProductDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import provider.*;

import java.util.*;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class OrderServiceImplTests {

    @Test
    public void testCreateOrder() {
        // product dtos for response from product client fields
        String name = "product name";
        Double price = 100.0d;
        Double weight = 100.0d;
        String category = "product category";
        String composition = "product composition";
        String description = "product description";
        List<ProductDto> productDtos = new ArrayList<>();
        List<Product> products = new ArrayList<>();
        for (int i = 1; i < 6; ++i) {
            UUID orderId = UUID.randomUUID();
            products.add(new Product(orderId, name, price, weight, category, composition, description));
            productDtos.add(new ProductDto(orderId, name, price, weight, category, composition, description));
        }

        // create order request fields
        UUID userId = UUID.randomUUID();
        UUID restaurantId = UUID.randomUUID();
        Date date = new Date(2024, Calendar.MAY, 21);
        String comment = "order comment";
        OrderState state = OrderState.CREATE;
        HashMap<UUID, Integer> detailsMap = new HashMap<>();
        Set<Detail> detailsSet = new HashSet<>();
        for (int i = 1; i < 6; ++i) {
            detailsMap.put(products.get(i - 1).getId(), i);
            detailsSet.add(new Detail(products.get(i - 1), i));
        }

        CreateOrderRequest request = new CreateOrderRequest(userId, restaurantId, date, comment, state, detailsMap);

        OrderRepository repository = mock(OrderRepository.class);
        when(repository.save(Mockito.any(Order.class))).thenAnswer(actual -> actual.getArguments()[0]);

        ProductClient productClient = Mockito.mock(ProductClient.class);
        when(productClient.getProductsById(detailsMap.keySet().stream().toList())).thenReturn(new ResponseEntity<>(productDtos, HttpStatus.OK));

        ProductMapper productMapper = Mockito.mock(ProductMapper.class);
        when(productMapper.dtoToEntity(Mockito.any(ProductDto.class))).thenAnswer(actual -> {
            ProductDto productDto = (ProductDto) actual.getArguments()[0];
            Product response = null;
            for (Product product : products) {
                if (product.getId().equals(productDto.getId())) {
                    response = product;
                    break;
                }
            }
            return response;
        });
        OrderService service = new OrderServiceImpl(repository, productClient, productMapper);

        Order expected = new Order(null, userId, restaurantId, date, comment, state, detailsSet);
        Order actual = service.createOrder(request);

        Assertions.assertNotNull(actual.getId());
        actual.setId(null);
        Assertions.assertEquals(expected, actual);
    }

    @ParameterizedTest
    @ArgumentsSource(OrderServiceImplTestsGetOrderByIdProvider.class)
    public void testGetOrderById(UUID productId, OrderRepository repository, Order expected) {
        OrderService service = new OrderServiceImpl(repository, null, null);
        Order actual = service.getOrderById(productId);
        Assertions.assertEquals(expected, actual);
    }

    @ParameterizedTest
    @ArgumentsSource(OrderServiceImplTestsGetAllOrdersProvider.class)
    public void testGetAllOrders(OrderRepository repository, List<Order> expected) {
        OrderService service = new OrderServiceImpl(repository, null, null);
        List<Order> actual = service.getAllOrders();
        Assertions.assertEquals(expected, actual);
    }

    @ParameterizedTest
    @ArgumentsSource(OrderServiceImplTestsUpdateOrderProvider.class)
    public void testUpdateOrder(UUID orderId, UpdateOrderRequest request, OrderRepository repository, ProductClient productClient, ProductMapper productMapper, Order expected) {
        OrderService service = new OrderServiceImpl(repository, productClient, productMapper);
        Order actual = service.updateOrder(orderId, request);
        Assertions.assertEquals(expected, actual);
    }

    @ParameterizedTest
    @ArgumentsSource(OrderServiceImplTestsUpdateOrderStateSuccessProvider.class)
    public void testUpdateOrderStateSuccess(UUID orderId, OrderState state, OrderRepository repository, Order expected) throws IllegalOrderStateChangeException {
        OrderService service = new OrderServiceImpl(repository, null, null);
        Order actual = service.updateOrderState(orderId, state);
        Assertions.assertEquals(expected, actual);
    }

    @ParameterizedTest
    @ArgumentsSource(OrderServiceImplTestsUpdateOrderStateErrorProvider.class)
    public void testUpdateOrderStateError(UUID orderId, OrderState state, OrderRepository repository) {
        OrderService service = new OrderServiceImpl(repository, null, null);
        Assertions.assertThrows(IllegalOrderStateChangeException.class, () -> service.updateOrderState(orderId, state));
    }

    @ParameterizedTest
    @ArgumentsSource(OrderServiceImplTestsPayOrderSuccessProvider.class)
    public void testPayOrderSuccess(UUID orderId, Double amount, OrderRepository repository) throws IllegalOrderStateChangeException, NotEnoughMoneyException {
        OrderService service = new OrderServiceImpl(repository, null, null);
        Order actual = service.payOrder(orderId, amount);
        Assertions.assertEquals(OrderState.PAY, actual.getState());
    }

    @Test
    public void testPayOrderError() {
        UUID userId = UUID.randomUUID();
        UUID restaurantId = UUID.randomUUID();
        Date date = new Date(2024, Calendar.MAY, 21);
        String comment = "order comment";
        Set<Detail> detailsSet = new HashSet<>();

        Order payedOrder = new Order(UUID.randomUUID(), userId, restaurantId, date, comment, OrderState.PAY, detailsSet);
        Order notEnoughMoney = new Order(UUID.randomUUID(), userId, restaurantId, date, comment, OrderState.CREATE, detailsSet);

        OrderRepository repository = mock(OrderRepository.class);
        when(repository.findById(payedOrder.getId())).thenReturn(Optional.of(payedOrder));
        when(repository.findById(notEnoughMoney.getId())).thenReturn(Optional.of(notEnoughMoney));
        when(repository.save(Mockito.any(Order.class))).thenAnswer(actual -> actual.getArguments()[0]);

        OrderService service = new OrderServiceImpl(repository, null, null);

        Assertions.assertThrows(IllegalOrderStateChangeException.class, () -> service.payOrder(payedOrder.getId(), 1000d));
        Assertions.assertThrows(NotEnoughMoneyException.class, () -> service.payOrder(notEnoughMoney.getId(), -100d));
    }
}
