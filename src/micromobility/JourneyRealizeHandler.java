package micromobility;

import data.*;
import services.*;
import services.ServerInterface;
import services.exceptions.*;
import services.smartfeatures.*;
import java.awt.image.BufferedImage;
import java.math.BigDecimal;
import java.net.ConnectException;
import java.time.Duration;
import java.time.LocalDateTime;

/**
 * Clase JourneyRealizeHandler
 * Controladora del caso de uso Realizar desplazamiento.
 * */

public class JourneyRealizeHandler {
    // Dependencias inyectadas
    private final ServerInterface server;
    private final QRDecoderInterface qrDecoder;
    private final ArduinoMicroControllerInterface arduino;

    // Estado de la clase
    private PMVehicle vehicle;
    private JourneyService journey;

    /**
     * Constructor de JourneyRealizeHandler
     *
     * @param server Instancia del servidor
     * @param qrDecoder Instancia del decodificador de QR
     * @param arduino Instancia del microcontrolador Arduino
     */

    public JourneyRealizeHandler(ServerInterface server, QRDecoderInterface qrDecoder, ArduinoMicroControllerInterface arduino) {
        this.server = server;
        this.qrDecoder = qrDecoder;
        this.arduino = arduino;
    }

    // Eventos de entrada relacionados con la interfaz de usuario
    public void scanQR(BufferedImage qrImage) throws ConnectException, InvalidPairingArgsException, CorruptedImgException, PMVNotAvailException, ProceduralException {
        try {
            // Decodificar el QR y verificar disponibilidad
            VehicleID vehicleID = qrDecoder.getVehicleID(qrImage);
            server.checkPMVAvail(vehicleID);

            // Crear instancia del vehículo y la jornada
            vehicle = vehicleID.getID()// Si no se especifica otro método, creamos una instancia
            journey = new JourneyService(vehicle);

            // Actualizar estado del vehículo
            vehicle.setNotAvailb();
        } catch (ConnectException | InvalidPairingArgsException | CorruptedImgException | PMVNotAvailException e) {
            throw e; // Relanzar las excepciones específicas
        } catch (Exception e) {
            throw new ProceduralException("Error inesperado al escanear el QR", e);
        }
    }

    public void unPairVehicle() throws ConnectException, InvalidPairingArgsException, PairingNotFoundException, ProceduralException {
        try {
            // Calcular importe
            BigDecimal cost = calculateImport(
                    journey.getDistance(),
                    journey.getDuration(),
                    journey.getAverageSpeed(),
                    journey.getEndTime()
            );

            // Detener emparejamiento en el servidor
            server.stopPairing(
                    journey.getUser(),
                    journey.getVehicle(),
                    journey.getStation(),
                    journey.getEndLocation(),
                    journey.getEndTime(),
                    journey.getAverageSpeed(),
                    journey.getDistance(),
                    journey.getDuration(),
                    cost
            );

            // Actualizar estado del vehículo
            vehicle.setAvailb();
        } catch (ConnectException | InvalidPairingArgsException | PairingNotFoundException e) {
            throw e; // Relanzar las excepciones específicas
        } catch (Exception e) {
            throw new ProceduralException("Error inesperado al desvincular el vehículo", e);
        }
    }

    // Eventos de entrada del canal Bluetooth no vinculado
    public void broadcastStationID(StationID stID) throws ConnectException {
        try {
            // Simulación de transmisión (el enunciado no especifica otro uso)
            // Aquí invocamos el servicio para transmitir información
        } catch (Exception e) {
            throw new ConnectException("Error al transmitir el ID de la estación");
        }
    }

    // Eventos de entrada del canal del microcontrolador Arduino
    public void startDriving() throws ConnectException, ProceduralException {
        try {
            arduino.startDriving();
            vehicle.setUnderWay();
            journey.setServiceInit(LocalDateTime.now());
        } catch (ConnectException e) {
            throw e;
        } catch (Exception e) {
            throw new ProceduralException("Error inesperado al iniciar el desplazamiento", e);
        }
    }

    public void stopDriving(GeographicPoint endLocation) throws ConnectException, ProceduralException {
        try {
            arduino.stopDriving();

            // Calcular valores de la jornada
            LocalDateTime endTime = LocalDateTime.now();
            float distance = calculateDistance(vehicle.getLocation(), endLocation);
            float averageSpeed = calculateAverageSpeed(vehicle.getLocation(), endLocation, endTime);

            // Actualizar jornada
            journey.setServiceFinish(endLocation, endTime, distance, averageSpeed);
            vehicle.setAvailb();
        } catch (ConnectException e) {
            throw e;
        } catch (Exception e) {
            throw new ProceduralException("Error inesperado al detener el desplazamiento", e);
        }
    }

    // Métodos privados de cálculo
    private BigDecimal calculateImport(float dis, int dur, float avSp, LocalDateTime date) {
        BigDecimal baseRate = new BigDecimal("0.5");
        BigDecimal distanceCost = new BigDecimal(dis).multiply(new BigDecimal("0.2"));
        BigDecimal timeCost = new BigDecimal(dur).multiply(new BigDecimal("0.1"));
        return baseRate.add(distanceCost).add(timeCost);
    }

    private float calculateDistance(GeographicPoint start, GeographicPoint end) {
        double xDiff = end.getLongitude() - start.getLongitude();
        double yDiff = end.getLatitude() - start.getLatitude();
        return (float) Math.sqrt(xDiff * xDiff + yDiff * yDiff);
    }

    private float calculateAverageSpeed(GeographicPoint start, GeographicPoint end, LocalDateTime endTime) {
        Duration duration = Duration.between(journey.getStartTime(), endTime);
        float distance = calculateDistance(start, end);
        return distance / (duration.toMinutes() * 60);
    }
}

