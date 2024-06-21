package provider;

import com.vr61v.CardRepository;
import com.vr61v.CardService;
import com.vr61v.model.Card;
import com.vr61v.model.CardType;
import com.vr61v.model.request.CreateCardRequest;
import com.vr61v.model.request.UpdateCardRequest;
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
    public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) throws Exception {
        UUID cardId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();
        String number = "1234123412341234";
        CardType type = CardType.BRONZE;
        Float discount = 0.5f;

        Card card = new Card(cardId, userId, number, type, discount);
        CardRepository repository = mock(CardRepository.class);
        when(repository.findById(cardId)).thenReturn(Optional.of(card));
        when(repository.save(Mockito.any(Card.class))).thenAnswer(actual -> actual.getArguments()[0]);

        UUID newUserId = UUID.randomUUID();
        String newNumber = "2345234523452345";
        CardType newType = CardType.SILVER;
        Float newDiscount = 0.7f;

        UpdateCardRequest successUpdateCard = new UpdateCardRequest(newUserId, newNumber, newType, newDiscount);
        UpdateCardRequest commonFieldsUpdateCard = new UpdateCardRequest(userId, number, type, discount);
        Card expectedUpdateCard = new Card(cardId, newUserId, newNumber, newType, newDiscount);
        Card expectedCommonFieldsUpdateCard = new Card(cardId, userId, number, type, discount);

        List<Arguments> arguments = new ArrayList<>();
        arguments.add(Arguments.of(cardId, successUpdateCard, repository, expectedUpdateCard, null)); // success update card
        arguments.add(Arguments.of(cardId, commonFieldsUpdateCard, repository, expectedCommonFieldsUpdateCard, null)); // success update card
        arguments.add(Arguments.of(UUID.randomUUID(), successUpdateCard, repository, null, new IllegalArgumentException())); // error illegal argument exception
        return arguments.stream();
    }
}
