package services;

import data.*;
import micromobility.*;
import services.exceptions.*;

import java.math.BigDecimal;
import java.net.ConnectException;
import java.time.LocalDateTime;

/**
 * Interface representing external services involved in the shared micromobility system.
 * Provides methods for vehicle availability checks, pairing registration, and location updates.
 */
public interface Server {

    // External service for persistent storage, invoked by the use case controller

    /**
     * Checks if a personal mobility vehicle (PMV) is available.
     *
     * @param vhID the ID of the vehicle to check.
     * @throws PMVNotAvailException if the vehicle is not available.
     * @throws ConnectException if there is a connection issue with the server.
     */
    void checkPMVAvail(VehicleID vhID) throws PMVNotAvailException, ConnectException;

    /**
     * Registers the pairing of a user with a vehicle at a station.
     *
     * @param user the user account.
     * @param veh the vehicle ID.
     * @param st the station ID.
     * @param loc the geographic location of the pairing.
     * @param date the date and time of the pairing.
     * @throws InvalidPairingArgsException if the pairing arguments are invalid.
     * @throws ConnectException if there is a connection issue with the server.
     */
    void registerPairing(UserAccount user, VehicleID veh, StationID st,
                         GeographicPoint loc, LocalDateTime date)
            throws InvalidPairingArgsException, ConnectException;

    /**
     * Stops the pairing of a user with a vehicle, recording journey statistics.
     *
     * @param user the user account.
     * @param veh the vehicle ID.
     * @param st the station ID.
     * @param loc the geographic location of the unpairing.
     * @param date the date and time of the unpairing.
     * @param avSp the average speed during the journey.
     * @param dist the total distance of the journey.
     * @param dur the duration of the journey in minutes.
     * @param imp the environmental impact of the journey.
     * @throws InvalidPairingArgsException if the pairing arguments are invalid.
     * @throws ConnectException if there is a connection issue with the server.
     */
    void stopPairing(UserAccount user, VehicleID veh, StationID st,
                     GeographicPoint loc, LocalDateTime date, float avSp, float dist,
                     int dur, BigDecimal imp)
            throws InvalidPairingArgsException, ConnectException;

    // Internal operations

    /**
     * Sets up the pairing of a user with a vehicle at a station.
     *
     * @param user the user account.
     * @param veh the vehicle ID.
     * @param st the station ID.
     * @param loc the geographic location of the pairing.
     * @param date the date and time of the pairing.
     */
    void setPairing(UserAccount user, VehicleID veh, StationID st,
                    GeographicPoint loc, LocalDateTime date);

    /**
     * Removes a pairing registration service.
     *
     * @param service the journey service to unpair.
     * @throws PairingNotFoundException if the pairing is not found.
     */
    void unPairRegisterService(JourneyService service) throws PairingNotFoundException;

    /**
     * Registers the current location of a vehicle at a station.
     *
     * @param veh the vehicle ID.
     * @param st the station ID.
     */
    void registerLocation(VehicleID veh, StationID st);
}
