package dataTests;

import data.NotCorrectFormatException;
import data.VehicleID;
import dataTests.DataNotCorrectInit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class VehicleIDNotCorrectLength implements DataNotCorrectInit {
    private VehicleID notCorrectLength;
    private int[] longList = new int[8];

    @BeforeEach
    void setup(){
        for (int i = 0; i < longList.length; i++){
            longList[i] = i;
        }
    }

    @Override
    @Test
    public void checkThrows() throws NotCorrectFormatException {
        assertThrows(NotCorrectFormatException.class, () -> notCorrectLength = new VehicleID(longList));
    }
}
