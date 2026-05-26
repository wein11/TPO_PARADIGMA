package exception;

// Se lanza cuando no se encuentra un odontólogo por ID o matrícula
public class OdontologoNoEncontradoException extends ClinicaException {

    public OdontologoNoEncontradoException(String mensaje) {
        super(mensaje);
    }

}
