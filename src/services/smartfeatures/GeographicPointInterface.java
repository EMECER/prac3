package services.smartfeatures;

import data.GeographicPoint;
public interface GeographicPointInterface {
 /**
     * Crea y devuelve un GeographicPoint válido.
     * @return una instancia válida de GeographicPoint.
     */
    GeographicPoint createValidPoint();

    /**
     * Crea y devuelve un GeographicPoint inválido, lo que debería generar una excepción.
     * @return una instancia inválida de GeographicPoint.
     */
    GeographicPoint createInvalidPoint();

    /**
     * Compara dos objetos GeographicPoint para verificar si son iguales.
     * @param p1 el primer GeographicPoint.
     * @param p2 el segundo GeographicPoint.
     * @return true si son iguales, false en caso contrario.
     */
    boolean comparePoints(GeographicPoint p1, GeographicPoint p2);

    /**
     * Genera un hashCode para un GeographicPoint.
     * @param point el GeographicPoint.
     * @return el código hash como un entero.
     */
    int generateHashCode(GeographicPoint point);

    /**
     * Devuelve la representación en forma de cadena de un GeographicPoint.
     * @param point el GeographicPoint.
     * @return la representación en forma de cadena.
     */
    String stringifyPoint(GeographicPoint point);
}