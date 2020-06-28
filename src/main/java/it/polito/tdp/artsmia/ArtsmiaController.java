package it.polito.tdp.artsmia;

import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import it.polito.tdp.artsmia.model.Arco;
import it.polito.tdp.artsmia.model.Artist;
import it.polito.tdp.artsmia.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class ArtsmiaController {
	
	private Model model ;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnCreaGrafo;

    @FXML
    private Button btnArtistiConnessi;

    @FXML
    private Button btnCalcolaPercorso;

    @FXML
    private ComboBox<String> boxRuolo;

    @FXML
    private TextField txtArtista;

    @FXML
    private TextArea txtResult;

    @FXML
    void doArtistiConnessi(ActionEvent event) {
    	
    	List<Arco> listaArchi = this.model.getListaInfoArchi();
    	if(listaArchi == null) {
    		
    		txtResult.appendText("Devi prima crearfe un grafo\n");
    		return;
    	}
    	
    	txtResult.clear();
    	
    	Collections.sort(listaArchi);
    	Map<Integer, Artist> mappaArtisti = this.model.getMappaArtisti();
    	
    	for(Arco a : listaArchi) {
    		
    		Artist a1 = mappaArtisti.get(a.getId1());
    		Artist a2 = mappaArtisti.get(a.getId2());
    		
    		txtResult.appendText(a.getPeso()+" "+a1+" "+a2+"\n");
    	}
    }

    @FXML
    void doCalcolaPercorso(ActionEvent event) {
    	
    	int id = 0;
    	
    	Map<Integer, Artist> mappaArtisti = this.model.getMappaArtisti();
    	if(mappaArtisti == null) {
    		
    		txtResult.appendText("Devi prima crearfe un grafo\n");
    		return;
    	}
    	
    	try {
    		
    		id = Integer.parseInt(txtArtista.getText());
    		
    	}
    	catch(NumberFormatException nfe) {
    		
    		txtResult.appendText("Devi inserire un id valido\n");
    		return;
    	}
    	
    	if(!mappaArtisti.containsKey(id)) {
    		
    		txtResult.appendText("Artista non presente\n");
    		return;
    	}
    	
    	List<Artist> percorsoArtisti = this.model.calcolaPercorso(id);
    	
    	txtResult.clear();
    	txtResult.appendText("Peso del percorso più lungo: "+this.model.getPesoPercorsoMax()+"\n");
    	
    	for(Artist a : percorsoArtisti) {
    		
    		txtResult.appendText(a.toString()+"\n");
    	}
    	
    }

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	
    	String ruolo = boxRuolo.getValue();
    	this.model.creaGrafo(ruolo);
    	
    	int numeroVertici = this.model.getGrafo().vertexSet().size();
    	int numeroArchi = this.model.getGrafo().edgeSet().size();
    	
    	txtResult.clear();
    	txtResult.appendText(String.format("Grafo creato correttamente con %d vertici e %d archi", numeroVertici, numeroArchi));
    	
    }

    public void setModel(Model model) {
    	this.model = model;
    }

    
    @FXML
    void initialize() {
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'Artsmia.fxml'.";
        assert btnArtistiConnessi != null : "fx:id=\"btnArtistiConnessi\" was not injected: check your FXML file 'Artsmia.fxml'.";
        assert btnCalcolaPercorso != null : "fx:id=\"btnCalcolaPercorso\" was not injected: check your FXML file 'Artsmia.fxml'.";
        assert boxRuolo != null : "fx:id=\"boxRuolo\" was not injected: check your FXML file 'Artsmia.fxml'.";
        assert txtArtista != null : "fx:id=\"txtArtista\" was not injected: check your FXML file 'Artsmia.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Artsmia.fxml'.";

    }

	public void popolaTendinaRuoli() {
		
		List<String> listaRuoli = this.model.getAllDistinctRoles();
		
		Collections.sort(listaRuoli);
		
		boxRuolo.getItems().clear();
		boxRuolo.getItems().addAll(listaRuoli);
		boxRuolo.setValue(listaRuoli.get(0));
		
	}
}
