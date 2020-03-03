package application;

import javafx.scene.control.Alert;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Klient {

	private Integer klientId;
	private String klientImie;
	private String klientNazwisko;
	private String klientDataUrodzin;
	private String klientZdolnoscAdoptacyjna;
	private String klientMiasto;
	private String klientUlica;
	private String klientNumerMieszkania;
	private String klientKodPocztowy;
	private String klientNumerKontaktowy;
	private Integer klientSchroniskoId;
	
	
	public Integer getKlientId() {
		return klientId;
	}

	public void setKlientId(Integer klientId) {
		this.klientId = klientId;
	}

	public String getKlientImie() {
		return klientImie;
	}

	public void setKlientImie(String klientImie) {
		this.klientImie = klientImie;
	}

	public String getKlientNazwisko() {
		return klientNazwisko;
	}

	public void setKlientNazwisko(String klientNazwisko) {
		this.klientNazwisko = klientNazwisko;
	}

	public String getKlientZdolnoscAdoptacyjna() {
		return klientZdolnoscAdoptacyjna;
	}

	public void setKlientZdolnoscAdoptacyjna(String klientZdolnoscAdoptacyjna) {
		this.klientZdolnoscAdoptacyjna = klientZdolnoscAdoptacyjna;
	}

	public String getKlientMiasto() {
		return klientMiasto;
	}

	public void setKlientMiasto(String klientMiasto) {
		this.klientMiasto = klientMiasto;
	}

	public String getKlientUlica() {
		return klientUlica;
	}

	public void setKlientUlica(String klientUlica) {
		this.klientUlica = klientUlica;
	}

	public String getKlientKodPocztowy() {
		return klientKodPocztowy;
	}

	public void setKlientKodPocztowy(String klientKodPocztowy) {
		this.klientKodPocztowy = klientKodPocztowy;
	}

	public String getKlientNumerKontaktowy() {
		return klientNumerKontaktowy;
	}

	public void setKlientNumerKontaktowy(String klientNumerKontaktowy) {
		this.klientNumerKontaktowy = klientNumerKontaktowy;
	}
		
	public Klient get(Connection con, Integer kontoId)
	{
		Klient klient = new Klient();
		
		String sql = "SELECT * FROM klienci " 
				+ "WHERE klient_fk like ?";
	    
		PreparedStatement stm;
		ResultSet rs;
		
		try 
		{
			stm = con.prepareStatement(sql);
			stm.setInt(1, kontoId);
			rs = stm.executeQuery();
			
			while(rs.next()) 
			{
				klient.klientId = rs.getInt(1);
				klient.klientImie = rs.getString(2);
				klient.klientNazwisko = rs.getString(3);
				klient.klientDataUrodzin = rs.getString(4);
				klient.klientZdolnoscAdoptacyjna = rs.getString(5);
				klient.klientMiasto = rs.getString(6);
				klient.klientUlica = rs.getString(7) + " " + rs.getString(8);
				klient.klientNumerMieszkania = rs.getString(9);
				klient.klientKodPocztowy = rs.getString(10);
				klient.klientNumerKontaktowy =  rs.getString(11);
				klient.klientSchroniskoId = rs.getInt(12);
	
				System.out.println(klient.klientImie);
			}
			
		}
		catch(SQLException exc)
		{
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Problem z dostepem do danych");
			alert.setContentText("Szczegoly: " + exc.getMessage()); 
			alert.showAndWait();
			
		}
		
		
		return klient;
	}

	public int zaktualizujKlientMiasto(Connection con, Integer klientId, String klientMiasto)
	{
		    String sql = "UPDATE klienci SET miasto = ? WHERE id_klient = ?";
	        PreparedStatement stmt;
	        Integer res = 0;

	        try{
	            stmt = con.prepareStatement(sql);
	            stmt.setString(1, klientMiasto);
	            stmt.setInt(2, klientId);

	            res = stmt.executeUpdate();
	        }
	        catch (SQLException ex){
	            Alert alert = new Alert(Alert.AlertType.ERROR);
	            alert.setTitle("Błąd atualizowania Klienta.");
	            alert.setContentText("Szczegóły: " + ex.getMessage());
	            alert.showAndWait();
	        }

	        return res;
	    }
	
	public int zaktualizujKlientKodPocztowy(Connection con, Integer klientId, String klientKodPocztowy)
	{
		    String sql = "UPDATE klienci SET kod_pocztowy = ? WHERE id_klient = ?";
	        PreparedStatement stmt;
	        Integer res = 0;

	        try{
	            stmt = con.prepareStatement(sql);
	            stmt.setString(1, klientKodPocztowy);
	            stmt.setInt(2, klientId);

	            res = stmt.executeUpdate();
	        }
	        catch (SQLException ex){
	            Alert alert = new Alert(Alert.AlertType.ERROR);
	            alert.setTitle("Błąd atualizowania Klienta.");
	            alert.setContentText("Szczegóły: " + ex.getMessage());
	            alert.showAndWait();
	        }

	        return res;
	    }
	
	public int zaktualizujKlientNumerTelefonu(Connection con, Integer klientId, String klientNumerTelefonu)
	{
		    String sql = "UPDATE klienci SET numer_kontaktowy = ? WHERE id_klient = ?";
	        PreparedStatement stmt;
	        Integer res = 0;

	        try{
	            stmt = con.prepareStatement(sql);
	            stmt.setString(1, klientNumerTelefonu);
	            stmt.setInt(2, klientId);

	            res = stmt.executeUpdate();
	        }
	        catch (SQLException ex){
	            Alert alert = new Alert(Alert.AlertType.ERROR);
	            alert.setTitle("Błąd atualizowania Klienta.");
	            alert.setContentText("Szczegóły: " + ex.getMessage());
	            alert.showAndWait();
	        }

	        return res;
	    }
	
	public int zaktualizujKlientAdres(Connection con, Integer klientId, String klientUlica, String klientNumerDomu)
	{
		    String sql = "UPDATE klienci SET ulica = ?, numer_ulicy = ?  WHERE id_klient = ?";
	        PreparedStatement stmt;
	        Integer res = 0;

	        try{
	            stmt = con.prepareStatement(sql);
	            stmt.setString(1, klientUlica);
	            stmt.setString(2, klientNumerDomu);
	            stmt.setInt(3, klientId);

	            res = stmt.executeUpdate();
	        }
	        catch (SQLException ex){
	            Alert alert = new Alert(Alert.AlertType.ERROR);
	            alert.setTitle("Błąd atualizowania Klienta.");
	            alert.setContentText("Szczegóły: " + ex.getMessage());
	            alert.showAndWait();
	        }

	        return res;
	    }
	
}
