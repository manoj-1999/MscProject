import java.util.ArrayList;
import java.util.HashMap;
import java.util.*;

public class OrderingAndProblems {

    //Function to implement the LexBFS algorithm
    String LexBFS(HashMap<Integer, List<Integer>> graph){
        StringBuilder sb=new StringBuilder();
        //head is the intial label
        Node head=new Node("");
        HashMap<Integer,Node> vertexAddress=new HashMap();
        HashMap<String,Node> nodeAddress=new HashMap();
        appendVertices(head,graph,vertexAddress,nodeAddress);
        //end points to the node with largest label
        Node end=head;
        HashSet<Integer> visited=new HashSet<>();

        for(int j=graph.size();j>=1;j--){

            List<Integer> nodes=end.vertices;

            while(nodes.size()==0&&end!=head){
                end.prev.next=end.next;
                end=end.prev;
                nodes=end.vertices;
            }

            //pick a vertex with largest label
            int curVertex=nodes.get(0);
            nodes.remove(Integer.valueOf(curVertex));

            sb.append(curVertex);
            sb.append(" ");
            visited.add(curVertex);

            List<Integer> adjacent=graph.get(curVertex);

            //loop to update the unnumbered adjacent vertices
            for(int i:adjacent){
                if(visited.contains(i)) continue;

                Node cur=vertexAddress.get(i);

                //newLabel of the vertex i is being created
                String newLabel=cur.label+" "+j;
                List<Integer> curChild=cur.vertices;

                curChild.remove(Integer.valueOf(i));
                cur.vertices=curChild;
                Node newNode;
                //what to do when node with newlabel exists or not.
                if(nodeAddress.containsKey(newLabel)){
                    newNode=nodeAddress.get(newLabel);
                    vertexAddress.put(i,newNode);
                    newNode.vertices.add(i);

                }
                else{
                    newNode=new Node(newLabel);
                    newNode.vertices=new ArrayList();
                    newNode.vertices.add(i);
                    nodeAddress.put(newLabel,newNode);
                    vertexAddress.put(i,newNode);


                    newNode.prev=cur;
                    newNode.next=cur.next;

                    cur.next=newNode;


                    //if(newNode.next==null) end=newNode;
                    //else newNode.next.prev=newNode;


                    if(newNode.next==null) {
                        end=newNode;
                    }
                    else newNode.next.prev=newNode;
                }



            }

        }


        return sb.toString().trim();
    }

     //Intialize the values of vertices in nodes, vertexAddress, nodeAddress
     void appendVertices(Node head,HashMap<Integer, List<Integer>> graph,HashMap<Integer,Node> vertexAddress,HashMap<String,Node> nodeAddress){
        List<Integer> nodes=head.vertices;
        for(int i:graph.keySet())
        {
            nodes.add(i);
            vertexAddress.put(i,head);
            nodeAddress.put(head.label,head);
            //System.out.println(i);

        }

    }

    //Function to check if a ordering is perfect vertex elemination scheme
    boolean perfect(HashMap<Integer,List<Integer>> A, HashMap<Integer, List<Integer>> graph, String ordering, HashMap<Integer,Integer> ordering_check){
        for(int i:graph.keySet()){
            A.put(i,new ArrayList<>());
        }
        String orders[]=ordering.split(" ");
        for(int i=1;i<=orders.length-1;i++){
            //System.out.println("perfect");
            int v=Integer.parseInt(orders[i-1]);

            List<Integer> Adj=graph.get(v);

            List<Integer> X=new ArrayList<>();
            for(int j:Adj){
                if(ordering_check.get(j)>i)
                    X.add(j);
            }

            if(X.size()>0){
                int min=Integer.MAX_VALUE;
                int u=Integer.MAX_VALUE;
                for(int j:X){
                    if(ordering_check.get(j)<min)
                    {
                        min=ordering_check.get(j);
                        u=j;
                    }
                }
                //System.out.println(orders[i-1]+" "+temp+" perfect"+" "+u);
                X.remove(Integer.valueOf(u));
                List<Integer> temp1=A.get(u);
                for(int j:X)
                    temp1.add(j);
                A.put(u,temp1);
            }
            //check if all the elements in A(v) is present in Adj(v)
            boolean check_empty=subtract(A.get(v),graph.get(v),graph.size());
            if(!check_empty)
            {
                return false;
            }

        }



        return true;
    }



