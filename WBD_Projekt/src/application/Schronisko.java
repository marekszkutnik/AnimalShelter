package application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import java.sql.*;

public class Schronisko {

    private Integer schroniskoId;
    private String schroniskoNazwa;
    private String schroniskoDataZalozenia;
    private Integer schroniskoBudzet;

    public Integer getSchroniskoId() {
        return schroniskoId;
    }

    public void setSchroniskoId(Integer schroniskoId) {
        this.schroniskoId = schroniskoId;
    }

    public String getSchroniskoNazwa() {
        return schroniskoNazwa;
    }

    public void setSchroniskoNazwa(String schroniskoNazwa) {
        this.schroniskoNazwa = schroniskoNazwa;
    }

    public String getSchroniskoDataZalozenia() {
        return schroniskoDataZalozenia;
    }

    public void setSchroniskoDataZalozenia(String schroniskoDataZalozenia) {
        this.schroniskoDataZalozenia = schroniskoDataZalozenia;
    }

    public Integer getSchroniskoBudzet() {
        return schroniskoBudzet;
    }

    public void setSchroniskoBudzet(Integer schroniskoBudzet) {
        this.schroniskoBudzet = schroniskoBudzet;
    }

    @Override
    public String toString() {
        return "Schronisko{" +
                "schroniskoId=" + schroniskoId +
                ", schroniskoNazwa='" + schroniskoNazwa + '\'' +
                ", schroniskoDataZalozenia='" + schroniskoDataZalozenia + '\'' +
                ", schroniskoBudzet=" + schroniskoBudzet +
                '}';
    }

    public ObservableList<Schronisko> getAll(Connection conn) {
        ObservableList<Schronisko> listaSchronisk = FXCollections.observableArrayList();
        String sql = "SELECT * FROM schroniska order by id_schronisko";

        Statement stmt;
        ResultSet rs;

        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Schronisko schronisko = new Schronisko();
                schronisko.schroniskoId = rs.getInt(1);
                schronisko.schroniskoNazwa= rs.getString(2);
                schronisko.schroniskoDataZalozenia = rs.getString(3);
                schronisko.schroniskoBudzet = rs.getInt(4);

                listaSchronisk.add(schronisko);
            }
        } catch (SQLException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Błąd dostępu.");
            alert.setContentText("Szczegóły: " + ex.getMessage());
            alert.showAndWait();
        }

        return listaSchronisk;
    }

    public ObservableList<Schronisko> wyszukajSchronisko(Connection conn, String schroniskoNazwa) {
        ObservableList<Schronisko> listaSchronisk = FXCollections.observableArrayList();
        String sql = "SELECT id_schronisko, nazwa, data_zalozenia, budzet "
                + "FROM schroniska WHERE UPPER(nazwa) like ?"
                + "ORDER BY id_schronisko";
        PreparedStatement stmt;
        ResultSet rs;

        try {
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, schroniskoNazwa.toUpperCase() + "%");
            rs = stmt.executeQuery();

            while (rs.next()) {
                Schronisko schronisko = new Schronisko();
                schronisko.schroniskoId = rs.getInt(1);
                schronisko.schroniskoNazwa= rs.getString(2);
                schronisko.schroniskoDataZalozenia = rs.getString(3);
                schronisko.schroniskoBudzet = rs.getInt(4);

                listaSchronisk.add(schronisko);
            }

        } catch (SQLException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Błąd dostępu do opcji wyszukiwania.");
            alert.setContentText("Szczegóły: " + ex.getMessage());
            alert.showAndWait();
        }
        return listaSchronisk;
    }

    public int dodajSchronisko(Connection conn, String schroniskoNazwa, String schroniskoDataZalozenia, Integer schroniskoBudzet) {
        String sql = "INSERT into schroniska(nazwa, data_zalozenia, budzet) VALUES (?, ?, ?)";
        PreparedStatement stmt;
        Integer res = 0;

        try {
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, schroniskoNazwa);
            stmt.setString(2, schroniskoDataZalozenia);
            stmt.setInt(3, schroniskoBudzet);

            res = stmt.executeUpdate();

        } catch (SQLException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Błąd dodawania schroniska.");
            alert.setContentText("Szczegóły: " + ex.getMessage());
            alert.showAndWait();
        }
        return res;
    }

    public int usunSchronisko(Connection conn, Integer schroniskoId){
        String sql = "DELETE FROM schroniska WHERE id_schronisko = ?";
        PreparedStatement stmt;
        Integer res = 0;

        try{
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, schroniskoId);
            res = stmt.executeUpdate();

        }
        catch(SQLException ex){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Błąd usuwania schroniska.");
            alert.setContentText("Szczegóły: " + ex.getMessage());
            alert.showAndWait();
        }
        return res;
    }

    public int zaaktualizujSchroniskoNazwa(Connection conn, Integer schroniskoId, String schroniskoNazwa){
        String sql = "UPDATE schroniska SET nazwa = ? WHERE id_schronisko = ?";
        PreparedStatement stmt;
        Integer res = 0;

        try{
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, schroniskoNazwa);
            stmt.setInt(2, schroniskoId);

            res = stmt.executeUpdate();
        }
        catch (SQLException ex){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Błąd atualizowania schroniska.");
            alert.setContentText("Szczegóły: " + ex.getMessage());
            alert.showAndWait();
        }

        return res;
    }

    public int zaaktualizujSchroniskoDataZalozenia(Connection conn, Integer schroniskoId, String schroniskoDataZalozenia){
        String sql = "UPDATE schroniska SET data_zalozenia = ? WHERE id_schronisko = ?";
        PreparedStatement stmt;
        Integer res = 0;

        try{
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, schroniskoDataZalozenia);
            stmt.setInt(2, schroniskoId);

            res = stmt.executeUpdate();
        }
        catch (SQLException ex){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Błąd atualizowania schroniska.");
            alert.setContentText("Szczegóły: " + ex.getMessage());
            alert.showAndWait();
        }

        return res;
    }

    public int zaaktualizujSchroniskoBudzet(Connection conn, Integer schroniskoId, Integer schroniskoBudzet){
        String sql = "UPDATE schroniska SET budzet = ? WHERE id_schronisko = ?";
        PreparedStatement stmt;
        Integer res = 0;

        try{
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, schroniskoBudzet);
            stmt.setInt(2, schroniskoId);

            res = stmt.executeUpdate();
        }
        catch (SQLException ex){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Błąd atualizowania schroniska.");
            alert.setContentText("Szczegóły: " + ex.getMessage());
            alert.showAndWait();
        }

        return res;
    }
}
