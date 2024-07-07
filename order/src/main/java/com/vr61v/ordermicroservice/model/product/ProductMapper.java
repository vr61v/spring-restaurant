package com.vr61v.ordermicroservice.model.product;

import com.vr61v.productmicroservice.model.Product;
import com.vr61v.productmicroservice.model.ProductDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductDto entityToDto(Product product);

    Product dtoToEntity(ProductDto productDto);

}
