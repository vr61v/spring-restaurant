package provider;

import com.vr61v.cardmicroservice.CardRepository;
import com.vr61v.cardmicroservice.model.Card;
import com.vr61v.cardmicroservice.model.CardType;
import com.vr61v.cardmicroservice.model.request.UpdateCardRequest;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CardServiceImplTestsUpdateCardProvider implements ArgumentsProvider {

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) {
        UUID cardId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();
        String number = "1234123412341234";
        CardType cardType = CardType.BRONZE;
        Float discount = 0.5f;

        List<Arguments> arguments = new ArrayList<>();
        for (UUID newUserId : new UUID[]{null, userId, UUID.randomUUID()}) {
            for (String newNumber : new String[]{null, number, "2345234523452345"}) {
                for (CardType newCardType : CardType.values()) {
                    for (Float newDiscount : new Float[]{null, discount, 0.6f}) {
                        Card card = new Card(cardId, userId, number, cardType, discount);

                        CardRepository repository = mock(CardRepository.class);
                        when(repository.findById(cardId)).thenReturn(Optional.of(card));
                        when(repository.save(Mockito.any(Card.class))).thenAnswer(a -> a.getArguments()[0]);

                        UpdateCardRequest request = new UpdateCardRequest(
                                newUserId,
                                newNumber,
                                newCardType,
                                newDiscount
                        );

                        Card expected = new Card(
                                cardId,
                                newUserId != null ? newUserId : userId,
                                newNumber != null ? newNumber : number,
                                newCardType != null ? newCardType : cardType,
                                newDiscount != null ? newDiscount : discount
                        );

                        arguments.add(Arguments.of(cardId, request, repository, expected));
                    }
                }
            }
        }
        return arguments.stream();
    }
}
