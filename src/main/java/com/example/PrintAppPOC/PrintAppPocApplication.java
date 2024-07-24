package com.example.PrintAppPOC;


import com.example.PrintAppPOC.Configurations.TwilioConfig;
import com.twilio.Twilio;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;


//Test
@SpringBootApplication
@RequiredArgsConstructor
@OpenAPIDefinition(servers = {@Server(url = "/", description = "Default Server URL")})
public class PrintAppPocApplication {
	private final TwilioConfig twilioConfig;
	public static void main(String[] args) {
		SpringApplication.run(PrintAppPocApplication.class, args);
	}
	@Bean
	public ModelMapper modelMapper (){
		return new ModelMapper();
	}
	@Bean
	public WebMvcConfigurer corsConfigurer()
	{
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**").allowedOrigins("*").allowedMethods("*");
			}
		};
	}
	@PostConstruct
	public void initTwilio(){
		Twilio.init(twilioConfig.getAccountSid(),twilioConfig.getAuthToken());
	}
}
