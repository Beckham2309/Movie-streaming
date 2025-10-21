package com.ghostman.movie_streaming_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableDiscoveryClient
public class MovieStreamingServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MovieStreamingServiceApplication.class, args);
	}

}
