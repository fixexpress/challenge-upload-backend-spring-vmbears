package com.vmbears.challenge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;

@SpringBootApplication
@OpenAPIDefinition(
		info =@Info(title="Vmbears File Upload Challenge", version ="1.0", description="BackEnd Challenge API"),
		servers = {
				@Server(url = "http://localhost:8080")
		}
)
public class VmbearsBackEndApplication {

	public static void main(String[] args) {
		SpringApplication.run(VmbearsBackEndApplication.class, args);
	}

}
