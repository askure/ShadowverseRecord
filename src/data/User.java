package data;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class User {
	private String playerName;
	DuplicateMap<String,String> Arkey;
	private List<String>Memo;
	private String path = "./Record/UserData.csv";
	
	public User(){
		Arkey = new DuplicateMap<>();
		Memo = new ArrayList<>();
	}
	void CraeateCSV() {
		Path p = Paths.get(path);
		if(Files.exists(p))return;
		Path pd = Paths.get("./Record");
		try {
			if(!Files.exists(pd))Files.createDirectory(pd);
			Files.createFile(p);
            File file = new File(path);
            PrintWriter pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file),"UTF-8")));
            pw.println("-\nClass,Arkey");
            pw.close();
            System.out.println("作成完了");
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	public void ResetCSV() {
		Path p = Paths.get(path);
		if(!Files.exists(p)) {
			return;
		}
		try {
			Files.delete(p);
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		ReadCSV();
	}
	public void AddList(String c,String Arkey,String Memo) {
		if(Arkey.length() > 20)return;
		this.Arkey.put(c, Arkey);
	}
	public void DeleateList(String c,String Arkey) {
		int index = this.Arkey.indexOf(c,Arkey);
		if(index== -1)return;
		this.Arkey.remove(c, index);
	}
	void ChangeName(String name) {
		playerName = name;
	}
	public void ReadCSV() {
		CraeateCSV();
		try {
			List<String> lines =Files.readAllLines(Paths.get(path),Charset.forName("UTF-8"));
			playerName = lines.get(0);
			Arkey.clear();
			for(int i=2;i<lines.size(); i++) {
				String[] data = lines.get(i).split(",");
				Arkey.put(data[0],data[1]);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	public void WriteCSV() {
		CraeateCSV();
		try {
			File file = new File(path);
            PrintWriter pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file),"UTF-8")));
            pw.println(playerName+"\nClass,Arkey");
			for(String c : Arkey.keyset()) {
				for(String a: Arkey.get(c)) {
					pw.println(c+","+a);
				}
			}
            pw.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public String GetName() {
		return playerName;
	}
	
	public List<String> ArkeyToString() {
		List<String> s = new ArrayList<String>();
		for(String c : Arkey.keyset()) {
			for(String a: Arkey.get(c)) {
				//System.out.println(c+","+a);
				s.add(a + "(" + c + ")");
			}
		}
		return s;
	}
	public DuplicateMap<String,String>  GetArkey(){
		return Arkey;
	}
	
	public List<String> GetMemo(){
		return Memo;
	}
	//debug
	public void PrintData() {
		for(String c : Arkey.keyset()) {
			for(String a: Arkey.get(c)) {
				System.out.println(c+","+a);
			}
		}
	}
}
