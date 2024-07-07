import com.vr61v.productmicroservice.ProductRepository;
import com.vr61v.productmicroservice.ProductService;
import com.vr61v.productmicroservice.ProductServiceImpl;
import com.vr61v.productmicroservice.model.Product;
import com.vr61v.productmicroservice.model.request.CreateProductRequest;
import com.vr61v.productmicroservice.model.request.UpdateProductRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.mockito.Mockito;
import provider.ProductServiceImplTestsGetAllProductsProvider;
import provider.ProductServiceImplTestsGetProductByIdProvider;
import provider.ProductServiceImplTestsUpdateProductProvider;

import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ProductServiceImplTests {

    @Test
    public void testCreateProduct() {
        String name = "product name";
        Double price = 100.0d;
        Double weight = 100.0d;
        String category = "product category";
        String composition = "product composition";
        String description = "product description";
        CreateProductRequest request = new CreateProductRequest(name, price, weight, category, composition, description);
        ProductRepository repository = mock(ProductRepository.class);
        when(repository.save(Mockito.any(Product.class))).thenAnswer(actual -> actual.getArguments()[0]);
        ProductService service = new ProductServiceImpl(repository);

        Product expected = new Product(null, name, price, weight, category, composition, description);
        Product actual = service.createProduct(request);

        Assertions.assertNotNull(actual.getId());
        actual.setId(null);
        Assertions.assertEquals(expected, actual);
    }

    @ParameterizedTest
    @ArgumentsSource(ProductServiceImplTestsGetProductByIdProvider.class)
    public void testGetProductById(UUID productId, ProductRepository repository, Product expected) {
        ProductService service = new ProductServiceImpl(repository);
        Product actual = service.getProductById(productId);
        Assertions.assertEquals(expected, actual);
    }

    @ParameterizedTest
    @ArgumentsSource(ProductServiceImplTestsGetAllProductsProvider.class)
    public void testGetAllProducts(ProductRepository repository, List<Product> expected) {
        ProductService service = new ProductServiceImpl(repository);
        List<Product> actual = service.getAllProducts();
        Assertions.assertEquals(expected, actual);
    }

    @ParameterizedTest
    @ArgumentsSource(ProductServiceImplTestsUpdateProductProvider.class)
    public void testUpdateProduct(UUID productId, UpdateProductRequest request, ProductRepository repository, Product expected) {
        ProductService service = new ProductServiceImpl(repository);
        Product actual = service.updateProduct(productId, request);
        Assertions.assertEquals(expected, actual);
    }
}
