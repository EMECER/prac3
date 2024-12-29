package data;

import services.exceptions.NotCorrectFormatException;

public class VehicleID implements VehicleIDInterface {
    //formato del id: 4 números (como una matrícula pero sin letras)
    private int id;

    public VehicleID (int receivedId) throws NotCorrectFormatException {
        if (receivedId < 1000 || receivedId > 9999){
            throw new NotCorrectFormatException("El formato del id del vehículo es de 4 números");
        }

        this.id = receivedId;
    }
    @Override
    public int getId() {
        return id;
    }
}
