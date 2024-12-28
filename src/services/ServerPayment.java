package services;

import data.ServiceID;
import data.UserAccount;

import java.math.BigDecimal;
import java.net.ConnectException;

/**
 * Interfaz que gestiona la parte del pago en el sistema de micromovilidad, para evitar futuros code smells.
 */
public interface ServerPayment {

    /**
     * Registra los detalles de un pago en el servidor.
     *
     * @param servID el identificador único del servicio.
     * @param user el usuario que realiza el pago.
     * @param imp el importe del pago.
     * @param payMeth el método de pago (carácter que indica el tipo).
     * @throws ConnectException si ocurre un problema de conexión con el servidor.
     */
    void registerPayment(ServiceID servID, UserAccount user, BigDecimal imp, char payMeth) throws ConnectException;
}
