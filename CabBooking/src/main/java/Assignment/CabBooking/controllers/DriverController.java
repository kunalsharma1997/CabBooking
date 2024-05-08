package Assignment.CabBooking.controllers;

import Assignment.CabBooking.dto.request.CreateDriverRequest;
import Assignment.CabBooking.dto.response.CreateDriverResponse;
import Assignment.CabBooking.exceptions.DriverAlreadyExistsException;
import Assignment.CabBooking.models.Driver;
import Assignment.CabBooking.service.DriverService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;



@RestController
@Getter
@Setter
@RequestMapping(value = "/v1/driver")
public class DriverController {
    @Autowired
    DriverService driverService;

    @PostMapping(value = "/add")
    public CreateDriverResponse createDriver(@RequestBody CreateDriverRequest createDriverRequest) throws DriverAlreadyExistsException {
        Driver driver = driverService.addDriver(createDriverRequest);
        return new CreateDriverResponse(driver.getId());
    }

}
