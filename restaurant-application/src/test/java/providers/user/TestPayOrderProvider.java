package providers.user;

import com.vr61v.exceptions.NotEnoughMoneyException;
import com.vr61v.models.order.Order;
import com.vr61v.models.order.OrderDetail;
import com.vr61v.models.order.types.OrderState;
import com.vr61v.models.product.Product;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.mockito.Mockito;

import java.util.*;
import java.util.stream.Stream;

public class TestPayOrderProvider implements ArgumentsProvider {
    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) throws Exception {
        UUID orderId = UUID.fromString("33333333-3333-3333-3333-333333333333");
        UUID userId = UUID.fromString("11111111-1111-1111-1111-111111111111");
        UUID restaurantId = UUID.fromString("22222222-2222-2222-2222-222222222222");
        Calendar date = new GregorianCalendar(2000, Calendar.JANUARY, 1);
        String comment = "i want something...";

        List<OrderDetail> details =  new ArrayList<>();

        Product product = Mockito.mock(Product.class);
        Mockito.when(product.getPrice()).thenReturn(10);

        OrderDetail detail = Mockito.mock(OrderDetail.class);
        Mockito.when(detail.getProduct()).thenReturn(product);
        Mockito.when(detail.getQuantity()).thenReturn(3);
        details.add(detail);

        Order orderCreate =  new Order(orderId, userId, restaurantId, date, comment, OrderState.CREATE, details);
        Order orderPay =  new Order(orderId, userId, restaurantId, date, comment, OrderState.PAY, details);
        Order orderCook =  new Order(orderId, userId, restaurantId, date, comment, OrderState.COOK, details);
        Order orderCansel =  new Order(orderId, userId, restaurantId, date, comment, OrderState.CANCEL, details);

        return Stream.of(
                Arguments.of(orderId, 10, orderCreate),
                Arguments.of(orderId, 30, orderCreate),
                Arguments.of(orderId, 40, orderCreate),

                Arguments.of(orderId, 30, orderPay),
                Arguments.of(orderId, 30, orderCook),
                Arguments.of(orderId, 30, orderCansel)
        );
    }
}
