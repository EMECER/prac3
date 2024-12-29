package micromobility;

import data.GeographicPoint;
import data.StationID;
import data.UserAccount;
import data.VehicleID;

import java.time.LocalDateTime;

public interface JourneyServiceInterface {
    public void setServiceInit(LocalDateTime startTime);
    public void setServiceFinish(GeographicPoint endLocation, LocalDateTime endTime, float distance, float averageSpeed);
    public UserAccount getUser();

    public VehicleID getVehicle();

    public StationID getStation();

    public GeographicPoint getStartLocation();

    public GeographicPoint getEndLocation();

    public LocalDateTime getStartTime();

    public LocalDateTime getEndTime();

    public float getDistance();

    public float getAverageSpeed();

    public int getDuration();

}
