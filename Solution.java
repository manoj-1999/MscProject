import java.util.*;

public class Solution {
    public static void main(String[] ar) {

        Scanner sc = new Scanner(System.in);
        // Take in the number of vertices for a graph. The number must be between 3 and 10001
        System.out.println("Enter the number of vertices between 3 and 10001 ");
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
        System.out.println("The Lexicographical Ordering is "+LexBFSOrdering + ". The time taken to build the ordering is " + (LexEnd - LexStart)+" ms");
        //Build a map to store the vertices index in ordering for easy retrieval
        HashMap<Integer, Integer> ordering_checkLexBFS = od.buildOrder(LexBFSOrdering);
        HashMap<Integer, Integer> ordering_checkMCS = od.buildOrder(MCSOrdering);
        //System.out.println(LexBFSOrdering);
        System.out.println("The MCS Ordering is "+MCSOrdering + ". The time taken to build the ordering is " + (MCSEnd - MCSStart)+" ms");

        long perfectStart = System.currentTimeMillis();
        //To check if the obtained ordering is perfect or not the graph and ordering are passed to function perfect
        boolean flag = od.perfect(new HashMap<>(), graph, LexBFSOrdering, ordering_checkLexBFS);
        long perfectEnd = System.currentTimeMillis();
        System.out.println("The time taken to check if the ordering is perfect or not is " + (perfectEnd - perfectStart) + " ms" );
        //boolean check=od.perfect(new HashMap<>(),graph,MCSOrdering,ordering_checkMCS);


        if (flag) {
            System.out.println("The ordering produced is perfect vertex elemination scheme");
            //k for clique  decision version
            System.out.println("Enter the k for clique decision problem");
            int k=sc.nextInt();
            long cliqueStart = System.currentTimeMillis();
            //function call to find the largest clique of the graph
            List<Integer> clique = od.clique(new HashMap<>(), graph, LexBFSOrdering, ordering_checkLexBFS);
            long cliqueEnd = System.currentTimeMillis();
            int S=clique.size();
            System.out.println((S>=k)?"True, for maximum clique decision problem":"False, for maximum clique decision problem");
            System.out.println("Largest clique of the graph is " + clique + ". The time taken to calculate the largest clique is " + (cliqueEnd - cliqueStart)+" ms");




            //k for colourability decision problem
            System.out.println("Enter the k for colourability decision problem");
            k=sc.nextInt();
            System.out.println((k>=S)?"True, for colourability decision problem":"False, for colourability decision problem");
            System.out.println("Minimum number of colors required to colour the graph is "+clique.size());


            long weightedStart = System.currentTimeMillis();
            //function call to find the weighted clique of the graph
            List<Integer> result = od.weightedClique(new HashMap<>(), graph, LexBFSOrdering, ordering_checkLexBFS, weights);
            long weightedEnd = System.currentTimeMillis();

            List<Integer> weightedclique=new ArrayList<>();
            for(int i=0;i<result.size()-1;i++)
            weightedclique.add(result.get(i));





            // k for weighted clique decision version
            S=result.get(result.size()-1);
            System.out.println("Enter the k for weighted clique decision problem");
            k=sc.nextInt();
            System.out.println((S>=k)?"True, for weighted clique decision problem ":"False, for weighted clique decision problem ");
            System.out.println("Largest weighted clique of the graph is " + weightedclique + ".The time taken to calculate the largest weighted clique is " + (weightedEnd - weightedStart));



            long stableStart = System.currentTimeMillis();
            //function call to find the maximum independent set of the graph
            List<List<Integer>> stableset = od.stableset(graph, LexBFSOrdering, ordering_checkLexBFS);
            long stableEnd = System.currentTimeMillis();


            // k for independent set
            S=stableset.get(stableset.size() - 1).size();
            System.out.println("Enter the k for independent set decision problem");
            k=sc.nextInt();
            System.out.println((S>=k)?"True, for independent set independent set decision problem ":"False, for independent set independent set decision problem");
            System.out.println("Maximum Independet Set is " + stableset.get(stableset.size() - 1) + ". The time taken to calculate the maximum independent set is " + (stableEnd - stableStart));

           // k for clique cover problem
            System.out.println("Enter the k for clique cover decision problem");
            k=sc.nextInt();
            System.out.println((S==k)?"True, for clique cover decision problem":"False, for clique cover decision problem");

            System.out.println("Minimum number of cliques to cover the entire graph are : ");
            for (int i = 0; i < stableset.size() - 1; i++)
                System.out.print(stableset.get(i) + " ");


        }
        else {
            System.out.print("The input graph is not a chordal graph");
        }
    }
}


