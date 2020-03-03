package application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import java.sql.*;

public class Pracownik {

    private Integer pracownikId;
    private String pracownikImie;
    private String pracownikNazwisko;
    private String pracownikDataUrodzenia;
    private String pracownikNrKontaktowy;
    private String pracownikSchronisko;

    public Integer getPracownikId() {
        return pracownikId;
    }

    public void setPracownikId(Integer pracownikId) {
        this.pracownikId = pracownikId;
    }

    public String getPracownikImie() {
        return pracownikImie;
    }

    public void setPracownikImie(String pracownikImie) {
        this.pracownikImie = pracownikImie;
    }

    public String getPracownikNazwisko() {
        return pracownikNazwisko;
    }

    public void setPracownikNazwisko(String pracownikNazwisko) {
        this.pracownikNazwisko = pracownikNazwisko;
    }

    public String getPracownikDataUrodzenia() {
        return pracownikDataUrodzenia;
    }

    public void setPracownikDataUrodzenia(String pracownikDataUrodzenia) {
        this.pracownikDataUrodzenia = pracownikDataUrodzenia;
    }

    public String getPracownikNrKontaktowy() {
        return pracownikNrKontaktowy;
    }

    public void setPracownikNrKontaktowy(String pracownikNrKontaktowy) {
        this.pracownikNrKontaktowy = pracownikNrKontaktowy;
    }

    public String getPracownikSchronisko() {
  		return pracownikSchronisko;
  	}

  	public void setPracownikSchronisko(String pracownikSchronisko) {
  		this.pracownikSchronisko = pracownikSchronisko;
  	}
    
    @Override
    public String toString() {
        return "Pracownik{" +
                "pracownikId=" + pracownikId +
                ", pracownikImie='" + pracownikImie + '\'' +
                ", pracownikNazwisko='" + pracownikNazwisko + '\'' +
                ", pracownikDataUrodzenia='" + pracownikDataUrodzenia + '\'' +
                ", pracownikNrKontaktowy=" + pracownikNrKontaktowy +
                ", pracownikSchronisko=" + pracownikSchronisko +
                '}';
    }

