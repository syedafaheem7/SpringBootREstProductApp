package com.ny.queenscollege.service;

import java.util.List;

import com.ny.queenscollege.entity.Product;

public interface IProductService {
	Integer saveProduct(Product p);
	List<Product> findAllProducts();
	Product findOneProduct(Integer id);
	void deleteProduct(Integer id);
	void updateProduct(Product p);
	int updateProductVendor(String vendor,Integer id);

}
