package Assignment.CabBooking.models;

import Assignment.CabBooking.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class User {

    private int id;
    private String name;
    private Gender gender;
    private int age;
}
