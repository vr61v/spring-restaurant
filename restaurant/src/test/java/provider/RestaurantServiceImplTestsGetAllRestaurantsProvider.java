package provider;

import com.vr61v.RestaurantRepository;
import com.vr61v.model.Restaurant;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.time.LocalTime;
import java.util.*;
import java.util.stream.Stream;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RestaurantServiceImplTestsGetAllRestaurantsProvider implements ArgumentsProvider {
    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) {
        String address = "restaurant address";
        String phone = "+79052779090";
        LocalTime openingHoursFrom = LocalTime.of(10, 0);
        LocalTime openingHoursTo = LocalTime.of(22, 0);
        Set<UUID> menu = new HashSet<>();
        for (int i = 0; i < 10; ++i) menu.add(UUID.randomUUID());

        List<Restaurant> restaurantsEmpty = new ArrayList<>();
        List<Restaurant> restaurantsNotEmpty = new ArrayList<>();
        for (int i = 0; i < 10; ++i) {
            restaurantsNotEmpty.add(new Restaurant(UUID.randomUUID(), address, phone, openingHoursFrom, openingHoursTo, menu));
        }

        List<Arguments> arguments = new ArrayList<>();
        for (List<Restaurant> restaurants : List.of(restaurantsEmpty, restaurantsNotEmpty)) {
            RestaurantRepository repository = mock(RestaurantRepository.class);
            when(repository.findAll()).thenReturn(restaurants);
            arguments.add(Arguments.of(repository, restaurants));
        }

        return arguments.stream();
    }
}
