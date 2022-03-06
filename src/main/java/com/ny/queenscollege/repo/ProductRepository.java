package com.ny.queenscollege.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.ny.queenscollege.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {

	@Modifying
	@Query("UPDATE Product SET prodVendor=:vendor WHERE prodId=:id")
	int updateProductVendor(String vendor,Integer id);
}
