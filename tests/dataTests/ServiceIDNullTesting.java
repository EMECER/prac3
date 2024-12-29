package dataTests;

import services.exceptions.NotCorrectFormatException;
import data.ServiceID;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class ServiceIDNullTesting implements DataNotCorrectInit {

    private ServiceID serviceNull;


    @Override
    @Test
    public void checkThrows() throws NotCorrectFormatException {
        assertThrows(NotCorrectFormatException.class, () -> serviceNull = new ServiceID(null));
    }
}
