package Assignment.CabBooking.dto.response;

import Assignment.CabBooking.models.Driver;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class FindRideResponse {

    private String message;
    List<Driver> driverList;
}
