package application;

import java.net.URL;
import java.util.ResourceBundle;

import data.Data;
import data.User;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class ShadowverseRecorderController implements Initializable{
	@FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ChoiceBox<String> EnemyClass;

    @FXML
    private ChoiceBox<String> MyClass;
    
    @FXML
    private TextField AddDeck;
    
    @FXML
    private ChoiceBox<String> First;
    @FXML
    private ChoiceBox<String> DefalutClass;
    
    @FXML
    private ChoiceBox<String> DeleateDeckList;
   
    @FXML
    private Label FirstLabel;
    
    @FXML
    private Label InfoText;
    
    @FXML
    private Label Buttleinfo;
    
    @FXML
    private Button AddDeckButton;
    
    @FXML
    private Button DeleateDeckButton;
    
    @FXML
    private Button ResultButton;
    
    
    @FXML
    private CheckBox EnemyCheck;
    
    @FXML
    private CheckBox MyCheck;
    
    private User user;
    
    private Data data;
    
    @FXML
    void PassPhrase(ActionEvent event) {
    	int index = DefalutClass.getSelectionModel().selectedIndexProperty().intValue();
    	//System.out.println(AddDeck.getText() +"(" + DefalutClass.getItems().get(index) + ")");
    	if(index == -1 || AddDeck.getText().equals("")) {
    		InfoText.setText("内容に不備があります");
    		return;
    	}
    	user.AddList(DefalutClass.getItems().get(index),AddDeck.getText(), null);
    	user.WriteCSV();
    	ChangeChoiceBox();
    	InfoText.setText("デッキの登録が完了しました");
    	Init();
    	
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		assert EnemyClass != null : "fx:id=\"EnemyClass\" was not injected: check your FXML file 'ShadowverseRecorder.fxml'.";
        assert MyClass != null : "fx:id=\"myClass\" was not injected: check your FXML file 'ShadowverseRecorder.fxml'.";
        DefalutClass.getItems().addAll("エルフ","ロイヤル","ウィッチ","ドラゴン","ネクロマンサー","ヴァンパイア","ビショップ","ネメシス");
        EnemyClass.getItems().addAll("エルフ","ロイヤル","ウィッチ","ドラゴン","ネクロマンサー","ヴァンパイア","ビショップ","ネメシス");
        AddDeck.setPromptText("デッキ名を入力");
        user  = new User();
        user.ReadCSV();
        data = new Data();
        data.ReadCSV();
        ChangeChoiceBox();
        InfoText.setText("");
        First.getItems().addAll("先攻","後攻");
        FirstLabel.setText("");
        Buttleinfo.setText("");
    	First.getSelectionModel().selectedIndexProperty().addListener((ObservableValue<? extends Number> 
        observ, Number oldVal, Number newVal)->{
        	int newnum = newVal.intValue();
        	if(newnum == 0) 
        		FirstLabel.setText("   後攻");
        	else
        		FirstLabel.setText("   先攻");
        });
	}
	
    @FXML
    void Deleate(ActionEvent event) {
    	int index = DeleateDeckList.getSelectionModel().selectedIndexProperty().intValue();
    	//System.out.println(AddDeck.getText() +"(" + DefalutClass.getItems().get(index) + ")");
    	if(index == -1) {
    		InfoText.setText("削除するデッキを選択してください");
    		return;
    	}
    	String s  = DeleateDeckList.getItems().get(index);
    	String[] del = s.replace("(",",").replace(")", ",").split(",");
     	user.DeleateList(del[1],del[0]);
    	user.WriteCSV();
    	ChangeChoiceBox();  
    	InfoText.setText("デッキの削除が完了しました");
    }
    
    @FXML
    void OnEnemyCheck(ActionEvent event) {
    	boolean e = EnemyCheck.selectedProperty().get();
    	boolean m = MyCheck.selectedProperty().get();
    	if(e == m) {
    		MyCheck.selectedProperty().set(!e);
    	}
    }

    @FXML
    void OnMyCheck(ActionEvent event) {
    	boolean e = EnemyCheck.selectedProperty().get();
    	boolean m = MyCheck.selectedProperty().get();
    	if(e == m) {
    		EnemyCheck.selectedProperty().set(!m);
    	}
    }
    
    @FXML
    void AddResult(ActionEvent event) {
    	boolean e = EnemyCheck.selectedProperty().get();
    	boolean m = MyCheck.selectedProperty().get();
    	boolean f = false;
    	int mindex = MyClass.getSelectionModel().selectedIndexProperty().intValue();
    	int eindex = EnemyClass.getSelectionModel().selectedIndexProperty().intValue();
    	
    	int findex = First.getSelectionModel().selectedIndexProperty().intValue();
    	if(findex == -1 || findex ==1) f = false;
    	else if(findex == 0) f = true;
    	//System.out.println(AddDeck.getText() +"(" + DefalutClass.getItems().get(index) + ")");
    	if(mindex == -1 || eindex == -1) {
    		Buttleinfo.setText("デッキを選択してください");
    		return;
    	}
    	if(!e== !m) {
    		Buttleinfo.setText("勝敗を選択してください");
    		return;
    	}
    	String ms  = MyClass.getItems().get(mindex);
    	String es = EnemyClass.getItems().get(eindex);
    	if(ms.contains("(")) {
    		String[] add = ms.replace("(",",").replace(")", ",").split(",");
    		if(findex != -1)data.WriteCSV(add[1], es, add[0], m, f);
    		else data.WriteCSV(add[1], es, add[0], m, null);
    	}
    	else {
    		if(findex != -1)data.WriteCSV(ms, es,"", m, f);
    		else data.WriteCSV(ms, es,"", m, null);
    	}
    	Buttleinfo.setText("勝敗登録が完了しました");
    	Init();
    }
	
	void ChangeChoiceBox() {
		MyClass.getItems().clear();
		DeleateDeckList.getItems().clear();
		//user.ReadCSV();
		MyClass.getItems().addAll(user.ArkeyToString());
        DeleateDeckList.getItems().addAll(user.ArkeyToString());
        MyClass.getItems().addAll( "----------","エルフ","ロイヤル","ウィッチ","ドラゴン","ネクロマンサー","ヴァンパイア","ビショップ","ネメシス");
        Init();
	}
	
	void Init() {
		First.getSelectionModel().select("");
		DeleateDeckList.getSelectionModel().select("");
		AddDeck.setText("");
		DefalutClass.getSelectionModel().select("");
		
		
	}
}
