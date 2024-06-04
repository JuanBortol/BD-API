import java.sql.*;


/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public class tp {
public static void main(String[] args) {
    String url = "jdbc:postgresql://localhost:5432/postgres";
    String user = "micag";
    String password = "pass";
    
    try (Connection connection = DriverManager.getConnection(url, user, password)) {
          // Insertar un cine
          String insertCineQuery = "INSERT INTO cine(nombre_cine, direccion, telefono) VALUES (?, ?, ?)";
          try (PreparedStatement insertCineStatement = connection.prepareStatement(insertCineQuery)) {
              insertCineStatement.setString(1, "CINEPLEX 4");
              insertCineStatement.setString(2, "Av. Principal 123");
              insertCineStatement.setString(3, "123456789"); // Aquí se coloca un valor para el teléfono
              insertCineStatement.executeUpdate();
              System.out.println("Cine insertado correctamente");
          } catch (SQLException e) {
              System.out.println("Error al insertar el cine: " + e.getMessage());
          }
          
          
          // Insertar una sala en un cine
            String insertSalaQuery = "INSERT INTO sala(nro_sala, nro_butacas, nombre_cine) VALUES (?, ?, ?)";
            try (PreparedStatement insertSalaStatement = connection.prepareStatement(insertSalaQuery)) {
                insertSalaStatement.setInt(1, 9); // Número de sala
                insertSalaStatement.setInt(2, 100); // Capacidad de la sala
                insertSalaStatement.setString(3, "CINEPLEX 4"); // nombre del cine al que pertenece
                insertSalaStatement.executeUpdate();
                System.out.println("Sala insertada correctamente en el cineplex 4");
            } catch (SQLException e) {
                System.out.println("Error al insertar la sala: " + e.getMessage());
            }

            // Listar los cines con la información de sus salas
              String listCinesQuery = "SELECT c.nombre_cine, c.direccion, c.telefono, s.nro_sala, s.nro_butacas " +
                                      "FROM cine c " +
                                      "INNER JOIN sala s ON c.nombre_cine = s.nombre_cine";
              try (PreparedStatement listCinesStatement = connection.prepareStatement(listCinesQuery);
                   ResultSet cinesResultSet = listCinesStatement.executeQuery()) {
                  while(cinesResultSet.next()) {
                      System.out.println("Cine: " + cinesResultSet.getString(1));
                      System.out.println("Dirección: " + cinesResultSet.getString(2));
                      System.out.println("Teléfono: " + cinesResultSet.getString(3));
                      System.out.println("Nro_sala: " + cinesResultSet.getInt(4));
                      System.out.println("Nro_butacas: " + cinesResultSet.getInt(5));
                      System.out.println("---------------------------------------");
                  }
              } catch (SQLException e) {
                  System.out.println("Error al listar los cines y sus salas: " + e.getMessage());
              }      
    } catch (SQLException e) {
        System.out.println("Error de conexión a la base de datos: " + e.getMessage());
    }
  }
}
