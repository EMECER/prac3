package data;

import services.exceptions.NotCorrectFormatException;

public class StationID {

    //Formato: letra del barrio + número de 3 dígitos (inspiración en matrículas antiguas). Por ejemplo: P001 (pardinyes, estación1)
    private String id;
    enum Barris {
        F('F'), C('C'), P('P'), S('S'), M('M'), Z('Z'), B('B');
        // Balafia, CapPont, Pardinyes, Secà, Mariola, Zona Alta, Bordeta

        private final char valor;

        Barris(char valor) {
            this.valor = valor;
        }

        public char getValor() {
            return valor;
        }
    }

    public StationID(String receivedId) throws NotCorrectFormatException {

        try {
            if (receivedId.length() != 4) {
                throw new NotCorrectFormatException("El formato es Letra del barrio de la estación + 3 números identificadores");
            }
            if (receivedId.charAt(0) < 'a' || receivedId.charAt(0) > 'Z') {
                throw new NotCorrectFormatException("El id debe empezar por la letra del barrio donde está localizada la estación");
            }
            char receivedBarri = receivedId.charAt(0);
            if (!exisits(receivedBarri)){
                throw new NotCorrectFormatException("La primera letra del barrio no es un barrio válido");
            }
            String numericPart = receivedId.substring(1);
            if (!isNumeric(numericPart)) {
                throw new NotCorrectFormatException("El formato es letra del barrio de la estación + 3 números identificadores");
            }
            this.id = receivedId;
        } catch (NullPointerException e) {
            throw new NotCorrectFormatException("La id recibida es null");
        }
    }

    private boolean exisits(char receivedBarri) {
        boolean exists = false;
        for (Barris barri : Barris.values()){
            if (barri.getValor() == receivedBarri){
                return true;
            }
        }
        return false;
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