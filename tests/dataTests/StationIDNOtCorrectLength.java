package dataTests;

import data.NotCorrectFormatException;
import data.StationID;
import dataTests.DataNotCorrectInit;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class StationIDNOtCorrectLength implements DataNotCorrectInit {
    private StationID notCorrectLength;

    @Test
    @Override
    public void checkThrows() throws NotCorrectFormatException {
        assertThrows(NotCorrectFormatException.class, () -> notCorrectLength = new StationID("A1231354"));
    }
}
