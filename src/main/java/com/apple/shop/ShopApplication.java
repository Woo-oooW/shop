package com.apple.shop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.SQLOutput;

@SpringBootApplication
public class ShopApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShopApplication.class, args);

		Friend friend = new Friend("park");

		System.out.println(friend.name);

	}
}

class Friend {
	String name = "sung";
	int age = 25;
	Friend(String F_name){
		this.name = F_name;
	}
}