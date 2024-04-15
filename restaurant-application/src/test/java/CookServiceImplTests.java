import com.vr61v.exceptions.IllegalOrderStateChangeException;
import com.vr61v.exceptions.NotFoundException;
import com.vr61v.models.order.Order;
import com.vr61v.models.order.OrderDetail;
import com.vr61v.models.order.types.OrderState;
import com.vr61v.out.order.Orders;
import com.vr61v.services.CookServiceImpl;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.util.*;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/*
 * Tests for:
 * 1. takeOrder, completeOrder
 */

public class CookServiceImplTests {
    private final Orders orders = mock(Orders.class);

    @Test
    public void testTakeOrder() throws IllegalOrderStateChangeException, NotFoundException {
        UUID orderId = UUID.fromString("33333333-3333-3333-3333-333333333333");
        UUID restaurantId = UUID.fromString("22222222-2222-2222-2222-222222222222");
        UUID userid = UUID.fromString("11111111-1111-1111-1111-111111111111");
        Calendar date = new GregorianCalendar(2000, Calendar.JANUARY, 1);
        String comment = "i want something...";
        List<OrderDetail> details =  new ArrayList<>();
        Order order =  new Order(orderId, userid, restaurantId, date, comment, OrderState.PAY, details);

        when(orders.find(orderId)).thenReturn(order);
        CookServiceImpl service = new CookServiceImpl(orders);

        Order actual = service.takeOrder(orderId);

        Assertions.assertEquals(OrderState.COOK, actual.getState());
    }

    @Test
    public void testTakeOrderExpectedIllegalOrderStateChangeException() throws NotFoundException {
        UUID orderId1 = UUID.fromString("11111111-1111-1111-1111-111111111111");
        UUID orderId2 = UUID.fromString("22222222-2222-2222-2222-222222222222");
        UUID orderId3 = UUID.fromString("33333333-3333-3333-3333-333333333333");
        Order orderCreate =  Order.builder().id(orderId1).state(OrderState.CREATE).build();
        Order orderCook =  Order.builder().id(orderId2).state(OrderState.COOK).build();
        Order orderCansel =  Order.builder().id(orderId3).state(OrderState.CANCEL).build();

        when(orders.find(orderId1)).thenReturn(orderCreate);
        when(orders.find(orderId2)).thenReturn(orderCook);
        when(orders.find(orderId3)).thenReturn(orderCansel);
        CookServiceImpl service = new CookServiceImpl(orders);

        IllegalOrderStateChangeException actualCreate = Assertions.assertThrows(IllegalOrderStateChangeException.class, () -> {
            service.takeOrder(orderId1);
        });
        IllegalOrderStateChangeException actualCook = Assertions.assertThrows(IllegalOrderStateChangeException.class, () -> {
            service.takeOrder(orderId2);
        });

        IllegalOrderStateChangeException actualCansel = Assertions.assertThrows(IllegalOrderStateChangeException.class, () -> {
            service.takeOrder(orderId3);
        });

        Assertions.assertEquals("The order must be in PAY state.", actualCreate.getMessage());
        Assertions.assertEquals("The order must be in PAY state.", actualCook.getMessage());
        Assertions.assertEquals("The order must be in PAY state.", actualCansel.getMessage());
    }

    @Test
    public void testCompleteOrder() throws IllegalOrderStateChangeException, NotFoundException {
        UUID orderId = UUID.fromString("33333333-3333-3333-3333-333333333333");
        UUID restaurantId = UUID.fromString("22222222-2222-2222-2222-222222222222");
        UUID userid = UUID.fromString("11111111-1111-1111-1111-111111111111");
        Calendar date = new GregorianCalendar(2000, Calendar.JANUARY, 1);
        String comment = "i want something...";
        List<OrderDetail> details =  new ArrayList<>();
        Order order =  new Order(orderId, userid, restaurantId, date, comment, OrderState.COOK, details);

        when(orders.find(orderId)).thenReturn(order);
        CookServiceImpl service = new CookServiceImpl(orders);

        Order actual = service.completeOrder(orderId);

        Assertions.assertEquals(OrderState.DONE, actual.getState());
    }

    @Test
    public void testCompleteOrderExpectedIllegalOrderStateChangeException() throws NotFoundException {
        UUID orderId1 = UUID.fromString("11111111-1111-1111-1111-111111111111");
        UUID orderId2 = UUID.fromString("22222222-2222-2222-2222-222222222222");
        UUID orderId3 = UUID.fromString("33333333-3333-3333-3333-333333333333");
        Order orderCreate =  Order.builder().id(orderId1).state(OrderState.CREATE).build();
        Order orderPay =  Order.builder().id(orderId2).state(OrderState.PAY).build();
        Order orderCansel =  Order.builder().id(orderId3).state(OrderState.CANCEL).build();

        when(orders.find(orderId1)).thenReturn(orderCreate);
        when(orders.find(orderId2)).thenReturn(orderPay);
        when(orders.find(orderId3)).thenReturn(orderCansel);
        CookServiceImpl service = new CookServiceImpl(orders);

        IllegalOrderStateChangeException actualCreate = Assertions.assertThrows(IllegalOrderStateChangeException.class, () -> {
            service.completeOrder(orderId1);
        });
        IllegalOrderStateChangeException actualCook = Assertions.assertThrows(IllegalOrderStateChangeException.class, () -> {
            service.completeOrder(orderId2);
        });

        IllegalOrderStateChangeException actualCansel = Assertions.assertThrows(IllegalOrderStateChangeException.class, () -> {
            service.completeOrder(orderId3);
        });

        Assertions.assertEquals("The order must be in COOK state.", actualCreate.getMessage());
        Assertions.assertEquals("The order must be in COOK state.", actualCook.getMessage());
        Assertions.assertEquals("The order must be in COOK state.", actualCansel.getMessage());
    }
}
