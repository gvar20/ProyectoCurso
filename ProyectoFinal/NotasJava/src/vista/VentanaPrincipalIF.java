package vista;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import controlador.*;
import modelo.Nota;
import modelo.Usuario;
import java.awt.*;
import java.util.List;

public class VentanaPrincipalIF extends JFrame {
    private static final long serialVersionUID = 1L;
    private UsuarioController usuarioController;
    private NotaController notaController;
    private NotasCompartidasController notasCompartidasController;
    
    private JTable tablaMisNotas;
    private JTable tablaNotasCompartidas;
    private DefaultTableModel modeloMisNotas;
    private DefaultTableModel modeloNotasCompartidas;
    private JTextArea txtContenido;
    private JTextField txtTitulo;
    private JButton btnNueva;
    private JButton btnEditar;
    private JButton btnEliminar;
    private JButton btnCompartir;
    private int notaSeleccionadaId = -1;

    public VentanaPrincipalIF(UsuarioController usuarioController) {
        this.usuarioController = usuarioController;
        this.notaController = new NotaController(usuarioController);
        this.notasCompartidasController = new NotasCompartidasController(usuarioController);
        inicializarComponentes();
        cargarNotas();
    }

    private void inicializarComponentes() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Notas - " + usuarioController.getUsuarioActual().getApodo());
        setSize(800, 600);
        setLocationRelativeTo(null);

        // Panel principal con split
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        
        // Panel izquierdo con tabs
        JTabbedPane tabbedPane = new JTabbedPane();
        
        // Tab de mis notas
        JPanel misNotasPanel = new JPanel(new BorderLayout());
        modeloMisNotas = new DefaultTableModel(
            new Object[]{"ID", "Título", "Fecha Creación"}, 0
        );
        tablaMisNotas = new JTable(modeloMisNotas);
        misNotasPanel.add(new JScrollPane(tablaMisNotas), BorderLayout.CENTER);
        tabbedPane.addTab("Mis Notas", misNotasPanel);
        
        // Tab de notas compartidas
        JPanel notasCompartidasPanel = new JPanel(new BorderLayout());
        modeloNotasCompartidas = new DefaultTableModel(
            new Object[]{"ID", "Título", "Compartido por", "Fecha"}, 0
        );
        tablaNotasCompartidas = new JTable(modeloNotasCompartidas);
        notasCompartidasPanel.add(new JScrollPane(tablaNotasCompartidas), BorderLayout.CENTER);
        tabbedPane.addTab("Compartidas Conmigo", notasCompartidasPanel);

        // Panel derecho
        JPanel panelDerecho = new JPanel(new BorderLayout());
        
        // Panel de título y contenido
        JPanel panelEdicion = new JPanel(new BorderLayout());

        // Crear y configurar el título
        txtTitulo = new JTextField();
        txtTitulo.setPreferredSize(new Dimension(200, 30)); 

        // Crear y configurar el contenido
        txtContenido = new JTextArea();
        txtContenido.setLineWrap(true); 
        txtContenido.setWrapStyleWord(true); 

        // Crear un panel para el título y agregar la etiqueta y el campo de texto
        JPanel panelTitulo = new JPanel(new BorderLayout());
        panelTitulo.add(new JLabel("Título:"), BorderLayout.WEST);
        panelTitulo.add(txtTitulo, BorderLayout.CENTER);

        // Crear un panel para el contenido y agregar la etiqueta y el área de texto
        JPanel panelContenido = new JPanel(new BorderLayout());
        panelContenido.add(new JLabel("Contenido:"), BorderLayout.NORTH);
        panelContenido.add(new JScrollPane(txtContenido), BorderLayout.CENTER);

        // Agregar los componentes al panel principal
        panelEdicion.add(panelTitulo, BorderLayout.NORTH);
        panelEdicion.add(panelContenido, BorderLayout.CENTER);

        
        // Panel de botones
        JPanel panelBotones = new JPanel(new FlowLayout());
        btnNueva = new JButton("Nueva Nota");
        btnEditar = new JButton("Guardar Cambios");
        btnEliminar = new JButton("Eliminar");
        btnCompartir = new JButton("Compartir");

        panelBotones.add(btnNueva);
        panelBotones.add(btnEditar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnCompartir);

        panelDerecho.add(panelEdicion, BorderLayout.CENTER);
        panelDerecho.add(panelBotones, BorderLayout.SOUTH);

