package com.in28minutes.springboot.jpa;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class UserCommandLineRunner implements CommandLineRunner {

	private static final Logger log = LoggerFactory.getLogger(UserCommandLineRunner.class);

	@Autowired
	private UserRepository repository;

	@Override
	public void run(String... args) {
		// save a couple of customers
		repository.save(new MyUser("Ranga", "Admin"));
		repository.save(new MyUser("Ravi", "User"));
		repository.save(new MyUser("Satish", "Admin"));
		repository.save(new MyUser("Raghu", "User"));

		log.info("-------------------------------");
		log.info("Finding all users");
		log.info("-------------------------------");
		for (MyUser user : repository.findAll()) {
			log.info(user.toString());
		}

		log.info("-------------------------------");
		log.info("Finding all admins");
		log.info("-------------------------------");
		for (MyUser user : repository.findByRole("Admin")) {
			log.info(user.toString());
		}
	}
}
