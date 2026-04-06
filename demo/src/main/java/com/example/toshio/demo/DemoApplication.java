package com.example.toshio.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {

	@org.springframework.beans.factory.annotation.Value("${client.baseUrl}")
	private String clientBaseUrl;

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@org.springframework.context.annotation.Bean
	public com.example.toshio.client.api.DefaultApi defaultApi() {
		com.example.toshio.client.api.DefaultApi api = new com.example.toshio.client.api.DefaultApi();
		api.getApiClient().setBasePath(clientBaseUrl);
		return api;
	}

}
