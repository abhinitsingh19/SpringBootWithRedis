package com.springboot.redis.repository;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.springboot.redis.entity.Product;

@Repository
public class ProductDAO 
{
	private static final String HASH_KEY = "Product";
	@Autowired
	private RedisTemplate<String,Object> redisTemplate;
	
	public Product save(Product product)
	{
		redisTemplate.opsForHash().put(HASH_KEY, product.getId(), product);
		return product;
	}
	
	public List<Product> findAll()
	{
		return redisTemplate.opsForHash().values(HASH_KEY)
				.stream()
				.map(obj->(Product)obj)
				.collect(Collectors.toList());
	}
	
	public Product findProductById(int id)
	{
		System.out.println("called from DB");
		return (Product) redisTemplate.opsForHash().get(HASH_KEY,id);
	}
	
	public String deleteProductById(int id)
	{
		 redisTemplate.opsForHash().delete(HASH_KEY,id);
		 return "Product Removed";
	}


}
