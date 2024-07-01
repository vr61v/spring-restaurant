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

public class RestaurantServiceImplTestsGetRestaurantByIdProvider implements ArgumentsProvider {
    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) {
        UUID id = UUID.randomUUID();
        String address = "restaurant address";
        String phone = "+79052779090";
        LocalTime openingHoursFrom = LocalTime.of(10, 0);
        LocalTime openingHoursTo = LocalTime.of(22, 0);
        Set<UUID> menu = new HashSet<>();
        for (int i = 0; i < 10; ++i) menu.add(UUID.randomUUID());
        Restaurant restaurant = new Restaurant(id, address, phone, openingHoursFrom, openingHoursTo, menu);

        RestaurantRepository repository = mock(RestaurantRepository.class);
        when(repository.findById(id)).thenReturn(Optional.of(restaurant));

        List<Arguments> arguments = new ArrayList<>();
        arguments.add(Arguments.of(id, repository, restaurant)); // success find then return found product
        arguments.add(Arguments.of(UUID.randomUUID(), repository, null)); // error find then return null

        return arguments.stream();
    }
}
