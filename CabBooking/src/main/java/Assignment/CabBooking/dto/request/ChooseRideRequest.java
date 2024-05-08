package Assignment.CabBooking.dto.request;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@AllArgsConstructor
public class ChooseRideRequest {
    private String userName;
    private String cabDetail;
}
