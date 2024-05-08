package Assignment.CabBooking;

import Assignment.CabBooking.controllers.DriverController;
import Assignment.CabBooking.dto.request.CreateDriverRequest;
import Assignment.CabBooking.dto.response.CreateDriverResponse;
import Assignment.CabBooking.enums.Gender;
import Assignment.CabBooking.exceptions.DriverAlreadyExistsException;
import Assignment.CabBooking.models.Location;
import Assignment.CabBooking.service.DriverService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CabBookingApplication {

	public static void main(String[] args) {
		SpringApplication.run(CabBookingApplication.class, args);
	}

}
