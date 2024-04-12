package providers.user;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

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
        
        return Stream.of(
                Arguments.of(nameUpdate, phoneUpdate, emailUpdate),
                Arguments.of(nameUpdate, phoneUpdate, null),
                Arguments.of(nameUpdate, null, emailUpdate),
                Arguments.of(nameUpdate, null, null),
                Arguments.of(null, phoneUpdate, emailUpdate),
                Arguments.of(null, phoneUpdate, null),
                Arguments.of(null, null, emailUpdate),
                Arguments.of(null, null, null),

                Arguments.of(name, phone, email),
                Arguments.of(name, phone, null),
                Arguments.of(name, null, email),
                Arguments.of(name, null, null),
                Arguments.of(null, phone, email),
                Arguments.of(null, phone, null),
                Arguments.of(null, null, email),
                Arguments.of(null, null, null)
        );
    }
}
