//package com.besign.contests.hashCode;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
//import org.springframework.transaction.annotation.EnableTransactionManagement;
//
//import javax.annotation.PostConstruct;
//import java.io.IOException;
//
//@SpringBootApplication
//@Configuration
//@EnableTransactionManagement
//@EnableJpaRepositories(
//        basePackages = {"com.besign.contests.hashCode.persistence"}
//)
//public class HashCodeApplication {
//
//	public static void main(String[] args) {
//		SpringApplication.run(HashCodeApplication.class, args);
//	}
//
//	@Autowired
//    private GoogleHashPractice2020 googleHashPractice2020;
//
//	@PostConstruct
//    public void practice2020() throws IOException {
//	    googleHashPractice2020.solve();
//    }
//
//}
