public class Cobro {
    private double precioConsulta;
    private double precioVacuna;
    private double precioTotal;

    public Cobro(double precioConsulta, double precioVacuna) {
        this.precioConsulta = precioConsulta;
        this.precioVacuna = precioVacuna;
    }

    public double calcularPrecioTotal() {
        precioTotal = precioConsulta + precioVacuna;
        return precioTotal;
    }
}
