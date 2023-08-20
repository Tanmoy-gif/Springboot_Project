package com.enotes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

//@ComponentScan({"com.enotes.service","com.enotes.repository"})//(exclude = {DataSourceAutoConfiguration.class })

@SpringBootApplication
@EnableJpaAuditing
public class ENotesApplication {

	public static void main(String[] args) {
		SpringApplication.run(ENotesApplication.class, args);
	}

}
