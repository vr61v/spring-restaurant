package provider;

import com.vr61v.cardmicroservice.CardRepository;
import com.vr61v.cardmicroservice.model.Card;
import com.vr61v.cardmicroservice.model.CardType;
import com.vr61v.cardmicroservice.model.request.CreateCardRequest;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CardServiceImplTestsCreateCardProvider implements ArgumentsProvider {

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) {
        UUID userId = UUID.randomUUID();
        String number = "1234123412341234";
        Float discount = 0.5f;

        List<Arguments> arguments = new ArrayList<>();
        for (CardType type : CardType.values()) {
            CardRepository repository = mock(CardRepository.class);
            CreateCardRequest request = new CreateCardRequest(userId, number, type, discount);
            Card expected = new Card(null, userId, number, type, discount); // null because id generated value in postgres
            when(repository.save(Mockito.any(Card.class))).thenAnswer(actual -> actual.getArguments()[0]);
            arguments.add(Arguments.of(request, repository, expected));
        }
        return arguments.stream();
    }

}
