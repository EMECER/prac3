// Paquete micromobility: Implementación del caso de uso Realizar desplazamiento
package micromobility;

import data.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Clase JourneyService
 * Representa un trayecto realizado en el sistema de micromovilidad.
 */
public class JourneyService {
    // Atributos según el Modelo del Dominio
    private UserAccount user;
    private VehicleID vehicle;
    private StationID station;
    private GeographicPoint startLocation;
    private GeographicPoint endLocation;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private float averageSpeed;
    private float distance;
    private int duration;

    /**
     * Constructor principal de JourneyService
     * @param user El usuario asociado al trayecto
     * @param vehicle El vehículo utilizado
     * @param station La estación de inicio
     * @param startLocation La ubicación inicial del trayecto
     */
    public JourneyService(UserAccount user, VehicleID vehicle, StationID station, GeographicPoint startLocation) {
        this.user = user;
        this.vehicle = vehicle;
        this.station = station;
        this.startLocation = startLocation;
    }

    /**
     * Inicializa el servicio con la hora de inicio
     * @param startTime La hora de inicio del trayecto
     */
    public void setServiceInit(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    /**
     * Finaliza el servicio registrando detalles del trayecto
     * @param endLocation La ubicación final del trayecto
     * @param endTime La hora de finalización del trayecto
     * @param distance La distancia recorrida en metros
     * @param averageSpeed La velocidad promedio en m/s
     */
    public void setServiceFinish(GeographicPoint endLocation, LocalDateTime endTime, float distance, float averageSpeed) {
        this.endLocation = endLocation;
        this.endTime = endTime;
        this.distance = distance;
        this.averageSpeed = averageSpeed;
        this.duration = (int) java.time.Duration.between(startTime, endTime).toMinutes();
    }

    // Métodos getter
    public UserAccount getUser() {
        return user;
    }

    public VehicleID getVehicle() {
        return vehicle;
    }

    public StationID getStation() {
        return station;
    }

    public GeographicPoint getStartLocation() {
        return startLocation;
    }

    public GeographicPoint getEndLocation() {
        return endLocation;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public float getDistance() {
        return distance;
    }

    public float getAverageSpeed() {
        return averageSpeed;
    }

    public int getDuration() {
        return duration;
    }
}
