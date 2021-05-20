package com.task1.task1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.xml.bind.JAXBException;
import java.io.IOException;


@SpringBootApplication
public class Task1Application {


	public static void main(String[] args) throws JAXBException, IOException {
		SpringApplication.run(Task1Application.class, args);
	}

}