    boolean subtract(List<Integer> A, List<Integer> adj,int n){
        int[] Test=new int[n+1];
        for(int w:adj)
            Test[w]=1;
        for(int w:A){
            if(Test[w]!=1)
                return false;
        }
        return true;
    }

    //building a map where vertices are assigned their index value in ordering
    HashMap<Integer, Integer> buildOrder(String lexBFSOrdering) {
        String orders[]=lexBFSOrdering.split(" ");
        HashMap<Integer, Integer> check=new HashMap<>();

        for(int i=1;i<=orders.length;i++){
            check.put(Integer.parseInt(orders[i-1]),i);

        }
        return check;
    }

    //reversing the input ordering
    String reverseOrdering(String lexBFSOrdering) {
        String orders[]=lexBFSOrdering.split(" ");
        StringBuilder sb=new StringBuilder();
        for(int i= orders.length-1;i>=0;i--){
            sb.append(orders[i]);
            sb.append(" ");
        }
        return sb.toString().trim();
    }

    //Function to implement the LexBFS algorithm
    String MCS(HashMap<Integer, List<Integer>> graph){
        StringBuilder sb=new StringBuilder();
        //head is the intial label
        Node head=new Node(String.valueOf(0));
        HashMap<Integer,Node> vertexAddress=new HashMap();
        HashMap<String,Node> nodeAddress=new HashMap();
        appendVertices(head,graph,vertexAddress,nodeAddress);
        //end points to the node with largest label
        Node end=head;
        HashSet<Integer> visited=new HashSet<>();
        for(int j=1;j<= graph.size();j++){

            List<Integer> nodes=end.vertices;
            while(nodes.size()==0&&end!=head){
                end.prev.next=end.next;
                end=end.prev;
                nodes=end.vertices;

            }
            //pick the unnumbered vertex with the largest label
            int curVertex=nodes.get(0);
            nodes.remove(Integer.valueOf(curVertex));


            sb.append(curVertex);
            sb.append(" ");
            visited.add(curVertex);

            List<Integer> adjacent=graph.get(curVertex);
            for(int i:adjacent){
                if(visited.contains(i)) continue;

                Node cur=vertexAddress.get(i);

                String newLabel=String.valueOf(Integer.parseInt(cur.label)+1);
                List<Integer> curChild=cur.vertices;
                //System.out.println(newLabel+" "+i);
                curChild.remove(Integer.valueOf(i));
                cur.vertices=curChild;
                Node newNode;
                //what to do when node with newlabel exists or not.
                if(nodeAddress.containsKey(newLabel)){
                    newNode=nodeAddress.get(newLabel);
                    vertexAddress.put(i,newNode);
                    newNode.vertices.add(i);

                }
                else{
                    newNode=new Node(newLabel);
                    newNode.vertices=new ArrayList();
                    newNode.vertices.add(i);
                    nodeAddress.put(newLabel,newNode);
                    vertexAddress.put(i,newNode);


                    newNode.prev=cur;
                    newNode.next=cur.next;

                    cur.next=newNode;


                    //if(newNode.next==null) end=newNode;
                    //else newNode.next.prev=newNode;


                    if(newNode.next==null) {
                        end=newNode;
                    }
                    else newNode.next.prev=newNode;
                }



            }

        }


        return sb.toString().trim();
    }

