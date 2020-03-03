package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.util.ResourceBundle;

public class KlientController implements Initializable {

	private Klient klient;
	private Connection con;
	private static Integer klientKonto;

	@FXML
	private VBox vBoxLabelKlient;

	@FXML
	private VBox vBoxTextKlient;

	@FXML
	private VBox vBoxTextKlientModyfikuj;

	@FXML
	private VBox vBoxTextFieldKlientModyfikuj;

	@FXML
	private Text textKlientModyfikuj;

	@FXML
	private Text textKlientImie;

	@FXML
	private Text textKlientNazwisko;

    @FXML
    private Text textKlientZdolnosc;

    @FXML
    private Text textKlientNumerTelefonu;

    @FXML
    private Text textKlientMiasto;

    @FXML
    private Text textKlientUlica;

    @FXML
    private Text textKlientKodPocztowy;

    @FXML
    private Label labelKlientImie;

    @FXML
    private Label labelKlientNazwisko;

    @FXML
    private Label labelKlientZdolnosc;

    @FXML
    private Label labelKlientMiasto;

    @FXML
    private Label lanelKlientUlica;

    @FXML
    private Label labelKlientKodPocztowy;

    @FXML
    private Label labelKlientNumerTelefonu;
    
    @FXML
    private Button buttonKlientWyloguj;
    
    @FXML
    private Button buttonKlientSpisZwierzat;
    
    @FXML
    private TextField textFieldKlientMiasto;

    @FXML
    private TextField textFieldKlientUlica;

    @FXML
    private TextField textFieldKlientKodPocztowy;

    @FXML
    private TextField textFieldKlientNumerTelefonu;

    @FXML
    private Button buttonKlientModyfikuj;

    
    
    public void setButtonWyloguj(ActionEvent event) {
    	
    	Stage stage1 = (Stage) buttonKlientWyloguj.getScene().getWindow();
    	stage1.close();
    	
    	con = DBConnection.getConnection();
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
    
    public void setButtonSpisZwierzat(ActionEvent event)
    {
    	if(klient.getKlientZdolnoscAdoptacyjna().contentEquals("Z"))
    	{
    		ZwierzeController.setKlientZalogowany(klientKonto);
        	Stage stage1 = (Stage) buttonKlientSpisZwierzat.getScene().getWindow();

        	con = DBConnection.getConnection();
        	try{
    			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Zwierze.fxml"));
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
        	stage1.close();   	
    	}
    	else
    	{
    		Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Brak dostępu");
    		alert.setContentText("Odmowa dostępu z powodu braku zdolności adopcyjnej");
            alert.showAndWait();
    	}
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		con = DBConnection.getConnection();
		klient = new Klient().get(con, klientKonto);
		showKlient();
	
	}


	public void showKlient()
	{
		labelKlientImie.setText(klient.getKlientImie());
		labelKlientNazwisko.setText(klient.getKlientNazwisko());
		labelKlientZdolnosc.setText(klient.getKlientZdolnoscAdoptacyjna());
		labelKlientMiasto.setText(klient.getKlientMiasto());
		lanelKlientUlica.setText(klient.getKlientUlica());
		labelKlientKodPocztowy.setText(klient.getKlientKodPocztowy());
		labelKlientNumerTelefonu.setText(klient.getKlientNumerKontaktowy());
	}

	public static void setKlientKonto(Integer konto)
	{
		klientKonto = konto;
	}

	 @FXML
	    public void setButtonKlientModyfikuj(ActionEvent actionEvent){

	        String klientMiasto;
	        String klientAdres;
	        String klientNumerUlicy;
	        String klientKodPocztowy;
	        String klientNumerTelefonu;
	        Integer klientID = klient.getKlientId();
	        Integer res;

	        System.out.println("here 1");
	        
	            con = DBConnection.getConnection();
	            System.out.println("here 2");
	            klientMiasto = textFieldKlientMiasto.getText().trim();
	            String[] adresTextField = textFieldKlientUlica.getText().trim().split(" ");
	           
	            klientAdres = adresTextField[0];
	            klientNumerUlicy = adresTextField[1];
	            
	            klientKodPocztowy = textFieldKlientKodPocztowy.getText().trim();
	            klientNumerTelefonu = textFieldKlientNumerTelefonu.getText().trim();

	            System.out.println("here 3");
	            if(textFieldKlientMiasto.getText().equals("")){
	            	klientMiasto = klient.getKlientMiasto();
	            }
	            if(textFieldKlientUlica.getText().equals("")){
	            	{
	            	String[] adres = klient.getKlientUlica().split(" ");
	            	klientAdres = adres[0];
	            	klientNumerUlicy = adres[1];
	            	}
	            }
	            if(textFieldKlientKodPocztowy.getText().equals("")){
	            	klientKodPocztowy = klient.getKlientKodPocztowy();
	            }
	           
	            if(textFieldKlientNumerTelefonu.getText().equals(""))
	            	klientNumerTelefonu = klient.getKlientNumerKontaktowy();
	            
	            System.out.println("here 4");
	            res = new Klient().zaktualizujKlientMiasto(con, klientID, klientMiasto);
	            System.out.println("here 5");
	            res = new Klient().zaktualizujKlientAdres(con, klientID, klientAdres, klientNumerUlicy);
	            System.out.println("here 6");
	            res = new Klient().zaktualizujKlientKodPocztowy(con, klientID, klientKodPocztowy);
	            System.out.println("here 7");
	            res = new Klient().zaktualizujKlientNumerTelefonu(con, klientID, klientNumerTelefonu);
	            System.out.println("here 8");
	            
	            Alert alert = new Alert(Alert.AlertType.INFORMATION);
	            alert.setContentText("Pomyślnie zaaktualizowano schronisko");
	            alert.showAndWait();

	           showKlient();

	           Stage stage1 = (Stage) buttonKlientSpisZwierzat.getScene().getWindow();
	           stage1.close();
	           
	   		try{
	   			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Klient.fxml"));
	   			Parent root = fxmlLoader.load();
	   			Scene scene = new Scene(root);
	   	    	Stage stage = new Stage();
	   	    	stage.setScene(scene);
	   	    	stage.show();
	   	    	KlientController.setKlientKonto(klientKonto);
	   			} 
	   		catch (IOException ex)
	   			{	
	   			ex.printStackTrace();
	   			}
	    }
	
}
