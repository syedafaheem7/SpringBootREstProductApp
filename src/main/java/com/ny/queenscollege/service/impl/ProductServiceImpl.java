package com.ny.queenscollege.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ny.queenscollege.entity.Product;
import com.ny.queenscollege.exception.ProductNotFoundException;
import com.ny.queenscollege.repo.ProductRepository;
import com.ny.queenscollege.service.IProductService;
@Service
public class ProductServiceImpl implements IProductService {

	@Autowired
	private ProductRepository repo;
	
	@Override
	public Integer saveProduct(Product p) {
		p = repo.save(p);
		return p.getProdId();
	}

	@Override
	public List<Product> findAllProducts() {
		List<Product> list = repo.findAll();
		return list;
	}

	@Override
	public Product findOneProduct(Integer id) {
		return repo.findById(id)
				.orElseThrow(
						()->new ProductNotFoundException("Product Not exist with id "+id)
						);
	}

	@Override
	public void deleteProduct(Integer id) {
		//repo.deleteById(id);
		repo.delete(findOneProduct(id));

	}

	@Override
	public void updateProduct(Product p) {
		if(p.getProdId()!=null && 
				repo.existsById(p.getProdId()) ) {
			repo.save(p);
		} else {
			throw new ProductNotFoundException("Product Not exist with id "+p.getProdId());
		}

	}
	
	@Transactional
	public int updateProductVendor(String vendor, Integer id) {
		if(id!=null && 
				repo.existsById(id) ) {
			return repo.updateProductVendor(vendor, id);
		}
		else {
			throw new ProductNotFoundException("Product Not exist with id "+id);
		}
	}


}
