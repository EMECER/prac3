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

public class JourneyRealizeHandler implements JourneyRealizeHandlerInterface {
    // Dependencias inyectadas
    private final ServerInterface server;
    private final QRDecoderInterface qrDecoder;
    private final ArduinoMicroControllerInterface arduino;

    private int step = 1;

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
    @Override
    // Eventos de entrada relacionados con la interfaz de usuario
    public void scanQR(BufferedImage qrImage,UserAccount user) throws ConnectException, InvalidPairingArgsException, CorruptedImgException, PMVNotAvailException, ProceduralException {
        if (step != 1){
            throw new ProceduralException("Se está escaneando el QR en el momento equivocado");
        }
        if (server.getState().equals("Down")){
            throw new ConnectException("El servidor actualmente está caído");
        }


        try {
            step++;
            // Decodificar el QR y verificar disponibilidad
            this.vehicle = qrDecoder.getVehicle(qrImage);
            VehicleID id = vehicle.getId();
            server.checkPMVAvail(id);

            // Crear instancia del vehículo y la jornada
            journey = new JourneyService(user, vehicle);

            PMVehicle.PMVState status = vehicle.getState();
            if (status == PMVehicle.PMVState.NotAvailable) {
                throw new InvalidPairingArgsException("No te puedes emparejar a un vehículo que no está disponible");
            }

            // Actualizar estado del vehículo
            vehicle.setNotAvailb();
        } catch (CorruptedImgException e) {
            throw new CorruptedImgException("Se ha escaneado un QR corrupto");
        } catch (NotCorrectFormatException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    // Eventos de entrada del canal Bluetooth no vinculado
    public void broadcastStationID(StationIDInterface stID) throws ConnectException {
        try {
            // Simulación de transmisión (el enunciado no especifica otro uso)
            // Aquí invocamos el servicio para transmitir información
        } catch (Exception e) {
            throw new ConnectException("Error al transmitir el ID de la estación");
        }
    }
    @Override
    // Eventos de entrada del canal del microcontrolador Arduino
    public void startDriving() throws ConnectException, ProceduralException {
        if (step != 2){
            throw new ProceduralException("No se ha hecho en el orden correcto");
        }
        if (server.getState().equals("Down")){
            throw new ConnectException("El servidor no está disponible");
        }
        try {
            step++;
            arduino.startDriving();
            this.vehicle.setUnderWay();
            journey.setServiceInit(LocalDateTime.now());
        } catch (ConnectException e) {
            throw e;
        } catch (PMVPhisicalException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public void stopDriving(GeographicPoint endLocation) throws ConnectException, ProceduralException{
        if (step != 3){
            throw new ProceduralException("No se ha hecho en el orden correcto");
        }
        if (server.getState().equals("Down")){
            throw new ConnectException("El servidor no está disponible");
        }
        try {
            step++;
            if (server.getState().equals("Down")){
                throw new ConnectException("El servidor actualmente está caído");
            }
            arduino.stopDriving();

            // Calcular valores de la jornada
            LocalDateTime endTime = LocalDateTime.now();
            float distance = calculateDistance(vehicle.getLocation(), endLocation);
            float averageSpeed = calculateAverageSpeed(vehicle.getLocation(), endLocation, endTime);

            // Actualizar jornada
            journey.setServiceFinish(endLocation, endTime, distance, averageSpeed);
            vehicle.setAvailb();
        } catch (Exception e) {
            throw new ProceduralException("Error inesperado al detener el desplazamiento");
        }
    }
    @Override
    public void unPairVehicle() throws ConnectException, InvalidPairingArgsException, PairingNotFoundException, ProceduralException {
        if (step != 4){
            throw new ProceduralException("No se ha hecho en el orden correcto");
        }
        if (server.getState().equals("Down")){
            throw new ConnectException("El servidor no está disponible");
        }
        step++;
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

    public PMVehicle getVehicle() {
        return vehicle;
    }

    public void setVehicleStatus(PMVehicle.PMVState status){
        this.vehicle.setState(status);
    }
}

