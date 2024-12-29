package micromobility;

/**
 * Enumeración PMVState
 * Representa los posibles estados de un vehículo de micromovilidad.
 */
public enum PMVState {
    Available,       // El vehículo está disponible para usar
    NotAvailable,    // El vehículo no está disponible
    UnderWay,        // El vehículo está en uso
    TemporaryParking // El vehículo está temporalmente estacionado
}
