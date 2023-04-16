package treeSem;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Father {
    public String name="Perso";

    int age=0;

    public Father() {
        //System.out.println("father");
    }

    public    void aa(){

    }

    public Map<String,String> getName() {
        Map<String,String> map=new HashMap<>();
        try {
            map.put("344","sdfgd");
            map.put(null,null);

            //return name;
        }catch (Exception e){
            name= "3";

        }finally {
            map.put("344","gggg");
           //return "sd";
        }
return map;
    }

    public int getAge() {
        try {
            return age;
        }catch (Exception e){
            return 3;
        }finally {
            return 5;
        }
    }
}
