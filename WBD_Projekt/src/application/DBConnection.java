package application;

import javafx.scene.control.Alert;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DBConnection {

	
	private static Connection conn;
	
	public static Connection getConnectionStart()
	{
		String url = "jdbc:oracle:thin:@ora3.elka.pw.edu.pl:1521:ora3inf";
		String userName = "wszalyga";
		String userPassword = "wszalyga";

		try{
			conn = DriverManager.getConnection(url,userName, userPassword);
			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setContentText("Udało Ci się połączyć z bazą danych!");
			alert.show();
		}
		catch(SQLException ex){
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Błąd połączenia.");
			alert.setContentText("Szczegóły: " + ex.getMessage());
		}

		return conn;
	}
	public static Connection getConnection()
	{
		String url = "jdbc:oracle:thin:@localhost:1521:orcl1";
		String userName = "marek";
		String userPassword = "marek";

		try{
			conn = DriverManager.getConnection(url,userName, userPassword);
		}
		catch(SQLException ex){
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Błąd połączenia.");
			alert.setContentText("Szczegóły: " + ex.getMessage());
		}

		return conn;
	}

}
