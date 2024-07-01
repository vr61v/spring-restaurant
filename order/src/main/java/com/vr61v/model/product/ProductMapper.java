package com.vr61v.model.product;

import com.vr61v.product.ProductDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductDto entityToDto(Product product);

    Product dtoToEntity(ProductDto productDto);

}
