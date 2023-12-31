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
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Data {
	private List<String> Time ;
	private List<String> PlayerClass;
	private List<String> EnemyClass;
	private List<String> Arkey;
	private List<Boolean> Result;
	private List<String> First;
	private String path = "./Record/Data.csv";
	public Data(){
		Time = new ArrayList<>();		
		PlayerClass =new ArrayList<>();		
		EnemyClass= new ArrayList<>();		
		Arkey  =  new ArrayList<>();		
		Result = new ArrayList<>();		
		First = new ArrayList<>();
		
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
            pw.println("Time,PlayerClass,Arkey,EnemyClass,Result,First");
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
	
	public void ReadCSV() {
		CraeateCSV();
		try {
			List<String> lines =Files.readAllLines(Paths.get(path),Charset.forName("UTF-8"));
			lines.remove(0);
			Collections.reverse(lines);
			String now = new Timestamp(System.currentTimeMillis()).toString();
			String nowdate = now.substring(0,10);
			Time.clear();
			PlayerClass.clear();
			Arkey.clear();
			EnemyClass.clear();
			Result.clear();
			First.clear();
			for(int i=0; i<lines.size(); i++) {
				if(i== lines.size()) return;
				String[] data = lines.get(i).split(",");
				if(nowdate.equals(data[0].substring(0,10))) {
					Time.add(data[0].substring(11,16));
				}else {
					Time.add(data[0].substring(6,10).replace("-", "/"));
					
				}
				PlayerClass.add(data[1]);
				Arkey.add(data[2]);
				EnemyClass.add(data[3]);
				Result.add(Boolean.parseBoolean(data[4]));
				First.add(data[5]);			
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	public void WriteCSV(String playerclass,String enemyclass,String arkey,Boolean result,String first) {
		CraeateCSV();
		try {
			Timestamp now = new Timestamp(System.currentTimeMillis());
			File file = new File(path);
            PrintWriter pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file,true),"UTF-8")));
            pw.println(now.toString() + "," + playerclass + "," +arkey
            		 + "," +enemyclass + "," +result + "," +first);
            pw.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	//自分と相手クラス指定
	public int[] Result(String p,String e) {
		ReadCSV();
		int f_win  = 0;
		int f_lose = 0;
		int s_win  = 0;
		int s_lose = 0;
		int sum_win = 0;
		int sum_lose = 0;
		for(int i=0;i < Result.size(); i++) {
			if((!PlayerClass.get(i).equals(p)
					||!EnemyClass.get(i).equals(e)))continue;
			if(Result.get(i)) sum_win++;
			if(!Result.get(i))sum_lose++;
			if(First.get(i).equals("null")) continue;
			if(Result.get(i) && First.get(i).equals("先攻")) f_win++;
			if(Result.get(i) && !First.get(i).equals("先攻") ) s_win++;
			if(!Result.get(i) && First.get(i).equals("先攻")) f_lose++;
			if(!Result.get(i) && !First.get(i).equals("先攻")) s_lose++;
		}
		//先攻勝利,先攻敗北,後攻勝利,後攻敗北,合計勝利,合計敗北
		int[] result = {f_win,f_lose,s_win,s_lose,sum_win,sum_lose}; 
		
		return result;
 	}
	
	//全体
	public int[] Result() {
		ReadCSV();
		int f_win  = 0;
		int f_lose = 0;
		int s_win  = 0;
		int s_lose = 0;
		int sum_win = 0;
		int sum_lose = 0;
		for(int i=0;i < Result.size(); i++) {
			if(Result.get(i)) sum_win++;
			if(!Result.get(i))sum_lose++;
			if(First.get(i).equals("null")) continue;
			if(Result.get(i) && First.get(i).equals("先攻")) f_win++;
			if(Result.get(i) && !First.get(i).equals("先攻")) s_win++;
			if(!Result.get(i) && First.get(i).equals("先攻")) f_lose++;
			if(!Result.get(i) && !First.get(i).equals("先攻")) s_lose++;
		}
		//先攻勝利,先攻敗北,後攻勝利,後攻敗北,合計勝利,合計敗北
		int[] result = {f_win,f_lose,s_win,s_lose,sum_win,sum_lose}; 
		return result;
 	}
	
	//相手クラスのみ指定
	public int[] Result(String e) {
		ReadCSV();
		int f_win  = 0;
		int f_lose = 0;
		int s_win  = 0;
		int s_lose = 0;
		int sum_win = 0;
		int sum_lose = 0;
		for(int i=0;i < Result.size(); i++) {
			if(!EnemyClass.get(i).equals(e)) continue;
			if(Result.get(i)) sum_win++;
			if(!Result.get(i))sum_lose++;
			if(First.get(i).equals("null")) continue;
			if(Result.get(i) && First.get(i).equals("先攻")) f_win++;
			if(Result.get(i) && !First.get(i).equals("先攻")) s_win++;
			if(!Result.get(i) && First.get(i).equals("先攻")) f_lose++;
			if(!Result.get(i) && !First.get(i).equals("先攻")) s_lose++;
		}
		//先攻勝利,先攻敗北,後攻勝利,後攻敗北,合計勝利,合計敗北
		int[] result = {f_win,f_lose,s_win,s_lose,sum_win,sum_lose}; 
		return result;
 	}
	public int[] MyResult(String p) {
		ReadCSV();
		int f_win  = 0;
		int f_lose = 0;
		int s_win  = 0;
		int s_lose = 0;
		int sum_win = 0;
		int sum_lose = 0;
		for(int i=0;i < Result.size(); i++) {
			if(!PlayerClass.get(i).equals(p)) continue;
			if(Result.get(i)) sum_win++;
			if(!Result.get(i))sum_lose++;
			if(First.get(i).equals("null")) continue;
			if(Result.get(i) && First.get(i).equals("先攻")) f_win++;
			if(Result.get(i) && !First.get(i).equals("先攻")) s_win++;
			if(!Result.get(i) && First.get(i).equals("先攻")) f_lose++;
			if(!Result.get(i) && !First.get(i).equals("先攻")) s_lose++;
		}
		//先攻勝利,先攻敗北,後攻勝利,後攻敗北,合計勝利,合計敗北
		int[] result = {f_win,f_lose,s_win,s_lose,sum_win,sum_lose}; 
		return result;
	}
	public int[] MyResult(String p,String a) {
		ReadCSV();
		int f_win  = 0;
		int f_lose = 0;
		int s_win  = 0;
		int s_lose = 0;
		int sum_win = 0;
		int sum_lose = 0;
		for(int i=0;i < Result.size(); i++) {
			if((!PlayerClass.get(i).equals(p)
					|| !Arkey.get(i).equals(a)))continue;
			if(Result.get(i)) sum_win++;
			if(!Result.get(i))sum_lose++;
			if(First.get(i).equals("null")) continue;
			if(Result.get(i) && First.get(i).equals("先攻")) f_win++;
			if(Result.get(i) && !First.get(i).equals("先攻")) s_win++;
			if(!Result.get(i) && First.get(i).equals("先攻")) f_lose++;
			if(!Result.get(i) && !First.get(i).equals("先攻")) s_lose++;
		}
		//先攻勝利,先攻敗北,後攻勝利,後攻敗北,合計勝利,合計敗北
		int[] result = {f_win,f_lose,s_win,s_lose,sum_win,sum_lose}; 
		return result;
	}
	//クラスとアーキ指定
	public int[] Result(String p,String a,String e) {
		ReadCSV();
		int f_win  = 0;
		int f_lose = 0;
		int s_win  = 0;
		int s_lose = 0;
		int sum_win = 0;
		int sum_lose = 0;
		for(int i=0;i < Result.size(); i++) {
			if((!PlayerClass.get(i).equals(p)
					||!EnemyClass.get(i).equals(e)|| !Arkey.get(i).equals(a)))continue;
			if(Result.get(i)) sum_win++;
			if(!Result.get(i))sum_lose++;
			if(First.get(i).equals("null")) continue;
			if(Result.get(i) && First.get(i).equals("先攻")) f_win++;
			if(Result.get(i) && !First.get(i).equals("先攻")) s_win++;
			if(!Result.get(i) && First.get(i).equals("先攻")) f_lose++;
			if(!Result.get(i) && !First.get(i).equals("先攻")) s_lose++;
		}
		//先攻勝利,先攻敗北,後攻勝利,後攻敗北,合計勝利,合計敗北
		int[] result = {f_win,f_lose,s_win,s_lose,sum_win,sum_lose}; 
		return result;
 	}
	public List<String> GetTime(){
		return Time;
	}
	public List<String> GetPlayerClass(){
		return PlayerClass;
	}
	public List<String> GetEnemyClass(){
		return EnemyClass;
	}
	public List<String> GetArkey(){
		return Arkey;
	}
	public List<Boolean> GetResult(){
		return Result;
	}
	public List<String> GetFirst(){
		return First;
	}
	
	//debug
	public void PrintData() {
		for(int i=0; i < Time.size(); i++) {
			System.out.println(
					Time.get(i) + "," + PlayerClass.get(i) + "," + Arkey.get(i) + "," + 
					EnemyClass.get(i) + "," + Result.get(i) + "," + First.get(i) 				
			);
			
		}
	}
}
