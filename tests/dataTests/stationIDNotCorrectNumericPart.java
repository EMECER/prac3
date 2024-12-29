package dataTests;

import data.NotCorrectFormatException;
import data.StationID;
import dataTests.DataNotCorrectInit;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class stationIDNotCorrectNumericPart implements DataNotCorrectInit {
    private StationID notCorrectNumeric;
    @Override
    @Test
    public void checkThrows() throws NotCorrectFormatException {

        assertThrows(NotCorrectFormatException.class, () -> notCorrectNumeric = new StationID("W123SA"));
    }

}