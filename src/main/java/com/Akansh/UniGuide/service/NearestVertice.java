//By Akansh Grover

package com.Akansh.UniGuide.service;

import java.util.HashMap;
import java.util.TreeMap;

public class NearestVertice {
    static double euclideanDis(double x1, double y1, double x2, double y2){
        return Math.pow(Math.pow(x2-x1,2) + Math.pow(y2-y1,2),0.5);
    }

    public static int get_nearest_vertice(String r){
        double c_latitude = Double.parseDouble(r.split(",")[0]);
        double c_longitude = Double.parseDouble(r.split(",")[1]);
        int v=55;
        HashMap<Integer, Double[]> irl_coords = IRLCoordsInfo.get_irl_coords();
        TreeMap<Double, Integer> distance = new TreeMap<>();
        for (int i=0; i<v; i++){
            double latitude = irl_coords.get(i)[0];
            double longitude = irl_coords.get(i)[1];
            distance.put(euclideanDis(c_latitude, c_longitude, latitude, longitude),i);
        }
        if (distance.firstKey() > 0.004599){//this value came from testing the maximum distance between two vertices on the campus graph
            return -1;
        }
        else{
            int nearest = distance.get(distance.firstKey());
            return nearest;
        }
    }
}
