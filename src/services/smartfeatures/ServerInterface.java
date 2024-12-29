package services.smartfeatures;

import data.*;
import micromobility.*;
import services.exceptions.*;

import java.math.BigDecimal;
import java.net.ConnectException;
import java.time.LocalDateTime;

/**
 * Interfaz que representa servicios externos en el sistema de micromovilidad compartida.
 * Proporciona métodos para verificar la disponibilidad de vehículos, registrar emparejamientos y actualizar ubicaciones.
 */
public interface ServerInterface {

    /**
     * Verifica si un vehículo de movilidad personal (PMV) está disponible.
     *
     * @param vhID el ID del vehículo a verificar.
     * @throws PMVNotAvailException si el vehículo no está disponible.
     * @throws ConnectException si ocurre un problema de conexión con el servidor.
     */
    void checkPMVAvail(VehicleID vhID) throws PMVNotAvailException, ConnectException;

    /**
     * Registra el emparejamiento de un usuario con un vehículo en una estación.
     *
     * @param user el usuario.
     * @param veh el ID del vehículo.
     * @param st el ID de la estación.
     * @param loc la ubicación geográfica del emparejamiento.
     * @param date la fecha y hora del emparejamiento.
     * @throws InvalidPairingArgsException si los argumentos del emparejamiento no son válidos.
     * @throws ConnectException si ocurre un problema de conexión con el servidor.
     */
    void registerPairing(UserAccount user, VehicleID veh, StationID st,
                         GeographicPoint loc, LocalDateTime date)
            throws InvalidPairingArgsException, ConnectException;

    /**
     * Finaliza el emparejamiento de un usuario con un vehículo, registrando estadísticas del trayecto.
     *
     * @param user el usuario.
     * @param veh el ID del vehículo.
     * @param st el ID de la estación.
     * @param loc la ubicación geográfica del final del trayecto.
     * @param date la fecha y hora del final del trayecto.
     * @param avSp la velocidad promedio durante el trayecto.
     * @param dist la distancia total recorrida.
     * @param dur la duración del trayecto en minutos.
     * @param imp el impacto ambiental del trayecto.
     * @throws InvalidPairingArgsException si los argumentos no son válidos.
     * @throws ConnectException si ocurre un problema de conexión con el servidor.
     */
    void stopPairing(UserAccount user, VehicleID veh, StationID st,
                     GeographicPoint loc, LocalDateTime date, float avSp, float dist,
                     int dur, BigDecimal imp)
            throws InvalidPairingArgsException, ConnectException;

    /**
     * Configura el emparejamiento de un usuario con un vehículo en una estación.
     *
     * @param user el usuario.
     * @param veh el ID del vehículo.
     * @param st el ID de la estación.
     * @param loc la ubicación geográfica del emparejamiento.
     * @param date la fecha y hora del emparejamiento.
     */
    void setPairing(UserAccount user, VehicleID veh, StationID st,
                    GeographicPoint loc, LocalDateTime date);

    /**
     * Elimina un servicio de registro de emparejamientos.
     *
     * @param service el servicio de trayecto a eliminar.
     * @throws PairingNotFoundException si no se encuentra el emparejamiento.
     */
    void unPairRegisterService(JourneyService service) throws PairingNotFoundException;

    /**
     * Registra la ubicación actual de un vehículo en una estación.
     *
     * @param veh el ID del vehículo.
     * @param st el ID de la estación.
     */
    void registerLocation(VehicleID veh, StationID st);
}
