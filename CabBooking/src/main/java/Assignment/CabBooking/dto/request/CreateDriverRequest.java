package Assignment.CabBooking.dto.request;

import Assignment.CabBooking.enums.DriverState;
import Assignment.CabBooking.enums.Gender;
import Assignment.CabBooking.models.Location;
import lombok.*;

@Data
@AllArgsConstructor
public class CreateDriverRequest {

    private String name;
    private Gender gender;
    private int age;
    private String cabDetail;
    private Location location;
    private DriverState driverState;

    public CreateDriverRequest(String name, Gender gender, int age, String cabDetail, Location location) {
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.cabDetail = cabDetail;
        this.location = location;
    }

    public String getDriverDetailsInString() {
        return "\nDriver Name: " + this.name + "\nGender: " + this.gender + "\nAge: " + this.age + "\nCab Detail: " + this.cabDetail;
    }
}
