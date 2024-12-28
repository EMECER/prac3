import data.VehicleID;
/**
 * Interfaz para probar la clase VehicleID.
 */
public interface VehicleIDInterface {

    /**
     * Crea y devuelve un VehicleID válido.
     * @return una instancia válida de VehicleID.
     */
    VehicleID createValidVehicleID();

    /**
     * Crea y devuelve un VehicleID inválido, lo que debería generar una excepción.
     * @return una instancia inválida de VehicleID.
     */
    VehicleID createInvalidVehicleID();

    /**
     * Compara dos objetos VehicleID para verificar si son iguales.
     * @param v1 el primer VehicleID.
     * @param v2 el segundo VehicleID.
     * @return true si son iguales, false en caso contrario.
     */
    boolean compareVehicleIDs(VehicleID v1, VehicleID v2);

    /**
     * Devuelve la representación en forma de cadena de un VehicleID.
     * @param vehicleID el VehicleID.
     * @return la representación en forma de cadena.
     */
    String stringifyVehicleID(VehicleID vehicleID);
}