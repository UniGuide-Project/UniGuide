//By Akansh Grover

package com.Akansh.UniGuide.model;

import java.util.ArrayList;

public class InfoResponse {
    private ArrayList<Integer> path;
    private double totalDistance;
    private ArrayList<Double[]> pathCoords;

    public InfoResponse(ArrayList<Integer> path, double totalDistance, ArrayList<Double[]> pathCoords) {
        this.path = path;
        this.totalDistance = totalDistance;
        this.pathCoords = pathCoords;
    }

    public ArrayList<Integer> getPath() { 
        return path; 
    } 
    public double getTotalDistance() { 
        return totalDistance; 
    }
    public ArrayList<Double[]> getPathCoords() {
        return pathCoords;
    }
}
