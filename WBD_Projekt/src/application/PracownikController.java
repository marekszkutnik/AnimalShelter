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

public class PracownikController implements Initializable {
	
	private Connection conn;
    private ObservableList<Pracownik> listaPracownikow = FXCollections.observableArrayList();

    @FXML
    private VBox vBoxLabelPracownik;

    @FXML
    private VBox vBoxTextFieldPracownik;

    @FXML
    private TableView<Pracownik> tablePracownik;

    @FXML
    private TableColumn<Pracownik, Integer> tableColumnPracownikId;

    @FXML
    private TableColumn<Pracownik, String> tableColumnPracownikImie;

    @FXML
    private TableColumn<Pracownik, String> tableColumnPracownikNazwisko;

    @FXML
    private TableColumn<Pracownik, String> tableColumnPracownikDataUrodzenia;

    @FXML
    private TableColumn<Pracownik, String> tableColumnPracownikNrKontaktowy;

    @FXML
    private TableColumn<Pracownik, String> tableColumnSchronisko;
    
    @FXML
    private ComboBox<String> comboBoxPracownikSchronisko;

    @FXML
    private Button buttonPracownikDodaj;

    @FXML
    private Text textPracownikTytul;

    @FXML
    private Button buttonPracownikUsun;

    @FXML
    private Button buttonPracownikSzukaj;

    @FXML
    private Separator separatorPracownik;

    @FXML
    private Label labelPracownikImie;

    @FXML
    private TextField textFieldPracownikImie;

    @FXML
    private Label labelPracownikNazwisko;

    @FXML
    private TextField textFieldPracownikNazwisko;

    @FXML
    private Label labelPracownikDataUrodzenia;

    @FXML
    private TextField textFieldPracownikDataUrodzenia;

    @FXML
    private Label labelPracownikNrKontaktowy;

    @FXML
    private TextField textFieldPracownikNrKontaktowy;

    @FXML
    private Button buttonPracownikOdswiez;

    @FXML
    private Button buttonPracownikAkualizuj;
   
    @FXML
    private Label labelPracownikSchronisko;
    
    @FXML
    private Button buttonPracownikSchroniska;

    

    public void setButtonPracownikSchroniska(ActionEvent actionEvent)
    {
    	Stage stage1 = (Stage) buttonPracownikSchroniska.getScene().getWindow();
    	stage1.close();
    	conn = DBConnection.getConnection();
    	
    	try{
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Schronisko.fxml"));
			Parent root = fxmlLoader.load();
			Scene scene = new Scene(root);
	    	Stage stage2 = new Stage();
	    	stage2.setScene(scene);
	    	stage2.show();
			} 
		catch (IOException ex)
			{	
			ex.printStackTrace();
			}
    }

    @FXML
    public void setButtonPracownikSzukaj(ActionEvent actionEvent){
        conn = DBConnection.getConnection();
        listaPracownikow = new Pracownik().wyszukajPracownik(conn, textFieldPracownikImie.getText().trim(), textFieldPracownikNazwisko.getText().trim());

        setTableViewPracownik(listaPracownikow);
    }

    @FXML
    public void setButtonPracownikDodaj(ActionEvent actionEvent){

        String pracownikImie;
        String pracownikNazwisko;
        String pracownikDataUrodzenia;
        Integer pracownikNrKontaktowy;
        String pracownikSchronisko;
        Integer res;

        try{
            conn = DBConnection.getConnection();
            pracownikImie = textFieldPracownikImie.getText().trim();
            pracownikNazwisko = textFieldPracownikNazwisko.getText().trim();
            pracownikDataUrodzenia = textFieldPracownikDataUrodzenia.getText().trim();
            pracownikNrKontaktowy = Integer.parseInt(textFieldPracownikNrKontaktowy.getText().trim());
            pracownikSchronisko =  comboBoxPracownikSchronisko.getValue().toString();

            if(pracownikImie.length() == 0 || pracownikNazwisko.length() == 0 || pracownikNrKontaktowy < 0){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Zły format wprowadzonych danych.");
                alert.showAndWait();
                return;
            }

            res = new Pracownik().dodajPracownik(conn, pracownikImie, pracownikNazwisko, pracownikDataUrodzenia, pracownikNrKontaktowy, new Pracownik().zwrocSchroniskoId(conn, pracownikSchronisko));
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Pomyślnie dodano pracownika");
            alert.showAndWait();

        }catch(NumberFormatException ex){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Zły format wprowadzonej danych.");
            alert.showAndWait();
        }

        listaPracownikow = new Pracownik().getAll(conn);
        setTableViewPracownik(listaPracownikow);
    }
    
    @FXML
    public void setButtonPracownikUsun(ActionEvent actionEvent){
        Integer rowIndex = tablePracownik.getSelectionModel().getSelectedIndex();

        if(rowIndex < 1){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Nie wybrano pracownika, którego należy usunąć.");
            alert.showAndWait();
            return;
        }

        Integer pracownikId = tablePracownik.getSelectionModel().getSelectedItem().getPracownikId();
        conn = DBConnection.getConnection();

        Alert alertv1 = new Alert(Alert.AlertType.CONFIRMATION);
        alertv1.setContentText("Czy napewno chcesz usunąć tego pracownika?");
        Optional<ButtonType> resv1 = alertv1.showAndWait();

        if(resv1.get() == ButtonType.OK){
            Integer res = new Pracownik().usunPracownik(conn, pracownikId);

            if(res > 0){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Pomyślnie usunięto pracownika.");
                alert.showAndWait();
            }
        }
        listaPracownikow = new Pracownik().getAll(conn);
        setTableViewPracownik(listaPracownikow);

    }

