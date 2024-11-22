//By Akansh Grover

package com.Akansh.UniGuide.service;

import java.util.HashMap;
import java.util.TreeMap;

import com.Akansh.UniGuide.model.LocResponse;

public class NearestVertice {
    static double euclideanDis(double x1, double y1, double x2, double y2){
        return Math.pow(Math.pow(x2-x1,2) + Math.pow(y2-y1,2),0.5);
    }

    static boolean binarySearch(int a[], int l, int r, int x){
        while (l <= r) {
            int m = (l + r) / 2;
            if (a[m] == x) {
                return true;
            } else if (a[m] > x) {
                r = m - 1;
            } else {
              l = m + 1;
            }  
        }
        return false;
    }

    static String capitalize(String ip){
        String[] l = ip.split(" ");
        String r;
        for (int i=0; i<l.length; i++){
            String a = l[i];
            l[i] = a.substring(0,1).toUpperCase() + a.substring(1);
        }
        r = String.join(" ", l);
        return r;
    }

    static String checkPlace(int n){
        HashMap<String, int[]> coord_nodes = NodeInfo.getNodeInfo();
        String place = "-1";
        for (String i : coord_nodes.keySet()){
            place = i;
            int[] vs = coord_nodes.get(i);
            if (binarySearch(vs, 0, vs.length-1, n)){
                return capitalize(place);
            }
        }
        return place;
    }

    public static LocResponse get_nearest_place(double c_latitude, double c_longitude){
        int v=65;
        HashMap<Integer, Double[]> irl_coords = IRLCoordsInfo.get_irl_coords();
        TreeMap<Double, Integer> distance = new TreeMap<>();
        for (int i=0; i<v; i++){
            double latitude = irl_coords.get(i)[0];
            double longitude = irl_coords.get(i)[1];
            distance.put(euclideanDis(c_latitude, c_longitude, latitude, longitude),i);
        }
        if (distance.firstKey() > 0.004599){//this value came from testing the maximum distance between two vertices on the campus graph
            return new LocResponse("-1");
        }
        else{
            int nearest = distance.get(distance.firstKey());
            return new LocResponse(checkPlace(nearest));
        }
    }
}
