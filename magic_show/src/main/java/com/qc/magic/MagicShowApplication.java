package com.qc.magic;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.qc.magic.dao")
public class MagicShowApplication {

	public static void main(String[] args) {
		SpringApplication.run(MagicShowApplication.class, args);
	}
	
}
