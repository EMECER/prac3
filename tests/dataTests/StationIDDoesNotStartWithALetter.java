package dataTests;

import services.exceptions.NotCorrectFormatException;
import data.StationID;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class StationIDDoesNotStartWithALetter implements DataNotCorrectInit {

    private StationID notLetterFirstPos;
    @Test
    @Override
    public void checkThrows() throws NotCorrectFormatException {
        assertThrows(NotCorrectFormatException.class, () -> notLetterFirstPos = new StationID("5123S"));
    }
}
