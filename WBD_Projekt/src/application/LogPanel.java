package application;

import javafx.scene.control.Alert;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LogPanel {

	private Integer kontoId;
	private String kontoLogin;
	private String kontoHaslo;
	private String kontoUprawnienia;
	
/*
   String sql = "SELECT id_pracownik, imie, nazwisko, data_urodzenia, nr_kontaktowy "
                + "FROM pracownicy WHERE UPPER(imie) like ? AND UPPER(nazwisko) like ? "
                + "ORDER BY id_pracownik";
        PreparedStatement stmt;
        ResultSet rs;

        try {
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, pracownikImie.toUpperCase() + "%");
            stmt.setString(2, pracownikNazwisko.toUpperCase() + "%");
            rs = stmt.executeQuery();

           
                Pracownik pracownik = new Pracownik();
                pracownik.pracownikId = rs.getInt(1);
                pracownik.pracownikImie = rs.getString(2);
                pracownik.pracownikNazwisko = rs.getString(3);
                pracownik.pracownikDataUrodzenia = rs.getString(4);
                pracownik.pracownikNrKontaktowy = rs.getInt(5);
                listaPrcownikow.add(pracownik);
          

        } catch (SQLException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Błąd dostępu do opcji wyszukiwania.");
            alert.setContentText("Szczegóły: " + ex.getMessage());
            alert.showAndWait();
        }
        return listaPrcownikow;
	
*/	
	
	
	
	public LogPanel get(Connection con, String login, String password)
	{
		LogPanel konto =  new LogPanel();;
		
		String sql = "SELECT id_konta, login, haslo, uprawnienia "
				+ "FROM konta WHERE login like ? AND haslo like ?";
		PreparedStatement stmt;
		ResultSet rs;
		
		try 
		{
			    stmt = con.prepareStatement(sql);
	            stmt.setString(1, login);
	            stmt.setString(2, password);
	            rs = stmt.executeQuery();
			
	            while (rs.next())
	            {	
	            	konto.kontoId = rs.getInt(1);
					konto.kontoLogin = rs.getString(2);
					konto.kontoHaslo = rs.getString(3);
					konto.kontoUprawnienia = rs.getNString(4);	
				
	            }
		}
		catch(SQLException exc)
		{
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Problem z dostepem      do danych");
			alert.setContentText("Szczegoly: " + exc.getMessage()); 
			alert.showAndWait();
			
		}
		
		
		return konto;
	}

	public String getKontoLogin() {
		return kontoLogin;
	}

	public void setKontoLogin(String kontoLogin) {
		this.kontoLogin = kontoLogin;
	}

	public String getKontoHaslo() {
		return kontoHaslo;
	}

	public void setKontoHaslo(String kontoHaslo) {
		this.kontoHaslo = kontoHaslo;
	}
		
	public String getKontoUprawnienia() {
		return kontoUprawnienia;
	}
	
	public Integer getKontoId() {
		return kontoId;
	}
	
}
