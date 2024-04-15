import com.vr61v.exceptions.IllegalOrderStateChangeException;
import com.vr61v.exceptions.NotEnoughMoneyException;
import com.vr61v.exceptions.NotFoundException;
import com.vr61v.models.order.Order;
import com.vr61v.models.order.OrderDetail;
import com.vr61v.models.order.types.OrderState;
import com.vr61v.models.user.Card;
import com.vr61v.models.user.User;
import com.vr61v.models.user.types.CardType;
import com.vr61v.models.user.types.Gender;
import com.vr61v.models.user.types.Role;
import com.vr61v.out.order.Orders;
import com.vr61v.out.user.Cards;
import com.vr61v.out.user.Users;
import com.vr61v.services.UserServiceImpl;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import providers.user.TestPayOrderProvider;
import providers.user.TestUpdateUserProvider;

import java.util.*;

import static org.mockito.Mockito.*;

/*
 * Tests for:
 * 1. getUserById, updateUser, deleteUser
 * 2. createOrder, payOrder, cancelOrder
 * 3. registerCard
 */

public class UserServiceImplTests {
    private final UUID id = UUID.fromString("11111111-1111-1111-1111-111111111111");
    private final String name = "I am test user";
    private final String phone = "+7-900-200-50-50";
    private final String email = "something@gmail.com";
    private final GregorianCalendar dob = new GregorianCalendar(2000, GregorianCalendar.JANUARY, 1);
    private final Gender gender = Gender.MALE;
    private final Role role = Role.USER;

    private final Users users = mock(Users.class);
    private final Orders orders = mock(Orders.class);
    private final Cards cards = mock(Cards.class);

    @Test
    public void testGetUserById() throws NotFoundException {
        User expected = new User(id, name, phone, email, dob, gender, role);
        when(users.find(id)).thenReturn(expected);
        UserServiceImpl service = new UserServiceImpl(users, orders, cards);

        User actual = service.getUserById(id);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void testGetUserByIdExpectedIllegalArgumentException() throws NotFoundException {
        String  expected = "User can get only USER.";
        User admin = new User(id, name, phone, email, dob, gender, Role.ADMIN);
        when(users.find(id)).thenReturn(admin);
        UserServiceImpl service = new UserServiceImpl(users, orders, cards);

        IllegalArgumentException actual = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            service.getUserById(id);
        });

