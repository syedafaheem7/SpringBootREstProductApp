package com.ny.queenscollege.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ny.queenscollege.entity.Product;
import com.ny.queenscollege.exception.ProductNotFoundException;
import com.ny.queenscollege.service.IProductService;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/product")
@Slf4j
public class ProductRestController {
	@Autowired
	private IProductService service;

	//1. save Product
	@PostMapping("/create")
	public ResponseEntity<String> createProduct(
			@RequestBody Product product 
			) 
	{
		Integer id = service.saveProduct(product);
		String message = "Product '"+id+"' created";
		return new ResponseEntity<String>(
				message, 
				HttpStatus.CREATED); 
	}

	//2. fetch all products
	@GetMapping("/all")
	public ResponseEntity<List<Product>> showAllProducts() {
		List<Product> list =  service.findAllProducts();
		return new ResponseEntity<List<Product>>(list,HttpStatus.OK);
	}

	//3. fetch one product
	@GetMapping("/find/{id}")
	public ResponseEntity<?> fetchOneProduct(
			@PathVariable Integer id
			) 
	{
		log.info("ENTERED INTO FETCH ONE METHOD");
		ResponseEntity<?> resp = null;
		try {
			Product prod = service.findOneProduct(id);
			log.debug("DATA IS FOUND AT DB ");
			resp = new ResponseEntity<Product>(prod,HttpStatus.OK);
		} catch (ProductNotFoundException e) {
			e.printStackTrace();
			log.error("Unable to process request {}",e.getMessage());
			throw e; //calls exception handler
		}
		log.info("ABOUT TO LEAVE THIS METHOD");
		return resp;
	}

	//4. delete one product
	@DeleteMapping("/remove/{id}")
	public ResponseEntity<String> deleteProduct(
			@PathVariable Integer id
			) 
	{
		ResponseEntity<String> resp = null;
		try {
			service.deleteProduct(id);
			resp = new ResponseEntity<String>(
					"Product Deleted=>"+id,
					HttpStatus.OK);
		} catch (ProductNotFoundException e) {
			e.printStackTrace();
			throw e; //calls exception handler
		} 
		return resp;
	}


	//5. update one product
	@PutMapping("/modify")
	public ResponseEntity<String> updateProduct(
			@RequestBody Product product
			) 
	{
		ResponseEntity<String> resp = null;
		try {
			service.updateProduct(product);
			resp = new ResponseEntity<String>(
					"Product Updated",
					HttpStatus.OK);
		} catch (ProductNotFoundException e) {
			e.printStackTrace();
			throw e; //calls exception handler
		}
		return resp;
	}

	//6. Patch: Partial Data Update
	@PatchMapping("/update/vendor/{id}/{venName}")
	public ResponseEntity<String> updateProductVendor(
			@PathVariable Integer id,
			@PathVariable String venName
			) 
	{
		ResponseEntity<String> resp = null;
		try {
			service.updateProductVendor(venName, id);
			resp = new ResponseEntity<String>(
					"Product Vendor Updated",
					HttpStatus.OK);
		} catch (ProductNotFoundException e) {
			e.printStackTrace();
			throw e; //calls exception handler
		}
		return resp;
	}

}