        // Configurar split
        splitPane.setLeftComponent(tabbedPane);
        splitPane.setRightComponent(panelDerecho);
        splitPane.setDividerLocation(400);

        getContentPane().add(splitPane);

        // Eventos
        configurarEventos();
    }

    private void configurarEventos() {
        // Eventos de los botones sin expresiones lambda
        btnNueva.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                nuevaNota();
            }
        });

        btnEditar.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                guardarCambios();
            }
        });

        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                eliminarNota();
            }
        });

        btnCompartir.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                compartirNota();
            }
        });

        tablaMisNotas.getSelectionModel().addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            @Override
            public void valueChanged(javax.swing.event.ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    int row = tablaMisNotas.getSelectedRow();
                    if (row >= 0) {
                        notaSeleccionadaId = (int) tablaMisNotas.getValueAt(row, 0);
                        cargarNotaSeleccionada();
                        tablaMisNotas.clearSelection();
                        tablaNotasCompartidas.clearSelection();
                    }
                }
            }
        });
        
        tablaNotasCompartidas.getSelectionModel().addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            @Override
            public void valueChanged(javax.swing.event.ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    int row = tablaNotasCompartidas.getSelectedRow();
                    if (row >= 0) {
                        notaSeleccionadaId = (int) tablaNotasCompartidas.getValueAt(row, 0);
                        cargarNotaSeleccionada();
                        tablaMisNotas.clearSelection();
                        tablaNotasCompartidas.clearSelection();
                    }
                }
            }
        });
        
        
    }

    private void cargarNotas() {
        // Limpiar tablas
        modeloMisNotas.setRowCount(0);
        modeloNotasCompartidas.setRowCount(0);

        // Cargar mis notas
        List<Nota> misNotas = notaController.obtenerNotasUsuario();
        if (misNotas != null) {
            for (Nota nota : misNotas) {
                modeloMisNotas.addRow(new Object[]{
                    nota.getId(),
                    nota.getTitulo(),
                    nota.getFechaCreacion()
                });
            }
        }

        // Cargar notas compartidas
        List<Nota> notasCompartidas = notaController.obtenerNotasCompartidas();
        if (notasCompartidas != null) {
            for (Nota nota : notasCompartidas) {
                Usuario propietario = usuarioController.buscarUsuarioPorId(nota.getUsuarioId());
                modeloNotasCompartidas.addRow(new Object[]{
                    nota.getId(),
                    nota.getTitulo(),
                    propietario != null ? propietario.getApodo() : "Desconocido",
                    nota.getFechaCreacion()
                });
            }
        }
    }

    private void nuevaNota() {
        notaSeleccionadaId = -1;
        txtTitulo.setText("");
        txtContenido.setText("");
        btnEditar.setEnabled(true);
        btnEliminar.setEnabled(true);
        btnCompartir.setEnabled(true);
    }

    private void guardarCambios() {
        String titulo = txtTitulo.getText();
        String contenido = txtContenido.getText();

        if (titulo.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "El título no puede estar vacío",
                "Error",
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        boolean exito;
        if (notaSeleccionadaId == -1) {
            exito = notaController.crearNota(titulo, contenido);
        } else {
            exito = notaController.actualizarNota(notaSeleccionadaId, titulo, contenido);
        }

        if (exito) {
            JOptionPane.showMessageDialog(this,
                "Nota guardada exitosamente",
                "Éxito",
                JOptionPane.INFORMATION_MESSAGE);
            cargarNotas();
        } else {
            JOptionPane.showMessageDialog(this,
                "Error al guardar la nota",
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private void eliminarNota() {
        if (notaSeleccionadaId == -1) {
            JOptionPane.showMessageDialog(this,
                "Por favor seleccione una nota para eliminar",
                "Error",
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        int confirmacion = JOptionPane.showConfirmDialog(this,
            "¿Está seguro de eliminar esta nota?",
            "Confirmar eliminación",
            JOptionPane.YES_NO_OPTION);

        if (confirmacion == JOptionPane.YES_OPTION) {
            if (notaController.eliminarNota(notaSeleccionadaId)) {
                JOptionPane.showMessageDialog(this,
                    "Nota eliminada exitosamente",
                    "Éxito",
                    JOptionPane.INFORMATION_MESSAGE);
                nuevaNota();
                cargarNotas();
            } else {
                JOptionPane.showMessageDialog(this,
                    "Error al eliminar la nota",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void compartirNota() {
        if (notaSeleccionadaId == -1) {
            JOptionPane.showMessageDialog(this,
                "Por favor seleccione una nota para compartir",
                "Error",
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        List<Usuario> usuarios = usuarioController.obtenerTodosLosUsuarios();
        if (usuarios == null) {
            JOptionPane.showMessageDialog(this,
                "Error al obtener la lista de usuarios",
                "Error",
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Filtrar usuario actual de la lista
        for (int i = 0; i < usuarios.size(); i++) {
            if (usuarios.get(i).getId() == usuarioController.getUsuarioActual().getId()) {
                usuarios.remove(i);
                i--;
            }
        }
        
        if (usuarios.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "No hay otros usuarios disponibles para compartir. Intente en otro momento.",
                "Error",
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Crear array para el combobox
        String[] opciones = new String[usuarios.size()];
        for (int i = 0; i < usuarios.size(); i++) {
            opciones[i] = usuarios.get(i).getApodo();
        }

        String seleccion = (String) JOptionPane.showInputDialog(this,
            "Seleccione el usuario con quien compartir:",
            "Compartir Nota",
            JOptionPane.QUESTION_MESSAGE,
            null,
            opciones,
            opciones[0]);

        if (seleccion != null) {
            Usuario usuarioDestino = null;
            for (Usuario u : usuarios) {
                if (u.getApodo().equals(seleccion)) {
                    usuarioDestino = u;
                    break;
                }
            }

            if (usuarioDestino != null) {
                if (notasCompartidasController.compartirNota(notaSeleccionadaId, usuarioDestino.getId())) {
                    JOptionPane.showMessageDialog(this,
                        "Nota compartida exitosamente",
                        "Éxito",
                        JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this,
                        "Error al compartir la nota",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    private void cargarNotaSeleccionada() {
        boolean encontrada = false;
        
        // Primero, obtener la fila seleccionada de la tabla Mis Notas
        int rowMisNotas = tablaMisNotas.getSelectedRow();
        if (rowMisNotas != -1) { // Si se ha seleccionado una fila
            // Obtener los datos de la fila seleccionada en Mis Notas
            int notaId = (int) modeloMisNotas.getValueAt(rowMisNotas, 0);
            String titulo = (String) modeloMisNotas.getValueAt(rowMisNotas, 1);
            
            System.out.println("Nota propia seleccionada - ID: " + notaId + ", Título: " + titulo);
            
            // Mostrar datos en los campos
            txtTitulo.setText(titulo);
            Nota nota = null;
            List<Nota> misNotas = notaController.obtenerNotasUsuario();
            for (Nota n : misNotas) {
                if (n.getId() == notaId) {
                    nota = n;                        
                    break;
                }
            }
            if (nota != null) {
                txtContenido.setText(nota.getContenido());                    
            }
            encontrada = true;
        }

        // Si no se encuentra en las notas propias, buscar en las notas compartidas
        if (!encontrada) {
            // Obtener la fila seleccionada de la tabla Notas Compartidas
            int rowNotasCompartidas = tablaNotasCompartidas.getSelectedRow();
            if (rowNotasCompartidas != -1) { // Si se ha seleccionado una fila
                // Obtener los datos de la fila seleccionada en Notas Compartidas
                int notaIdCompartida = (int) modeloNotasCompartidas.getValueAt(rowNotasCompartidas, 0);
                String tituloCompartido = (String) modeloNotasCompartidas.getValueAt(rowNotasCompartidas, 1);
                
                System.out.println("Nota compartida seleccionada - ID: " + notaIdCompartida + ", Título: " + tituloCompartido);
                
                // Mostrar datos en los campos
                txtTitulo.setText(tituloCompartido);
                Nota notaCompartida = null;
                List<Nota> notasCompartidas = notaController.obtenerNotasCompartidas();
                for (Nota n : notasCompartidas) {
                    if (n.getId() == notaIdCompartida) {
                        notaCompartida = n;
                        break;
                    }
                }
                if (notaCompartida != null) {
                    txtContenido.setText(notaCompartida.getContenido());
                }
            }
        }

        // Habilitar o deshabilitar botones según si es nota propia o compartida
        boolean esNotaPropia = true;
        for (int i = 0; i < modeloNotasCompartidas.getRowCount(); i++) {
            if ((int) modeloNotasCompartidas.getValueAt(i, 0) == notaSeleccionadaId) {
                esNotaPropia = false;
                break;
            }
        }

        btnEditar.setEnabled(esNotaPropia);
        btnEliminar.setEnabled(esNotaPropia);
        btnCompartir.setEnabled(esNotaPropia);
    }


}
