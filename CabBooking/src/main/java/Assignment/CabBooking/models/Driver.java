package Assignment.CabBooking.models;

import Assignment.CabBooking.enums.DriverState;
import Assignment.CabBooking.enums.Gender;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.concurrent.Semaphore;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Driver {

    private int id;
    private String name;
    private Gender gender;
    private int age;
    private String cabDetail;
    private Location location;
    private DriverState driverState;
    @JsonIgnore
    private Semaphore lock;
}
