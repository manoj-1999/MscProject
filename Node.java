import java.util.*;

public class Node {

    String label;
    List<Integer> vertices;
    Node prev;
    Node next;

    Node(String label){
        this.label=label;
        vertices=new ArrayList<>();
        prev=null;
        next=null;
    }




}
