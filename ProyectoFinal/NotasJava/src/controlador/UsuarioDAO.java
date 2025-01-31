package controlador;

import modelo.BDConexion;
import modelo.Usuario;
import java.sql.*;

import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {
    private Connection conn;

    public UsuarioDAO() {
        this.conn = BDConexion.getInstance().getConnection();
    }

    public Usuario autenticar(String apodo, String contrasena) throws SQLException {
        String sql = "SELECT * FROM usuarios WHERE apodo = ? AND contrasena = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, apodo);
            stmt.setString(2, contrasena);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Usuario usuario = new Usuario();
                usuario.setId(rs.getInt("id"));
                usuario.setApodo(rs.getString("apodo"));
                usuario.setContrasena(rs.getString("contrasena"));
                usuario.setFechaRegistro(rs.getTimestamp("fecha_registro"));
                return usuario;
            }
        }
        return null;
    }

    public boolean registrar(Usuario usuario) throws SQLException {
        String sql = "INSERT INTO usuarios (apodo, contrasena) VALUES (?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, usuario.getApodo());
            stmt.setString(2, usuario.getContrasena());
            int filasAfectadas = stmt.executeUpdate();
            if (filasAfectadas > 0) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    usuario.setId(rs.getInt(1));
                }
                return true;
            }
        }
        return false;
    }

    public List<Usuario> obtenerTodos() throws SQLException {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT * FROM usuarios";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Usuario usuario = new Usuario();
                usuario.setId(rs.getInt("id"));
                usuario.setApodo(rs.getString("apodo"));
                usuario.setContrasena(rs.getString("contrasena"));
                usuario.setFechaRegistro(rs.getTimestamp("fecha_registro"));
                usuarios.add(usuario);
            }
        }
        return usuarios;
    }

    public Usuario buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM usuarios WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Usuario usuario = new Usuario();
                usuario.setId(rs.getInt("id"));
                usuario.setApodo(rs.getString("apodo"));
                usuario.setContrasena(rs.getString("contrasena"));
                usuario.setFechaRegistro(rs.getTimestamp("fecha_registro"));
                return usuario;
            }
        }
        return null;
    }
}