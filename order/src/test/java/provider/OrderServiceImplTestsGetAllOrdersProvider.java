package provider;

import com.vr61v.OrderRepository;
import com.vr61v.model.order.Order;
import com.vr61v.model.order.OrderState;
import com.vr61v.model.product.Detail;
import com.vr61v.model.product.Product;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.*;
import java.util.stream.Stream;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class OrderServiceImplTestsGetAllOrdersProvider implements ArgumentsProvider {
    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) {
        String name = "product name";
        Double price = 100.0d;
        Double weight = 100.0d;
        String category = "product category";
        String composition = "product composition";
        String description = "product description";
        List<Product> products = new ArrayList<>();
        for (int i = 1; i < 6; ++i) {
            UUID orderId = UUID.randomUUID();
            products.add(new Product(orderId, name, price, weight, category, composition, description));
        }

        UUID userId = UUID.randomUUID();
        UUID restaurantId = UUID.randomUUID();
        Date date = new Date(2024, Calendar.MAY, 21);
        String comment = "order comment";
        OrderState state = OrderState.CREATE;
        Set<Detail> detailsSet = new HashSet<>();
        for (int i = 1; i < 6; ++i) {
            detailsSet.add(new Detail(products.get(i - 1), i));
        }

        List<Order> ordersEmpty = new ArrayList<>();
        List<Order> ordersNotEmpty = new ArrayList<>();
        for (int i = 0; i < 10; ++i) {
            ordersNotEmpty.add(new Order(UUID.randomUUID(), userId, restaurantId, date, comment, state, detailsSet));
        }

        List<Arguments> arguments = new ArrayList<>();
        for (List<Order> orders : List.of(ordersEmpty, ordersNotEmpty)) {
            OrderRepository repository = mock(OrderRepository.class);
            when(repository.findAll()).thenReturn(orders);
            arguments.add(Arguments.of(repository, orders));
        }

        return arguments.stream();
    }
}
