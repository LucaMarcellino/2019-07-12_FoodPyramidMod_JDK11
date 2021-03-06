package it.polito.tdp.food;

import java.net.URL;
import java.util.ResourceBundle;

import it.polito.tdp.food.model.Calorie;
import it.polito.tdp.food.model.Food;
import it.polito.tdp.food.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

//controller turno A --> switchare al branch master_turnoB per turno B

public class FXMLController {
	
	private Model model;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField txtPorzioni;

    @FXML
    private TextField txtK;

    @FXML
    private Button btnAnalisi;

    @FXML
    private Button btnCalorie;

    @FXML
    private Button btnSimula;

    @FXML
    private ComboBox<Food> boxFood;

    @FXML
    private TextArea txtResult;

    @FXML
    void doCalorie(ActionEvent event) {
    	txtResult.clear();
    	if(boxFood.getValue() == null)
    		txtResult.appendText("Scegli un cibo");
    	for(int i=0;i<5;i++) {
			txtResult.appendText(model.getVicini(boxFood.getValue()).get(i)+"\n");
		}
		

    }

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	int ctn=0;
    	try {
    		ctn=Integer.parseInt(txtPorzioni.getText());
		} catch (NumberFormatException nfe) {
			txtResult.appendText("inserire un numero intero");
			return;
		}
    	model.creaGrafo(ctn);
    	
    	boxFood.getItems().addAll(model.getVertex());
    	

    }

    @FXML
    void doSimula(ActionEvent event) {
    	txtResult.clear();
    	int k=0;
    	try {
			k= Integer.parseInt(txtK.getText());
		} catch (NumberFormatException nfe) {
			txtResult.appendText("inserire un numero intero tra 1 e 10");
			return;
		}
    	
    	if(k<1 && k>10)
    		txtResult.appendText("Inserisci un numero compreso tra 1 e 10");
    	
    	if(boxFood.getValue() == null)
    		txtResult.appendText("Scegli un cibo");
    	
    	String messaggo = model.simula(boxFood.getValue(), k);
    	txtResult.appendText(messaggo);

    }

    @FXML
    void initialize() {
        assert txtPorzioni != null : "fx:id=\"txtPorzioni\" was not injected: check your FXML file 'Food.fxml'.";
        assert txtK != null : "fx:id=\"txtK\" was not injected: check your FXML file 'Food.fxml'.";
        assert btnAnalisi != null : "fx:id=\"btnAnalisi\" was not injected: check your FXML file 'Food.fxml'.";
        assert btnCalorie != null : "fx:id=\"btnCalorie\" was not injected: check your FXML file 'Food.fxml'.";
        assert btnSimula != null : "fx:id=\"btnSimula\" was not injected: check your FXML file 'Food.fxml'.";
        assert boxFood != null : "fx:id=\"boxFood\" was not injected: check your FXML file 'Food.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Food.fxml'.";

    }

	public void setModel(Model model) {
		this.model = model;
		txtResult.setEditable(false);
	}
}
