package unit.mock;

import com.hanaberia.enums.Categories;
import com.hanaberia.model.Products;

import java.util.Arrays;
import java.util.List;

public class ProductMock {
    
    public static Products mockedProductOne(){
        return Products.builder()
                .id(4L)
                .imageName("one")
                .name("Product One")
                .description("description")
                .price(10)
                .category(Categories.BRACELET)
                .available(true)
                .build();
    }

    public static Products mockedProductTwo(){
        return Products.builder()
                .id(5L)
                .imageName("Two")
                .name("Product Two")
                .description("description")
                .price(10)
                .category(Categories.NECKLACE)
                .reservation(ReservationMock.mockedReservationOne())
                .available(false)
                .build();
    }

    public static Products mockedProductThree(){
        return Products.builder()
                .id(4L)
                .imageName("Three")
                .name("Product Three")
                .description("description")
                .price(10)
                .category(Categories.EARRINGS)
                .order(OrderMock.mockedOrderNotCompleted())
                .available(false)
                .build();
    }

    public static List<Products> productList(){
        return Arrays.asList(mockedProductOne(), mockedProductTwo(), mockedProductThree());
    }
}