// ==================== AGREGACIÓN ====================
// Un teléfono puede existir aunque la persona lo cambie o lo venda
public class Telefono {
    private String numero;
    private String tipo; // "móvil", "casa", etc.

    public Telefono(String numero, String tipo) {
        this.numero = numero;
        this.tipo = tipo;
    }

    @Override
    public String toString() {
        return tipo + ": " + numero;
    }
}
