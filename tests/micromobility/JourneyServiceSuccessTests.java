package micromobility;

import data.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import services.exceptions.NotCorrectFormatException;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class JourneyServiceSuccessTests {

    private JourneyService journeyService;
    private UserAccount testUser;
    private PMVInterface testVehicle;

    @BeforeEach
    public void setUp() throws NotCorrectFormatException {
        testUser = new UserAccount("testUser");
        testVehicle = new PMVehicleDoble();
        journeyService = new JourneyService(testUser, testVehicle);
    }

    @Test
    public void testJourneyInitialization() {
        assertEquals(testUser, journeyService.getUser(), "El usuario inicializado no coincide");
        assertEquals(testVehicle.getId(), journeyService.getVehicle(), "El vehículo inicializado no coincide");
        assertEquals(testVehicle.getLocation(), journeyService.getStartLocation(), "La ubicación inicial no coincide");
    }

    @Test
    public void testSetServiceInit() {
        LocalDateTime startTime = LocalDateTime.now();
        journeyService.setServiceInit(startTime);

        assertEquals(startTime, journeyService.getStartTime(), "La hora de inicio no se configuró correctamente");
    }

    @Test
    public void testSetServiceFinish() {
        LocalDateTime startTime = LocalDateTime.now();
        LocalDateTime endTime = startTime.plusMinutes(15);
        float distance = 5.0f;
        float averageSpeed = 3.0f;
        GeographicPoint endLocation = new GeographicPoint(15, 25);

        journeyService.setServiceInit(startTime);
        journeyService.setServiceFinish(endLocation, endTime, distance, averageSpeed);

        assertEquals(endLocation, journeyService.getEndLocation(), "La ubicación final no coincide");
        assertEquals(endTime, journeyService.getEndTime(), "La hora de finalización no coincide");
        assertEquals(distance, journeyService.getDistance(), "La distancia recorrida no coincide");
        assertEquals(averageSpeed, journeyService.getAverageSpeed(), "La velocidad promedio no coincide");
        assertEquals(15, journeyService.getDuration(), "La duración no coincide");
    }
}

