import com.vr61v.exceptions.NotFoundException;
import com.vr61v.models.product.Category;
import com.vr61v.out.adapters.product.CategoriesRepositoryAdapter;
import com.vr61v.out.entities.product.CategoryEntity;
import com.vr61v.out.entities.product.mappers.CategoryMapperImpl;
import com.vr61v.out.repositories.product.CategoriesRepository;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TestClass {
    @Test
    public void test() {
        CategoryMapperImpl mapper = new CategoryMapperImpl();
        UUID id = UUID.fromString("11111111-1111-1111-1111-111111111111");

        CategoriesRepository repository = mock(CategoriesRepository.class);
        CategoryEntity entity = new CategoryEntity(id, "test");
        when(repository.findById(id)).thenReturn(Optional.of(entity));

        CategoriesRepositoryAdapter adapter = new CategoriesRepositoryAdapter(repository, mapper);

        Category categories = null;
        try {
            categories = adapter.find(id);
        } catch (NotFoundException e) {
            throw new RuntimeException(e);
        }

        System.out.println(categories.getId() + categories.getName());
    }
}
