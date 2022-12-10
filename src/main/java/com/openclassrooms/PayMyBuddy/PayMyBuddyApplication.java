package com.openclassrooms.PayMyBuddy;

import com.openclassrooms.PayMyBuddy.model.User;
import com.openclassrooms.PayMyBuddy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Optional;

@SpringBootApplication
public class PayMyBuddyApplication implements CommandLineRunner {

	@Autowired
	private UserService userService;

	public static void main(String[] args) {
		SpringApplication.run(PayMyBuddyApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Optional<User> optUser = userService.findUserByID(1);
		User userId1 = optUser.get();

		System.out.println(userId1.getFirstName());

		userId1.getContacts().forEach(
				contact -> System.out.println(contact.getFirstName()));
	}

}