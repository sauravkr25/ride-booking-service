package com.shareride.ridebooking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class RideBookingServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(RideBookingServiceApplication.class, args);
	}

}
