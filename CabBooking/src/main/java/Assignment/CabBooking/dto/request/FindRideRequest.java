package Assignment.CabBooking.dto.request;

import Assignment.CabBooking.models.Location;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FindRideRequest {

    private String userName;
    private Location source;
    private Location destination;
}
