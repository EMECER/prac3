package dataTests;

import services.exceptions.NotCorrectFormatException;
import data.VehicleID;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class VehicleIDNotCorrectLength implements DataNotCorrectInit {
    private VehicleID notCorrectLength;
    @Override
    @Test
    public void checkThrows() throws NotCorrectFormatException {
        assertThrows(NotCorrectFormatException.class, () -> notCorrectLength = new VehicleID(10000));
    }
}
