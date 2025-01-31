package controlador;

import modelo.Usuario;
import java.sql.SQLException;
import java.util.List;

public class UsuarioController {
    private final UsuarioDAO usuarioDAO;
    private Usuario usuarioActual;

    public UsuarioController() {
        this.usuarioDAO = new UsuarioDAO();
    }

    public boolean registrarUsuario(String apodo, String contrasena) {
        try {
            Usuario nuevoUsuario = new Usuario(apodo, contrasena);
            return usuarioDAO.registrar(nuevoUsuario);
        } catch (SQLException e) {
            System.err.println("Error al registrar usuario: " + e.getMessage());
            return false;
        }
    }

    public boolean iniciarSesion(String apodo, String contrasena) {
        try {
            usuarioActual = usuarioDAO.autenticar(apodo, contrasena);
            return usuarioActual != null;
        } catch (SQLException e) {
            System.err.println("Error al iniciar sesión: " + e.getMessage());
            return false;
        }
    }

    public void cerrarSesion() {
        usuarioActual = null;
    }

    public Usuario getUsuarioActual() {
        return usuarioActual;
    }

    public List<Usuario> obtenerTodosLosUsuarios() {
        try {
            return usuarioDAO.obtenerTodos();
        } catch (SQLException e) {
            System.err.println("Error al obtener usuarios: " + e.getMessage());
            return null;
        }
    }

    public Usuario buscarUsuarioPorId(int id) {
        try {
            return usuarioDAO.buscarPorId(id);
        } catch (SQLException e) {
            System.err.println("Error al buscar usuario: " + e.getMessage());
            return null;
        }
    }
}