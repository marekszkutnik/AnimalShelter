package application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.util.Optional;
import java.util.ResourceBundle;

public class SchroniskoController implements Initializable {

    private Connection conn;
    private ObservableList<Schronisko> listaSchronisk = FXCollections.observableArrayList();

    @FXML
    private VBox vBoxLabelSchronisko;

    @FXML
    private VBox vBoxTextFieldSchronisko;

    @FXML
    private TableView<Schronisko> tableSchronisko;

    @FXML
    private TableColumn<Schronisko, Integer> tableColumnSchroniskoId;

    @FXML
    private TableColumn<Schronisko, String> tableColumnSchroniskoNazwa;

    @FXML
    private TableColumn<Schronisko, String> tableColumnSchroniskoDataZalozenia;

    @FXML
    private TableColumn<Schronisko, Integer> tableColumnSchroniskoBudzet;

    @FXML
    private Button buttonSchroniskoDodaj;

    @FXML
    private Text textSchroniskoTytul;

    @FXML
    private Button buttonSchroniskoUsun;

    @FXML
    private Button buttonSchroniskoSzukaj;

    @FXML
    private Separator separatorSchronisko;

    @FXML
    private Label labelSchroniskoNazwa;

    @FXML
    private TextField textFieldSchroniskoNazwa;

    @FXML
    private Label labelSchroniskoDataZalozenia;

    @FXML
    private TextField textFieldSchroniskoDataZalozenia;

    @FXML
    private Label labelSchroniskoBudzet;

    @FXML
    private TextField textFieldSchroniskoBudzet;

    @FXML
    private Button buttonSchroniskoOdswiez;

    @FXML
    private Button buttonSchroniskoAkualizuj;
    
    @FXML
    private Button buttonSchroniskoPracownicy;
    
    @FXML
    private Button buttonSchroniskoWyloguj;
    
    
    
