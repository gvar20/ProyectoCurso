package vista;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.*;
import controlador.UsuarioController;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginIF extends JFrame {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField txtApodo;
    private JPasswordField txtContrasena;
    private UsuarioController usuarioController;

    public LoginIF() {
        usuarioController = new UsuarioController();
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Inicio de Sesión - Notas");
        setSize(412, 309);
        setLocationRelativeTo(null);
        
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Panel de login
        JPanel loginPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        loginPanel.add(new JLabel("Apodo:"));
        txtApodo = new JTextField();
        loginPanel.add(txtApodo);
        loginPanel.add(new JLabel("Contraseña:"));
        txtContrasena = new JPasswordField();
        loginPanel.add(txtContrasena);

        // Panel de botones
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JButton btnLogin = new JButton("Iniciar Sesión");
        JButton btnRegistro = new JButton("Registrarse");

        buttonPanel.add(btnLogin);
        buttonPanel.add(btnRegistro);

        mainPanel.add(Box.createVerticalGlue());
        mainPanel.add(loginPanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        mainPanel.add(buttonPanel);
        mainPanel.add(Box.createVerticalGlue());

        getContentPane().add(mainPanel);

        // Eventos
        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                iniciarSesion();
            }
        });

        btnRegistro.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarRegistro();
            }
        });
    }

    private void iniciarSesion() {
        String apodo = txtApodo.getText();
        String contrasena = new String(txtContrasena.getPassword());

        if (apodo.isEmpty() || contrasena.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Por favor complete todos los campos",
                "Error",
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (usuarioController.iniciarSesion(apodo, contrasena)) {
            JOptionPane.showMessageDialog(this, 
                "Bienvenido " + apodo,
                "Éxito",
                JOptionPane.INFORMATION_MESSAGE);
            abrirVentanaPrincipal();
        } else {
            JOptionPane.showMessageDialog(this,
                "Usuario o contraseña incorrectos",
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private void mostrarRegistro() {
        RegistroIF registroFrame = new RegistroIF(this);
        registroFrame.setVisible(true);
        this.setVisible(false);
    }

    private void abrirVentanaPrincipal() {
        VentanaPrincipalIF mainFrame = new VentanaPrincipalIF(usuarioController);
        mainFrame.setVisible(true);
        this.dispose();
    }
}