
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Paciente {
    private String nombrePaciente;
    private int idPaciente;
    private int categoria;
    private String raza;
    private String sexo;
    private Date fechaInscripcion;
    private Date fechaNacimiento;
    private double peso;
    private String informacion;
    public static List<Paciente> getPacientes() {
        return pacientes;
    }
    

    public String getNombrePaciente() {
        return nombrePaciente;
    }

    public void setNombrePaciente(String nombrePaciente) {
        this.nombrePaciente = nombrePaciente;
    }

    public int getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(int idPaciente) {
        this.idPaciente = idPaciente;
    }

    public int getCategoria() {
        return categoria;
    }

    public void setCategoria(int categoria) {
        this.categoria = categoria;
    }

    public String getRaza() {
        return raza;
    }

    public void setRaza(String raza) {
        this.raza = raza;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public Date getFechaInscripcion() {
        return fechaInscripcion;
    }

    public void setFechaInscripcion(Date fechaInscripcion) {
        this.fechaInscripcion = fechaInscripcion;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public String getInformacion() {
        return informacion;
    }

    public void setInformacion(String informacion) {
        this.informacion = informacion;
    }

    public Paciente(String nombrePaciente, int idPaciente, int categoria, String raza, String sexo, Date fechaInscripcion, Date fechaNacimiento, double peso) {
        this.nombrePaciente = nombrePaciente;
        this.idPaciente = idPaciente;
        this.categoria = categoria;
        this.raza = raza;
        this.sexo = sexo;
        this.fechaInscripcion = fechaInscripcion;
        this.fechaNacimiento = fechaNacimiento;
        this.peso = peso;
        this.informacion = "";
    }

    public static List<Paciente> pacientes = new ArrayList<>();

    public static void nuevoPaciente(Paciente paciente) {
        pacientes.add(paciente);
        escribirEnArchivo(paciente);

    }
private static void escribirEnArchivo(Paciente paciente) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("paciente.txt", true))) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String fechaNacimientoStr = sdf.format(paciente.getFechaNacimiento());
            String fechaInscripcionStr = sdf.format(paciente.getFechaInscripcion());

            writer.write(
                paciente.getIdPaciente() + ";" +
                paciente.getNombrePaciente() + ";" +
                paciente.getCategoria() + ";" +
                paciente.getRaza() + ";" +
                paciente.getSexo() + ";" +
                fechaInscripcionStr + ";" +
                fechaNacimientoStr + ";" +
                paciente.getPeso() + ";" +
                paciente.getInformacion()
            );
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void editarPaciente(int id, Paciente paciente) {
        for (int i = 0; i < pacientes.size(); i++) {
            if (pacientes.get(i).idPaciente == id) {
                pacientes.set(i, paciente);
                break;
            }
        }
    }

    public static void eliminarPaciente(int id) {
        pacientes.removeIf(paciente -> paciente.idPaciente == id);
    }
    public static Paciente verPaciente(int id) {
        try (BufferedReader reader = new BufferedReader(new FileReader("paciente.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Paciente paciente = parsearLineaPaciente(line);
                if (paciente != null && paciente.getIdPaciente() == id) {
                    return paciente;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static Paciente parsearLineaPaciente(String line) {
        String[] partes = line.split(";");
        try {
            int id = Integer.parseInt(partes[0]);
            String nombrePaciente = partes[1];
            int categoria = Integer.parseInt(partes[2]);
            String raza = partes[3];
            String sexo = partes[4];
            Date fechaInscripcion = new SimpleDateFormat("yyyy-MM-dd").parse(partes[5]);
            Date fechaNacimiento = new SimpleDateFormat("yyyy-MM-dd").parse(partes[6]);
            double peso = Double.parseDouble(partes[7]);
            return new Paciente(nombrePaciente, id, categoria, raza, sexo, fechaInscripcion, fechaNacimiento, peso);
        } catch (Exception e) {
            System.err.println("Error al parsear línea: " + line);
            e.printStackTrace();
            return null;
        }
    }

    public String verInformacionPaciente() {
        return informacion;
    }

    public void agregarInformacion(String nuevaInformacion) {
        this.informacion = nuevaInformacion;
    }

    public static void eliminarLineaPaciente(int id) {
        try {
            List<String> lines = Files.readAllLines(Paths.get("paciente.txt"));
            lines.removeIf(line -> line.trim().startsWith(id + ";"));
            Files.write(Paths.get("paciente.txt"), lines);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
        public static void cargarPacientesDesdeArchivo() {
        try {
            List<String> lines = Files.readAllLines(Paths.get("paciente.txt"));

            for (String line : lines) {
                String[] parts = line.split("\\|");
                if (parts.length == 8) {
                    String nombrePaciente = parts[0];
                    int idPaciente = Integer.parseInt(parts[1]);
                    int categoria = Integer.parseInt(parts[2]);
                    String raza = parts[3];
                    String sexo = parts[4];
                    Date fechaInscripcion = new SimpleDateFormat("yyyy-MM-dd").parse(parts[5]);
                    Date fechaNacimiento = new SimpleDateFormat("yyyy-MM-dd").parse(parts[6]);
                    double peso = Double.parseDouble(parts[7]);

                    Paciente nuevoPaciente = new Paciente(nombrePaciente, idPaciente, categoria, raza, sexo,
                            fechaInscripcion, fechaNacimiento, peso);

                    // Agregar el paciente a la lista de pacientes
                    pacientes.add(nuevoPaciente);
                }
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }
        public static void modificarArchivoPaciente(Paciente paciente) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("paciente.txt", true))) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String fechaNacimientoStr = sdf.format(paciente.getFechaNacimiento());
            String fechaInscripcionStr = sdf.format(paciente.getFechaInscripcion());

            // Crear la línea con la información actualizada del paciente
            String nuevaLinea = paciente.getIdPaciente() + ";" +
                                paciente.getNombrePaciente() + ";" +
                                paciente.getCategoria() + ";" +
                                paciente.getRaza() + ";" +
                                paciente.getSexo() + ";" +
                                fechaInscripcionStr + ";" +
                                fechaNacimientoStr + ";" +
                                paciente.getPeso() + ";" +
                                paciente.getInformacion();

            // Eliminar la antigua línea del paciente
            eliminarLineaPaciente(paciente.getIdPaciente());

            // Escribir la nueva línea en el archivo
            writer.write(nuevaLinea);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
