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
        int v = 49; //vertices no
        ArrayList<ArrayList<Nodes>> graph = new ArrayList<>();
        for (int i=0; i<v; i++){
            graph.add(new ArrayList<>());
        }

        graph.get(0).add(new Nodes(1, 21.4));
        graph.get(1).add(new Nodes(2, 41.73));
        graph.get(1).add(new Nodes(3, 82.94));
        graph.get(2).add(new Nodes(1, 41.73));
        graph.get(2).add(new Nodes(4, 23.51));
        graph.get(2).add(new Nodes(6, 58.59));
        graph.get(3).add(new Nodes(1, 82.94));
        graph.get(3).add(new Nodes(5, 49.90));
        graph.get(4).add(new Nodes(2, 23.51));
        graph.get(4).add(new Nodes(5, 59.51));
        graph.get(4).add(new Nodes(7, 68.17));
        graph.get(5).add(new Nodes(3, 49.90));
        graph.get(5).add(new Nodes(4, 59.51));
        graph.get(5).add(new Nodes(8, 35.22));
        graph.get(5).add(new Nodes(9, 31.45));
        graph.get(6).add(new Nodes(2, 58.59));
        graph.get(6).add(new Nodes(15, 39.94));
        graph.get(7).add(new Nodes(4, 68.17));
        graph.get(7).add(new Nodes(48, 60.65));
        graph.get(8).add(new Nodes(5, 35.22));
        graph.get(8).add(new Nodes(12, 31.90));
        graph.get(8).add(new Nodes(48, 30.19));
        graph.get(9).add(new Nodes(5, 31.45));
        graph.get(9).add(new Nodes(10, 26.89));
        graph.get(9).add(new Nodes(12, 35.22));
        graph.get(10).add(new Nodes(9, 26.89));
        graph.get(10).add(new Nodes(11, 35.56));
        graph.get(11).add(new Nodes(10, 35.56));
        graph.get(11).add(new Nodes(12, 32.18));
        graph.get(11).add(new Nodes(14, 45.34));
        graph.get(12).add(new Nodes(8, 31.90));
        graph.get(12).add(new Nodes(9, 35.22));
        graph.get(12).add(new Nodes(11, 32.18));
        graph.get(12).add(new Nodes(13, 44.12));
        graph.get(13).add(new Nodes(12, 44.12));
        graph.get(13).add(new Nodes(14, 46.18));
        graph.get(13).add(new Nodes(19, 12.3));
        graph.get(13).add(new Nodes(47, 66.72));
        graph.get(13).add(new Nodes(48, 17.20));
        graph.get(14).add(new Nodes(11, 45.34));
        graph.get(14).add(new Nodes(13, 46.18));
        graph.get(14).add(new Nodes(19, 49.34));
        graph.get(14).add(new Nodes(21, 36.76));
        graph.get(14).add(new Nodes(29, 32.39));
        graph.get(14).add(new Nodes(38, 98.66));
        graph.get(15).add(new Nodes(6, 39.94));
        graph.get(15).add(new Nodes(16, 12.23));
        graph.get(15).add(new Nodes(47, 49.11));
        graph.get(16).add(new Nodes(15, 12.23));
        graph.get(16).add(new Nodes(17, 18.20));
        graph.get(17).add(new Nodes(16, 18.20));
        graph.get(17).add(new Nodes(46, 33.31));
        graph.get(17).add(new Nodes(25, 36.40));
        graph.get(18).add(new Nodes(46, 23.25));
        graph.get(18).add(new Nodes(19, 39.43));
        graph.get(18).add(new Nodes(24, 36.76));
        graph.get(19).add(new Nodes(13, 12.3));
        graph.get(19).add(new Nodes(14, 49.34));
        graph.get(19).add(new Nodes(18, 39.43));
        graph.get(19).add(new Nodes(20, 36.76));
        graph.get(20).add(new Nodes(19, 36.76));
        graph.get(20).add(new Nodes(21, 49.34));
        graph.get(20).add(new Nodes(24, 39.43));
        graph.get(21).add(new Nodes(14, 36.76));
        graph.get(21).add(new Nodes(20, 49.34));
        graph.get(21).add(new Nodes(22, 28.49));
        graph.get(22).add(new Nodes(21, 28.49));
        graph.get(22).add(new Nodes(23, 88.77));
        graph.get(22).add(new Nodes(41, 70.34));
        graph.get(23).add(new Nodes(22, 88.77));
        graph.get(23).add(new Nodes(24, 28.49));
        graph.get(23).add(new Nodes(27, 80.25));
        graph.get(24).add(new Nodes(18, 36.76));
        graph.get(24).add(new Nodes(20, 39.43));
        graph.get(24).add(new Nodes(23, 28.49));
        graph.get(25).add(new Nodes(17, 36.40));
        graph.get(25).add(new Nodes(26, 22.62));
        graph.get(26).add(new Nodes(25, 22.62));
        graph.get(26).add(new Nodes(27, 42.88));
        graph.get(27).add(new Nodes(23, 80.25));
        graph.get(27).add(new Nodes(26, 42.88));
        graph.get(27).add(new Nodes(28, 18.76));
        graph.get(28).add(new Nodes(27, 18.76));
        graph.get(28).add(new Nodes(42, 78.76));
        graph.get(29).add(new Nodes(14, 32.39));
        graph.get(29).add(new Nodes(30, 13.48));
        graph.get(29).add(new Nodes(31, 43.58));
        graph.get(30).add(new Nodes(29, 13.48));
        graph.get(31).add(new Nodes(29, 43.58));
        graph.get(31).add(new Nodes(32, 14.88));
        graph.get(31).add(new Nodes(33, 18.86));
        graph.get(32).add(new Nodes(31, 14.88));
        graph.get(33).add(new Nodes(31, 18.86));
        graph.get(33).add(new Nodes(34, 25.53));
        graph.get(33).add(new Nodes(37, 14));
        graph.get(34).add(new Nodes(33, 25.53));
        graph.get(34).add(new Nodes(35, 22.44));
        graph.get(34).add(new Nodes(39, 29.65));
        graph.get(35).add(new Nodes(34, 22.44));
        graph.get(35).add(new Nodes(36, 29.41));
        graph.get(36).add(new Nodes(35, 29.41));
        graph.get(37).add(new Nodes(33, 14));
        graph.get(37).add(new Nodes(38, 8.02));
        graph.get(38).add(new Nodes(14, 98.66));
        graph.get(38).add(new Nodes(37, 8.02));
        graph.get(38).add(new Nodes(39, 25.53));
        graph.get(39).add(new Nodes(34, 29.65));
        graph.get(39).add(new Nodes(38, 25.53));
        graph.get(39).add(new Nodes(40, 15.69));
        graph.get(40).add(new Nodes(39, 15.69));
        graph.get(41).add(new Nodes(22, 70.34));
        graph.get(41).add(new Nodes(42, 101.97));
        graph.get(41).add(new Nodes(43, 32.43));
        graph.get(42).add(new Nodes(41, 101.97));
        graph.get(42).add(new Nodes(28, 78.76));
        graph.get(43).add(new Nodes(41, 32.43));
        graph.get(43).add(new Nodes(44, 54.45));
        graph.get(43).add(new Nodes(45, 28.57));
        graph.get(44).add(new Nodes(43, 54.45));
        graph.get(45).add(new Nodes(43, 28.57));
        graph.get(46).add(new Nodes(17, 33.31));
        graph.get(46).add(new Nodes(18, 23.25));
        graph.get(46).add(new Nodes(47, 12.3));
        graph.get(47).add(new Nodes(13, 66.72));
        graph.get(47).add(new Nodes(15, 49.11));
        graph.get(47).add(new Nodes(46, 12.3));
        graph.get(48).add(new Nodes(7, 60.65));
        graph.get(48).add(new Nodes(8, 30.19));
        graph.get(48).add(new Nodes(13, 17.20));

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
        System.out.println("\nThe path from " + source + " and " + destination + " is:");
        for (int i=0; i<path.size(); i++){
            System.out.print("> " + path.get(i) + " ");
        }
        System.out.println("\nThe distance from " + source + " and " + destination + " is: " + shortestDistance + "m.");
    }
}
