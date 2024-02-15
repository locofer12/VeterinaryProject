import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Expediente  {

    private static Map<Integer, Expediente> expedientes = new HashMap<>();

    private int idPaciente;
    private String informacion;

    public Expediente(int idPaciente) {
        this.idPaciente = idPaciente;
        this.informacion = "";
    }
    public static String verInformacionPacienteDesdeExpediente(int idPaciente) {
        try (BufferedReader reader = new BufferedReader(new FileReader("expediente.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] partes = line.split(";");
                int id = Integer.parseInt(partes[0]);

                if (id == idPaciente) {
                    // Encontramos la información del paciente
                    String informacionPaciente = partes[1];

                    // Recupera la nota correspondiente desde expedientes
                    Expediente expediente = expedientes.get(idPaciente);
                    String notaExpediente = expediente != null ? expediente.informacion : "";

                    // Combinar la información del paciente y la nota del expediente
                    return "Información del Paciente:\n" + informacionPaciente  + notaExpediente;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Si no se encuentra información para el ID dado
        return "No se encontraron notas para el paciente con ID: " + idPaciente;
    }
    

    public void agregarInformacion(String nuevaInformacion) {
        informacion = nuevaInformacion;
        expedientes.put(idPaciente, this);
        escribirExpedientesEnArchivo(expedientes);
    }

    public static void agregarInformacionDesdeInterfaz(int idPaciente, String nuevaInformacion) {
        // Recuperar expediente existente o crear uno nuevo
        Expediente expediente = expedientes.getOrDefault(idPaciente, new Expediente(idPaciente));
        expediente.agregarInformacion(nuevaInformacion);
    }

    private static void escribirExpedientesEnArchivo(Map<Integer, Expediente> expedientes) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("expediente.txt", true))) {
            for (Expediente expediente : expedientes.values()) {
                writer.write(expediente.idPaciente + ";" + expediente.informacion);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
