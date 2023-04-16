package treeSem;


import link.Childd;

abstract interface  Tree33{
    void mm();
    void m2();
     abstract void mmm();
}

abstract class Tr implements Tree33 {
    public  Integer m2;
    @Override
    public void mm() {
        System.out.println("mm");
    }
    @Override
    public void m2() {
        System.out.println("m2t");
    }
}

abstract class TY extends Tr {

}
public class RedBlackTree extends Father {

    public  String name="44";

    public RedBlackTree() {
        //System.out.println("child");
    }



    public static void main(String[] args){


        RedBlackTree p = new RedBlackTree();
        System.out.println(p.getName().get("344"));

    }

}
