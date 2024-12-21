package data;

public class StationID {

    //Formato: letra del barrio + número de 3 dígitos (inspiración en matrículas antiguas). Por ejemplo: P001 (pardinyes, estación1)
    private String id;

    public StationID(String receivedId) throws NotCorrectFormatException {
        if (receivedId.length() != 4){
            throw new NotCorrectFormatException("El formato es Letra del barrio de la estación + 3 números identificadores");
        }
        if (receivedId.charAt(0) < 'a' || receivedId.charAt(0) > 'Z'){
            throw new NotCorrectFormatException("El id debe empezar por la letra del barrio donde está localizada la estación");
        }
        String numericPart = receivedId.substring(1);
        if (!isNumeric(numericPart)){
            throw new NotCorrectFormatException("El formato es letra del barrio de la estación + 3 números identificadores");
        }
        if (receivedId == null){
            throw new NullPointerException("El identificador no puede ser null");
        }
        this.id = receivedId;
    }

    private boolean isNumeric(String numericPart){
        try{
            Integer.parseInt(numericPart);
            return true;
        } catch (NumberFormatException e){
            return false;
        }
    }
}
