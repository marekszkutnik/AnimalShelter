package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.util.ResourceBundle;

public class LogPanelController implements Initializable  {

	private Connection conn;
	
	private LogPanel konto;
	
	
    @FXML
    private Label labelLogin;

    @FXML
    private Label labelHaslo;

    @FXML
    private Button buttonZaloguj;

    @FXML
    private TextField textFieldLogin;

    @FXML
    private PasswordField passwordFieldHaslo;

    AnchorPane pane;
    
    
    
    public void setButtonZaloguj(ActionEvent event) {

    	conn = DBConnection.getConnection();
    	Stage stage1 = (Stage) buttonZaloguj.getScene().getWindow();
    	konto = new LogPanel().get(conn, textFieldLogin.getText().trim(), passwordFieldHaslo.getText().trim());

    	try
    	{
    		KlientController.setKlientKonto(konto.getKontoId());
    		if(!konto.getKontoUprawnienia().isEmpty() && konto.getKontoUprawnienia().equals("k"))
    		{
    		try{
    			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Klient.fxml"));
				Parent root = fxmlLoader.load();
				Scene scene = new Scene(root);
    	    	Stage stage = new Stage();
    	    	stage1.close();	
    	    	stage.setScene(scene);
    	    	stage.show();
    	    	System.out.println(konto.getKontoId());
    	    	
    	    	System.out.println(konto.getKontoId());
    			} 
    		catch (IOException e1) 
    			{	
				e1.printStackTrace();
				}
    		KlientController.setKlientKonto(konto.getKontoId());
    		
    		}
    		else if(!konto.getKontoUprawnienia().isEmpty() && konto.getKontoUprawnienia().equals("a"))
    		{
    			try{
        			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Pracownik.fxml"));
    				Parent root = fxmlLoader.load();
    				Scene scene = new Scene(root);
        	    	Stage stage = new Stage();
        	    	stage1.close();	
        	    	stage.setScene(scene);
        	    	stage.show();
        	    	KlientController.setKlientKonto(2);
        			} 
        		catch (IOException e1) 
        			{	
    				e1.printStackTrace();
    				}
        			
        		
    		}
    		else
    		{
    			Alert alert = new Alert(Alert.AlertType.INFORMATION);
    			alert.setTitle("Błąd logowania.");
    			alert.setContentText("Niepoprawne haslo lub login");
    			alert.showAndWait();
    			return;
    		}
    	}
    	catch(NullPointerException npe)
    	{
    		Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setTitle("Błąd logowania.");
			alert.setContentText("Niepoprawne haslo lub login");
			alert.showAndWait();
    	}
    	
    	
    
    
    }
    
   @FXML
   void zaloguj(ActionEvent actionEvent)
   {
	  
   	try {
   		    AnchorPane  scena = FXMLLoader.load(getClass().getResource("Klient.fxml"));
			pane.getChildren().setAll(scena);
   	} catch (IOException e1) {
			
			e1.printStackTrace();
		}
   }
   
    
    
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
	
		
	}

}