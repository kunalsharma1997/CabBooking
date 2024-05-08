package Assignment.CabBooking.controllers;

import Assignment.CabBooking.dto.request.ChooseRideRequest;
import Assignment.CabBooking.dto.request.CreateUserRequest;
import Assignment.CabBooking.dto.request.FindRideRequest;
import Assignment.CabBooking.dto.response.ChooseRideResponse;
import Assignment.CabBooking.dto.response.CreateUserResponse;
import Assignment.CabBooking.dto.response.FindRideResponse;
import Assignment.CabBooking.exceptions.DriverNotAvailableException;
import Assignment.CabBooking.exceptions.DriverNotFoundException;
import Assignment.CabBooking.exceptions.UserAlreadyExistsException;
import Assignment.CabBooking.exceptions.UserNotFoundException;
import Assignment.CabBooking.models.Driver;
import Assignment.CabBooking.models.User;
import Assignment.CabBooking.service.UserService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static Assignment.CabBooking.service.DriverService.DRIVER_ALLOWED_DISTANCE;

@RestController
@Setter
@Getter
@RequestMapping(value = "/v1/user")
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping(value = "/add")
    public CreateUserResponse createUser(@RequestBody CreateUserRequest createUserRequest) throws UserAlreadyExistsException {
        User user = userService.createUser(createUserRequest);
        return new CreateUserResponse(user.getId());
    }

    @GetMapping(value = "/findRide")
    public FindRideResponse findRide(@RequestBody FindRideRequest findRideRequest) throws UserNotFoundException {
        List<Driver> driverList = userService.findRide(findRideRequest);
        FindRideResponse findRideResponse = new FindRideResponse();
        if(driverList == null) {
            findRideResponse.setMessage("No ride found. Since all the drivers are more than" + DRIVER_ALLOWED_DISTANCE + " away");
        }
        else if (driverList.isEmpty()) {
            findRideResponse.setMessage("No ride found. Since all the drivers are UNAVAILABLE.");
        } else {
            findRideResponse.setMessage("Drivers available");
            findRideResponse.setDriverList(driverList);
        }
        return findRideResponse;
    }

    @PostMapping(value = "/chooseRide")
    public ChooseRideResponse chooseRide(@RequestBody ChooseRideRequest chooseRideRequest) throws DriverNotFoundException, DriverNotAvailableException, InterruptedException {
        userService.chooseRide(chooseRideRequest);
        String successMsg = String.format(" %s is booked and arriving at your location", chooseRideRequest.getCabDetail());
        ChooseRideResponse chooseRideResponse = new ChooseRideResponse(successMsg);
        return chooseRideResponse;
    }

}
