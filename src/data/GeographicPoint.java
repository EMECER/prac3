package data;

/**
 * Clase que representa un punto geográfico expresado en grados decimales.
 * La latitud debe estar entre -90 y 90 grados.
 * La longitud debe estar entre -180 y 180 grados.
 */
final public class GeographicPoint {
    // Coordenadas geográficas expresadas en grados decimales
    private final float latitude;
    private final float longitude;

    /**
     * Constructor de la clase GeographicPoint.
     *
     * @param lat latitud, debe estar entre -90 y 90 grados.
     * @param lon longitud, debe estar entre -180 y 180 grados.
     * @throws IllegalArgumentException si la latitud o longitud están fuera de rango.
     */
    public GeographicPoint(float lat, float lon) {
        if (lat < -90 || lat > 90) {
            throw new IllegalArgumentException("La latitud debe estar entre -90 y 90 grados.");
        }
        if (lon < -180 || lon > 180) {
            throw new IllegalArgumentException("La longitud debe estar entre -180 y 180 grados.");
        }
        this.latitude = lat;
        this.longitude = lon;
    }

    /**
     * Obtiene la latitud del punto geográfico.
     *
     * @return la latitud.
     */
    public float getLatitude() {
        return latitude;
    }

    /**
     * Obtiene la longitud del punto geográfico.
     *
     * @return la longitud.
     */
    public float getLongitude() {
        return longitude;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GeographicPoint gP = (GeographicPoint) o;
        return Float.compare(gP.latitude, latitude) == 0 &&
               Float.compare(gP.longitude, longitude) == 0;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Float.floatToIntBits(latitude);
        result = prime * result + Float.floatToIntBits(longitude);
        return result;
    }

    @Override
    public String toString() {
        return "Punto geográfico {" +
               "latitud=" + latitude +
               ", longitud=" + longitude +
               '}';
    }
}
