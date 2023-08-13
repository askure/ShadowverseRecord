package data;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Resulter {
	private String Time;
	private String MyClass;
	private final SimpleStringProperty EnemyClass = new SimpleStringProperty("");
	private final SimpleStringProperty First = new SimpleStringProperty("");
	private final SimpleStringProperty Second = new SimpleStringProperty("");
	private final SimpleStringProperty  All = new SimpleStringProperty("");
	
	public Resulter(String enemyclass,String first,String second,String all) {
		this.EnemyClass.set(enemyclass);
		this.First.set(first);
		this.Second.set(second);
		this.All.set(all);
	}
	
	public StringProperty EnemyClassProperty() {
	    return EnemyClass;
	}
	public StringProperty FirstProperty() {
		return First;
	}
	
	public StringProperty SecondProperty() {
		return Second;
	}
	
	public StringProperty AllProperty() {
		return All;
	}
	
	
	public String GetTime() {
		return this.Time;
	}
	
	public String GetMyClass() {
		return this.MyClass;
	}
	
	public String GetEnemyClass() {
		return this.EnemyClass.get();
	}
	

	public String GetFirst() {
		return this.First.get();
	}
	
	public String GetSecond() {
		return this.Second.get();
	}
	public String GetAll() {
		return this.All.get();
	}
	
	
	public void SetTime(String t) {
		this.Time = t;
	}
	
	public void SetMyClass(String m) {
		this.MyClass = m;
	}
	public void SetEnemyClass(String e) {
		this.EnemyClass.set(e);
	}
	
	public void SetFirst(String f) {
		this.First.set(f);
	}
	public void SetSecond(String s) {
		this.Second.set(s);
	}
	
	public void SetAll(String a) {
		this.All.set(a);
	}
}
