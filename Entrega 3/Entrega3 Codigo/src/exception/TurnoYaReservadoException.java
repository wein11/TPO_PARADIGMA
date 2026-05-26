package exception;

// Se lanza cuando el odontólogo ya tiene un turno registrado en esa fecha y hora
public class TurnoYaReservadoException extends ClinicaException {

    public TurnoYaReservadoException(String mensaje) {
        super(mensaje);
    }

}
