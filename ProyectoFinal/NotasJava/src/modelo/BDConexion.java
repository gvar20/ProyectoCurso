package modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class BDConexion {
    private static BDConexion instance;
    private Connection connection;
        
    private BDConexion() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            this.connection = DriverManager.getConnection("jdbc:mysql://sql10.freesqldatabase.com/sql10760385","sql10760385","j2zq9HEAW7");
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException("Error al conectar con la base de datos: " + e.getMessage());
        }
    }
    
    public static BDConexion getInstance() {
        if (instance == null) {
            instance = new BDConexion();
        }
        return instance;
    }
    
    public Connection getConnection() {
        return connection;
    }

    public void cerrarConexion() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                System.out.println("Error al cerrar la conexión: " + e.getMessage());
            }
        }
    }
}
