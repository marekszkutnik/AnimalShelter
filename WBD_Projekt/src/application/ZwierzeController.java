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
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.util.ResourceBundle;

public class ZwierzeController implements Initializable {

	private Connection con;
	private ObservableList<Zwierze> listaZwierzat = FXCollections.observableArrayList();
	private static Integer klientZalogowany;
	
    @FXML
    private TableView<Zwierze> zwierzeTableView;
	
	@FXML
    private TableColumn<Zwierze, Integer> zwierzeTableColumnId;

    @FXML
    private TableColumn<Zwierze, String> zwierzeTableColumnNazwa;

    @FXML
    private TableColumn<Zwierze, String> zwierzeTableColumnGatunek;

    @FXML
    private TableColumn<Zwierze, String> zwierzeTableColumnRasa;

    @FXML
    private TableColumn<Zwierze, String> zwierzeTableColumnKolor;

    @FXML
    private TableColumn<Zwierze, String> zwierzeTableColumnDataNarodzin;

    @FXML
    private Button zwierzeButtonPanelKlienta;

	@FXML
	private Button buttonZwierzeSzukaj;

	@FXML
	private Label labelZwierzePoszukiwanyGatunek;

	@FXML
	private TextField textFieldZwierzePoszukiwanyGatunek;


	
	public void setTableViewZwierze(ObservableList<Zwierze> listaZwierzat)
	{
		    zwierzeTableColumnId.setCellValueFactory(new PropertyValueFactory<>("zwierzeId"));
		    zwierzeTableColumnNazwa.setCellValueFactory((new PropertyValueFactory<>("zwierzeNazwa")));
		    zwierzeTableColumnGatunek.setCellValueFactory((new PropertyValueFactory<>("zwierzeGatunek")));
		    zwierzeTableColumnRasa.setCellValueFactory((new PropertyValueFactory<>("zwierzeRasa")));
		    zwierzeTableColumnKolor.setCellValueFactory((new PropertyValueFactory<>("zwierzeKolor")));
		    zwierzeTableColumnDataNarodzin.setCellValueFactory((new PropertyValueFactory<>("zwierzeDataNarodzin")));
	                                                                             
	        zwierzeTableView.setItems(listaZwierzat);
	}
	
	public void setButtonKlientPanel(ActionEvent e)
	{
		Integer klient = klientZalogowany;
		Stage stage1 = (Stage) zwierzeButtonPanelKlienta.getScene().getWindow();

		KlientController.setKlientKonto(klientZalogowany);
		try{
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Klient.fxml"));
			Parent root = fxmlLoader.load();
			Scene scene = new Scene(root);
	    	Stage stage = new Stage();
	    	stage.setScene(scene);
	    	stage.show();
	    	KlientController.setKlientKonto(klient);
			} 
		catch (IOException ex)
			{	
			ex.printStackTrace();
			}
		stage1.close();
		
	}

	@FXML
	public void setButtonZwierzeSzukaj(ActionEvent actionEvent){
		con = DBConnection.getConnection();
		listaZwierzat = new Zwierze().wyszukajZwierze(con, textFieldZwierzePoszukiwanyGatunek.getText().trim());

		setTableViewZwierze(listaZwierzat);
	}
	
	public static void setKlientZalogowany(Integer klient) 
	{
		klientZalogowany = klient;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		con = DBConnection.getConnection();
		listaZwierzat = new Zwierze().getAll(con);
		setTableViewZwierze(listaZwierzat);
	}
	
}
