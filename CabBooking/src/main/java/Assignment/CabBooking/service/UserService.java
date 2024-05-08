package Assignment.CabBooking.service;

import Assignment.CabBooking.dto.request.ChooseRideRequest;
import Assignment.CabBooking.dto.request.CreateUserRequest;
import Assignment.CabBooking.dto.request.FindRideRequest;
import Assignment.CabBooking.enums.DriverState;
import Assignment.CabBooking.exceptions.DriverNotAvailableException;
import Assignment.CabBooking.exceptions.DriverNotFoundException;
import Assignment.CabBooking.exceptions.UserAlreadyExistsException;
import Assignment.CabBooking.exceptions.UserNotFoundException;
import Assignment.CabBooking.models.Driver;
import Assignment.CabBooking.models.User;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
@Getter
public class UserService {

    @Autowired
    DriverService driverService;

    // List of users registered
    List<User> usersList;

    public UserService(DriverService driverService) {
        this.driverService = driverService;
        this.usersList = new ArrayList<>();
    }

    public User createUser(CreateUserRequest createUserRequest) throws UserAlreadyExistsException {

        // Get the id of the user.
        int id = usersList.size() + 1;

        User user = new User(
             id,
             createUserRequest.getName(),
             createUserRequest.getGender(),
             createUserRequest.getAge()
        );

        // validation for creating a user
        if (isExistingUser(user)) {
           throw new UserAlreadyExistsException("[UserServiceError] Error in creating user, user already exists");
        }

        usersList.add(user);

        return user;
    }

    public List<Driver> findRide(FindRideRequest findRideRequest) throws UserNotFoundException {
        boolean userFound = false;
        for(User user : usersList){
            if(findRideRequest.getUserName().equals(user.getName())){
                userFound = true;
                break;
            }
        }
        if(!userFound) {
            throw new UserNotFoundException("User not found while finding a ride");
        }

        return driverService.getNearestAvailableDrivers(findRideRequest.getSource());
    }

    public void chooseRide(ChooseRideRequest chooseRideRequest) throws DriverNotAvailableException, InterruptedException, DriverNotFoundException {
        int driverId = driverService.getDriverIdFromCabDetails(chooseRideRequest.getCabDetail());
        driverService.driverList.get(driverId-1).getLock().acquire();
        if(!driverService.driverList.get(driverId-1).getDriverState().equals(DriverState.AVAILABLE)) {
            throw new DriverNotAvailableException("Driver not available now");
        }
        driverService.driverList.get(driverId-1).setDriverState(DriverState.NOT_AVAILABLE);
        driverService.driverList.get(driverId-1).getLock().release();
    }


    private boolean isExistingUser(User user) {
        for (User existingUser: usersList) {
            if (existingUser.getAge() == user.getAge() &&
                    existingUser.getGender() == user.getGender() &&
                    existingUser.getName().equals(user.getName())) {
                return true;
            }
        }
        return false;
    }
}
