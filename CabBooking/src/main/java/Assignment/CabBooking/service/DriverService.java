package Assignment.CabBooking.service;

import Assignment.CabBooking.dto.request.CreateDriverRequest;
import Assignment.CabBooking.enums.DriverState;
import Assignment.CabBooking.exceptions.DriverAlreadyExistsException;
import Assignment.CabBooking.exceptions.DriverNotFoundException;
import Assignment.CabBooking.models.Driver;
import Assignment.CabBooking.models.Location;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

@Service
@Getter
public class DriverService {


    public static final int DRIVER_ALLOWED_DISTANCE = 5;

    // List of drivers registered
    List<Driver> driverList;

    public DriverService() {
        this.driverList = new ArrayList<>();
    }

    public Driver addDriver(CreateDriverRequest createDriverRequest) throws DriverAlreadyExistsException {

        // Get the id of the driver.
        int id = driverList.size() + 1;

        Driver driver = new Driver(
                id,
                createDriverRequest.getName(),
                createDriverRequest.getGender(),
                createDriverRequest.getAge(),
                createDriverRequest.getCabDetail(),
                createDriverRequest.getLocation(),
                DriverState.AVAILABLE,
                new Semaphore(1)
        );

        // validation for creating a user
        if (isExistingDriver(driver)) {
            throw new DriverAlreadyExistsException("[DriverServiceError] Error in creating driver, driver already exists");
        }

        driverList.add(driver);

        return driver;
    }

    public List<Driver> getNearestAvailableDrivers(Location location) {
        List<Driver> availableDrivers = new ArrayList<>();
        int driversInDistance = 0;
        for (Driver driver: driverList) {
            if (driver.getLocation().calculateDistance(location) < DRIVER_ALLOWED_DISTANCE) {
                driversInDistance++;
                if (driver.getDriverState().equals(DriverState.AVAILABLE)) {
                    availableDrivers.add(driver);
                }
            }
        }
        if (driversInDistance == 0) {
            return null;
        }
        return availableDrivers;
    }

    private boolean isExistingDriver(Driver driver) {
        for (Driver existingDriver: driverList) {
            if (existingDriver.getAge() == driver.getAge() &&
                    existingDriver.getGender() == driver.getGender() &&
                    existingDriver.getName().equals(driver.getName()) &&
                    existingDriver.getCabDetail().equals(driver.getCabDetail())) {
                return true;
            }
        }
        return false;
    }

    public boolean isDriverAvailable(Driver driver) {
        for(Driver d: driverList) {
            if( d.getCabDetail().equals(driver.getCabDetail()) &&
                    d.getDriverState().equals(DriverState.AVAILABLE)) {
                return true;
            }
        }
        return false;
    }

    public int getDriverIdFromCabDetails(String cabDetails) throws DriverNotFoundException {
        for(Driver d: driverList) {
            if( d.getCabDetail().equals(cabDetails)) {
                return d.getId();
            }
        }
        throw new DriverNotFoundException(String.format("Driver with cab {} does not exist", cabDetails));
    }
}
