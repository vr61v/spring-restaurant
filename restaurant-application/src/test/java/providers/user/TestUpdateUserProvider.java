package providers.user;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class TestUpdateUserProvider implements ArgumentsProvider {
    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) throws Exception {
        String name = "I am test user";
        String phone = "+7-900-200-50-50";
        String email = "something@gmail.com";

        String nameUpdate = "I am test user update";
        String phoneUpdate = "+7-911-211-51-51";
        String emailUpdate = "updated@gmail.com";

        List<Arguments> arguments = new ArrayList<>();
        for (String n : new String[] {nameUpdate, name, null}){
            for (String ph : new String[] {phoneUpdate, phone, null}){
                for (String em : new String[] {emailUpdate, email, null}){
                    arguments.add(Arguments.of(n, ph, em));
                }
            }
        }

        return arguments.stream();
    }
}
