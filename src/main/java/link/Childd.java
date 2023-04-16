package link;

import treeSem.Father;

public class Childd extends Father{
    public static void main(String[] args) {
        Father p = new Father();
        System.out.println(p);
    }


    public String aa(String A) {
        System.out.println("aa");
        return "3";
    }
}