        Assertions.assertEquals(expected, actual.getMessage());
    }

    @ParameterizedTest
    @ArgumentsSource(TestUpdateUserProvider.class)
    public void testUpdateUser(String nameUpdate, String phoneUpdate, String emailUpdate) throws NotFoundException {
        User old = new User(id, name, phone, email, dob, gender, role);
        when(users.find(id)).thenReturn(old);
        UserServiceImpl service = new UserServiceImpl(users, orders, cards);
        User expected = new User(id,
                nameUpdate == null ? name : nameUpdate,
                phoneUpdate == null ? phone : phoneUpdate,
                emailUpdate == null ? email : emailUpdate,
                dob, gender, role
        );

        User actual = null;
        try {
            actual = service.updateUser(id, nameUpdate, phoneUpdate, emailUpdate);
        } catch (IllegalArgumentException e) {
            Assertions.assertEquals(
                    old.getRole() == Role.ADMIN ?
                            "User can update only USER." :
                            "The updated fields must be different from the existing ones.",
                    e.getMessage());
        }
        if (actual != null) {
            User finalActual = actual;
            Assertions.assertAll(
                    () -> Assertions.assertEquals(expected.getId(), finalActual.getId()),
                    () -> Assertions.assertEquals(expected.getName(), finalActual.getName()),
                    () -> Assertions.assertEquals(expected.getPhone(), finalActual.getPhone()),
                    () -> Assertions.assertEquals(expected.getEmail(), finalActual.getEmail()),
                    () -> Assertions.assertEquals(expected.getDateOfBirth(), finalActual.getDateOfBirth()),
                    () -> Assertions.assertEquals(expected.getGender(), finalActual.getGender()),
                    () -> Assertions.assertEquals(expected.getRole(), finalActual.getRole())
            );
        }
    }

    @Test
    public void testDeleteUser() throws NotFoundException {
        User expected = new User(id, name, phone, email, dob, gender, role);
        when(users.find(id)).thenReturn(expected);
        doNothing().when(users).delete(id);
        UserServiceImpl service = new UserServiceImpl(users, orders, cards);

        UUID actual = service.deleteUser(id);

        Assertions.assertEquals(id, actual);
    }

    @Test
    public void testDeleteUserExpectedIllegalArgumentException() throws NotFoundException {
        String  expected = "User can delete only USER.";
        User user = new User(id, name, phone, email, dob, gender, Role.ADMIN);
        when(users.find(id)).thenReturn(user);
        doNothing().when(users).delete(id);
        UserServiceImpl service = new UserServiceImpl(users, orders, cards);

        IllegalArgumentException actual = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            service.deleteUser(id);
        });

        Assertions.assertEquals(expected, actual.getMessage());
    }

    @Test
    public void testCreateOrder() {
        UUID orderId = UUID.fromString("33333333-3333-3333-3333-333333333333");
        UUID restaurantId = UUID.fromString("22222222-2222-2222-2222-222222222222");
        Calendar date = new GregorianCalendar(2000, Calendar.JANUARY, 1);
        String comment = "i want something...";
        List<OrderDetail> details =  new ArrayList<>();
        Order expected =  new Order(orderId, id, restaurantId, date, comment, OrderState.CREATE, details);

        when(orders.save(expected)).thenReturn(expected);
        UserServiceImpl service = new UserServiceImpl(users, orders, cards);

        Order actual = service.createOrder(expected);

        Assertions.assertEquals(expected, actual);
    }

    @ParameterizedTest
    @ArgumentsSource(TestPayOrderProvider.class)
    public void testPayOrder(UUID orderId, Integer amount, Order expected) throws NotFoundException {
        when(orders.find(orderId)).thenReturn(expected);
        when(orders.save(expected)).thenReturn(expected);
        UserServiceImpl service = new UserServiceImpl(users, orders, cards);

        Order actual = null;
        try {
            actual = service.payOrder(orderId, amount);
        } catch (Exception e) {
            if (expected.getState() != OrderState.CREATE) {
                Assertions.assertEquals(IllegalOrderStateChangeException.class, e.getClass());
            } else {
                Assertions.assertEquals(NotEnoughMoneyException.class, e.getClass());
            }
            return;
        }

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void testCanselOrder() throws NotFoundException, IllegalOrderStateChangeException {
        UUID orderId = UUID.fromString("33333333-3333-3333-3333-333333333333");
        UUID restaurantId = UUID.fromString("22222222-2222-2222-2222-222222222222");
        Calendar date = new GregorianCalendar(2000, Calendar.JANUARY, 1);
        String comment = "i want something...";
        List<OrderDetail> details =  new ArrayList<>();
        Order expected =  new Order(orderId, id, restaurantId, date, comment, OrderState.CREATE, details);

        when(orders.find(orderId)).thenReturn(expected);
        UserServiceImpl service = new UserServiceImpl(users, orders, cards);

        Order actual = service.cancelOrder(orderId);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void testCanselOrderExpectedIllegalOrderStateChangeException() throws NotFoundException {
        UUID orderId1 = UUID.fromString("11111111-1111-1111-1111-111111111111");
        UUID orderId2 = UUID.fromString("22222222-2222-2222-2222-22222222222");
        Order cook =  Order.builder().id(orderId1).state(OrderState.COOK).build();
        Order cancel =  Order.builder().id(orderId2).state(OrderState.CANCEL).build();

        when(orders.find(orderId1)).thenReturn(cook);
        when(orders.find(orderId2)).thenReturn(cancel);
        UserServiceImpl service = new UserServiceImpl(users, orders, cards);

        IllegalOrderStateChangeException actualCook = Assertions.assertThrows(IllegalOrderStateChangeException.class, () -> {
            service.cancelOrder(orderId1);
        });

        IllegalOrderStateChangeException actualCansel = Assertions.assertThrows(IllegalOrderStateChangeException.class, () -> {
            service.cancelOrder(orderId2);
        });

        Assertions.assertEquals("The order must be in CREATE or PAY state.", actualCook.getMessage());
        Assertions.assertEquals("The order must be in CREATE or PAY state.", actualCansel.getMessage());
    }

    @Test
    public void testRegisterCard() throws NotFoundException {
        UUID cardId = UUID.fromString("11111111-1111-1111-1111-111111111111");
        String number = "1234123412341234";
        Card expected = new Card(cardId, null, number, CardType.BRONZE, 0.1F);
        when(cards.findByNumber(number)).thenReturn(expected);
        UserServiceImpl service = new UserServiceImpl(users, orders, cards);

        Card actual = service.registerCard(id, number);

        Assertions.assertEquals(id, actual.getUserId());
    }
}
