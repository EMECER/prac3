package micromobility.payment;
import java.math.BigDecimal;
abstract class Payment {
    private final ServiceID serviceID;
    private final String userAccount;

    public Payment(ServiceID serviceID, String userAccount) {
        if (serviceID == null || userAccount == null || userAccount.isEmpty()) {
            throw new IllegalArgumentException("Los datos del pago no son válidos.");
        }
        this.serviceID = serviceID;
        this.userAccount = userAccount;
    }

    public ServiceID getServiceID() {
        return serviceID;
    }

    public String getUserAccount() {
        return userAccount;
    }

    public abstract void processPayment(BigDecimal amount) throws NotEnoughWalletException;
}