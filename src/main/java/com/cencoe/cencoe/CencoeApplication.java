package com.cencoe.cencoe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.HashSet;
import java.util.Set;

@SpringBootApplication


//@EnableJpaAuditing
public class CencoeApplication {


	public static void main(String[] args) {

		SpringApplication.run(CencoeApplication.class, args);
	}


}
