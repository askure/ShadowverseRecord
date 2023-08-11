package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

public class ShadowverseRecorderController implements Initializable{
	@FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ChoiceBox<String> EnemyClass;

    @FXML
    private ChoiceBox<String> myClass;
    
    @FXML
    private TextField AddDeck;
    
    @FXML
    private ChoiceBox<String> DefalutClass;
    
    @FXML
    private Button addDeck;
    
    private 
    
    @FXML
    void PassPhrase(ActionEvent event) {
    	int index = DefalutClass.getSelectionModel().selectedIndexProperty().intValue();
    	System.out.println(AddDeck.getText() +"(" + DefalutClass.getItems().get(index) + ")");
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		assert EnemyClass != null : "fx:id=\"EnemyClass\" was not injected: check your FXML file 'ShadowverseRecorder.fxml'.";
        assert myClass != null : "fx:id=\"myClass\" was not injected: check your FXML file 'ShadowverseRecorder.fxml'.";
        DefalutClass.getItems().addAll("エルフ","ロイヤル","ウィッチ","ドラゴン","ネクロマンサー","ヴァンパイア","ビショップ","ネメシス");
        AddDeck.setPromptText("デッキ名を入力");
	}
}
