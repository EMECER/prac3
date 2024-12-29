package dataTests;

import services.exceptions.NotCorrectFormatException;
import data.StationID;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class stationIDDoesNotStartWithValidLetter implements DataNotCorrectInit {

    private StationID notCorrectBarri;
    @Test
    @Override
    public void checkThrows() throws NotCorrectFormatException {
        assertThrows(NotCorrectFormatException.class, () -> notCorrectBarri = new StationID("W1234"));
    }
}
