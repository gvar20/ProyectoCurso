package controlador;

import modelo.BDConexion;
import modelo.Nota;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NotaDAO {
    private Connection conn;

    public NotaDAO() {
        this.conn = BDConexion.getInstance().getConnection();
    }

    public boolean crear(Nota nota) throws SQLException {
        String sql = "INSERT INTO notas (titulo, contenido, usuario_id) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, nota.getTitulo());
            stmt.setString(2, nota.getContenido());
            stmt.setInt(3, nota.getUsuarioId());
            
            int filasAfectadas = stmt.executeUpdate();
            if (filasAfectadas > 0) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    nota.setId(rs.getInt(1));
                }
                return true;
            }
        }
        return false;
    }

    public boolean actualizar(Nota nota) throws SQLException {
        String sql = "UPDATE notas SET titulo = ?, contenido = ?, fecha_modificacion = CURRENT_TIMESTAMP WHERE id = ? AND usuario_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nota.getTitulo());
            stmt.setString(2, nota.getContenido());
            stmt.setInt(3, nota.getId());
            stmt.setInt(4, nota.getUsuarioId());
            return stmt.executeUpdate() > 0;
        }
    }

    public boolean eliminar(int notaId, int usuarioId) throws SQLException {
        String sql = "DELETE FROM notas WHERE id = ? AND usuario_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, notaId);
            stmt.setInt(2, usuarioId);
            return stmt.executeUpdate() > 0;
        }
    }

    public List<Nota> obtenerNotasUsuario(int usuarioId) throws SQLException {
        List<Nota> notas = new ArrayList<>();
        String sql = "SELECT * FROM notas WHERE usuario_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, usuarioId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Nota nota = new Nota();
                nota.setId(rs.getInt("id"));
                nota.setTitulo(rs.getString("titulo"));
                nota.setContenido(rs.getString("contenido"));
                nota.setUsuarioId(rs.getInt("usuario_id"));
                nota.setFechaCreacion(rs.getTimestamp("fecha_creacion"));
                nota.setFechaModificacion(rs.getDate("fecha_modificacion"));
                notas.add(nota);
            }
        }
        return notas;
    }

    public List<Nota> obtenerNotasCompartidas(int usuarioId) throws SQLException {
        List<Nota> notas = new ArrayList<>();
        String sql = "SELECT n.* FROM notas n " +
                    "JOIN notas_compartidas nc ON n.id = nc.nota_id " +
                    "WHERE nc.usuario_destino_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, usuarioId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Nota nota = new Nota();
                nota.setId(rs.getInt("id"));
                nota.setTitulo(rs.getString("titulo"));
                nota.setContenido(rs.getString("contenido"));
                nota.setUsuarioId(rs.getInt("usuario_id"));
                nota.setFechaCreacion(rs.getTimestamp("fecha_creacion"));
                nota.setFechaModificacion(rs.getDate("fecha_modificacion"));
                notas.add(nota);
            }
        }
        return notas;
    }
}