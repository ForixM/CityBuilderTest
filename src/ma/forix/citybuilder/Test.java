package ma.forix.citybuilder;

import org.json.simple.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Test {
    public static void main(String[] args){
        JSONObject obj = new JSONObject();
        obj.putIfAbsent("gfgdfg", new Integer(2));
        obj.putIfAbsent("gsdfgsdfhj", 4);
        obj.putIfAbsent("test", 6);
        obj.putIfAbsent("oui", 666);
        obj.putIfAbsent("ok", 4125);

        File f = new File("test.json");

        if(!f.exists()) {
            System.out.println("Ecriture en cours");
            try {
                FileWriter file = new FileWriter("test.json");
                file.write(obj.toJSONString());
                file.flush();
                file.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else
            System.out.println("Fichier existe déjà");
        //System.out.println(obj.toJSONString());
    }
}
