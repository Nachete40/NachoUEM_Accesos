package paquete2;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Sample
{
  public static void main(String[] args) throws ClassNotFoundException
  {
    // load the sqlite-JDBC driver using the current class loader
	//Cargar el driver JDBC
    Class.forName("org.sqlite.JDBC");
    //Creamos el objeto Connection
    Connection connection = null;
    try
    {
      // create a database connection
    //estableciendo conexion con la base de datos
      connection = DriverManager.getConnection("jdbc:sqlite:./resources/bbdd/sample.db");
      //objeto Statement para empezar a realizar consultas
      Statement statement = connection.createStatement();
      statement.setQueryTimeout(30);  // set timeout to 30 sec.
      
      //Ejecutar una consulta con el objeto Statement
      //sentencias que nos permiten modificar la base de datos
      statement.executeUpdate("drop table if exists person");
      statement.executeUpdate("create table person (id integer, name string)");
      statement.executeUpdate("insert into person values(1, 'leo')");
      statement.executeUpdate("insert into person values(2, 'yui')");
      
      //Recuperar lo datos del objeto ResultSet
      //guarda la base de datos persona
      ResultSet rs = statement.executeQuery("select * from person");
      
      //con este bucle pasamos a mirar lo que tiene detro la base de datos
      while(rs.next())
      {
        // read the result set
        System.out.println("name = " + rs.getString("name"));
        System.out.println("id = " + rs.getInt("id"));
      }
    }
    catch(SQLException e)
    {
      // if the error message is "out of memory", 
      // it probably means no database file is found
      System.err.println(e.getMessage());
    }
    finally
    {
      try
      {
        if(connection != null)
          connection.close();
      }
      catch(SQLException e)
      {
        // connection close failed.
        System.err.println(e);
      }
    }
  }
}