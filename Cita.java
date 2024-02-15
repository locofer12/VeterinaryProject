import java.util.Date;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class Cita {
    private Date fechaCita;

    public Cita(Date fechaCita) {
        this.fechaCita = fechaCita;
    }

    public Date getFechaCita() {
        return fechaCita;
    }

    // Métodos para agregar, ver si paciente existe, editar y eliminar cita
    public static List<Cita> citas = new ArrayList<>();

    public static void agregarCita(Cita cita) {
        citas.add(cita);
        escribirEnArchivo(cita);

    }    private static void escribirEnArchivo(Cita cita) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("cita.txt", true))) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String fechaCitaStr = sdf.format(cita.fechaCita);

            writer.write(
                fechaCitaStr
            );
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static Cita verCita(Date fechaCita) {
        for (Cita cita : citas) {
            if (cita.fechaCita.equals(fechaCita)) {
                return cita;
            }
        }
        return null;
    }

    public static void editarCita(Date fechaCita, Cita nuevaCita) {
        for (int i = 0; i < citas.size(); i++) {
            if (citas.get(i).fechaCita.equals(fechaCita)) {
                citas.set(i, nuevaCita);
                break;
            }
        }
    }

    public static void eliminarCita(Date fechaCita) {
        citas.removeIf(cita -> cita.fechaCita.equals(fechaCita));
    }
}