    public ObservableList<Pracownik> getAll(Connection conn) {
        ObservableList<Pracownik> listaPracownikow = FXCollections.observableArrayList();
        String sql = "SELECT p.id_pracownik, p.imie, p.nazwisko ,p.data_urodzenia, p.numer_kontaktowy, s.nazwa "
        		   + "FROM pracownicy p, schroniska s "
        		   + "WHERE p.schronisko_id_schronisko = s.id_schronisko";
        /*
        SELECT p.id_pracownik, p.imie, p.nazwisko ,p.data_urodzenia, p.numer_kontaktowy, s.nazwa
        FROM pracownicy p, schroniska s
WHERE p.schronisko_id_schronisko = s.id_schronisko
        */
        Statement stmt;
        ResultSet rs;

        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Pracownik pracownik = new Pracownik();
                pracownik.pracownikId = rs.getInt(1);
                pracownik.pracownikImie = rs.getString(2);
                pracownik.pracownikNazwisko = rs.getString(3);
                pracownik.pracownikDataUrodzenia = rs.getString(4);
                pracownik.pracownikNrKontaktowy = rs.getString(5);
                pracownik.pracownikSchronisko = rs.getString(6);
                
                listaPracownikow.add(pracownik);

            }
        } catch (SQLException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Błąd dostępu.");
            alert.setContentText("Szczegóły auu: " + ex.getMessage());
            alert.showAndWait();
        }

        return listaPracownikow;
    }

	public ObservableList<Pracownik> wyszukajPracownik(Connection conn, String pracownikImie, String pracownikNazwisko) {
        ObservableList<Pracownik> listaPrcownikow = FXCollections.observableArrayList();
        String sql = "SELECT id_pracownik, imie, nazwisko, data_urodzenia, numer_kontaktowy, schronisko_id_schronisko "
                + "FROM pracownicy WHERE UPPER(imie) like ? AND UPPER(nazwisko) like ? "
                + "ORDER BY id_pracownik";
        PreparedStatement stmt;
        ResultSet rs;

        try {
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, pracownikImie.toUpperCase() + "%");
            stmt.setString(2, pracownikNazwisko.toUpperCase() + "%");
            rs = stmt.executeQuery();

            while (rs.next()) {
                Pracownik pracownik = new Pracownik();
                pracownik.pracownikId = rs.getInt(1);
                pracownik.pracownikImie = rs.getString(2);
                pracownik.pracownikNazwisko = rs.getString(3);
                pracownik.pracownikDataUrodzenia = rs.getString(4);
                pracownik.pracownikNrKontaktowy = rs.getString(5);
                pracownik.pracownikSchronisko = rs.getString(6);
                listaPrcownikow.add(pracownik);
            }

        } catch (SQLException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Błąd dostępu do opcji wyszukiwania.");
            alert.setContentText("Szczegóły: " + ex.getMessage());
            alert.showAndWait();
        }
        return listaPrcownikow;
    }

    public int dodajPracownik(Connection conn, String pracownikImie, String pracownikNazwisko, String pracownikDataUrodzenia, Integer pracownikNrKontaktowy, Integer pracownikSchronisko) {
        String sql = "INSERT into pracownicy(imie, nazwisko, data_urodzenia, numer_kontaktowy, schronisko_id_schronisko) VALUES (?, ?, ? , ?, ?)";
        PreparedStatement stmt;
        Integer res = 0;

        try {
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, pracownikImie);
            stmt.setString(2, pracownikNazwisko);
            stmt.setString(3, pracownikDataUrodzenia);
            stmt.setInt(4, pracownikNrKontaktowy);
            stmt.setInt(5, pracownikSchronisko);

            res = stmt.executeUpdate();

        } catch (SQLException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Błąd dodawania pracownika.");
            alert.setContentText("Szczegóły: " + ex.getMessage());
            alert.showAndWait();
        }
        return res;
    }

    public int usunPracownik(Connection conn, Integer pracownikId){
        String sql = "DELETE FROM pracownicy WHERE id_pracownik = ?";
        PreparedStatement stmt;
        Integer res = 0;

        try{
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, pracownikId);
            res = stmt.executeUpdate();
			
        }
        catch(SQLException ex){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Błąd usuwania pracownika.");
            alert.setContentText("Szczegóły: " + ex.getMessage());
            alert.showAndWait();
        }
        return res;
    }

    public int zaaktualizujPracownikImie(Connection conn, Integer pracownikId, String pracownikImie){
        String sql = "UPDATE pracownicy SET imie = ? WHERE id_pracownik = ?";
        PreparedStatement stmt;
        Integer res = 0;

        try{
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, pracownikImie);
            stmt.setInt(2, pracownikId);

            res = stmt.executeUpdate();
        }
        catch (SQLException ex){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Błąd atualizowania pracownika.");
            alert.setContentText("Szczegóły: " + ex.getMessage());
            alert.showAndWait();
        }
        return res;
    }

    public int zaaktualizujPracownikNazwisko(Connection conn, Integer pracownikId, String pracownikNazwisko){
        String sql = "UPDATE pracownicy SET nazwisko = ? WHERE id_pracownik = ?";
        PreparedStatement stmt;
        Integer res = 0;

        try{
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, pracownikNazwisko);
            stmt.setInt(2, pracownikId);

            res = stmt.executeUpdate();
        }
        catch (SQLException ex){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Błąd atualizowania pracownika.");
            alert.setContentText("Szczegóły: " + ex.getMessage());
            alert.showAndWait();
        }

        return res;
    }

    public int zaaktualizujPracownikDataUrodzenia(Connection conn, Integer pracownikId, String pracownikDataUrodzenia){
        String sql = "UPDATE pracownicy SET data_urodzenia = ? WHERE id_pracownik = ?";
        PreparedStatement stmt;
        Integer res = 0;

        try{
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, pracownikDataUrodzenia);
            stmt.setInt(2, pracownikId);

            res = stmt.executeUpdate();
        }
        catch (SQLException ex){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Błąd atualizowania pracownika.");
            alert.setContentText("Szczegóły: " + ex.getMessage());
            alert.showAndWait();
        }

        return res;
    }

    public int zaaktualizujPracownikNrKontaktowy(Connection conn, Integer pracownikId, String pracownikNrKontaktowy){
        String sql = "UPDATE pracownicy SET numer_kontaktowy = ? WHERE id_pracownik = ?";
        PreparedStatement stmt;
        Integer res = 0;

        try{
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, pracownikNrKontaktowy);
            stmt.setInt(2, pracownikId);

            res = stmt.executeUpdate();
        }
        catch (SQLException ex){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Błąd atualizowania pracownika.");
            alert.setContentText("Szczegóły: " + ex.getMessage());
            alert.showAndWait();
        }

        return res;
    }

    public int zaaktualizujPracownikSchronisko(Connection conn, Integer pracownikId, Integer pracownikSchronisko){
        String sql = "UPDATE pracownicy SET schronisko_id_schronisko = ? WHERE id_pracownik = ?";
        PreparedStatement stmt;
        Integer res = 0;

        try{
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, pracownikSchronisko);
            stmt.setInt(2, pracownikId);

            res = stmt.executeUpdate();
        }
        catch (SQLException ex){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Błąd atualizowania pracownika.");
            alert.setContentText("Szczegóły: " + ex.getMessage());
            alert.showAndWait();
        }

        return res;
    }

    public ObservableList<String> wyswietlDostepneSchroniska(Connection con)
    {
    	String sql = "SELECT nazwa FROM schroniska ORDER BY id_schronisko";
    	
    	Statement stmt;
        ResultSet rs;
        
      
        ObservableList<String> schroniska = FXCollections.observableArrayList();
        
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery(sql);

            while (rs.next()) {
            	
                String schronisko = rs.getString(1);
                schroniska.add(schronisko);
             //   System.out.println("Schronisko nr " + i + ": " + s;
                
                
            }
        } catch (SQLException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Błąd dostępu.");
            alert.setContentText("Szczegóły auu: " + ex.getMessage());
            alert.showAndWait();
            
        }

        
    	
        return schroniska;
    }

    public Integer zwrocSchroniskoId(Connection con, String schroniskoNazwa)
    {
    	
    	 Integer schroniskoId = 0;
    	
    	 String sql = "SELECT id_schronisko FROM schroniska WHERE nazwa = ?";
         PreparedStatement stmt;
         ResultSet rs;

         try {
             stmt = con.prepareStatement(sql);
             stmt.setString(1, schroniskoNazwa);
             rs = stmt.executeQuery();

             while (rs.next()) {
                
                schroniskoId = rs.getInt(1);
                
                 
             }

         } catch (SQLException ex) {
             Alert alert = new Alert(Alert.AlertType.ERROR);
             alert.setTitle("Błąd dostępu (Combo Box).");
             alert.setContentText("Szczegóły: " + ex.getMessage());
             alert.showAndWait();
         }
 	
    	return schroniskoId;
    }
}
