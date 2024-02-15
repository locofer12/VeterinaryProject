 import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

public class Vacuna {
    private String nombreVacuna;
    private Date fechaVacuna;

    public String getNombreVacuna() {
        return nombreVacuna;
    }

    public void setNombreVacuna(String nombreVacuna) {
        this.nombreVacuna = nombreVacuna;
    }

    public Date getFechaVacuna() {
        return fechaVacuna;
    }

    public void setFechaVacuna(Date fechaVacuna) {
        this.fechaVacuna = fechaVacuna;
    }

    public static List<Vacuna> getVacunas() {
        return vacunas;
    }

    public static void setVacunas(List<Vacuna> vacunas) {
        Vacuna.vacunas = vacunas;
    }

    public Vacuna(String nombreVacuna, Date fechaVacuna) {
        this.nombreVacuna = nombreVacuna;
        this.fechaVacuna = fechaVacuna;
    }public static void agregarNuevaVacunaDesdeInterfaz() {
    // Solicita el ID del paciente
    int idPaciente = obtenerIdPacienteDesdeInterfaz();

    // Verifica si el paciente existe
    Paciente paciente = Paciente.verPaciente(idPaciente);
    if (paciente == null) {
        JOptionPane.showMessageDialog(null, "El paciente con ID " + idPaciente + " no existe. Por favor, verifique el ID.");
        return;
    }

    // Solicita el nombre de la vacuna
    String nombreVacuna = JOptionPane.showInputDialog("Ingrese el nombre de la vacuna:");

    // Solicita la fecha de la vacuna
    String fechaVacunaStr = JOptionPane.showInputDialog("Ingrese la fecha de la vacuna (formato yyyy-MM-dd):");

    try {
        Date fechaVacuna = new SimpleDateFormat("yyyy-MM-dd").parse(fechaVacunaStr);
        Vacuna nuevaVacuna = new Vacuna(nombreVacuna, fechaVacuna);
        vacunas.add(nuevaVacuna);
        JOptionPane.showMessageDialog(null, "Vacuna agregada exitosamente para el paciente con ID " + idPaciente);
    } catch (ParseException e) {
        JOptionPane.showMessageDialog(null, "Error al parsear la fecha. Asegúrese de usar el formato correcto.");
    }
}

private static int obtenerIdPacienteDesdeInterfaz() {
    // Solicita el ID del paciente
    String idPacienteStr = JOptionPane.showInputDialog("Ingrese el ID del paciente:");
    
    try {
        int idPaciente = Integer.parseInt(idPacienteStr);
        return idPaciente;
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(null, "Error al parsear el ID. Asegúrese de ingresar un número válido.");
        return obtenerIdPacienteDesdeInterfaz(); // Volver a solicitar el ID si hay un error
    }
}


        // Método para agregar fecha de vacuna
    public static List<Vacuna> vacunas = new ArrayList<>();

    public static void agregarFechaVacuna(Vacuna vacuna) {
        vacunas.add(vacuna);
    }
}