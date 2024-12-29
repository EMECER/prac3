package micromobility;

import data.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import services.exceptions.NotCorrectFormatException;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class JourneyServiceFailureTests {

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
    public void testSetServiceFinishWithoutInit_ShouldThrowException() {
        LocalDateTime endTime = LocalDateTime.now();
        GeographicPoint endLocation = new GeographicPoint(15, 25);

        assertThrows(NullPointerException.class, () -> {
            journeyService.setServiceFinish(endLocation, endTime, 5.0f, 3.0f);
        }, "Debería lanzar una excepción si no se ha inicializado la hora de inicio");
    }

    @Test
    public void testSetServiceFinishWithInvalidData_ShouldFailCalculations() {
        LocalDateTime startTime = LocalDateTime.now();
        LocalDateTime endTime = startTime.minusMinutes(5); // Hora de finalización antes de la de inicio
        GeographicPoint endLocation = new GeographicPoint(15, 25);

        journeyService.setServiceInit(startTime);

        journeyService.setServiceFinish(endLocation, endTime, -5.0f, -3.0f); // Distancia y velocidad negativas

        assertTrue(journeyService.getDuration() < 0, "La duración debería ser negativa si las horas no son válidas");
        assertTrue(journeyService.getDistance() < 0, "La distancia no debería ser negativa");
        assertTrue(journeyService.getAverageSpeed() < 0, "La velocidad promedio no debería ser negativa");
    }
}
