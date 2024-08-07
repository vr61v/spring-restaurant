package provider;

import com.vr61v.cardmicroservice.CardRepository;
import com.vr61v.cardmicroservice.model.Card;
import com.vr61v.cardmicroservice.model.CardType;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CardServiceImplTestsGetAllCardsProvider implements ArgumentsProvider {

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) {
        final String number = "1234123412341234";
        final Float discount = 0.5f;

        List<Card> cardsEmpty = new ArrayList<>();
        List<Card> cardsNotEmpty = new ArrayList<>();
        for (CardType cardType : CardType.values()) {
            cardsNotEmpty.add(new Card(UUID.randomUUID(), UUID.randomUUID(), number, cardType, discount));
        }

        List<Arguments> arguments = new ArrayList<>();
        for (List<Card> cards : List.of(cardsEmpty, cardsNotEmpty)) {
            CardRepository cardRepository = mock(CardRepository.class);
            when(cardRepository.findAll()).thenReturn(cards);
            arguments.add(Arguments.of(cardRepository, cards));
        }

        return arguments.stream();
    }

}
