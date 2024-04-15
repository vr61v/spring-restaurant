package providers.admin;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

public class TestUpdateRestaurantProvider implements ArgumentsProvider {
    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) throws Exception {
        String phone = "+7-900-200-50-50";
        String address = "test street";
        Time openingHoursFrom = new Time(9, 0, 0);
        Time openingHoursTo = new Time(23, 0, 0);

        String phoneUpdate = "+7-911-211-51-51";
        String addressUpdate = "test street update";
        Time openingHoursFromUpdate = new Time(9, 1, 1);
        Time openingHoursToUpdate = new Time(23, 1, 1);

        List<Arguments> arguments = new ArrayList<>();
        for (String ph : new String[] {phoneUpdate, phone, null}){
            for (String add : new String[] {addressUpdate, addressUpdate, null}){
                for (Time ohf : new Time[] {openingHoursFromUpdate, openingHoursFrom, null}){
                    for (Time oht : new Time[] {openingHoursToUpdate, openingHoursTo, null}) {
                        arguments.add(Arguments.of(ph, add, ohf, oht));
                    }
                }
            }
        }

        return arguments.stream();
    }
}
