package application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import java.sql.*;

public class Zwierze {

	private Integer zwierzeId;
    private String zwierzeNazwa;
    private String zwierzeGatunek;
    private String zwierzeRasa;
    private String zwierzeKolor;
    private String zwierzeDataNarodzin;

    public Integer getZwierzeId() {
        return zwierzeId;
    }

    public void setZwierzeId(Integer zwierzeId) {
        this.zwierzeId = zwierzeId;
    }

    public String getZwierzeNazwa() {
        return zwierzeNazwa;
    }

    public void setZwierzeNazwa(String zwierzeNazwa) {
        this.zwierzeNazwa = zwierzeNazwa;
    }

    public String getZwierzeGatunek() {
        return zwierzeGatunek;
    }

    public void setZwierzeGatunek(String zwierzeGatunek) {
        this.zwierzeGatunek = zwierzeGatunek;
    }

    public String getZwierzeRasa() {
        return zwierzeRasa;
    }

    public void setZwierzeRasa(String zwierzeRasa) {
        this.zwierzeRasa = zwierzeRasa;
    }

    public String getZwierzeKolor() {
        return zwierzeKolor;
    }

    public void setZwierzeKolor(String zwierzeKolor) {
        this.zwierzeKolor = zwierzeKolor;
    }

    public String getZwierzeDataNarodzin() {
        return zwierzeDataNarodzin;
    }

    public void setZwierzeDataNarodzin(String zwierzeDataNarodzin) {
        this.zwierzeDataNarodzin = zwierzeDataNarodzin;
    }

    @Override
    public String toString() {
        return "Zwierze{" +
                "zwierzeId=" + zwierzeId +
                ", zwierzeNazwa='" + zwierzeNazwa + '\'' +
                ", zwierzeGatunek='" + zwierzeGatunek + '\'' +
                ", zwierzeRasa='" + zwierzeRasa + '\'' +
                ", zwierzeKolor='" + zwierzeKolor + '\'' +
                ", zwierzeDataNarodzin='" + zwierzeDataNarodzin + '\'' +
                '}';
    }

    public ObservableList<Zwierze> getAll(Connection conn) {
        ObservableList<Zwierze> listaZwierzat = FXCollections.observableArrayList();
        String sql = "SELECT id_zwierze, nazwa, gatunek, rasa, kolor, data_narodzin FROM zwierzeta order by id_zwierze";

        Statement stmt;
        ResultSet rs;

        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Zwierze zwierze = new Zwierze();
                zwierze.zwierzeId = rs.getInt(1);
                zwierze.zwierzeNazwa = rs.getString(2);
                zwierze.zwierzeGatunek = rs.getString(3);
                zwierze.zwierzeRasa = rs.getString(4);
                zwierze.zwierzeKolor = rs.getString(5);
                zwierze.zwierzeDataNarodzin = rs.getString(6);

                listaZwierzat.add(zwierze);
            }
        } catch (SQLException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Błąd dostępu.");
            alert.setContentText("Szczegóły: " + ex.getMessage());
            alert.showAndWait();
        }

        return listaZwierzat;
    }

    public ObservableList<Zwierze> wyszukajZwierze(Connection conn, String zwierzeGatunek) {
        ObservableList<Zwierze> listaZwierzat = FXCollections.observableArrayList();
        String sql = "SELECT id_zwierze, nazwa, gatunek, rasa, kolor, data_narodzin FROM zwierzeta WHERE UPPER(gatunek) like ? ORDER BY id_zwierze";
        PreparedStatement stmt;
        ResultSet rs;

        try {
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, zwierzeGatunek.toUpperCase() + "%");
            rs = stmt.executeQuery();

            while (rs.next()) {
                Zwierze zwierze = new Zwierze();
                zwierze.zwierzeId = rs.getInt(1);
                zwierze.zwierzeNazwa = rs.getString(2);
                zwierze.zwierzeGatunek = rs.getString(3);
                zwierze.zwierzeRasa = rs.getString(4);
                zwierze.zwierzeKolor = rs.getString(5);
                zwierze.zwierzeDataNarodzin = rs.getString(6);

                listaZwierzat.add(zwierze);
            }

        } catch (SQLException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Błąd dostępu do opcji wyszukiwania.");
            alert.setContentText("Szczegóły: " + ex.getMessage());
            alert.showAndWait();
        }
        return listaZwierzat;
    }
}