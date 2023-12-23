import java.util.HashMap;


public class Map<T> {
    HashMap<String,T> map = new HashMap<>();
    public Map(){

    }
    void add(String k,T v){
        map.put(k,v);
    }

}