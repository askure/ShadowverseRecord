package data;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class DuplicateMap<K,V> {
	private Map<K,ArrayList<V>> m = new HashMap<>();
	
	public void put(K k,V v) {
		if(m.containsKey(k)) {
			m.get(k).add(v);
		}
		else {
			ArrayList<V> arr = new ArrayList<>();
			arr.add(v);
			m.put(k, arr);
		}
	}
	
	public ArrayList<V> get(K k){
		return m.get(k);
	}
	
	public V get(K k,int index) {
		return m.get(k).size()-1< index ? null: m.get(k).get(index);
	}
	
	public void remove(K k,int index) {
		m.get(k).remove(index);
	}
	public int indexOf(K k ,V v) {
		if(!m.containsKey(k))return -1;
		return m.get(k).indexOf(v);
	}
	public Set<K> keyset(){
		return m.keySet();
	}

}
