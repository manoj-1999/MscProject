import java.util.*;

public class Solution {
    public static void main(String[] ar) {

        Scanner sc = new Scanner(System.in);
        // Take in the number of vertices for a graph. The number must be between 3 and 10001
        int n = sc.nextInt();
        //HashMap<Integer, List<Integer>> graph=new HashMap<>();
        /* The below commented code is used to take inputs of edges line wise in the first line the number of edges e should be given
        and the next consecutive e lines should have two vertices and there should be no loop or repetiton of unordered pair of vertices.
        int edges=sc.nextInt();
        sc.nextLine();
        for(int i=0;i<edges;i++){
            String s=sc.nextLine();
            String s1[]=s.split(" ");
            int src=Integer.parseInt(s1[0]);
            int dest=Integer.parseInt(s1[1]);
            List<Integer> temp=graph.getOrDefault(src,new ArrayList<>());
            temp.add(dest);
            graph.put(src,temp);
            List<Integer> temp1=graph.getOrDefault(dest,new ArrayList<>());
            temp1.add(src);
            graph.put(dest,temp1);
            //  System.out.println(graph);
        }*/

        ChordalGraph ch = new ChordalGraph();
        //Function called to create a graph
        HashMap<Integer, List<Integer>> graph = ch.createGraph(n);
        //Assign weights to each vertex.
        HashMap<Integer, Integer> weights = ch.generateWeights(graph);

        OrderingAndProblems od = new OrderingAndProblems();
        long LexStart = System.currentTimeMillis();
        //Pass the graph as input to function LexBFS to get an ordering as output.
        String LexBFSOrdering = od.LexBFS(graph);
        long LexEnd = System.currentTimeMillis();
        //As the obtained order is in the form n to 1 reverse the order by passing it to function reverseOrdering
        LexBFSOrdering = od.reverseOrdering(LexBFSOrdering);
        long MCSStart = System.currentTimeMillis();
        //Pass the graph as input to function MCS to get an ordering as output.
        String MCSOrdering = od.MCS(graph);
        long MCSEnd = System.currentTimeMillis();
        //As the obtained order should be reversed in MCS algorithm at the end, pass the ordering to function reverseOrdering
        MCSOrdering = od.reverseOrdering(MCSOrdering);
        System.out.println(LexBFSOrdering + " time LEX is " + (LexEnd - LexStart));
        //Build a map to store the vertices index in ordering for easy retrieval
        HashMap<Integer, Integer> ordering_checkLexBFS = od.buildOrder(LexBFSOrdering);
        HashMap<Integer, Integer> ordering_checkMCS = od.buildOrder(MCSOrdering);
        //System.out.println(LexBFSOrdering);
        System.out.println(MCSOrdering + " time MCS is " + (MCSEnd - MCSStart));

        long perfectStart = System.currentTimeMillis();
        //To check if the obtained ordering is perfect or not the graph and ordering are passed to function perfect
        boolean flag = od.perfect(new HashMap<>(), graph, LexBFSOrdering, ordering_checkLexBFS);
        long perfectEnd = System.currentTimeMillis();
        System.out.println("perfect time is " + (perfectEnd - perfectStart) + " " + flag);
        //boolean check=od.perfect(new HashMap<>(),graph,MCSOrdering,ordering_checkMCS);


        if (flag) {
            long cliqueStart = System.currentTimeMillis();
            //function call to find the largest clique of the graph
            List<Integer> clique = od.clique(new HashMap<>(), graph, LexBFSOrdering, ordering_checkLexBFS);
            long cliqueEnd = System.currentTimeMillis();
            System.out.println("Largest clique of the graph is " + clique + " " + (cliqueEnd - cliqueStart));
            //System.out.println("Minimum number of colors required to colour the graph is "+clique.size());
            long weightedStart = System.currentTimeMillis();
            //function call to find the weighted clique of the graph
            List<Integer> weightedclique = od.weightedClique(new HashMap<>(), graph, LexBFSOrdering, ordering_checkLexBFS, weights);
            long weightedEnd = System.currentTimeMillis();
            System.out.println("Largest weighted clique of the graph is " + weightedclique + " " + (weightedEnd - weightedStart));
            long stableStart = System.currentTimeMillis();
            //function call to find the maximum independent set of the graph
            List<List<Integer>> stableset = od.stableset(graph, LexBFSOrdering, ordering_checkLexBFS);
            long stableEnd = System.currentTimeMillis();
            System.out.println("Maximum Independet Set is " + stableset.get(stableset.size() - 1) + " " + (stableEnd - stableStart));
            //System.out.println("Minimum number of cliques to cover the entire graph : ");
            for (int i = 0; i < stableset.size() - 1; i++)
                System.out.print(stableset.get(i) + " ");
        }
        else {
            System.out.print("The input graph is not a chordal graph");
        }
    }
}


