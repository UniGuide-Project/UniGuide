//By Akansh Grover

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;

import java.util.Map;
import java.util.TreeMap;

public class FindPath {
    static class Nodes{
        int vertex;
        double weight;

        Nodes(int v, double w){
            vertex = v;
            weight = w;
        }
        public int getVertex() {
            return vertex;
        }
        public double getWeight() {
            return weight;
        }
    }
    
    public static double[] Dijkstra(int v, int src, ArrayList<ArrayList<Nodes>> graph, int[] prenode){ //vertices, source, graph adjancey list, prenode list
        double[] distance = new double[v];
        for (int i=0; i<v; i++){
            distance[i] = Integer.MAX_VALUE;
        }
        distance[src] = 0;

        for (int i=0; i<v; i++){
            prenode[i] = -1;
        }

        PriorityQueue<Nodes> pq = new PriorityQueue<>((v1, v2) -> Double.compare(v1.getWeight(), v2.getWeight())); //compare v1 and v2 weights, checks which is smaller and then that comes first
        pq.add(new Nodes(src, 0));

        while (pq.size()>0){
            Nodes current = pq.poll();

            for(Nodes n : graph.get(current.getVertex())){
                if (distance[current.getVertex()] + n.getWeight() < distance[n.getVertex()]){ //weight of current vertex + weight of the new vertex < weight of new vertex
                    distance[n.getVertex()] = distance[current.getVertex()] + n.getWeight(); //if true weight of new vertex is replace by new lesser one
                    prenode[n.getVertex()] = current.getVertex();
                    pq.add(new Nodes(n.getVertex(), distance[n.getVertex()])); //in pq we add the new vertex and weight
                }
            }
        }
        return distance;
    }

    public static ArrayList<Integer> createpath(int destination, int[] prenode){
        ArrayList<Integer> path = new ArrayList<>();
        for (int i = destination; i!=-1; i=prenode[i]){
            path.add(i);
        }
        ArrayList<Integer> finalpath = new ArrayList<>();
        for(int i=path.size()-1;i>=0;i--){
            finalpath.add(path.get(i));
        }
        return finalpath;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int v = 55; //vertices no
        ArrayList<ArrayList<Nodes>> graph = GraphInfo.getVerticeInfo(v);

        int[] prenode = new int[v];

        System.out.print("Enter source: ");
        String source = sc.nextLine();
        int[] source_nodes = FinalizeUserIP.returnBlock(source);
        while (source_nodes[0] == -1){
            System.out.print("Source does not exist\n");
            System.out.print("Enter source: ");
            source = sc.nextLine();
            source_nodes = FinalizeUserIP.returnBlock(source);
        }
        int source_node = source_nodes[0];

        System.out.print("Enter destination: ");
        String destination =sc.nextLine();
        int[] destination_nodes = FinalizeUserIP.returnBlock(destination);
        while (destination_nodes[0] == -1){
            System.out.print("Destination does not exist\n");
            System.out.print("Enter Destination: ");
            destination = sc.nextLine();
            destination_nodes = FinalizeUserIP.returnBlock(destination);
        }

        double[] distance = Dijkstra(v, source_node, graph, prenode);

        TreeMap<Double, Integer> sortedDis = new TreeMap<>();
        int nearestVertice = -1;
        double shortestDistance = -1;

        if (destination_nodes.length > 1){
            for (int i=0; i<destination_nodes.length; i++){
                int vertice = destination_nodes[i];
                sortedDis.put(distance[vertice], destination_nodes[i]);
            }
            shortestDistance = sortedDis.firstKey();
            nearestVertice = sortedDis.get(shortestDistance);
        }
        else if (destination_nodes.length == 1){
            nearestVertice = destination_nodes[0];
            shortestDistance = distance[nearestVertice];
        }

        ArrayList<Integer> path = createpath(nearestVertice, prenode);
        System.out.println("\nThe path from " + source + " to " + destination + " is:");
        System.out.print(source + " ");
        for (int i=0; i<path.size(); i++){
            System.out.print("> " + path.get(i) + " ");
        }
        System.out.print("> " + destination);
        System.out.println("\nThe distance from " + source + " and " + destination + " is: " + shortestDistance + "m.");
    }
}
