package controlador;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import modelo.BDConexion;
import vista.LoginIF;

public class TestConexion {
    public static void main(String[] args) {
        BDConexion bdConexion = BDConexion.getInstance();
        
        Connection connection = bdConexion.getConnection();
        
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                bdConexion.cerrarConexion();
				System.out.println("Conexión cerrada.");
            }
        });
        
        try {
            if (connection != null && !connection.isClosed()) {
                System.out.println("Conexión exitosa a la base de datos.");
                
                
                Statement stmt = connection.createStatement();
                String query = "SHOW TABLES";
                ResultSet resultSet = stmt.executeQuery(query);
                
               
                System.out.println("Tablas en la base de datos:");                
                
                while (resultSet.next()) {
                    System.out.println(resultSet.getString(1));
                }
                
                LoginIF registroFrame = new LoginIF();
                registroFrame.setVisible(true);
                
            } else {
                System.out.println("No se pudo establecer la conexión.");
            }
        } catch (SQLException e) {
            System.out.println("Error al realizar la consulta: " + e.getMessage());
        }
    }
}
