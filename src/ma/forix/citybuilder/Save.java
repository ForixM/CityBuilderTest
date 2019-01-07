package ma.forix.citybuilder;

import org.json.simple.JSONObject;

import java.io.FileWriter;
import java.io.IOException;

public class Save {
    JSONObject obj;
    public Save(){
        obj = new JSONObject();
    }

    public void save(Map map){
        obj = map.getMap();
        try (FileWriter file = new FileWriter("test.json")){
            file.write(obj.toString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
