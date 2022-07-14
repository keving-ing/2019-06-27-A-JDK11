package it.polito.tdp.crimes;

import it.polito.tdp.nyc.model.DestinazionePeso;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class Main {
	public static void main(String[] args) {
        EntryPoint.main(args);
    }
}


//RAGA IO CI SONO!!

//Per le tabelle guardate la simulazione del 2022-05-31-sim

@FXML // fx:id="clDistanza"
private TableColumn<DestinazionePeso, Double> clDistanza; // Value injected by FXMLLoader  
//In TableColumn dovete mettere la vostra classe Adiacenza e nella virgola cosa volete stampare,
//quindi ci saranno 3 colonne in due mettete <Adiacena, String> se l'arco é string i nell'ultima <Adiacenza, Double> per il peso

@FXML // fx:id="tblQuartieri"
private TableView<DestinazionePeso> tblQuartieri; // Value injected by FXMLLoader
//In TableView mettete solo Adiacenza

//Per il resto la table si popola da sola


//--------------------------------------------------------------------------

//Dice Gabbo che il peso é molto difficile e ad alcuni anche se leggermente sbagliato ha dato lo stesso 18,
//quindi completate tutto e non vi ritirate anche se il peso non torna, fatevelo correggere lo stesso