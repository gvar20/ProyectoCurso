package vista;

import javax.swing.*;
import controlador.UsuarioController;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegistroIF extends JFrame {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField txtApodo;
    private JPasswordField txtContrasena;
    private JPasswordField txtConfirmarContrasena;
    private UsuarioController usuarioController;
    private LoginIF loginFrame;

    public RegistroIF(LoginIF loginFrame) {
        this.loginFrame = loginFrame;
        usuarioController = new UsuarioController();
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("Registro - Notas");
        setSize(400, 350);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Panel de registro
        JPanel registroPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        registroPanel.add(new JLabel("Apodo:"));
        txtApodo = new JTextField();
        registroPanel.add(txtApodo);
        registroPanel.add(new JLabel("Contraseña:"));
        txtContrasena = new JPasswordField();
        registroPanel.add(txtContrasena);
        registroPanel.add(new JLabel("Confirmar Contraseña:"));
        txtConfirmarContrasena = new JPasswordField();
        registroPanel.add(txtConfirmarContrasena);

        // Panel de botones
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JButton btnRegistrar = new JButton("Registrarse");
        JButton btnVolver = new JButton("Volver");

        buttonPanel.add(btnRegistrar);
        buttonPanel.add(btnVolver);

        mainPanel.add(Box.createVerticalGlue());
        mainPanel.add(registroPanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        mainPanel.add(buttonPanel);
        mainPanel.add(Box.createVerticalGlue());

        add(mainPanel);

        // Eventos
        btnRegistrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registrarUsuario();
            }
        });

        btnVolver.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                volverLogin();
            }
        });
    }

    private void registrarUsuario() {
        String apodo = txtApodo.getText();
        String contrasena = new String(txtContrasena.getPassword());
        String confirmarContrasena = new String(txtConfirmarContrasena.getPassword());

        if (apodo.isEmpty() || contrasena.isEmpty() || confirmarContrasena.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Por favor complete todos los campos",
                "Error",
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!contrasena.equals(confirmarContrasena)) {
            JOptionPane.showMessageDialog(this,
                "Las contraseñas no coinciden",
                "Error",
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (usuarioController.registrarUsuario(apodo, contrasena)) {
            JOptionPane.showMessageDialog(this,
                "Usuario registrado exitosamente",
                "Éxito",
                JOptionPane.INFORMATION_MESSAGE);
            volverLogin();
        } else {
            JOptionPane.showMessageDialog(this,
                "Error al registrar usuario, el apodo ya está siendo utilizado.",
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private void volverLogin() {
        loginFrame.setVisible(true);
        this.dispose();
    }
}