package com.springbackend.webservice.config;

import java.time.Instant;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.springbackend.webservice.entities.Category;
import com.springbackend.webservice.entities.Order;
import com.springbackend.webservice.entities.OrderItem;
import com.springbackend.webservice.entities.Payment;
import com.springbackend.webservice.entities.Permission;
import com.springbackend.webservice.entities.Product;
import com.springbackend.webservice.entities.User;
import com.springbackend.webservice.entities.enums.OrderStatus;
import com.springbackend.webservice.repositories.CategoryRepository;
import com.springbackend.webservice.repositories.OrderItemRepository;
import com.springbackend.webservice.repositories.OrderRepository;
import com.springbackend.webservice.repositories.PermissionRepository;
import com.springbackend.webservice.repositories.ProductRepository;
import com.springbackend.webservice.repositories.UserRepository;

@Configuration
@Profile("test")
public class TestConfig implements CommandLineRunner {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private OrderItemRepository orderItemRepository;
	
	@Autowired
	private PermissionRepository permissionRepository;
	
	@Autowired
    private PasswordEncoder passwordEncoder;
	
	 
	@Override
	public void run(String... args) throws Exception {
		
		Category cat1 = new Category(null, "Electronics"); 
		Category cat2 = new Category(null, "Books"); 
		Category cat3 = new Category(null, "Computers"); 
 		Product p1 = new Product(null, "The Lord of the Rings", "Lorem ipsum dolor sit amet, consectetur.", 90.5, ""); 
		Product p2 = new Product(null, "Smart TV", "Nulla eu imperdiet purus. Maecenas ante.", 2190.0, ""); 
		Product p3 = new Product(null, "Macbook Pro", "Nam eleifend maximus tortor, at mollis.", 1250.0, ""); 
		Product p4 = new Product(null, "PC Gamer", "Donec aliquet odio ac rhoncus cursus.", 1200.0, ""); 
		Product p5 = new Product(null, "Rails for Dummies", "Cras fringilla convallis sem vel faucibus.", 100.99, ""); 

		categoryRepository.saveAll(Arrays.asList(cat1, cat2, cat3));
		productRepository.saveAll(Arrays.asList(p1, p2, p3, p4, p5));
		
		// Adding to tb_category_product
		p1.getCategories().add(cat2);
		p2.getCategories().add(cat1);
		p2.getCategories().add(cat3);
		p3.getCategories().add(cat3);
		p4.getCategories().add(cat3);
		p5.getCategories().add(cat2);
		
		// Saving again
		productRepository.saveAll(Arrays.asList(p1, p2, p3, p4, p5));
		
		Permission perm1 = new Permission(null, "ADMIN");
		
		permissionRepository.saveAll(Arrays.asList(perm1));
		
		
		String encryptedPassword = passwordEncoder.encode("12345");

		User u1 = new User(null, "maria", "Maria Bowen", "maria@gmail.com", "988888888", encryptedPassword, 
                true, true, true, true, Arrays.asList(perm1)); 
        User u2 = new User(null, "alex", "Alex Green", "alex@gmail.com", "977777777", encryptedPassword, 
                true, true, true, true, Arrays.asList(perm1)); 
        User u3 = new User(null, "john", "John Doe", "john@gmail.com", "977777799", encryptedPassword, 
                true, true, true, true, Arrays.asList(perm1)); 
		Order o1 = new Order(null, Instant.parse("2019-06-20T19:53:07Z"), u1, OrderStatus.PAID); 
		Order o2 = new Order(null, Instant.parse("2019-07-21T03:42:10Z"), u2, OrderStatus.WAITING_PAYMENT); 
		Order o3 = new Order(null, Instant.parse("2019-07-22T15:21:22Z"), u1, OrderStatus.WAITING_PAYMENT); 
		
		userRepository.saveAll(Arrays.asList(u1, u2, u3));
		orderRepository.saveAll(Arrays.asList(o1, o2, o3));
		
		OrderItem oi1 = new OrderItem(o1, p1, 2, p1.getPrice());
		OrderItem oi2 = new OrderItem(o1, p3, 1, p3.getPrice());
		OrderItem oi3 = new OrderItem(o2, p3, 2, p3.getPrice());
		OrderItem oi4 = new OrderItem(o3, p5, 2, p5.getPrice());

		orderItemRepository.saveAll(Arrays.asList(oi1, oi2, oi3, oi4));	
		
		Payment pay1 = new Payment(null, Instant.parse("2019-06-20T19:53:07Z"), o1);
		o1.setPayment(pay1);
		
		orderRepository.save(o1);
	}
}
