package data;

/**
 * Representa el identificador único de un servicio.
 */
public final class ServiceID {
    private final String id;

    public ServiceID(String id) throws NotCorrectFormatException {
        if (id == null || id.isEmpty()) {
            throw new NotCorrectFormatException("ServiceID no puede ser nulo o vacío");
        }
        this.id = id;
    }

    public String getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ServiceID serviceID = (ServiceID) o;
        return id.equals(serviceID.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return "ServiceID{" + "id='" + id + '\'' + '}';
    }
}
