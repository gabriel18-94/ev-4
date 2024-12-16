package cl.inacap.evaluacion4;

public class Indicador {
    private String fecha;
    private double valor;
    private String tipo;

    // Constructor completo
    public Indicador(String fecha, double valor, String tipo) {
        this.fecha = fecha;
        this.valor = valor;
        this.tipo = tipo;
    }

    // Getters
    public String getFecha() {
        return fecha;
    }

    public double getValor() {
        return valor;
    }

    public String getTipo() {
        return tipo;
    }

    // Setters (opcional, si necesitas modificar los valores después de crear el objeto)
    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}