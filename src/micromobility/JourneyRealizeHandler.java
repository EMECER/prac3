// Paquete micromobility: Implementación del caso de uso Realizar desplazamiento
package micromobility;

import data.*;
import services.*;
import services.smartfeatures.*;
import java.awt.image.BufferedImage;
import java.math.BigDecimal;
import java.net.ConnectException;
import java.time.Duration;
import java.time.LocalDateTime;

/**
 * Clase JourneyRealizeHandler
 * Controladora del caso de uso Realizar desplazamiento.
 */
public class JourneyRealizeHandler {
    // Miembros de la clase
    private final Server server;
    private final QRDecoder qrDecoder;
    private final ArduinoMicroController arduino;
    private PMVehicle vehicle;
    private JourneyService journey;

    /**
     * Constructor de JourneyRealizeHandler
     * @param server Instancia del servidor
     * @param qrDecoder Instancia del decodificador de QR
     * @param arduino Instancia del microcontrolador Arduino
     */
    public JourneyRealizeHandler(Server server, QRDecoder qrDecoder, ArduinoMicroController arduino) {
        this.server = server;
        this.qrDecoder = qrDecoder;
        this.arduino = arduino;
    }

    // Eventos de entrada relacionados con la interfaz de usuario
    public void scanQR(BufferedImage qrImage) throws ConnectException, InvalidPairingArgsException, CorruptedImgException, PMVNotAvailException, ProceduralException {
        try {
            VehicleID vehicleID = qrDecoder.getVehicleID(qrImage);
            server.checkPMVAvail(vehicleID);
            vehicle = server.getPMVehicle(vehicleID);
            journey = new JourneyService(vehicle);
            vehicle.setNotAvailb();
        } catch (Exception e) {
            throw new ProceduralException("Error al escanear el código QR", e);
            throw new ConnectException("Error al conectar",e);
            throw new InvalidPairingArgsException("Error al vincular",e);
            throw new CorruptedImgException("Error Imagen corrupta",e);
            throw new PMVNotAvailException("Error vehículo no disponible",e);
        }
    }

    public void unPairVehicle() throws ConnectException, InvalidPairingArgsException, PairingNotFoundException, ProceduralException {
        try {
            BigDecimal cost = calculateImport(journey.getDistance(), journey.getDuration(), journey.getAverageSpeed(), journey.getEndTime());
            server.stopPairing(journey.getUser(), journey.getVehicle(), journey.getStation(), journey.getEndLocation(), journey.getEndTime(), journey.getAverageSpeed(), journey.getDistance(), journey.getDuration(), cost);
            vehicle.setAvailb();
        } catch (Exception e) {
            throw new ProceduralException("Error al desvincular el vehículo", e);
            throw new InvalidPairingArgsException("Error al vincular",e);
            throw new PairingNotFoundException("Error vinculación no encontrada", e);
            throw new ConnectException("Error al conectar",e);
        }
    }

    // Eventos de entrada del canal Bluetooth no vinculado
    public void broadcastStationID(StationID stID) throws ConnectException {
        try {
            server.registerStationID(stID);
        } catch (Exception e) {
            throw new ConnectException("Error al recibir el ID de la estación", e);
        }
    }

    // Eventos de entrada del canal del microcontrolador Arduino
    public void startDriving() throws ConnectException, ProceduralException {
        try {
            arduino.startDriving();
            vehicle.setUnderWay();
            journey.setServiceInit(LocalDateTime.now());
        } catch (Exception e) {
            throw new ProceduralException("Error al iniciar el desplazamiento", e);
            throw new ConnectException("Error al recibir el ID de la estación", e);
        }   
    }

    public void stopDriving(GeographicPoint endLocation) throws ConnectException, ProceduralException {
        try {
            arduino.stopDriving();
            LocalDateTime endTime = LocalDateTime.now();
            float distance = calculateDistance(vehicle.getLocation(), endLocation);
            float averageSpeed = calculateAverageSpeed(vehicle.getLocation(), endLocation, endTime);
            journey.setServiceFinish(endLocation, endTime, distance, averageSpeed);
            vehicle.setAvailb();
        } catch (Exception e) {
            throw new ProceduralException("Error al detener el desplazamiento", e);
            throw new ConnectException("Error al recibir el ID de la estación", e);
        }
    }

    // Operaciones internas
    private void calculateValues(GeographicPoint gP, LocalDateTime date) {
        // Implementación del cálculo de duración, distancia y velocidad promedio
        float distance = calculateDistance(vehicle.getLocation(), gP);
        float averageSpeed = calculateAverageSpeed(vehicle.getLocation(), gP, date);
        journey.setServiceFinish(gP, date, distance, averageSpeed);
    }

    private BigDecimal calculateImport(float dis, int dur, float avSp, LocalDateTime date) {
        // Implementación del cálculo del importe
        BigDecimal baseRate = new BigDecimal("0.5");
        BigDecimal distanceCost = new BigDecimal(dis).multiply(new BigDecimal("0.2"));
        BigDecimal timeCost = new BigDecimal(dur).multiply(new BigDecimal("0.1"));
        return baseRate.add(distanceCost).add(timeCost);
    }

    private float calculateDistance(GeographicPoint start, GeographicPoint end) {
        // Fórmula para calcular la distancia entre dos puntos geográficos
        double xDiff = end.getLongitude() - start.getLongitude();
        double yDiff = end.getLatitude() - start.getLatitude();
        return (float) Math.sqrt(xDiff * xDiff + yDiff * yDiff);
    }

    private float calculateAverageSpeed(GeographicPoint start, GeographicPoint end, LocalDateTime endTime) {
        // Cálculo de velocidad promedio
        Duration duration = Duration.between(journey.getStartTime(), endTime);
        float distance = calculateDistance(start, end);
        return distance / (duration.toMinutes()*60);
    }
      // Métodos setter para inyectar dependencias
    public void setServer(Server server) {
        this.server = server;
    }

    public void setQrDecoder(QRDecoder qrDecoder) {
        this.qrDecoder = qrDecoder;
    }

    public void setArduino(ArduinoMicroController arduino) {
        this.arduino = arduino;
    }

    public void setVehicle(PMVehicle vehicle) {
        this.vehicle = vehicle;
    }

    public void setJourney(JourneyService journey) {
        this.journey = journey;
    }
}
