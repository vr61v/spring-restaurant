
/*
 * Tests for:
 * 1. updateRestaurant
 * 2. addProductInRestaurant, addProductsInRestaurant
 * 3. updateProduct
 * 4. updateCategory
 */

import com.vr61v.exceptions.NotFoundException;
import com.vr61v.models.product.Category;
import com.vr61v.models.product.Product;
import com.vr61v.models.restaurant.Restaurant;
import com.vr61v.out.product.Categories;
import com.vr61v.out.product.Products;
import com.vr61v.out.restaurant.Restaurants;
import com.vr61v.out.user.Users;
import com.vr61v.services.AdminServiceImpl;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import providers.admin.TestUpdateProductProvider;
import providers.admin.TestUpdateRestaurantProvider;

import java.sql.Time;
import java.util.*;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AdminServiceImplTests {
    private final UUID id = UUID.fromString("11111111-1111-1111-1111-111111111111");
    private final String phone = "+7-900-200-50-50";
    private final String address = "test street";
    private final Time openingHoursFrom = new Time(9, 0, 0);
    private final Time openingHoursTo = new Time(23, 0, 0);
    private final Set<Product> menu = new HashSet<>();

    private final Restaurants restaurants = mock(Restaurants.class);
    private final Products products = mock(Products.class);
    private final Categories categories = mock(Categories.class);
    private final Users users = mock(Users.class);


    @ParameterizedTest
    @ArgumentsSource(TestUpdateRestaurantProvider.class)
    public void testUpdateRestaurant(String addressUpdate, String phoneUpdate, Time openingHoursFromUpdate, Time openingHoursToUpdate) throws NotFoundException {
        Restaurant old = new Restaurant(id, address, phone, openingHoursFrom, openingHoursTo, menu);
        when(restaurants.find(id)).thenReturn(old);
        AdminServiceImpl service = new AdminServiceImpl(restaurants, products, categories, users);
        Restaurant expected = new Restaurant(id,
                addressUpdate == null ? address : addressUpdate,
                phoneUpdate == null ? phone : phoneUpdate,
                openingHoursFromUpdate == null ? openingHoursFrom : openingHoursFromUpdate,
                openingHoursToUpdate == null ? openingHoursTo : openingHoursToUpdate,
                menu
        );

        Restaurant actual = null;
        try {
            actual = service.updateRestaurant(id, addressUpdate, phoneUpdate, openingHoursFromUpdate, openingHoursToUpdate);
        } catch (IllegalArgumentException e) {
            Assertions.assertEquals("The updated fields must be different from the existing ones.", e.getMessage());
        }
        if (actual != null) {
            Restaurant finalActual = actual;
            Assertions.assertAll(
                    () -> Assertions.assertEquals(expected.getId(), finalActual.getId()),
                    () -> Assertions.assertEquals(expected.getAddress(), finalActual.getAddress()),
                    () -> Assertions.assertEquals(expected.getPhone(), finalActual.getPhone()),
                    () -> Assertions.assertEquals(expected.getOpeningHoursFrom(), finalActual.getOpeningHoursFrom()),
                    () -> Assertions.assertEquals(expected.getOpeningHoursTo(), finalActual.getOpeningHoursTo()),
                    () -> Assertions.assertEquals(expected.getMenu(), finalActual.getMenu())
            );
        }
    }

    @Test
    public void testAddProductInRestaurant() throws NotFoundException {
        Restaurant restaurant = new Restaurant(id, address, phone, openingHoursFrom, openingHoursTo, menu);
        Product product = mock(Product.class);
        when(restaurants.find(id)).thenReturn(restaurant);
        when(products.find(id)).thenReturn(product);
        AdminServiceImpl service = new AdminServiceImpl(restaurants, products, categories, users);
        Set<Product> newMenu = new HashSet<>();
        newMenu.add(product);
        Restaurant expected = new Restaurant(id, address, phone, openingHoursFrom, openingHoursTo, newMenu);

        Restaurant actual = service.addProductInRestaurant(id, id);

        Assertions.assertEquals(expected.getMenu(), actual.getMenu());
    }

    @Test
    public void testAddProductInRestaurantExpectedIllegalArgumentException() throws NotFoundException {
        Product product = mock(Product.class);
        when(product.getId()).thenReturn(id);
        Set<Product> newMenu = new HashSet<>();
        newMenu.add(product);
        Restaurant restaurant = new Restaurant(id, address, phone, openingHoursFrom, openingHoursTo, newMenu);
        when(restaurants.find(id)).thenReturn(restaurant);
        when(products.find(id)).thenReturn(product);
        AdminServiceImpl service = new AdminServiceImpl(restaurants, products, categories, users);

        IllegalArgumentException actual = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            service.addProductInRestaurant(id, id);
        });

        Assertions.assertEquals("The product {" + product.getId() + "} is already on the menu.", actual.getMessage());
    }

    @Test
    public void testAddProductsInRestaurant() throws NotFoundException {
        Restaurant restaurant = new Restaurant(id, address, phone, openingHoursFrom, openingHoursTo, menu);
        Set<Product> newMenu = new HashSet<>();
        List<UUID> productList = new ArrayList<>();
        for (int i = 1; i <= 5; ++i) {
            Product product = mock(Product.class);
            when(products.find(UUID.fromString("11111111-1111-1111-1111-11111111111" + i))).thenReturn(product);
            newMenu.add(product);
            productList.add(UUID.fromString("11111111-1111-1111-1111-11111111111" + i));
        }

        when(restaurants.find(id)).thenReturn(restaurant);
        AdminServiceImpl service = new AdminServiceImpl(restaurants, products, categories, users);
        Restaurant expected = new Restaurant(id, address, phone, openingHoursFrom, openingHoursTo, newMenu);

        Restaurant actual = service.addProductsInRestaurant(id, productList);

        Assertions.assertEquals(expected.getMenu(), actual.getMenu());
    }

    @Test
    public void testAddProductsInRestaurantExpectedIllegalArgumentException() throws NotFoundException {
        Restaurant restaurant = new Restaurant(id, address, phone, openingHoursFrom, openingHoursTo, menu);
        Set<Product> newMenu = new HashSet<>();
        List<UUID> productList = new ArrayList<>();
        for (int i = 1; i <= 5; ++i) {
            Product product = mock(Product.class);
            when(product.getId()).thenReturn(UUID.fromString("11111111-1111-1111-1111-11111111111" + i));
            when(products.find(UUID.fromString("11111111-1111-1111-1111-11111111111" + i))).thenReturn(product);
            newMenu.add(product);
            productList.add(product.getId());
        }
        Product illegalProduct = mock(Product.class);
        when(illegalProduct.getId()).thenReturn(UUID.fromString("11111111-1111-1111-1111-111111111111"));
        newMenu.add(illegalProduct);
        productList.add(illegalProduct.getId());
        when(restaurants.find(id)).thenReturn(restaurant);
        AdminServiceImpl service = new AdminServiceImpl(restaurants, products, categories, users);

        IllegalArgumentException actual = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            service.addProductsInRestaurant(id, productList);
        });

        Assertions.assertEquals("The product {" + illegalProduct.getId() + "} is already on the menu.", actual.getMessage());
    }

    @ParameterizedTest
    @ArgumentsSource(TestUpdateProductProvider.class)
    public void testUpdateProduct(UUID categoryId, Integer price, String name, Integer weight, String composition, String description) throws NotFoundException {
        UUID categoryIdOld = UUID.fromString("22222222-2222-2222-2222-222222222222");
        Integer priceOld = 20;
        String nameOld = "updated";
        Integer weightOld = 20;
        String compositionOld = "something.......";
        String descriptionOld = "something.................";

        Category category = mock(Category.class);
        when(category.getId()).thenReturn(categoryId == null ? categoryIdOld : categoryId);
        Product old = new Product(id, category, nameOld, priceOld, weightOld, compositionOld, descriptionOld);
        when(products.find(id)).thenReturn(old);
        when(categories.find(categoryIdOld)).thenReturn(category);
        AdminServiceImpl service = new AdminServiceImpl(restaurants, products, categories, users);

        Product expected = new Product(id,
                category,
                name == null ? nameOld : name,
                price == null ? priceOld : price,
                weight == null ? weightOld : weight,
                composition == null ? compositionOld : composition,
                description == null ? descriptionOld : description
        );

        Product actual = null;
        try {
            actual = service.updateProduct(id, categoryId, price, name, weight, composition, description);
        } catch (IllegalArgumentException e) {
            Assertions.assertEquals("The updated fields must be different from the existing ones.", e.getMessage());
        }
        if (actual != null) {
            Product finalActual = actual;
            Assertions.assertAll(
                    () -> Assertions.assertEquals(expected.getId(), finalActual.getId()),
                    () -> Assertions.assertEquals(expected.getPrice(), finalActual.getPrice()),
                    () -> Assertions.assertEquals(expected.getName(), finalActual.getName()),
                    () -> Assertions.assertEquals(expected.getWeight(), finalActual.getWeight()),
                    () -> Assertions.assertEquals(expected.getComposition(), finalActual.getComposition()),
                    () -> Assertions.assertEquals(expected.getDescription(), finalActual.getDescription())
            );
        }
    }

    @Test
    public void testUpdateCategory() throws NotFoundException {
        String nameOld = "old";
        String nameUpdate = "update";
        Category category = new Category(id, nameOld);
        when(categories.find(id)).thenReturn(category);
        AdminServiceImpl service = new AdminServiceImpl(restaurants, products, categories, users);

        Category actual = service.updateCategory(id, nameUpdate);

        Assertions.assertEquals(nameUpdate, actual.getName());
    }

    @Test
    public void testUpdateCategoryExpectedIllegalArgumentException() throws NotFoundException {
        String nameOld = "old";
        Category category = new Category(id, nameOld);
        when(categories.find(id)).thenReturn(category);
        AdminServiceImpl service = new AdminServiceImpl(restaurants, products, categories, users);

        IllegalArgumentException actual = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            service.updateCategory(id, nameOld);
        });

        Assertions.assertEquals("The updated fields must be different from the existing ones.", actual.getMessage());
    }
}
