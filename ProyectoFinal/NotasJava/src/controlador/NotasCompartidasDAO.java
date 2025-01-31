package controlador;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import modelo.BDConexion;
import modelo.NotasCompartidas;

public class NotasCompartidasDAO {
    private Connection conn;

    public NotasCompartidasDAO() {
    	this.conn = BDConexion.getInstance().getConnection();
    }

    public boolean compartirNota(NotasCompartidas notaCompartida) throws SQLException {
        String sql = "INSERT INTO notas_compartidas (nota_id, usuario_origen_id, usuario_destino_id) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, notaCompartida.getNotaId());
            stmt.setInt(2, notaCompartida.getUsuarioOrigenId());
            stmt.setInt(3, notaCompartida.getUsuarioDestinoId());
            
            int filasAfectadas = stmt.executeUpdate();
            if (filasAfectadas > 0) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    notaCompartida.setId(rs.getInt(1));
                }
                return true;
            }
        }
        return false;
    }
    
    public List<NotasCompartidas> obtenerCompartidasPorUsuario(int usuarioId) throws SQLException {
        List<NotasCompartidas> compartidas = new ArrayList<>();
        String sql = "SELECT * FROM notas_compartidas WHERE usuario_destino_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, usuarioId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                NotasCompartidas nc = new NotasCompartidas();
                nc.setId(rs.getInt("id"));
                nc.setNotaId(rs.getInt("nota_id"));
                nc.setUsuarioOrigenId(rs.getInt("usuario_origen_id"));
                nc.setUsuarioDestinoId(rs.getInt("usuario_destino_id"));
                nc.setFechaCompartido(rs.getTimestamp("fecha_compartido").toLocalDateTime());
                compartidas.add(nc);
            }
        }
        return compartidas;
    }
    
    public boolean eliminarRelacionNotaCompartida(int notaId) throws SQLException {
        String sql = "DELETE FROM notas_compartidas WHERE nota_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, notaId);
            return stmt.executeUpdate() > 0;
        }
    }
    
    public boolean fueCompartida(int notaId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM notas_compartidas WHERE nota_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, notaId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        }
        return false;
    }
}