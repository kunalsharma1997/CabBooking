package Assignment.CabBooking.dto.request;

import Assignment.CabBooking.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CreateUserRequest {

    private String  name;
    private Gender  gender;
    private Integer age;

    public String getUserDetailsInString() {
        return "\nUser Name: " + this.name + "\nGender: " + this.gender + "\nAge: " + this.age;
    }
}