    @FXML
    private void setButtonPracownikOdswiez(ActionEvent actionEvent){
        conn = DBConnection.getConnection();
        listaPracownikow = new Pracownik().getAll(conn);
        setTableViewPracownik(listaPracownikow);
    }

    @FXML
    public void setButtonPracownikAkualizuj(ActionEvent actionEvent){
        Integer pracownikId;
        String pracownikImie;
        String pracownikNazwisko;
        String pracownikDataUrodzenia;
        String pracownikNrKontaktowy;
        String pracownikSchronisko;
        Integer rowIndex = tablePracownik.getSelectionModel().getSelectedIndex();
        Integer res;


        if(rowIndex < 1){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Nie wybrano pracownika, którego chcesz zaaktualizować.");
            alert.showAndWait();
            return;
        }

        try{
            conn = DBConnection.getConnection();
            pracownikId = tablePracownik.getSelectionModel().getSelectedItem().getPracownikId();
            pracownikImie = textFieldPracownikImie.getText().trim();
            pracownikNazwisko = textFieldPracownikNazwisko.getText().trim();
            pracownikDataUrodzenia = textFieldPracownikDataUrodzenia.getText().trim();
            pracownikNrKontaktowy = textFieldPracownikNrKontaktowy.getText().trim();
            pracownikSchronisko = comboBoxPracownikSchronisko.getValue().toString();


            if(textFieldPracownikImie.getText().equals("")){
                pracownikImie = tablePracownik.getSelectionModel().getSelectedItem().getPracownikImie();
            }
            if(textFieldPracownikNazwisko.getText().equals("")){
                pracownikNazwisko = tablePracownik.getSelectionModel().getSelectedItem().getPracownikNazwisko();
            }
            if(textFieldPracownikDataUrodzenia.getText().equals("")){
                pracownikDataUrodzenia = tablePracownik.getSelectionModel().getSelectedItem().getPracownikDataUrodzenia();
            }
            if(textFieldPracownikNrKontaktowy.getText().equals("")){
                pracownikNrKontaktowy = tablePracownik.getSelectionModel().getSelectedItem().getPracownikNrKontaktowy();
            }
            if(comboBoxPracownikSchronisko.getValue().toString().equals("")){
                pracownikSchronisko = tablePracownik.getSelectionModel().getSelectedItem().getPracownikSchronisko();
            }

            res = new Pracownik().zaaktualizujPracownikImie(conn, pracownikId, pracownikImie);
            res = new Pracownik().zaaktualizujPracownikNazwisko(conn, pracownikId, pracownikNazwisko);
            res = new Pracownik().zaaktualizujPracownikDataUrodzenia(conn, pracownikId, pracownikDataUrodzenia);
            res = new Pracownik().zaaktualizujPracownikNrKontaktowy(conn, pracownikId, pracownikNrKontaktowy);
            res = new Pracownik().zaaktualizujPracownikSchronisko(conn, pracownikId, new Pracownik().zwrocSchroniskoId(conn, pracownikSchronisko));
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Pomyślnie zaaktualizowano pracownika");
            alert.showAndWait();
        }catch(NumberFormatException ex){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Zły format wprowadzonej daty.");
            alert.showAndWait();
        }

        listaPracownikow = new Pracownik().getAll(conn);
        setTableViewPracownik(listaPracownikow);

    }

    private void setTableViewPracownik(ObservableList<Pracownik> listaPracownikow){
       
    	tableColumnPracownikId.setCellValueFactory(new PropertyValueFactory<>("pracownikId"));
        tableColumnPracownikImie.setCellValueFactory((new PropertyValueFactory<>("pracownikImie")));
        tableColumnPracownikNazwisko.setCellValueFactory((new PropertyValueFactory<>("pracownikNazwisko")));
        tableColumnPracownikDataUrodzenia.setCellValueFactory((new PropertyValueFactory<>("pracownikDataUrodzenia")));
        tableColumnPracownikNrKontaktowy.setCellValueFactory((new PropertyValueFactory<>("pracownikNrKontaktowy")));
        tableColumnSchronisko.setCellValueFactory((new PropertyValueFactory<>("pracownikSchronisko")));
                                                                             
        tablePracownik.setItems(listaPracownikow);
        
    }

    private void setComboBoxSchronisko(ObservableList<String> schroniska)
    {
    	comboBoxPracownikSchronisko.getItems().setAll(schroniska);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb){
        conn = DBConnection.getConnectionStart();
        Pracownik pracownik = new Pracownik();
        listaPracownikow =  pracownik.getAll(conn);
        setTableViewPracownik(listaPracownikow);
        setComboBoxSchronisko(pracownik.wyswietlDostepneSchroniska(conn));
    }
    
    
}

/*
ObservableList<String> options = 
    FXCollections.observableArrayList(
        "Option 1",
        "Option 2",
        "Option 3"
    );
final ComboBox comboBox = new ComboBox(options);
*/