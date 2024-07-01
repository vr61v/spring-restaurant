package provider;

import com.vr61v.RestaurantRepository;
import com.vr61v.model.Restaurant;
import com.vr61v.model.request.UpdateRestaurantRequest;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.mockito.Mockito;

import java.time.LocalTime;
import java.util.*;
import java.util.stream.Stream;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RestaurantServiceImplTestsUpdateRestaurantProvider implements ArgumentsProvider {
    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) {
        UUID id = UUID.randomUUID();
        String address = "restaurant address";
        String phone = "+79052779090";
        LocalTime openingHoursFrom = LocalTime.of(10, 0);
        LocalTime openingHoursTo = LocalTime.of(22, 0);
        Set<UUID> firstMenu = new HashSet<>();
        Set<UUID> secondMenu = new HashSet<>();
        for (int i = 0; i < 10; ++i) {
            firstMenu.add(UUID.randomUUID());
            secondMenu.add(UUID.randomUUID());
        }

        List<Arguments> arguments = new ArrayList<>();
        for (String newAddress : new String[]{null, address, "restaurant new address"}) {
            for (String newPhone : new String[]{null, phone, "+74052779090"}) {
                for (LocalTime newOpeningHoursFrom : new LocalTime[]{null, openingHoursFrom, LocalTime.of(8, 0)}) {
                    for (LocalTime newOpeningHoursTo : new LocalTime[]{null, openingHoursTo, LocalTime.of(20, 0)}) {
                        for (Set newMenu : new Set[]{null, firstMenu, secondMenu}) {
                            Restaurant restaurant = new Restaurant(id, address, phone, openingHoursFrom, openingHoursTo, firstMenu);

                            RestaurantRepository repository = mock(RestaurantRepository.class);
                            when(repository.findById(id)).thenReturn(Optional.of(restaurant));
                            when(repository.save(Mockito.any(Restaurant.class))).thenAnswer(a -> a.getArguments()[0]);

                            UpdateRestaurantRequest request = new UpdateRestaurantRequest(
                                    newAddress,
                                    newPhone,
                                    newOpeningHoursFrom,
                                    newOpeningHoursTo,
                                    newMenu
                            );

                            Restaurant expected = new Restaurant(
                                    id,
                                    newAddress != null ? newAddress : address,
                                    newPhone != null ? newPhone : phone,
                                    newOpeningHoursFrom != null ? newOpeningHoursFrom : openingHoursFrom,
                                    newOpeningHoursTo != null ? newOpeningHoursTo : openingHoursTo,
                                    newMenu != null ? newMenu : firstMenu
                            );

                            arguments.add(Arguments.of(id, request, repository, expected));
                        }
                    }
                }
            }
        }
        return arguments.stream();
    }
}
