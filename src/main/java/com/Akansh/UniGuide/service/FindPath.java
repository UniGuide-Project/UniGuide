//By Akansh Grover

package com.Akansh.UniGuide.service;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;

import java.util.Map;
import java.util.TreeMap;

import java.util.HashMap;

import org.springframework.stereotype.Service;
import com.Akansh.UniGuide.model.InfoResponse;

@Service
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
            if (path.get(i)<57){//dont like this, will change in future
                finalpath.add(path.get(i));
            }
        }
        return finalpath;
    }

    public static ArrayList<Double[]> getIRLvertices(ArrayList<Integer> vertices){
        HashMap<Integer, Double[]> irl_coords = IRLCoordsInfo.get_irl_coords();
        ArrayList<Double[]> IRLvertices = new ArrayList<>();
        for (int i=0; i<vertices.size(); i++){
            IRLvertices.add(irl_coords.get(vertices.get(i)));
        }
        return IRLvertices;
    }

    public static InfoResponse getPathandDis(String source, String destination) {
        int v = 65; //vertices no
        ArrayList<ArrayList<Nodes>> graph = GraphInfo.getVerticeInfo(v);

        int[] prenode = new int[v];
        int[] source_nodes = FinalizeUserIP.returnBlock(source.toLowerCase());
        
        int source_node = source_nodes[0];

        int[] destination_nodes = FinalizeUserIP.returnBlock(destination.toLowerCase());

        if (source_nodes[0] != -1 && destination_nodes[0] != -1){
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
            ArrayList<Double[]> pathCoords = getIRLvertices(path);
            return new InfoResponse(path, shortestDistance, pathCoords);
        }
        else if (destination_nodes[0]==-1){
            ArrayList<Integer> emptyPath = new ArrayList<>();
            emptyPath.add(0);
            emptyPath.add(-1);
            ArrayList<Double[]> emptyCoords = new ArrayList<>();
            emptyCoords.add(new Double[] {-1.0});
            return new InfoResponse(emptyPath, -1, emptyCoords);
        }
        else if (source_nodes[0]==-1){
            ArrayList<Integer> emptyPath = new ArrayList<>();
            emptyPath.add(-1);
            emptyPath.add(0);
            ArrayList<Double[]> emptyCoords = new ArrayList<>();
            emptyCoords.add(new Double[] {-1.0});
            return new InfoResponse(emptyPath, -1, emptyCoords);
        }
        else {
            ArrayList<Integer> emptyPath = new ArrayList<>();
            emptyPath.add(-2);
            emptyPath.add(-2);
            ArrayList<Double[]> emptyCoords = new ArrayList<>();
            emptyCoords.add(new Double[] {-1.0});
            return new InfoResponse(emptyPath, -1, emptyCoords);
        }
    }
}
