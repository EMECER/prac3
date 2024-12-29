package dataTests;

import data.GeographicPoint;
import services.exceptions.NotCorrectFormatException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class GeograaphicPointNotCorrectLatitude implements DataNotCorrectInit{

    GeographicPoint toTest;
    @Override
    @Test
    public void checkThrows() throws NotCorrectFormatException {
        assertThrows(IllegalArgumentException.class, () -> toTest = new GeographicPoint(180, 0));
    }
}
