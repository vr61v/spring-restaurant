package provider;

import com.vr61v.ProductRepository;
import com.vr61v.model.Product;
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

public class ProductServiceImplTestsGetProductByIdProvider implements ArgumentsProvider {
    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) throws Exception {
        UUID id = UUID.randomUUID();
        String name = "product name";
        Double price = 100.0d;
        Double weight = 100.0d;
        String category = "product category";
        String composition = "product composition";
        String description = "product description";
        Product product = new Product(id, name, price, weight, category, composition, description);

        ProductRepository repository = mock(ProductRepository.class);
        when(repository.findById(id)).thenReturn(Optional.of(product));

        List<Arguments> arguments = new ArrayList<>();
        arguments.add(Arguments.of(id, repository, product)); // success find then return found card
        arguments.add(Arguments.of(UUID.randomUUID(), repository, null)); // error find then return null

        return arguments.stream();
    }
}
