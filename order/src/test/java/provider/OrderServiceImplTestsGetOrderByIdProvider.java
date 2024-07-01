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

public class OrderServiceImplTestsGetOrderByIdProvider implements ArgumentsProvider {
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

        UUID orderId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();
        UUID restaurantId = UUID.randomUUID();
        Date date = new Date(2024, Calendar.MAY, 21);
        String comment = "order comment";
        OrderState state = OrderState.CREATE;
        Set<Detail> detailsSet = new HashSet<>();
        for (int i = 1; i < 6; ++i) {
            detailsSet.add(new Detail(products.get(i - 1), i));
        }

        Order order = new Order(orderId, userId, restaurantId, date, comment, state, detailsSet);

        OrderRepository repository = mock(OrderRepository.class);
        when(repository.findById(orderId)).thenReturn(Optional.of(order));

        List<Arguments> arguments = new ArrayList<>();
        arguments.add(Arguments.of(orderId, repository, order)); // success find then return found order
        arguments.add(Arguments.of(UUID.randomUUID(), repository, null)); // error find then return null

        return arguments.stream();
    }
}
