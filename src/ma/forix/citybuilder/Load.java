package ma.forix.citybuilder;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;

public class Load {
    JSONParser parser;
    JSONObject jsonObject;
    Object obj;

    public Load(){
        parser = new JSONParser();
    }

    public void load(Map map){
        try {
            obj = parser.parse(new FileReader("test.json"));
            jsonObject = (JSONObject) obj;
            System.out.println(jsonObject.toString());
            map.setMap(jsonObject);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
