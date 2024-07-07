package provider;

import com.vr61v.ordermicroservice.OrderRepository;
import com.vr61v.ordermicroservice.model.order.Order;
import com.vr61v.ordermicroservice.model.order.OrderState;
import com.vr61v.ordermicroservice.model.product.Detail;
import com.vr61v.ordermicroservice.model.product.Product;
import com.vr61v.ordermicroservice.model.product.ProductMapper;
import com.vr61v.ordermicroservice.model.request.UpdateOrderRequest;
import com.vr61v.product.ProductClient;
import com.vr61v.product.ProductDto;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.*;
import java.util.stream.Stream;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class OrderServiceImplTestsUpdateOrderProvider implements ArgumentsProvider {
    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) {
        // product dtos for response from product client fields
        String name = "product name";
        Double price = 100.0d;
        Double weight = 100.0d;
        String category = "product category";
        String composition = "product composition";
        String description = "product description";

        // create order request fields
        UUID orderId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();
        UUID restaurantId = UUID.randomUUID();
        Date date = new Date(2024, Calendar.MAY, 21);
        String comment = "order comment";
        OrderState state = OrderState.CREATE;

        List<Product> firstProducts = new ArrayList<>();
        List<ProductDto> firstProductDtos = new ArrayList<>();
        HashMap<UUID, Integer> firstDetailsMap = new HashMap<>();
        HashSet<Detail> firstDetailsSet = new HashSet<>();
        for (int i = 1; i < 6; ++i) {
            UUID productId = UUID.randomUUID();
            firstProducts.add(new Product(productId, name, price, weight, category, composition, description));
            firstProductDtos.add(new ProductDto(productId, name, price, weight, category, composition, description));
            firstDetailsMap.put(productId, i);
            firstDetailsSet.add(new Detail(firstProducts.get(i - 1), i));
        }

        List<Product> secondProducts = new ArrayList<>();
        List<ProductDto> secondProductDtos = new ArrayList<>();
        HashMap<UUID, Integer> secondDetailsMap = new HashMap<>();
        HashSet<Detail> secondDetailsSet = new HashSet<>();
        for (int i = 1; i < 4; ++i) {
            UUID productId = UUID.randomUUID();
            secondProducts.add(new Product(productId, name, price, weight, category, composition, description));
            secondProductDtos.add(new ProductDto(productId, name, price, weight, category, composition, description));
            secondDetailsMap.put(productId, i);
            secondDetailsSet.add(new Detail(secondProducts.get(i - 1), i));
        }

        ProductClient productClient = Mockito.mock(ProductClient.class);
        when(productClient.getProductsById(firstDetailsMap.keySet().stream().toList())).thenReturn(new ResponseEntity<>(firstProductDtos, HttpStatus.OK));
        when(productClient.getProductsById(secondDetailsMap.keySet().stream().toList())).thenReturn(new ResponseEntity<>(secondProductDtos, HttpStatus.OK));

        ProductMapper productMapper = Mockito.mock(ProductMapper.class);
        when(productMapper.dtoToEntity(Mockito.any(ProductDto.class))).thenAnswer(actual -> {
            ProductDto productDto = (ProductDto) actual.getArguments()[0];
            Product response = null;
            for (Product product : firstProducts) {
                if (product.getId().equals(productDto.getId())) {
                    response = product;
                    break;
                }
            }
            if (response == null) {
                for (Product product : secondProducts) {
                    if (product.getId().equals(productDto.getId())) {
                        response = product;
                        break;
                    }
                }
            }
            return response;
        });

        List<Arguments> arguments = new ArrayList<>();
        for (UUID newUserId : new UUID[]{null, userId, UUID.randomUUID()}) {
            for (UUID newRestaurantId : new UUID[]{null, restaurantId, UUID.randomUUID()}) {
                for (Date newDate : new Date[]{null, date, new Date(2024, Calendar.JULY, 22)}) {
                    for (String newComment : new String[]{null, comment, "order new comment"}) {
                        for (HashMap newDetailMap : new HashMap[]{null, firstDetailsMap, secondDetailsMap}) {
                            Order order = new Order(orderId, userId, restaurantId, date, comment, state, firstDetailsSet);

                            OrderRepository repository = mock(OrderRepository.class);
                            when(repository.findById(orderId)).thenReturn(Optional.of(order));
                            when(repository.save(Mockito.any(Order.class))).thenAnswer(actual -> actual.getArguments()[0]);

                            UpdateOrderRequest request = new UpdateOrderRequest(
                                    newUserId,
                                    newRestaurantId,
                                    newDate,
                                    newComment,
                                    newDetailMap
                            );

                            HashSet<Detail> newDetailSet = firstDetailsSet;
                            if (newDetailMap != null) {
                                newDetailSet = newDetailMap.equals(firstDetailsMap) ? firstDetailsSet : secondDetailsSet;
                            }

                            Order expected = new Order(
                                    orderId,
                                    newUserId != null ? newUserId : userId,
                                    newRestaurantId != null ? newRestaurantId : restaurantId,
                                    newDate != null ? newDate : date,
                                    newComment != null ? newComment : comment,
                                    OrderState.CREATE,
                                    newDetailSet
                            );

                            arguments.add(Arguments.of(orderId, request, repository, productClient, productMapper, expected));
                        }
                    }
                }
            }
        }
        return arguments.stream();
    }
}
