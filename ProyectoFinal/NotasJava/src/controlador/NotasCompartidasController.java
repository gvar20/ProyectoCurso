package controlador;

import modelo.NotasCompartidas;
import java.sql.SQLException;
import java.util.List;

public class NotasCompartidasController {
    private final NotasCompartidasDAO notasCompartidasDAO;
    private final UsuarioController usuarioController;

    public NotasCompartidasController(UsuarioController usuarioController) {
        this.notasCompartidasDAO = new NotasCompartidasDAO();
        this.usuarioController = usuarioController;
    }

    public boolean compartirNota(int notaId, int usuarioDestinoId) {
        if (usuarioController.getUsuarioActual() == null) {
            return false;
        }

        try {
            NotasCompartidas notaCompartida = new NotasCompartidas();
            notaCompartida.setNotaId(notaId);
            notaCompartida.setUsuarioOrigenId(usuarioController.getUsuarioActual().getId());
            notaCompartida.setUsuarioDestinoId(usuarioDestinoId);
            
            return notasCompartidasDAO.compartirNota(notaCompartida);
        } catch (SQLException e) {
            System.err.println("Error al compartir nota: " + e.getMessage());
            return false;
        }
    }

    public List<NotasCompartidas> obtenerNotasCompartidasConmigo() {
        if (usuarioController.getUsuarioActual() == null) {
            return null;
        }

        try {
            return notasCompartidasDAO.obtenerCompartidasPorUsuario(
                usuarioController.getUsuarioActual().getId()
            );
        } catch (SQLException e) {
            System.err.println("Error al obtener notas compartidas: " + e.getMessage());
            return null;
        }
    }
    
    public boolean eliminarNotaCompartida(int notaId) {
        try {
            return notasCompartidasDAO.eliminarRelacionNotaCompartida(notaId);
        } catch (SQLException e) {
            System.err.println("Error al eliminar relación de nota compartida: " + e.getMessage());
            return false;
        }
    }
    
    
}