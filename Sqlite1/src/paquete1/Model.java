package paquete1;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Model extends Database {

	public Model() {
		super();
		createTableTiempo();
	}

	final static String CREATE_TABLE_TIEMPO = "create table tiempo (id integer PRIMARY KEY AUTOINCREMENT, "
			+ "fecha String NOT NULL, min Float NOT NULL, max Float NOT NULL, "
			+ "humedad FLOAT NOT NULL, presion Float NOT NULL)";

	/**
	 * 1
	 * Creamos la tabla tiempo
	 */
	public void createTableTiempo() {
		// Declaro una variable statement para ejecutar
		// sentencias sql
		Statement st = null;
		try {
			// Inicializa el objeto statement con
			// el objeto connection
			st = conn.createStatement();

			// Ejecuto la sentencia sql que
			// crea la tabla tiempo
			st.executeUpdate(CREATE_TABLE_TIEMPO);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (st != null) {
				try {
					st.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}
	}
	
	/**
	 * 2
	 * Método para insertar valores
	 * @param fecha
	 * @param min
	 * @param max
	 * @param humedad
	 * @param presion
	 * @param table
	 */

	public void insertPrediccion(String fecha, float min, float max,
			float humedad, float presion, String table) {

		Connection connection = null;
		

		try {
			connection = DriverManager.getConnection(getUrl());

			// Con este objeto hacemos consultas de SQL
			Statement statement = connection.createStatement();
			// No es obligatorio pero es bueno para dejar de buscar la BBDD
			statement.setQueryTimeout(30); // set timeout to 30 sec.

			// executeUpdate para todas menos las de SELECT
			// statement.executeUpdate("drop table if exists tiempo");
			String sql = "INSERT INTO " + table
					+ " (fecha, min, max, humedad, presion) values" + "('"
					+ fecha + "'," + min + "," + max + "," + humedad + ","
					+ presion + ")";
			System.out.println(sql);
			statement.executeUpdate(sql);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	/**
	 * 3
	 * Método para mostrar los valores de la tabla
	 * @param table
	 */

	public void imprimirPrediciones(String table) {

		Connection connection = null;

		try {
			connection = DriverManager.getConnection(getUrl());

			// Con este objeto hacemos consultas de SQL
			Statement statement = connection.createStatement();
			// No es obligatorio pero es bueno para dejar de buscar la BBDD
			statement.setQueryTimeout(30); // set timeout to 30 sec.

			// Guarda consultas de bbdd
			ResultSet rs = statement.executeQuery("SELECT * FROM " + table);
			

			while (rs.next()) {
				// read the result set
				System.out.println("id = " + rs.getString("id"));
				System.out.println("fecha = " + rs.getInt("fecha"));
				System.out.println("tempMin = " + rs.getInt("min"));
				System.out.println("tempMax = " + rs.getInt("max"));
				System.out.println("humedad = " + rs.getInt("humedad"));
				System.out.println("presion = " + rs.getInt("presion"));
				System.out.println("");
			}
			rs.close();
			statement.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	/**
	 * 4
	 * Borrar tabla 
	 */
public void borrarTabla(String table) {
		
		Connection connection = null;
		
		try {
			connection = DriverManager.getConnection(getUrl());
			
			// Con este objeto hacemos consultas de SQL
			Statement statement = connection.createStatement();
			// No es obligatorio pero es bueno para dejar de buscar la BBDD
			statement.setQueryTimeout(30); // set timeout to 30 sec.
			
			// executeUpdate para todas menos las de SELECT, porque no devuelve datos
			String sql = "DROP TABLE IF EXISTS "+table;
			//String sql = "DROP TABLE "+table;
			//String sql = "DELETE FROM "+table+" WHERE id ="+ident;
			
			statement.executeUpdate(sql);
			
			statement.close();
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		
	}

}
