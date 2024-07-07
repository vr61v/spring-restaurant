package provider;

import com.vr61v.productmicroservice.ProductRepository;
import com.vr61v.productmicroservice.model.Product;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ProductServiceImplTestsGetAllProductsProvider implements ArgumentsProvider {
    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) {
        String name = "product name";
        Double price = 100.0d;
        Double weight = 100.0d;
        String category = "product category";
        String composition = "product composition";
        String description = "product description";

        List<Product> productsEmpty = new ArrayList<>();
        List<Product> productsNotEmpty = new ArrayList<>();
        for (int i = 0; i < 10; ++i) {
            productsNotEmpty.add(new Product(UUID.randomUUID(), name, price, weight, category, composition, description));
        }

        List<Arguments> arguments = new ArrayList<>();
        for (List<Product> products : List.of(productsEmpty, productsNotEmpty)) {
            ProductRepository repository = mock(ProductRepository.class);
            when(repository.findAll()).thenReturn(products);
            arguments.add(Arguments.of(repository, products));
        }

        return arguments.stream();
    }
}
