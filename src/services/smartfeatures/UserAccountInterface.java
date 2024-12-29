import data.UserAccount;
/**
 * Interfaz para probar la clase UserAccount.
 */
public interface UserAccountInterface {

    /**
     * Crea y devuelve un UserAccount válido.
     * @return una instancia válida de UserAccount.
     */
    UserAccount createValidUserAccount();

    /**
     * Crea y devuelve un UserAccount inválido, lo que debería generar una excepción.
     * @return una instancia inválida de UserAccount.
     */
    UserAccount createInvalidUserAccount();

    /**
     * Verifica si un UserAccount está activo.
     * @param account el UserAccount.
     * @return true si está activo, false en caso contrario.
     */
    boolean isUserAccountActive(UserAccount account);

    /**
     * Devuelve la representación en forma de cadena de un UserAccount.
     * @param account el UserAccount.
     * @return la representación en forma de cadena.
     */
    String stringifyUserAccount(UserAccount account);
}