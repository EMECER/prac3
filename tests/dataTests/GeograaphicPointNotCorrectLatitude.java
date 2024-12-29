package dataTests;

import data.GeographicPoint;
import data.NotCorrectFormatException;
import data.ServiceID;
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
