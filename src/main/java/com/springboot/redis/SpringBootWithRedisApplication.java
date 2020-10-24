package com.springboot.redis;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.redis.entity.Product;
import com.springboot.redis.repository.ProductDAO;

@SpringBootApplication
@RestController
@RequestMapping("/product")
@EnableCaching
public class SpringBootWithRedisApplication 
{

	public static void main(String[] args) 
	{
		SpringApplication.run(SpringBootWithRedisApplication.class, args);
	}
	
	@Autowired
	private ProductDAO productDAO;
	
	@PostMapping
	private Product save(@RequestBody Product product)
	{
		return productDAO.save(product);
	}
	
	@GetMapping
	private List<Product> getAllProducts()
	{
		return productDAO.findAll();
	}
	
	@GetMapping("/{id}")
	@Cacheable(key="#Product.id",value="Product",unless ="#result.price>1000")
	private Product findProduct(@PathVariable int id)
	{
		return productDAO.findProductById(id);
	}
	
	@DeleteMapping("/{id}")
	private String deleteProduct(@PathVariable int id)
	{
		return productDAO.deleteProductById(id);
	}
	

}
