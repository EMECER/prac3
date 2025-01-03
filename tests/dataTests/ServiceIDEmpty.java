package dataTests;

import services.exceptions.NotCorrectFormatException;
import data.ServiceID;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class ServiceIDEmpty implements DataNotCorrectInit {

    private ServiceID serviceEmpty;
    private String empty = new String("");
    @Test
    @Override
    public void checkThrows() throws NotCorrectFormatException {
        assertThrows(NotCorrectFormatException.class, () -> serviceEmpty = new ServiceID(empty));
    }
}
