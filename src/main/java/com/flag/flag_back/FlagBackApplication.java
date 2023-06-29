package com.flag.flag_back;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FlagBackApplication {

	public static void main(String[] args) {
		SpringApplication.run(FlagBackApplication.class, args);
	}
}
