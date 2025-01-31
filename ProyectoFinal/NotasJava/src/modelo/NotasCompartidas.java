package modelo;

import java.time.LocalDateTime;

public class NotasCompartidas {
    private Integer id;
    private Integer notaId;
    private Integer usuarioOrigenId;
    private Integer usuarioDestinoId;
    private LocalDateTime fechaCompartido;
    
    public NotasCompartidas() {
    }
    
    public NotasCompartidas(Integer id, Integer notaId, Integer usuarioOrigenId, 
                          Integer usuarioDestinoId, LocalDateTime fechaCompartido) {
        this.id = id;
        this.notaId = notaId;
        this.usuarioOrigenId = usuarioOrigenId;
        this.usuarioDestinoId = usuarioDestinoId;
        this.fechaCompartido = fechaCompartido;
    }
    
    // Getters y Setters
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public Integer getNotaId() {
        return notaId;
    }
    
    public void setNotaId(Integer notaId) {
        this.notaId = notaId;
    }
    
    public Integer getUsuarioOrigenId() {
        return usuarioOrigenId;
    }
    
    public void setUsuarioOrigenId(Integer usuarioOrigenId) {
        this.usuarioOrigenId = usuarioOrigenId;
    }
    
    public Integer getUsuarioDestinoId() {
        return usuarioDestinoId;
    }
    
    public void setUsuarioDestinoId(Integer usuarioDestinoId) {
        this.usuarioDestinoId = usuarioDestinoId;
    }
    
    public LocalDateTime getFechaCompartido() {
        return fechaCompartido;
    }
    
    public void setFechaCompartido(LocalDateTime fechaCompartido) {
        this.fechaCompartido = fechaCompartido;
    }
    
    @Override
    public String toString() {
        return "NotasCompartidas{" +
                "id=" + id +
                ", notaId=" + notaId +
                ", usuarioOrigenId=" + usuarioOrigenId +
                ", usuarioDestinoId=" + usuarioDestinoId +
                ", fechaCompartido=" + fechaCompartido +
                '}';
    }
}