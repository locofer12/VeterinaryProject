import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Main extends JFrame {
    private CardLayout cardLayout;
    private JPanel cardPanel;
    private JPanel vacunaSubMenuPanel;
    private JPanel cobroSubMenuPanel;


    public Main() {
        // configuamos de la ventana principal
        setTitle("Clínica Veterinaria");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        cobroSubMenuPanel = new JPanel();
        cobroSubMenuPanel.setLayout(new FlowLayout());
    
        // se inicializa el panel de submenú de vacuna
        vacunaSubMenuPanel = new JPanel();
        vacunaSubMenuPanel.setLayout(new FlowLayout());
        // Crea un panel con CardLayout para mostrar diferentes "páginas" de opciones
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        // Crea paneles para cada "página" de opciones
        JPanel mainMenuPanel = createMainMenuPanel();
        JPanel pacienteSubMenuPanel = createPacienteSubMenuPanel();
        JPanel citaSubMenuPanel = createCitaSubMenuPanel();
        JPanel expedienteSubMenuPanel = createExpedienteSubMenuPanel();
        JPanel vacunaSubMenuPanel = createVacunaSubMenuPanel();

        // Se agregan los paneles al cardPanel con etiquetas para identificarlos
        cardPanel.add(mainMenuPanel, "MainMenu");
        cardPanel.add(pacienteSubMenuPanel, "PacienteSubMenu");
        cardPanel.add(citaSubMenuPanel, "citaSubMenu");
        cardPanel.add(expedienteSubMenuPanel, "expedienteSubMenu");
        cardPanel.add(vacunaSubMenuPanel, "vacunaSubMenu");
        cardPanel.add(cobroSubMenuPanel, "CobroSubMenu");

        // Agrega el panel de submenú de vacuna al cardPanel
        cardPanel.add(vacunaSubMenuPanel, "VacunaSubMenu");



        // Agrega el cardPanel a la ventana principal
        add(cardPanel);

        // muestra la página principal
        cardLayout.show(cardPanel, "MainMenu");
        
    }

    private JPanel createMainMenuPanel() {
        // Crea un panel para el menú principal
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());

        // Crea botones para las opciones principales
        JButton pacienteButton = new JButton("Paciente");
        JButton citaButton = new JButton("Cita");
        JButton vacunaButton = new JButton("Vacuna");
        JButton cobroButton = new JButton("Cobro");
        JButton expedienteButton = new JButton("Expediente");
        JButton salirButton = new JButton("Salir");

        JButton regresarCobroButton = new JButton("Regresar al Menú Principal");
        regresarCobroButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Muestra el menú principal
                cardLayout.show(cardPanel, "MainMenu");
            }
        });
        // Agrega ActionListener a los botones
        pacienteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Muestra el menú de subopciones para "Paciente"
                cardLayout.show(cardPanel, "PacienteSubMenu");
            }
        });
        citaButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                cardLayout.show(cardPanel, "citaSubMenu");
            }
        });
        expedienteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                cardLayout.show(cardPanel, "expedienteSubMenu");
            }
        });



        cobroButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Creamos la logica para la opción "Cobro"
                cardLayout.show(cardPanel, "CobroSubMenu");
                // Limpia el panel de submenú de cobro antes de agregar nuevos componentes
                cobroSubMenuPanel.removeAll();
    
                // Crea y agrega componentes al submenú de cobro
                JLabel precioConsultaLabel = new JLabel("Precio de Consulta:");
                JTextField precioConsultaField = new JTextField(25);
                JLabel precioVacunaLabel = new JLabel("\nPrecio de Vacuna:");
                JTextField precioVacunaField = new JTextField(25);
                JButton calcularPrecioButton = new JButton("\nCalcular Precio Total");
                JLabel resultadoLabel = new JLabel("Precio Total: ");
    
                // Agrega ActionListener al botón "Calcular Precio Total"
                calcularPrecioButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        try {
                            double precioConsulta = Double.parseDouble(precioConsultaField.getText());
                            double precioVacuna = Double.parseDouble(precioVacunaField.getText());
    
                            // Calcular el precio total utilizando la clase Cobro
                            Cobro cobro = new Cobro(precioConsulta, precioVacuna);
                            double precioTotal = cobro.calcularPrecioTotal();
    
                            // Mostrar el resultado
                            resultadoLabel.setText("Precio Total: " + precioTotal);
                        } catch (NumberFormatException ex) {
                            resultadoLabel.setText("Ingrese valores válidos para Consulta y Vacuna.");
                        }
                    }
                });
    
                // componentes al panel de submenú de cobro
                cobroSubMenuPanel.add(precioConsultaLabel);
                cobroSubMenuPanel.add(precioConsultaField);
                cobroSubMenuPanel.add(precioVacunaLabel);
                cobroSubMenuPanel.add(precioVacunaField);
                cobroSubMenuPanel.add(calcularPrecioButton);
                cobroSubMenuPanel.add(resultadoLabel);
                cobroSubMenuPanel.add(regresarCobroButton);

    
                // Actualiza la interfaz gráfica
                cardLayout.show(cardPanel, "CobroSubMenu");
                revalidate();
                repaint();
            }
        });vacunaButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Lógica para la opción "Vacuna"
                cardLayout.show(cardPanel, "VacunaSubMenu");
                // Limpia el panel de submenú de vacuna antes de agregar nuevos componentes
                vacunaSubMenuPanel.removeAll();
    
                // Crea y agrega componentes al submenú de vacuna
                JLabel nombreVacunaLabel = new JLabel("Nombre de la Vacuna:");

                JButton agregarVacunaButton = new JButton("Agregar Vacuna");
    
                // Agrega ActionListener al botón "Agregar Vacuna"
                agregarVacunaButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        // Mostrar un cuadro de diálogo para agregar una nueva vacuna
                        Vacuna.agregarNuevaVacunaDesdeInterfaz();
                    }
                });
    
                // Agrega componentes al panel de submenú de vacuna
                vacunaSubMenuPanel.add(nombreVacunaLabel);

                vacunaSubMenuPanel.add(agregarVacunaButton);
    
                // Actualizamos la interfaz gráfica
                cardLayout.show(cardPanel, "VacunaSubMenu");
                revalidate();
                repaint();
            }
        });
    

        // Se agregan los botones al panel
        panel.add(pacienteButton);
        panel.add(citaButton);
        panel.add(vacunaButton);
        panel.add(cobroButton);
        panel.add(expedienteButton);
        panel.add(salirButton);


        // Agrega ActionListener al botón "Salir"
        salirButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Cerrar la aplicación
                System.exit(0);
            }
        });

        return panel;
    }

    private JPanel createPacienteSubMenuPanel() {
        // hacemos un panel para el menú de subopciones "Paciente"
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());

        // se hace la creacion  de botones para las subopciones de "Paciente"
        JButton agregarPacienteButton = new JButton("Agregar Paciente");
        JButton editarPacienteButton = new JButton("Editar Paciente");
        JButton verPacienteButton = new JButton("Ver Paciente");
        JButton eliminarPacienteButton = new JButton("Eliminar Paciente");
        JButton regresarPacienteButton = new JButton("Regresar a menu principal"); // Botón para regresar

            agregarPacienteButton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            agregarPacienteDesdeInterfaz();
        }
    });

    editarPacienteButton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            editarPacienteDesdeInterfaz();
        }
    });

    verPacienteButton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            verPacienteDesdeInterfaz();
        }
    });

    eliminarPacienteButton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            eliminarPacienteDesdeInterfaz();
        }
    });

        // Agrega ActionListener al botón "Regresar a Paciente"
        regresarPacienteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Mostrar el menú principal "Paciente"
                cardLayout.show(cardPanel, "MainMenu");
            }
        });
        

        // Agrega los botones de subopciones al panel
        panel.add(agregarPacienteButton);
        panel.add(editarPacienteButton);
        panel.add(verPacienteButton);
        panel.add(eliminarPacienteButton);
        panel.add(regresarPacienteButton);

        


        return panel;
    }private void verPacienteDesdeInterfaz() {
        int idVer = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el ID del paciente a ver:"));
    
        Paciente pacienteVer = Paciente.verPaciente(idVer);
    
        if (pacienteVer != null) {
            String infoPaciente = String.format("Nombre: %s%nID: %d%nCategoría: %d%nRaza: %s%nSexo: %s%nFecha de Inscripción: %tF%nFecha de Nacimiento: %tF%nPeso: %.2f%n",
                    pacienteVer.getNombrePaciente(), pacienteVer.getIdPaciente(), pacienteVer.getCategoria(),
                    pacienteVer.getRaza(), pacienteVer.getSexo(), pacienteVer.getFechaInscripcion(),
                    pacienteVer.getFechaNacimiento(), pacienteVer.getPeso());
    
            JOptionPane.showMessageDialog(null, infoPaciente, "Información del Paciente", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "No se encontró un paciente con el ID proporcionado.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    //cita 
        private JPanel createCitaSubMenuPanel() {
    JPanel panel = new JPanel();
    panel.setLayout(new FlowLayout());

    JButton agregarCitaButton = new JButton("Agregar Cita");
    JButton verCitaButton = new JButton("Ver Cita");
    JButton editarCitaButton = new JButton("Editar Cita");
    JButton eliminarCitaButton = new JButton("Eliminar Cita");
    JButton regresarMenuButton = new JButton("Regresar al menú principal");
            // Hacemos los actionlisteners
    agregarCitaButton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            agregarCitaDesdeInterfaz();
        }
    });

    verCitaButton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            verCitaDesdeInterfaz();
        }
    });

    editarCitaButton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            editarCitaDesdeInterfaz();
        }
    });

    eliminarCitaButton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            eliminarCitaDesdeInterfaz();
        }
    });

    regresarMenuButton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            cardLayout.show(cardPanel, "MainMenu");
        }
    });

    panel.add(agregarCitaButton);
    panel.add(verCitaButton);
    panel.add(editarCitaButton);
    panel.add(eliminarCitaButton);
    panel.add(regresarMenuButton);

    return panel;
}
// Metodo para editar una cita desde la interfaz gráfica
private void editarCitaDesdeInterfaz() {
    // Obtiene la fecha de la cita desde el usuario
    String fechaString = JOptionPane.showInputDialog("Ingrese la fecha de la cita a editar (Formato: dd/MM/yyyy):");
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    Date fechaCita;

    try {
        fechaCita = dateFormat.parse(fechaString);
    } catch (ParseException ex) {
        JOptionPane.showMessageDialog(null, "Formato de fecha incorrecto. Use dd/MM/yyyy.", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    // Busca la cita por fecha
    Cita citaEditar = Cita.verCita(fechaCita);

    if (citaEditar != null) {
        // Obtiene la nueva fecha de la cita desde el usuario
        String nuevaFechaString = JOptionPane.showInputDialog("Ingrese la nueva fecha de la cita (Formato: dd/MM/yyyy):");

        try {
            Date nuevaFechaCita = dateFormat.parse(nuevaFechaString);

            // Crea una nueva cita con la nueva fecha
            Cita nuevaCita = new Cita(nuevaFechaCita);

            // Edita la cita en la lista
            Cita.editarCita(fechaCita, nuevaCita);

            // Edita la línea correspondiente en el archivo cita.txt
            editarLineaCita(fechaCita, nuevaFechaCita);

            JOptionPane.showMessageDialog(null, "Cita editada exitosamente.");
        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(null, "Formato de fecha incorrecto. Use dd/MM/yyyy.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    } else {
        JOptionPane.showMessageDialog(null, "No se encontró una cita en la fecha proporcionada.", "Error", JOptionPane.ERROR_MESSAGE);
    }
}

// Se hace un metodo para editar una línea específica en el archivo cita.txt
private void editarLineaCita(Date fechaAntigua, Date fechaNueva) {
    try {
        List<String> lines = Files.readAllLines(Paths.get("cita.txt"));

        for (int i = 0; i < lines.size(); i++) {
            if (lines.get(i).trim().equals(new SimpleDateFormat("dd/MM/yyyy").format(fechaAntigua))) {
                lines.set(i, new SimpleDateFormat("dd/MM/yyyy").format(fechaNueva));
                break;
            }
        }

        Files.write(Paths.get("cita.txt"), lines);
    } catch (IOException ex) {
        ex.printStackTrace();
    }
}

//  agrega una cita desde la interfaz gráfica
private void agregarCitaDesdeInterfaz() {
    // Obtiene la fecha de la cita desde el usuario
    String fechaString = JOptionPane.showInputDialog("Ingrese la fecha de la cita (Formato: dd/MM/yyyy):");
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    Date fechaCita;

    try {
        fechaCita = dateFormat.parse(fechaString);
    } catch (ParseException ex) {
        JOptionPane.showMessageDialog(null, "Formato de fecha incorrecto. Use dd/MM/yyyy.", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    // Crea una nueva cita
    Cita nuevaCita = new Cita(fechaCita);

    // Agrega la cita a la lista
    Cita.agregarCita(nuevaCita);

    // Guarda la información de la cita en el archivo cita.txt
    guardarCitaEnArchivo(nuevaCita);

    // Imprimimos el mensaje de éxito
    JOptionPane.showMessageDialog(null, "Cita agregada exitosamente.");
}

// Metodo para guardar la información de la cita en el archivo cita.txt
private void guardarCitaEnArchivo(Cita cita) {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter("cita.txt", true))) {
        String citaInfo = String.format("%tF%n", cita.getFechaCita());
        writer.write(citaInfo);
    } catch (IOException ex) {
        ex.printStackTrace();
    }
}

// Metodo para ver la información de una cita desde la interfaz gráfica
private void verCitaDesdeInterfaz() {
    // Se obtiene la fecha de la cita desde el usuario
    String fechaString = JOptionPane.showInputDialog("Ingrese la fecha de la cita a ver (Formato: dd/MM/yyyy):");
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    Date fechaCita;

    try {
        fechaCita = dateFormat.parse(fechaString);
    } catch (ParseException ex) {
        JOptionPane.showMessageDialog(null, "Formato de fecha incorrecto. Use dd/MM/yyyy.", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    // Buscar la cita por fecha
    Cita citaVer = Cita.verCita(fechaCita);

    if (citaVer != null) {
        JOptionPane.showMessageDialog(null, "Fecha de la Cita: " + dateFormat.format(citaVer.getFechaCita()), "Información de la Cita", JOptionPane.INFORMATION_MESSAGE);
    } else {
        JOptionPane.showMessageDialog(null, "No se encontró una cita en la fecha proporcionada.", "Error", JOptionPane.ERROR_MESSAGE);
    }
}

// Metodo para eliminar una cita desde la interfaz gráfica
private void eliminarCitaDesdeInterfaz() {
    // Obtener la fecha de la cita desde el usuario
    String fechaString = JOptionPane.showInputDialog("Ingrese la fecha de la cita a eliminar (Formato: dd/MM/yyyy):");
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    Date fechaCita;

    try {
        fechaCita = dateFormat.parse(fechaString);
    } catch (ParseException ex) {
        JOptionPane.showMessageDialog(null, "Formato de fecha incorrecto. Use dd/MM/yyyy.", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    // Elimina la cita de la lista
    Cita.eliminarCita(fechaCita);

    // Elimina la línea correspondiente en el archivo cita.txt
    eliminarLineaCita(fechaCita);

    JOptionPane.showMessageDialog(null, "Cita eliminada exitosamente.");
}

// Metodo para eliminar una línea específica del archivo cita.txt
private void eliminarLineaCita(Date fechaCita) {
    try {
        List<String> lines = Files.readAllLines(Paths.get("cita.txt"));
        lines.removeIf(line -> line.trim().equals(new SimpleDateFormat("dd/MM/yyyy").format(fechaCita)));
        Files.write(Paths.get("cita.txt"), lines);
    } catch (IOException ex) {
        ex.printStackTrace();
    }
}

    //expediente 
    private JPanel createExpedienteSubMenuPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());

        JButton agregarInfoButton = new JButton("Agregar notas al Paciente");
        JButton verInfoButton = new JButton("Ver Información del Paciente");
        JButton eliminarPacienteButton = new JButton("Eliminar Paciente");
        JButton regresarMenuButton = new JButton("Regresar al menú principal");

        agregarInfoButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                agregarInfoPacienteDesdeInterfaz();
            }
        });


        verInfoButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Solicitar al usuario el ID del paciente
                int idPaciente = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el ID del paciente:"));
    
                // Obtener y mostrar la información del paciente desde Expediente
                    Paciente pacienteVerInfo = Paciente.verPaciente(idPaciente);

                    String infoPaciente = String.format("Nombre: %s%nID: %d%nCategoría: %d%nRaza: %s%nSexo: %s%nFecha de Inscripción: %tF%nFecha de Nacimiento: %tF%nPeso: %.2f%n %s%n" ,
                    pacienteVerInfo.getNombrePaciente(), pacienteVerInfo.getIdPaciente(), pacienteVerInfo.getCategoria(),
                    pacienteVerInfo.getRaza(), pacienteVerInfo.getSexo(), pacienteVerInfo.getFechaInscripcion(),
                    pacienteVerInfo.getFechaNacimiento(), pacienteVerInfo.getPeso(), Expediente.verInformacionPacienteDesdeExpediente(idPaciente));

            JOptionPane.showMessageDialog(null, infoPaciente, "Notas del Paciente", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        eliminarPacienteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                eliminarPacienteDesdeInterfaz();
            }
        });

        regresarMenuButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "MainMenu");
            }
        });

        panel.add(agregarInfoButton);
        panel.add(verInfoButton);
        panel.add(eliminarPacienteButton);
        panel.add(regresarMenuButton);

        return panel;
    }

    // Agrega información a un paciente desde la interfaz gráfica
    private void agregarInfoPacienteDesdeInterfaz() {
        int idAgregarInfo = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el ID del paciente al que desea agregar información:"));

        Paciente pacienteAgregarInfo = Paciente.verPaciente(idAgregarInfo);

        if (pacienteAgregarInfo != null) {
            String nuevaInformacion = JOptionPane.showInputDialog("Ingrese la nueva información para el paciente:");

            // Agrega la información al expediente del paciente
            Expediente.agregarInformacionDesdeInterfaz(idAgregarInfo, nuevaInformacion);

            // Guarda la información en el archivo paciente.txt
            guardarInformacionPacienteEnArchivo(idAgregarInfo, nuevaInformacion);

            JOptionPane.showMessageDialog(null, "Información agregada al paciente exitosamente.");
        } else {
            JOptionPane.showMessageDialog(null, "No se encontró un paciente con el ID proporcionado.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Metodo para guardar la información de un paciente en el archivo paciente.txt
    private void guardarInformacionPacienteEnArchivo(int id, String nuevaInformacion) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("paciente.txt", true))) {
            String infoPaciente = String.format("%d;%s%n", id, nuevaInformacion);
            writer.write(infoPaciente);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    // Metodo para ver la información de un paciente desde la interfaz gráfica
    private void verInfoPacienteDesdeInterfaz() {
        int idVerInfo = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el ID del paciente del que desea ver la información:"));

        Paciente pacienteVerInfo = Paciente.verPaciente(idVerInfo);

        if (pacienteVerInfo != null) {
            String infoPaciente = String.format("Nombre: %s%nID: %d%nCategoría: %d%nRaza: %s%nSexo: %s%nFecha de Inscripción: %tF%nFecha de Nacimiento: %tF%nPeso: %.2f%n",
                    pacienteVerInfo.getNombrePaciente(), pacienteVerInfo.getIdPaciente(), pacienteVerInfo.getCategoria(),
                    pacienteVerInfo.getRaza(), pacienteVerInfo.getSexo(), pacienteVerInfo.getFechaInscripcion(),
                    pacienteVerInfo.getFechaNacimiento(), pacienteVerInfo.getPeso());
    
            JOptionPane.showMessageDialog(null, infoPaciente, "Información del Paciente", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "No se encontró un paciente con el ID proporcionado.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Metodo para eliminar un paciente desde la interfaz gráfica
    private void eliminarPacienteDesdeInterfaz() {
        int idEliminar = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el ID del paciente a eliminar:"));

        Paciente pacienteEliminar = Paciente.verPaciente(idEliminar);

        if (pacienteEliminar != null) {
            // Elimina el paciente de la lista
            Paciente.eliminarPaciente(idEliminar);

            // Elimina la línea correspondiente en el archivo paciente.txt
            eliminarLineaPaciente(idEliminar);

            JOptionPane.showMessageDialog(null, "Paciente eliminado exitosamente.");
        } else {
            JOptionPane.showMessageDialog(null, "No se encontró un paciente con el ID proporcionado.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


    //Vacuna sub menu  
        private JPanel createVacunaSubMenuPanel(){
            JPanel panel = new JPanel();
            panel.setLayout(new FlowLayout());

            JButton agregarVacunaButton =new JButton(" Agregar Informacion de vacuna");
            JButton regresarMenuButton =new JButton("Regresar al menu principal");
            agregarVacunaButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    // Mostrar un cuadro de diálogo para agregar una nueva vacuna
                    Vacuna.agregarNuevaVacunaDesdeInterfaz();
                }
            });
            //actionlistener de regresar 
            regresarMenuButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e){
                    cardLayout.show(cardPanel, "MainMenu");
                }
            });

            panel.add(agregarVacunaButton);
  
            panel.add(regresarMenuButton);
            
            return panel;
        }
// Metodo para agregar un paciente desde la interfaz gráfica
    private void agregarPacienteDesdeInterfaz() {
        String nombre = JOptionPane.showInputDialog("Ingrese el nombre del paciente:");
        int id = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el ID del paciente:"));
        int categoria = Integer.parseInt(JOptionPane.showInputDialog("Ingrese la categoría del paciente: (1-perro, 2- gato)"));
        String raza = JOptionPane.showInputDialog("Ingrese la raza del paciente:");
        String sexo = JOptionPane.showInputDialog("Ingrese el sexo del paciente:");
        Date fechaInscripcion = new Date();  
        Date fechaNacimiento = new Date();  
        double peso = Double.parseDouble(JOptionPane.showInputDialog("Ingrese el peso del paciente:"));

        // Crea un nuevo paciente
        Paciente nuevoPaciente = new Paciente(nombre, id, categoria, raza, sexo, fechaInscripcion, fechaNacimiento, peso);

        // Agrega el paciente a la lista
        Paciente.nuevoPaciente(nuevoPaciente);

        // Guardar la información del paciente en el archivo paciente.txt
        guardarPacienteEnArchivo(nuevoPaciente);

        // Mensaje de éxito
        JOptionPane.showMessageDialog(null, "Paciente agregado exitosamente.");
    }
    private void editarPacienteDesdeInterfaz() {
        int idEditar = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el ID del paciente a editar:"));
        Paciente pacienteEditar = Paciente.verPaciente(idEditar);

        if (pacienteEditar != null) {
            String nombre = JOptionPane.showInputDialog("Nombre actual: " + pacienteEditar.getNombrePaciente() +
                    "\nIngrese el nuevo nombre del paciente:");
            int categoria = Integer.parseInt(JOptionPane.showInputDialog("Categoría actual: " + pacienteEditar.getCategoria() +
                    "\nIngrese la nueva categoría del paciente:"));
            String raza = JOptionPane.showInputDialog("Raza actual: " + pacienteEditar.getRaza() +
                    "\nIngrese la nueva raza del paciente:");
            String sexo = JOptionPane.showInputDialog("Sexo actual: " + pacienteEditar.getSexo() +
                    "\nIngrese el nuevo sexo del paciente:");
            double peso = Double.parseDouble(JOptionPane.showInputDialog("Peso actual: " + pacienteEditar.getPeso() +
                    "\nIngrese el nuevo peso del paciente:"));

            Paciente pacienteActualizado = new Paciente(nombre, idEditar, categoria, raza, sexo,
                    pacienteEditar.getFechaInscripcion(), pacienteEditar.getFechaNacimiento(), peso);

            Paciente.editarPaciente(idEditar, pacienteActualizado);

            // Actualiza la información del paciente en el archivo paciente.txt
            // En este caso reescribimos todo el archivo con la información actualizada
            reescribirArchivoPacientes();

            JOptionPane.showMessageDialog(null, "Paciente editado exitosamente.");
        } else {
            JOptionPane.showMessageDialog(null, "No se encontró un paciente con el ID proporcionado.");
        }
    }
    private void reescribirArchivoPacientes() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("paciente.txt"))) {
            for (Paciente paciente : Paciente.getPacientes()) {
                String pacienteInfo = String.format("%s,%d,%d,%s,%s,%tF,%tF,%.2f%n",
                        paciente.getNombrePaciente(), paciente.getIdPaciente(), paciente.getCategoria(),
                        paciente.getRaza(), paciente.getSexo(), paciente.getFechaInscripcion(),
                        paciente.getFechaNacimiento(), paciente.getPeso());
        
                        writer.write(pacienteInfo);
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        



    private void guardarPacienteEnArchivo(Paciente paciente) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("paciente.txt", true))) {
            
            String pacienteInfo = String.format("%s,%d,%d,%s,%s,%tF,%tF,%.2f%n",
                    paciente.getNombrePaciente(), paciente.getIdPaciente(), paciente.getCategoria(),
                    paciente.getRaza(), paciente.getSexo(), paciente.getFechaInscripcion(),
                    paciente.getFechaNacimiento(), paciente.getPeso());

            writer.write(pacienteInfo);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }    private void eliminarLineaPaciente(int id) {
        try {
            File inputFile = new File("paciente.txt");
            File tempFile = new File("temp.txt");

            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

            String currentLine;

            while ((currentLine = reader.readLine()) != null) {
                // Buscar y omitir la línea correspondiente al paciente a eliminar
                if (currentLine.contains("," + id + ",")) {
                    continue;
                }
                writer.write(currentLine + System.getProperty("line.separator"));
            }

            writer.close();
            reader.close();

            // Reemplazar el archivo original con el archivo temporal
            tempFile.renameTo(inputFile);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Main().setVisible(true);
            }
        });
    }
}
