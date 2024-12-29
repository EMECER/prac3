package data;

public class VehicleIDDoble implements VehicleIDInterface{
    int id;
    public VehicleIDDoble(){
        this.id = 1234;
    }
    @Override
    public int getId() {
        return this.id;
    }
}
