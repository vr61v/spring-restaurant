package providers.admin;

import net.bytebuddy.asm.MemberSubstitution;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

public class TestUpdateProductProvider implements ArgumentsProvider {
    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) throws Exception {
        UUID productCategoryIdUpdate = UUID.fromString("11111111-1111-1111-1111-111111111111");
        Integer priceUpdate = 100;
        String nameUpdate = "updated";
        Integer weightUpdate = 100;
        String compositionUpdate = "something update.......";
        String descriptionUpdate = "something update.................";

        UUID productCategoryId = UUID.fromString("22222222-2222-2222-2222-222222222222");
        Integer price = 20;
        String name = "updated";
        Integer weight = 20;
        String composition = "something.......";
        String description = "something.................";

        List<Arguments> arguments = new ArrayList<>();
        for (UUID id : new UUID[] {productCategoryIdUpdate, productCategoryId, null}){
            for (Integer p : new Integer[] {priceUpdate, price, null}){
                for (String n : new String[] {nameUpdate, name, null}){
                    for (Integer w : new Integer[] {weightUpdate, weight, null}) {
                        for (String c : new String[]{compositionUpdate, composition, null}) {
                            for (String d : new String[] {descriptionUpdate, description, null}) {
                                arguments.add(Arguments.of(id, p, n, w, c, d));
                            }
                        }
                    }
                }
            }
        }

        return arguments.stream();
    }
}
