package Assignment.CabBooking;

import Assignment.CabBooking.controllers.DriverController;
import Assignment.CabBooking.controllers.UserController;
import Assignment.CabBooking.dto.request.ChooseRideRequest;
import Assignment.CabBooking.dto.request.CreateDriverRequest;
import Assignment.CabBooking.dto.request.CreateUserRequest;
import Assignment.CabBooking.dto.request.FindRideRequest;
import Assignment.CabBooking.dto.response.ChooseRideResponse;
import Assignment.CabBooking.dto.response.CreateDriverResponse;
import Assignment.CabBooking.dto.response.CreateUserResponse;
import Assignment.CabBooking.dto.response.FindRideResponse;
import Assignment.CabBooking.enums.Gender;
import Assignment.CabBooking.exceptions.*;
import Assignment.CabBooking.models.Driver;
import Assignment.CabBooking.models.Location;
import Assignment.CabBooking.service.DriverService;
import Assignment.CabBooking.service.UserService;

public class DriverTestClass {
    public static void main(String[] args) throws DriverAlreadyExistsException, UserAlreadyExistsException, UserNotFoundException, DriverNotFoundException, DriverNotAvailableException, InterruptedException {

        // Sample Test Cases:

        // Initializing the services
        DriverService driverService = new DriverService();
        UserService userService = new UserService(driverService);

        UserController userController = new UserController();
        userController.setUserService(userService);

        DriverController driverController = new DriverController();
        driverController.setDriverService(driverService);

        // Onboard 3 users
        System.out.println("------------------------------ONBOARDING TEST CASES-----------------------------  ");

        runUserOnboardingTestCases(userController);
        // Onboard 3 drivers to the application

        System.out.println("\n------------------------------ONBOARDING DRIVER CASES-----------------------------  ");
        runDriverOnboardingTestCases(driverController);
        // User trying to get a ride

        System.out.println("\n------------------------------FINDING RIDE CASES-----------------------------  ");
        runFindRideTestCases(userController);

    }

    private static void runUserOnboardingTestCases(UserController userController) throws UserAlreadyExistsException {

        // Test 1
        CreateUserRequest createUserRequest = new CreateUserRequest(
                "Abhishek",
                Gender.MALE,
                23
        );
        CreateUserResponse createUserResponse = userController.createUser(createUserRequest);

        System.out.println("Adding User with following details: " + createUserRequest.getUserDetailsInString());
        System.out.println("User created with User id: " + createUserResponse.getUserId());
        System.out.println("--------------------------------------------------------------------------------");

        // Test 2
        CreateUserRequest createUserRequest1 = new CreateUserRequest(
                "Rahul",
                Gender.MALE,
                29
        );
        CreateUserResponse createUserResponse1 = userController.createUser(createUserRequest1);

        System.out.println("Adding User with following details: " + createUserRequest1.getUserDetailsInString());
        System.out.println("User created with User id: " + createUserResponse1.getUserId());
        System.out.println("--------------------------------------------------------------------------------");

        // Test 3
        CreateUserRequest createUserRequest2 = new CreateUserRequest(
                "Nandini",
                Gender.FEMALE,
                22
        );
        CreateUserResponse createUserResponse2 = userController.createUser(createUserRequest2);

        System.out.println("Adding User with following details: " + createUserRequest2.getUserDetailsInString());
        System.out.println("User created with User id: " + createUserResponse2.getUserId());
        System.out.println("--------------------------------------------------------------------------------");
    }

