import com.vr61v.RestaurantRepository;
import com.vr61v.RestaurantService;
import com.vr61v.RestaurantServiceImpl;
import com.vr61v.model.Restaurant;
import com.vr61v.model.request.CreateRestaurantRequest;
import com.vr61v.model.request.UpdateRestaurantRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import provider.RestaurantServiceImplTestsGetAllRestaurantsProvider;
import provider.RestaurantServiceImplTestsGetRestaurantByIdProvider;
import provider.RestaurantServiceImplTestsUpdateRestaurantProvider;

import java.time.LocalTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RestaurantServiceImplTests {

    @Test
    public void testCreateRestaurant() {
        String address = "restaurant address";
        String phone = "+79052779090";
        LocalTime openingHoursFrom = LocalTime.of(10, 0);
        LocalTime openingHoursTo = LocalTime.of(22, 0);
        Set<UUID> menu = new HashSet<>();
        for (int i = 0; i < 10; ++i) menu.add(UUID.randomUUID());

        CreateRestaurantRequest request = new CreateRestaurantRequest(address, phone, openingHoursFrom, openingHoursTo, menu);
        RestaurantRepository repository = mock(RestaurantRepository.class);
        when(repository.save(any(Restaurant.class))).thenAnswer(actual -> actual.getArguments()[0]);
        RestaurantServiceImpl service = new RestaurantServiceImpl(repository);

        Restaurant expected = new Restaurant(null, address, phone, openingHoursFrom, openingHoursTo, menu);
        Restaurant actual = service.createRestaurant(request);

        Assertions.assertNotNull(actual.getId());
        actual.setId(null);
        Assertions.assertEquals(expected, actual);
    }

    @ParameterizedTest
    @ArgumentsSource(RestaurantServiceImplTestsGetRestaurantByIdProvider.class)
    public void testGetRestaurantById(UUID restaurantId, RestaurantRepository repository, Restaurant expected) {
        RestaurantService service = new RestaurantServiceImpl(repository);
        Restaurant actual = service.getRestaurantById(restaurantId);
        Assertions.assertEquals(expected, actual);
    }

    @ParameterizedTest
    @ArgumentsSource(RestaurantServiceImplTestsGetAllRestaurantsProvider.class)
    public void testGetAllRestaurants(RestaurantRepository repository, List<Restaurant> expected) {
        RestaurantService service = new RestaurantServiceImpl(repository);
        List<Restaurant> actual = service.getAllRestaurants();
        Assertions.assertEquals(expected, actual);
    }

    @ParameterizedTest
    @ArgumentsSource(RestaurantServiceImplTestsUpdateRestaurantProvider.class)
    public void testUpdateRestaurant(UUID productId, UpdateRestaurantRequest request, RestaurantRepository repository, Restaurant expected) {
        RestaurantService service = new RestaurantServiceImpl(repository);
        Restaurant actual = service.updateRestaurant(productId, request);
        Assertions.assertEquals(expected, actual);
    }
}
