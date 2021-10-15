import java.util.*;


public class ChordalGraph {

    HashMap<Integer, List<Integer>> createGraph(int n){
        HashMap<Integer,List<Integer>> graph=new HashMap<>();
        //Intially build a complete graph with  4 vertices.
        createCompleteGraph(graph,1,4);
        //then build the rest of the graph
        buildGraph(graph,5,n);


        return graph;
    }

    private void buildGraph(HashMap<Integer, List<Integer>> graph, int lo, int hi) {
        //pick the number preceding the current vertex and two adjacent vertices of the preceding vertex as the
        // three vertices form a clique of size 3 now make ot a clique of size 4 by adding the current to the clique.
        for(int i=lo;i<=hi;i++){
            int dest=i;
            int prev=i-1;
            List<Integer> temp=graph.getOrDefault(dest,new ArrayList<>());
            temp.add(prev);
            List<Integer> adj=graph.get(prev);
            adj.add(dest);
            graph.put(prev,adj);
            for(int j=0;j<=1;j++){
                int src=adj.get(j);
                List<Integer> temp1=graph.get(src);
                temp1.add(dest);
                graph.put(src,temp1);
                temp.add(src);
            }
            graph.put(dest,temp);
        }
    }

    //fuction that generates weights for each vertex
    HashMap<Integer,Integer> generateWeights(HashMap<Integer,List<Integer>> graph){
        Random rand=new Random();
        HashMap<Integer,Integer> weights=new HashMap<>();
        for(int i:graph.keySet())
        {
            int weight= rand.nextInt(10000);
            weights.put(i,weight);
        }
        return weights;
    }











    private void createCompleteGraph(HashMap<Integer,List<Integer>> graph,int lo,int hi){
        for(int i=lo;i<=hi;i++){
            for(int j=lo;j<=hi;j++){
                if(i!=j){
                    List<Integer> temp=graph.getOrDefault(i,new ArrayList<>());
                    temp.add(j);
                    graph.put(i,temp);
                }
            }
        }
    }










}
