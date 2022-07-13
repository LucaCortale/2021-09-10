/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.yelp;

import java.net.URL;
import java.util.ResourceBundle;

import org.jgrapht.Graphs;

import it.polito.tdp.yelp.model.Business;
import it.polito.tdp.yelp.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {
	
	private Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="btnCreaGrafo"
    private Button btnCreaGrafo; // Value injected by FXMLLoader

    @FXML // fx:id="btnDistante"
    private Button btnDistante; // Value injected by FXMLLoader

    @FXML // fx:id="btnCalcolaPercorso"
    private Button btnCalcolaPercorso; // Value injected by FXMLLoader

    @FXML // fx:id="txtX2"
    private TextField txtX2; // Value injected by FXMLLoader

    @FXML // fx:id="cmbCitta"
    private ComboBox<String> cmbCitta; // Value injected by FXMLLoader

    @FXML // fx:id="cmbB1"
    private ComboBox<Business> cmbB1; // Value injected by FXMLLoader

    @FXML // fx:id="cmbB2"
    private ComboBox<Business> cmbB2; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader
    
    @FXML
    void doCreaGrafo(ActionEvent event) {
    	txtResult.clear();
    	
    	String citta = cmbCitta.getValue();
    	
    	this.model.creaGrafo(citta);
    	
    	for(Business ss : this.model.getAllLocaliCity(citta)) {
    		
    		cmbB1.getItems().add(ss);
    		cmbB2.getItems().add(ss);
    	}
    	
    	txtResult.setText("GRAFO CREATO con : #VERTICI "+this.model.getVertici()+
    					" #ARCHI "+this.model.getArchi()+"\n");
    		
    }

    @FXML
    void doCalcolaLocaleDistante(ActionEvent event) {
    	
    	Business b = cmbB1.getValue();

    	txtResult.appendText(this.model.getLocaleDistante(b));
    	
    }

    @FXML
    void doCalcolaPercorso(ActionEvent event) {

    	Business b1 = cmbB1.getValue();
    	Business b2 = cmbB2.getValue();
    	Double x =  Double.parseDouble(txtX2.getText());
    	
    	for(Business b : this.model.calcolaPercorso(b1	, b2, x))
    	txtResult.appendText(b.getBusinessName()+"\n");;
    	
    	txtResult.appendText(""+this.model.contaDistanzaTotale(b2));
    	
    }


    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnDistante != null : "fx:id=\"btnDistante\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnCalcolaPercorso != null : "fx:id=\"btnCalcolaPercorso\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtX2 != null : "fx:id=\"txtX2\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbCitta != null : "fx:id=\"cmbCitta\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbB1 != null : "fx:id=\"cmbB1\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbB2 != null : "fx:id=\"cmbB2\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";

    }
    
    public void setModel(Model model) {
    	this.model = model;
    	for(String s : model.getAllCity())
    		cmbCitta.getItems().add(s);
    }
}
