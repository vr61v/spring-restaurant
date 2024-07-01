package provider;

import com.vr61v.OrderRepository;
import com.vr61v.model.order.Order;
import com.vr61v.model.order.OrderState;
import com.vr61v.model.product.Detail;
import com.vr61v.model.product.Product;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.mockito.Mockito;

import java.util.*;
import java.util.stream.Stream;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class OrderServiceImplTestsUpdateOrderStateErrorProvider implements ArgumentsProvider {
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
        Set<Detail> detailsSet = new HashSet<>();
        for (int i = 1; i < 6; ++i) {
            detailsSet.add(new Detail(products.get(i - 1), i));
        }


        OrderState[] orderStates = OrderState.values();
        List<Arguments> arguments = new ArrayList<>();
        for (int i = orderStates.length - 1; i >= 0; --i) {
            for (int j = 0; j < i; ++j) {
                Order order = new Order(orderId, userId, restaurantId, date, comment, orderStates[i], detailsSet);
                Order expected = new Order(orderId, userId, restaurantId, date, comment, orderStates[j], detailsSet);
                OrderRepository repository = mock(OrderRepository.class);
                when(repository.findById(orderId)).thenReturn(Optional.of(order));
                when(repository.save(Mockito.any(Order.class))).thenAnswer(actual -> actual.getArguments()[0]);
                arguments.add(Arguments.of(orderId, orderStates[j], repository, expected));
            }
        }

        return arguments.stream();
    }
}
