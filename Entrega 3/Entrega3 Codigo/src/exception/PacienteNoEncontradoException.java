package exception;

// Se lanza cuando no se encuentra un paciente por ID o DNI
public class PacienteNoEncontradoException extends ClinicaException {

    public PacienteNoEncontradoException(String mensaje) {
        super(mensaje);
    }

}