    //Maximum clique of the graph
    List<Integer> clique(HashMap<Integer,Integer> A, HashMap<Integer, List<Integer>> graph, String ordering, HashMap<Integer,Integer> ordering_check){
        for(int i:graph.keySet()){
            A.put(i,0);
        }
        int maxClique=1;
        List<Integer> result=new ArrayList<>();
        String orders[]=ordering.split(" ");
        for(int i=1;i<=orders.length;i++){
            //System.out.println("clique");
            int v=Integer.parseInt(orders[i-1]);
            List<Integer> Adj=graph.get(v);
            // System.out.println(i+" "+c+ " " +temp);
            List<Integer> X=new ArrayList<>();
            if(Adj.size()==0){
                System.out.println(v);
                continue;
            }
            for(int j:Adj){
                if(ordering_check.get(j)>i)
                    X.add(j);
            }
            if(X.size()==0)
                break;
            int min=Integer.MAX_VALUE;
            int u=Integer.MAX_VALUE;
            for(int j:X){
                if(ordering_check.get(j)<min)
                {
                    min=ordering_check.get(j);
                    u=j;
                }
            }
            A.put(u,Math.max(A.get(u),X.size()-1));
            if(A.get(v)<X.size()){
                X.add(v);
                //System.out.println(temp);
                if(maxClique<X.size()){
                    maxClique=X.size();
                    result=X;
                }
            }

        }



        return result;
    }


    List<Integer> weightedClique(HashMap<Integer,Integer> A, HashMap<Integer, List<Integer>> graph, String ordering, HashMap<Integer,Integer> ordering_check,HashMap<Integer,Integer> weights){
        for(int i:graph.keySet()){
            A.put(i,0);
        }
        int maxClique=1;
        List<Integer> result=new ArrayList<>();
        String orders[]=ordering.split(" ");
        for(int i=1;i<=orders.length;i++){
            int v=Integer.parseInt(orders[i-1]);
            List<Integer> adj=graph.get(v);
            // System.out.println(i+" "+c+ " " +temp);
            List<Integer> X=new ArrayList<>();
            if(adj.size()==0){
                int weight=weights.get(v);
                if(weight>maxClique) {
                    maxClique=weight;
                    adj.add(v);
                    result=adj;
                }
                continue;
            }
            for(int j:adj){
                if(ordering_check.get(j)>i)
                    X.add(j);
            }
            if(X.size()==0)
                break;
            int min=Integer.MAX_VALUE;
            int u=Integer.MAX_VALUE;
            int curWeight=0;
            int minWeight=0;
            for(int j:X){
                if(ordering_check.get(j)<min)
                {
                    min=ordering_check.get(j);
                    u=j;
                    minWeight=weights.get(j);
                }
                curWeight+=weights.get(j);
            }
            A.put(u,Math.max(A.get(u),curWeight-minWeight));

            if(A.get(v)<curWeight){
                curWeight+=weights.get(v);
                X.add(v);
                if(maxClique<curWeight){
                    maxClique=curWeight;
                    result=X;
                }
            }

        }



        return result;
    }



    List<List<Integer>> stableset( HashMap<Integer, List<Integer>> graph, String ordering, HashMap<Integer,Integer> ordering_check){

        List<List<Integer>> result=new ArrayList<>();
        List<Integer> stable=new ArrayList<>();
        Set<Integer> visited=new HashSet<>();
        String orders[]=ordering.split(" ");
        for(int i=1;i<=orders.length;i++){

            int v=Integer.parseInt(orders[i-1]);
            if(visited.contains(v))
                continue;
            stable.add(v);
            List<Integer> Adj=graph.get(v);

            if(Adj.size()==0){
                System.out.println(v);
                List<Integer> temp1=new ArrayList<>();
                temp1.add(v);
                result.add(temp1);
                continue;
            }

            //int min=Integer.MAX_VALUE;
            //String u="";
            List<Integer> X=new ArrayList<>();
            for(int j:Adj) {
                if(ordering_check.get(j)>i)
                    X.add(j);

            }

            for(int j:X)
                visited.add(j);

            X.add(v);
            //System.out.println(temp);
            result.add(X);


        }

        result.add(stable);

        return result;
    }



}
