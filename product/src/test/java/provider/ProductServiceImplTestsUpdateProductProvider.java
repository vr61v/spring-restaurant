package provider;

import com.vr61v.ProductRepository;
import com.vr61v.model.Product;
import com.vr61v.model.request.UpdateProductRequest;
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

public class ProductServiceImplTestsUpdateProductProvider implements ArgumentsProvider {
    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) {
        UUID id = UUID.randomUUID();
        String name = "product name";
        Double price = 100.0d;
        Double weight = 100.0d;
        String category = "product category";
        String composition = "product composition";
        String description = "product description";

        List<Arguments> arguments = new ArrayList<>();
        for (String newName : new String[]{null, name, "product new name"}) {
            for (Double newPrice : new Double[]{null, price, 200.0d}) {
                for (Double newWeight : new Double[]{null, weight, 200.0d}) {
                    for (String newCategory : new String[]{null, category, "product new category"}) {
                        for (String newComposition : new String[]{null, composition, "product new composition"}) {
                            for (String newDescription : new String[]{null, description, "product new description"}) {
                                Product product = new Product(id, name, price, weight, category, composition, description);

                                ProductRepository repository = mock(ProductRepository.class);
                                when(repository.findById(id)).thenReturn(Optional.of(product));
                                when(repository.save(Mockito.any(Product.class))).thenAnswer(a -> a.getArguments()[0]);

                                UpdateProductRequest request = new UpdateProductRequest(
                                        newName,
                                        newPrice,
                                        newWeight,
                                        newCategory,
                                        newComposition,
                                        newDescription
                                );
                                Product expected = new Product(
                                        id,
                                        newName != null ? newName : name,
                                        newPrice != null ? newPrice : price,
                                        newWeight != null ? newWeight : weight,
                                        newCategory != null ? newCategory : category,
                                        newComposition != null ? newComposition : composition,
                                        newDescription != null ? newDescription : description
                                );

                                arguments.add(Arguments.of(id, request, repository, expected));
                            }
                        }
                    }
                }
            }
        }
        return arguments.stream();
    }
}
