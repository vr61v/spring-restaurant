import com.vr61v.CardRepository;
import com.vr61v.CardService;
import com.vr61v.CardServiceImpl;
import com.vr61v.model.Card;
import com.vr61v.model.request.CreateCardRequest;
import com.vr61v.model.request.UpdateCardRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import provider.CardServiceImplTestsCreateCardProvider;
import provider.CardServiceImplTestsGetAllCardsProvider;
import provider.CardServiceImplTestsGetCardByIdProvider;
import provider.CardServiceImplTestsUpdateCardProvider;

import java.util.List;
import java.util.UUID;

public class CardServiceImplTests {

    @ParameterizedTest
    @ArgumentsSource(CardServiceImplTestsCreateCardProvider.class)
    public void testCreateCard(CreateCardRequest request, CardRepository repository, Card expected) {
        CardService cardService = new CardServiceImpl(repository);
        Card card = cardService.createCard(request);
        Assertions.assertEquals(expected.getId(), card.getId());
    }

    @ParameterizedTest
    @ArgumentsSource(CardServiceImplTestsGetCardByIdProvider.class)
    public void testGetCardById(UUID cardId, CardRepository repository, Card expected) {
        CardService cardService = new CardServiceImpl(repository);
        Card actual = cardService.getCardById(cardId);
        Assertions.assertEquals(expected, actual);
    }

    @ParameterizedTest
    @ArgumentsSource(CardServiceImplTestsGetAllCardsProvider.class)
    public void testGetAllCards(CardRepository repository, List<Card> expected) {
        CardService cardService = new CardServiceImpl(repository);
        List<Card> actual = cardService.getAllCards();
        Assertions.assertEquals(expected, actual);
    }

    @ParameterizedTest
    @ArgumentsSource(CardServiceImplTestsUpdateCardProvider.class)
    public void testUpdateCard(UUID cardId, UpdateCardRequest request, CardRepository repository, Card expected, Exception exception) {
        CardService cardService = new CardServiceImpl(repository);
        if (exception != null) {
            Assertions.assertThrows(exception.getClass(), () -> cardService.updateCard(cardId, request));
            return;
        }
        Card actual = cardService.updateCard(cardId, request);
        Assertions.assertEquals(expected, actual);
    }

}
