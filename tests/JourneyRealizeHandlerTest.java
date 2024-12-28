package micromobility;

import data.*;
import org.junit.jupiter.api.*;
import org.mockito.*;
import services.*;
import services.smartfeatures.*;
import java.awt.image.BufferedImage;
import java.math.BigDecimal;
import java.net.ConnectException;
import java.time.Duration;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class JourneyRealizeHandlerTest {

    @Mock
    private Server mockServer;

    @Mock
    private QRDecoder mockQRDecoder;

    @Mock
    private ArduinoMicroController mockArduino;

    @Mock
    private PMVehicle mockVehicle;

    @Mock
    private JourneyService mockJourneyService;

    private JourneyRealizeHandler handler;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        handler = new JourneyRealizeHandler(mockServer, mockQRDecoder, mockArduino);
    }

    @Test
    public void testScanQR_Success() throws Exception {
        BufferedImage qrImage = mock(BufferedImage.class);
        VehicleID vehicleID = mock(VehicleID.class);

        when(mockQRDecoder.getVehicleID(qrImage)).thenReturn(vehicleID);
        when(mockServer.getPMVehicle(vehicleID)).thenReturn(mockVehicle);

        handler.scanQR(qrImage);

        verify(mockQRDecoder).getVehicleID(qrImage);
        verify(mockServer).checkPMVAvail(vehicleID);
        verify(mockServer).getPMVehicle(vehicleID);
        verify(mockVehicle).setNotAvailb();
    }

    @Test
    public void testScanQR_Failure_QRDecodingError() {
        BufferedImage qrImage = mock(BufferedImage.class);
        when(mockQRDecoder.getVehicleID(qrImage)).thenThrow(new CorruptedImgException("QR Image corrupted"));

        assertThrows(CorruptedImgException.class, () -> handler.scanQR(qrImage));
    }

    @Test
    public void testScanQR_Failure_VehicleNotAvailable() throws Exception {
        BufferedImage qrImage = mock(BufferedImage.class);
        VehicleID vehicleID = mock(VehicleID.class);

        when(mockQRDecoder.getVehicleID(qrImage)).thenReturn(vehicleID);
        doThrow(new PMVNotAvailException("Vehicle not available"))
                .when(mockServer).checkPMVAvail(vehicleID);

        assertThrows(PMVNotAvailException.class, () -> handler.scanQR(qrImage));
    }

    @Test
    public void testStartDriving_Success() throws Exception {
        doNothing().when(mockArduino).startDriving();

        handler.setVehicle(mockVehicle);
        handler.setJourney(mockJourneyService);

        handler.startDriving();

        verify(mockArduino).startDriving();
        verify(mockVehicle).setUnderWay();
        verify(mockJourneyService).setServiceInit(any(LocalDateTime.class));
    }

    @Test
    public void testStartDriving_Failure_ArduinoError() {
        doThrow(new ConnectException("Arduino connection error"))
                .when(mockArduino).startDriving();

        assertThrows(ProceduralException.class, () -> handler.startDriving());
    }

    @Test
    public void testStopDriving_Success() throws Exception {
        GeographicPoint endLocation = new GeographicPoint(1.0, 1.0);

        handler.setVehicle(mockVehicle);
        handler.setJourney(mockJourneyService);

        when(mockVehicle.getLocation()).thenReturn(new GeographicPoint(0.0, 0.0));
        doNothing().when(mockArduino).stopDriving();

        handler.stopDriving(endLocation);

        verify(mockArduino).stopDriving();
        verify(mockJourneyService).setServiceFinish(eq(endLocation), any(LocalDateTime.class), anyFloat(), anyFloat());
        verify(mockVehicle).setAvailb();
    }

    @Test
    public void testStopDriving_Failure_InvalidLocation() {
        GeographicPoint endLocation = null;

        assertThrows(NullPointerException.class, () -> handler.stopDriving(endLocation));
    }

    @Test
    public void testBroadcastStationID_Success() throws Exception {
        StationID stationID = mock(StationID.class);

        handler.broadcastStationID(stationID);

        verify(mockServer).registerStationID(stationID);
    }

    @Test
    public void testBroadcastStationID_Failure() {
        StationID stationID = mock(StationID.class);

        doThrow(new ConnectException("Connection error"))
                .when(mockServer).registerStationID(stationID);

        assertThrows(ConnectException.class, () -> handler.broadcastStationID(stationID));
    }

    @Test
    public void testUnPairVehicle_Success() throws Exception {
        handler.setJourney(mockJourneyService);
        handler.setVehicle(mockVehicle);

        when(mockJourneyService.getDistance()).thenReturn(5.0f);
        when(mockJourneyService.getDuration()).thenReturn(10);
        when(mockJourneyService.getAverageSpeed()).thenReturn(1.0f);
        when(mockJourneyService.getEndTime()).thenReturn(LocalDateTime.now());

        handler.unPairVehicle();

        verify(mockServer).stopPairing(any(), any(), any(), any(), any(), anyFloat(), anyFloat(), anyInt(), any(BigDecimal.class));
        verify(mockVehicle).setAvailb();
    }

    @Test
    public void testUnPairVehicle_Failure_MissingJourney() {
        assertThrows(NullPointerException.class, () -> handler.unPairVehicle());
    }

    @Test
    public void testCalculateValues_InternalLogic() {
        handler.setVehicle(mockVehicle);
        handler.setJourney(mockJourneyService);

        GeographicPoint startLocation = new GeographicPoint(0.0, 0.0);
        GeographicPoint endLocation = new GeographicPoint(3.0, 4.0);
        LocalDateTime endTime = LocalDateTime.now();

        when(mockVehicle.getLocation()).thenReturn(startLocation);

        handler.stopDriving(endLocation);

        verify(mockJourneyService).setServiceFinish(eq(endLocation), eq(endTime), eq(5.0f), anyFloat());
    }
}