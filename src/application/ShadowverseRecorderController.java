package application;

import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

import data.Data;
import data.Resulter;
import data.User;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

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
    private ChoiceBox<String> ResultDeck;
   
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
    private Button DataReset;
    
    @FXML
    private CheckBox EnemyCheck;
    
    @FXML
    private CheckBox MyCheck;
    
    @FXML
    private TableView<Resulter> ResultView;
    
    @FXML
    private TableColumn<Resulter, String> EnemyClassColumn;
    
    @FXML
    private TableColumn<Resulter, String> FirstColumn;
    
    @FXML
    private TableColumn<Resulter, String> SecondColumn;
    
    @FXML
    private TableColumn<Resulter, String> AllColumn;
    
    
    private User user;
    
    private Data data;
    
    private String[] AllClass = {"エルフ","ロイヤル","ウィッチ","ドラゴン","ネクロマンサー","ヴァンパイア","ビショップ","ネメシス"};
    
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
    
    @FXML
    void Reset() {
    	user.ResetCSV();
    	data.ResetCSV();
    	ChangeChoiceBox();
    	Init();
    	InfoText.setText("全てのデータを削除しました。");
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		assert EnemyClass != null : "fx:id=\"EnemyClass\" was not injected: check your FXML file 'ShadowverseRecorder.fxml'.";
        assert MyClass != null : "fx:id=\"myClass\" was not injected: check your FXML file 'ShadowverseRecorder.fxml'.";
        DefalutClass.getItems().addAll(Arrays.asList(AllClass));
        EnemyClass.getItems().addAll(Arrays.asList(AllClass));
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
        ResultDeck.getSelectionModel().selectedIndexProperty().addListener((ObservableValue<? extends Number> 
        observ, Number oldVal, Number newVal)->{
        	int newnum = newVal.intValue();
        	if(newnum != -1)ChangeResultView(ResultDeck.getItems().get(newnum));
        	
        });
    	
    	First.getSelectionModel().selectedIndexProperty().addListener((ObservableValue<? extends Number> 
        observ, Number oldVal, Number newVal)->{
        	int newnum = newVal.intValue();
        	if(newnum == 0) 
        		FirstLabel.setText("   後攻");
        	else
        		FirstLabel.setText("   先攻");
        });
    	 EnemyClassColumn.setCellValueFactory(new PropertyValueFactory<>("EnemyClass"));
    	 FirstColumn.setCellValueFactory(new PropertyValueFactory<>("First"));
    	 SecondColumn.setCellValueFactory(new PropertyValueFactory<>("Second"));
    	 AllColumn.setCellValueFactory(new PropertyValueFactory<>("All"));
    	 ChangeResultView("");
    	
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
    	String f = "";
    	int mindex = MyClass.getSelectionModel().selectedIndexProperty().intValue();
    	int eindex = EnemyClass.getSelectionModel().selectedIndexProperty().intValue();
    	
    	int findex = First.getSelectionModel().selectedIndexProperty().intValue();
    	if(findex == -1) f = "null";
    	else f = First.getItems().get(findex);
    	//System.out.println(AddDeck.getText() +"(" + DefalutClass.getItems().get(index) + ")");
    	if(mindex == -1 || eindex == -1 || MyClass.getItems().get(mindex).equals("----------")) {
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
    		data.WriteCSV(add[1], es, add[0], m, f);
    		
    	}
    	else {
    		data.WriteCSV(ms, es,"", m, f);
    	}
    	Buttleinfo.setText("勝敗登録が完了しました");
    	System.out.println(data.Result()[0] + "/" + (data.Result()[0] + data.Result()[1]));
    	System.out.println(data.Result()[2] + "/" + (data.Result()[2] + data.Result()[3]));
    	System.out.println(data.Result()[4] + "/" + (data.Result()[4] + data.Result()[5]));
    	Init();
    }
	
    void ChangeResultView(String s) {
    	ResultView.getItems().clear();
    	System.out.println(s);
    	if(s.equals("") || s.equals("----------")) {
    		for(String e: AllClass) {
    			int[] r = data.Result(e);
    			String[] rs = ResultIntToString(r);
    			Resulter a = new Resulter(e,rs[0],rs[1],rs[2]);
    			ResultView.getItems().add(a);
    		}
    		int[] r = data.Result();
    		String[] rs = ResultIntToString(r);
			Resulter a = new Resulter("全体",rs[0],rs[1],rs[2]);
			ResultView.getItems().add(a);
    		
    	}
    	else {
        	if(s.contains("(")) {
        		String[] result = s.replace("(",",").replace(")", ",").split(",");
        		for(String e: AllClass) {
        			int[] r = data.Result(result[1],result[0],e);
        			String[] rs = ResultIntToString(r);
        			Resulter a = new Resulter(e,rs[0],rs[1],rs[2]);
        			ResultView.getItems().add(a);
        		}
        		int[] r = data.MyResult(result[1],result[0]);
        		String[] rs = ResultIntToString(r);
    			Resulter a = new Resulter("全体",rs[0],rs[1],rs[2]);
    			ResultView.getItems().add(a);
        		
        		
        	}
        	else {
        		for(String e: AllClass) {
        			int[] r = data.Result(s,e);
        			String[] rs = ResultIntToString(r);
        			Resulter a = new Resulter(e,rs[0],rs[1],rs[2]);
        			ResultView.getItems().add(a);
        		}
        		int[] r = data.MyResult(s);
        		String[] rs = ResultIntToString(r);
    			Resulter a = new Resulter("全体",rs[0],rs[1],rs[2]);
    			ResultView.getItems().add(a);
        	}
    	}
    }
    
    String[] ResultIntToString(int[] r) {
    	String[] rs = new String[3];
    	double fwinrate = (r[0]+ r[1] == 0) ? 0 :(Math.round((double)r[0]/(r[0]+r[1]) * 100)/100.0) * 100;
    	double swinrate = (r[2]+ r[3] == 0) ? 0 :(Math.round((double)r[2]/(r[2]+r[3]) * 100)/100.0) * 100;
    	double awinrate = (r[4]+ r[5] == 0) ? 0 :(Math.round((double)r[4]/(r[4]+r[5]) * 100)/100.0) * 100;
    	rs[0] = (r[0]+ r[1] == 0) ? "-":String.valueOf(r[0]) + "-" + String.valueOf(r[1])+ "(" + String.valueOf(fwinrate) + "%)";
    	rs[1] = (r[0]+ r[1] == 0) ? "-":String.valueOf(r[2]) + "-" + String.valueOf(r[3])+ "(" + String.valueOf(swinrate) + "%)";
    	rs[2] = (r[4]+ r[5] == 0) ? "-":String.valueOf(r[4]) + "-" + String.valueOf(r[5])+ "(" + String.valueOf(awinrate) + "%)";
    	return rs;
    }
	void ChangeChoiceBox() {
		MyClass.getItems().clear();
		ResultDeck.getItems().clear();
		DeleateDeckList.getItems().clear();
		//user.ReadCSV();
		MyClass.getItems().addAll(user.ArkeyToString());
        DeleateDeckList.getItems().addAll(user.ArkeyToString());
        MyClass.getItems().addAll( "----------","エルフ","ロイヤル","ウィッチ","ドラゴン","ネクロマンサー","ヴァンパイア","ビショップ","ネメシス");
        ResultDeck.getItems().addAll(user.ArkeyToString());
        ResultDeck.getItems().addAll( "----------","エルフ","ロイヤル","ウィッチ","ドラゴン","ネクロマンサー","ヴァンパイア","ビショップ","ネメシス");
        Init();
	}
	
	void Init() {
		First.getSelectionModel().select("");
		DeleateDeckList.getSelectionModel().select("");
		ResultDeck.getSelectionModel().select("----------");
		AddDeck.setText("");
		DefalutClass.getSelectionModel().select("");
		FirstLabel.setText("");	
		InfoText.setText("");
		ChangeResultView("");
	}
}