    public void setButtonSchroniskoWyloguj(ActionEvent action)
    {
    	Stage stage1 = (Stage) buttonSchroniskoPracownicy.getScene().getWindow();
    	stage1.close();
    	
    	try{
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("LogPanel.fxml"));
			Parent root = fxmlLoader.load();
			Scene scene = new Scene(root);
	    	Stage stage2 = new Stage();
	    	stage2.setScene(scene);
	    	stage2.show();
			} 
		catch (IOException e1) 
			{	
			e1.printStackTrace();
			}
    }


    public void setButtonSchroniskoPracownicy(ActionEvent action)
    {
    	Stage stage1 = (Stage) buttonSchroniskoPracownicy.getScene().getWindow();
    	stage1.close();
    	
    	try{
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Pracownik.fxml"));
			Parent root = fxmlLoader.load();
			Scene scene = new Scene(root);
	    	Stage stage2 = new Stage();
	    	stage2.setScene(scene);
	    	stage2.show();
			} 
		catch (IOException e1) 
			{	
			e1.printStackTrace();
			}
    	
    }
    
    @FXML
    public void setButtonSchroniskoSzukaj(ActionEvent actionEvent){
        conn = DBConnection.getConnection();
        listaSchronisk = new Schronisko().wyszukajSchronisko(conn, textFieldSchroniskoNazwa.getText().trim());

        setTableViewSchronisko(listaSchronisk);
    }

    @FXML
    public void setButtonSchroniskoDodaj(ActionEvent actionEvent){

        String schroniskoNazwa;
        String schroniskoDataZalozenia;
        Integer schroniskoBudzet;
        Integer res;

        try{
            conn = DBConnection.getConnection();
            schroniskoNazwa = textFieldSchroniskoNazwa.getText().trim();
            schroniskoDataZalozenia = textFieldSchroniskoDataZalozenia.getText().trim();
            schroniskoBudzet = Integer.parseInt(textFieldSchroniskoBudzet.getText().trim());


            if(schroniskoNazwa.length() == 0 || schroniskoBudzet < 0){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Zły format wprowadzonych danych.");
                alert.showAndWait();
                return;
            }

            res = new Schronisko().dodajSchronisko(conn, schroniskoNazwa, schroniskoDataZalozenia, schroniskoBudzet);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Pomyślnie dodano schronisko");
            alert.showAndWait();
        }catch(NumberFormatException ex){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Zły format wprowadzonej danych.");
            alert.showAndWait();
        }
        listaSchronisk = new Schronisko().getAll(conn);
        setTableViewSchronisko(listaSchronisk);
    }

    @FXML
    public void setButtonSchroniskoUsun(ActionEvent actionEvent){
        Integer rowIndex = tableSchronisko.getSelectionModel().getSelectedIndex();

        if(rowIndex < 1){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Nie wybrano schroniska, którego należy usunąć.");
            alert.showAndWait();
            return;
        }

        Integer schroniskoId = tableSchronisko.getSelectionModel().getSelectedItem().getSchroniskoId();
        conn = DBConnection.getConnection();

        Alert alertv1 = new Alert(Alert.AlertType.CONFIRMATION);
        alertv1.setContentText("Czy napewno chcesz usunąć to schronisko?");
        Optional<ButtonType> resv1 = alertv1.showAndWait();


        if(resv1.get() == ButtonType.OK){
            Integer res = new Schronisko().usunSchronisko(conn, schroniskoId);

            if(res > 0){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Pomyślnie usunięto schronisko.");
                alert.showAndWait();
            }
        }
        listaSchronisk = new Schronisko().getAll(conn);
        setTableViewSchronisko(listaSchronisk);
    }

    @FXML
    private void setButtonSchroniskoOdswiez(ActionEvent actionEvent){
        conn = DBConnection.getConnection();
        listaSchronisk = new Schronisko().getAll(conn);
        setTableViewSchronisko(listaSchronisk);
    }

    @FXML
    public void setButtonSchroniskoAkualizuj(ActionEvent actionEvent){

        Integer schroniskoId;
        String schroniskoNazwa;
        String schroniskoDataZalozenia;
        Integer schroniskoBudzet;
        Integer rowIndex = tableSchronisko.getSelectionModel().getSelectedIndex();
        Integer res;

        if(rowIndex < 1){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Nie wybrano schroniska, którego chcesz zaaktualizować.");
            alert.showAndWait();
            return;
        }

        try{
            conn = DBConnection.getConnection();
            schroniskoId = tableSchronisko.getSelectionModel().getSelectedItem().getSchroniskoId();
            schroniskoNazwa = textFieldSchroniskoNazwa.getText().trim();
            schroniskoDataZalozenia = textFieldSchroniskoDataZalozenia.getText().trim();
            schroniskoBudzet = Integer.parseInt(textFieldSchroniskoBudzet.getText().trim());

            if(textFieldSchroniskoNazwa.getText().equals("")){
                schroniskoNazwa = tableSchronisko.getSelectionModel().getSelectedItem().getSchroniskoNazwa();
            }
            if(textFieldSchroniskoDataZalozenia.getText().equals("")){
                schroniskoDataZalozenia = tableSchronisko.getSelectionModel().getSelectedItem().getSchroniskoDataZalozenia();
            }
            if(textFieldSchroniskoBudzet.getText().equals("")){
                schroniskoBudzet = tableSchronisko.getSelectionModel().getSelectedItem().getSchroniskoBudzet();
            }
            res = new Schronisko().zaaktualizujSchroniskoNazwa(conn, schroniskoId, schroniskoNazwa);
            res = new Schronisko().zaaktualizujSchroniskoDataZalozenia(conn, schroniskoId, schroniskoDataZalozenia);
            res = new Schronisko().zaaktualizujSchroniskoBudzet(conn, schroniskoId, schroniskoBudzet);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Pomyślnie zaaktualizowano schronisko");
            alert.showAndWait();
        }catch(NumberFormatException ex){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Zły format wprowadzonej daty.");
            alert.showAndWait();
        }
        listaSchronisk = new Schronisko().getAll(conn);
        setTableViewSchronisko(listaSchronisk);

    }

    private void setTableViewSchronisko(ObservableList<Schronisko> listaSchronisk){
        tableColumnSchroniskoId.setCellValueFactory(new PropertyValueFactory<>("schroniskoId"));
        tableColumnSchroniskoNazwa.setCellValueFactory((new PropertyValueFactory<>("schroniskoNazwa")));
        tableColumnSchroniskoDataZalozenia.setCellValueFactory((new PropertyValueFactory<>("schroniskoDataZalozenia")));
        tableColumnSchroniskoBudzet.setCellValueFactory((new PropertyValueFactory<>("schroniskoBudzet")));

        tableSchronisko.setItems(listaSchronisk);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb){
        conn = DBConnection.getConnectionStart();
        listaSchronisk = new Schronisko().getAll(conn);
        setTableViewSchronisko(listaSchronisk);
    }

}

