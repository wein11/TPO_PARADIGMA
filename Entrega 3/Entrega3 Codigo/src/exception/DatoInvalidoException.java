package exception;

// Se lanza cuando un dato ingresado no supera las validaciones del sistema
public class DatoInvalidoException extends ClinicaException {

    public DatoInvalidoException(String mensaje) {
        super(mensaje);
    }

}
