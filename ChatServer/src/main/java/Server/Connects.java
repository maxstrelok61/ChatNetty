package Server;

import java.util.HashMap;
import java.util.Map;

public class Connects {
    //список каналов хранится виде Strint"Номер канала" - String"Ник пользователя"
    private Map<String, String> array = new HashMap<>();

    Connects(){
    }

    public void add(String key, String userName){
        array.put(key,userName);
    }
    public  void del(String key){
        array.remove(key);
    }
    public String getUsername(String key){
        return array.get(key);
    }
    public String getKey(String userName){
        String res = "";
        for (Map.Entry entry: array.entrySet()) {
            if (entry.getValue().equals(userName)) res = (String) entry.getKey();
        }
        return res;
    }

}