    private static void runDriverOnboardingTestCases(DriverController driverController) throws DriverAlreadyExistsException {

        // Test 1
        CreateDriverRequest createDriverRequest = new CreateDriverRequest(
                "Driver1",
                Gender.MALE,
                22,
                "Swift, KA-01-12345",
                new Location(10, 1)
        );
        CreateDriverResponse createDriverResponse = driverController.createDriver(createDriverRequest);

        System.out.println("Adding driver with following details: " + createDriverRequest.getDriverDetailsInString());
        System.out.println("Driver created with driver id: " + createDriverResponse.getDriverId());
        System.out.println("--------------------------------------------------------------------------------");

        // Test 2
        CreateDriverRequest createDriverRequest1 = new CreateDriverRequest(
                "Driver2",
                Gender.MALE,
                29,
                "Swift, KA-01-123456",
                new Location(11, 10)
        );
        CreateDriverResponse createDriverResponse1 = driverController.createDriver(createDriverRequest1);

        System.out.println("Adding driver with following details: " + createDriverRequest1.getDriverDetailsInString());
        System.out.println("Driver created with driver id: " + createDriverResponse1.getDriverId());
        System.out.println("--------------------------------------------------------------------------------");

        // Test 3
        CreateDriverRequest createDriverRequest2 = new CreateDriverRequest(
                "Driver3",
                Gender.MALE,
                24,
                "Swift, KA-01-123457",
                new Location(5, 3)
        );
        CreateDriverResponse createDriverResponse2 = driverController.createDriver(createDriverRequest2);

        System.out.println("Adding driver with following details: " + createDriverRequest2.getDriverDetailsInString());
        System.out.println("Driver created with driver id: " + createDriverResponse2.getDriverId());
        System.out.println("--------------------------------------------------------------------------------");
    }

    private static void runFindRideTestCases(UserController userController) throws UserNotFoundException, DriverNotFoundException, DriverNotAvailableException, InterruptedException {

        Driver chosenDriver = new Driver();
        // Test 1
        FindRideRequest findRideRequest = new FindRideRequest(
                "Abhishek",
                new Location(0,0),
                new Location(20, 1)
        );
        FindRideResponse findRideResponse = userController.findRide(findRideRequest);

        System.out.println(findRideRequest.getUserName() + " is finding a ride");
        System.out.println("FindRide Result: " + findRideResponse.getMessage());
        if (findRideResponse.getDriverList() != null && !findRideResponse.getDriverList().isEmpty()) {
            System.out.println("Following drivers found: ");
            int cnt = 1;
            for(Driver driver: findRideResponse.getDriverList()) {
                System.out.println(cnt + ". " + driver.getName());
                cnt = cnt + 1;
            }
        }

        System.out.println("--------------------------------------------------------------------------------");

        // Test 2
        FindRideRequest findRideRequest1 = new FindRideRequest(
                "Rahul",
                new Location(10,0),
                new Location(15, 3)
        );
        FindRideResponse findRideResponse1 = userController.findRide(findRideRequest1);

        System.out.println(findRideRequest1.getUserName() + " is finding a ride");
        System.out.println("FindRide Result: " + findRideResponse1.getMessage());
        if (findRideResponse1.getDriverList() != null && !findRideResponse1.getDriverList().isEmpty()) {
            System.out.println("Following drivers found: ");
            int cnt = 1;
            for(Driver driver: findRideResponse1.getDriverList()) {
                System.out.println(cnt + ". " + driver.getName());
                cnt = cnt + 1;
                chosenDriver = driver;
            }
        }

        // We are also calling the choose ride functionality to mark the driver as unavailable
        ChooseRideRequest chooseRideRequest = new ChooseRideRequest(findRideRequest1.getUserName(), chosenDriver.getCabDetail());
        ChooseRideResponse chooseRideResponse = userController.chooseRide(chooseRideRequest);
        System.out.println(chooseRideResponse.getMessage());
        System.out.println("--------------------------------------------------------------------------------");

        // Test 3
        FindRideRequest findRideRequest2 = new FindRideRequest(
                "Nandini",
                new Location(10,1),
                new Location(20, 4)
        );
        FindRideResponse findRideResponse2 = userController.findRide(findRideRequest2);

        System.out.println(findRideRequest2.getUserName() + " is finding a ride");
        System.out.println("FindRide Result: " + findRideResponse2.getMessage());
        if (findRideResponse2.getDriverList() != null && !findRideResponse2.getDriverList().isEmpty()) {
            System.out.println("Following drivers found: ");
            int cnt = 1;
            for(Driver driver: findRideResponse2.getDriverList()) {
                System.out.println(cnt + ". " + driver.getName());
            }
        }

        System.out.println("--------------------------------------------------------------------------------");

    }
}
