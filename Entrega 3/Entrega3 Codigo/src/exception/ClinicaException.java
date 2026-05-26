package exception;

// Clase base para todas las excepciones del sistema de la clínica
public class ClinicaException extends Exception {

    public ClinicaException(String mensaje) {
        super(mensaje);
    }

}
