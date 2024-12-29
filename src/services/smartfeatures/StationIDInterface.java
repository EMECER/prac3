import data.StationID;
/**
 * Interfaz para probar la clase StationID.
 */
public interface StationIDTestInterface {
    /**
     * Crea y devuelve un StationID válido.
     * @return una instancia válida de StationID.
     */
    StationID createValidStationID();

    /**
     * Crea y devuelve un StationID inválido, lo que debería generar una excepción.
     * @return una instancia inválida de StationID.
     */
    StationID createInvalidStationID();

    /**
     * Compara dos objetos StationID para verificar si son iguales.
     * @param s1 el primer StationID.
     * @param s2 el segundo StationID.
     * @return true si son iguales, false en caso contrario.
     */
    boolean compareStationIDs(StationID s1, StationID s2);

    /**
     * Devuelve la representación en forma de cadena de un StationID.
     * @param stationID el StationID.
     * @return la representación en forma de cadena.
     */
    String stringifyStationID(StationID stationID);
}