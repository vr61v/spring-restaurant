package provider;

import com.vr61v.CardRepository;
import com.vr61v.model.Card;
import com.vr61v.model.CardType;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CardServiceImplTestsGetCardByIdProvider implements ArgumentsProvider {

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) throws Exception {
        final UUID cardId = UUID.randomUUID();
        final UUID userId = UUID.randomUUID();
        final String number = "1234123412341234";
        final Float discount = 0.5f;
        final Card card = new Card(cardId, userId, number, CardType.BRONZE, discount);

        CardRepository cardRepository = mock(CardRepository.class);
        when(cardRepository.findById(cardId)).thenReturn(Optional.of(card));

        List<Arguments> arguments = new ArrayList<>();
        arguments.add(Arguments.of(cardId, cardRepository, card)); // success find then return found card
        arguments.add(Arguments.of(UUID.randomUUID(), cardRepository, null)); // error find then return null

        return arguments.stream();
    }

}
