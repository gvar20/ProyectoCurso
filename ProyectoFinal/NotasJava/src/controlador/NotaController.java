package controlador;


import modelo.Nota;
import java.sql.SQLException;
import java.util.List;

public class NotaController {
    private final NotaDAO notaDAO;
    private final UsuarioController usuarioController;
    private NotasCompartidasDAO notasCompartidasDAO;
    private NotasCompartidasController notasCompartidasController;

    public NotaController(UsuarioController usuarioController) {
        this.notaDAO = new NotaDAO();
        this.usuarioController = usuarioController;
    }

    public boolean crearNota(String titulo, String contenido) {
        if (usuarioController.getUsuarioActual() == null) {
            return false;
        }

        try {
            Nota nuevaNota = new Nota(titulo, contenido, usuarioController.getUsuarioActual().getId());
            return notaDAO.crear(nuevaNota);
        } catch (SQLException e) {
            System.err.println("Error al crear nota: " + e.getMessage());
            return false;
        }
    }

    public boolean actualizarNota(int notaId, String titulo, String contenido) {
        if (usuarioController.getUsuarioActual() == null) {
            return false;
        }

        try {
            Nota nota = new Nota(titulo, contenido, usuarioController.getUsuarioActual().getId());
            nota.setId(notaId);
            return notaDAO.actualizar(nota);
        } catch (SQLException e) {
            System.err.println("Error al actualizar nota: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminarNota(int notaId) {
        if (usuarioController.getUsuarioActual() == null) {
            return false;
        }

        try {
        	 if (this.notasCompartidasDAO != null && notasCompartidasDAO.fueCompartida(notaId)) {
        		 notasCompartidasController.eliminarNotaCompartida(notaId);
             }
            return notaDAO.eliminar(notaId, usuarioController.getUsuarioActual().getId());
        } catch (SQLException e) {
            System.err.println("Error al eliminar nota: " + e.getMessage());
            return false;
        }
    }

    public List<Nota> obtenerNotasUsuario() {
        if (usuarioController.getUsuarioActual() == null) {
            return null;
        }

        try {
            return notaDAO.obtenerNotasUsuario(usuarioController.getUsuarioActual().getId());
        } catch (SQLException e) {
            System.err.println("Error al obtener notas: " + e.getMessage());
            return null;
        }
    }

    public List<Nota> obtenerNotasCompartidas() {
        if (usuarioController.getUsuarioActual() == null) {
            return null;
        }

        try {
            return notaDAO.obtenerNotasCompartidas(usuarioController.getUsuarioActual().getId());
        } catch (SQLException e) {
            System.err.println("Error al obtener notas compartidas: " + e.getMessage());
            return null;
        }
    }
}
