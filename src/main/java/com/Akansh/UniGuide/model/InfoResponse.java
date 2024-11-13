//By Akansh Grover

package com.Akansh.UniGuide.model;

import java.util.ArrayList;

public class InfoResponse {
    private ArrayList<Integer> path;
    private double totalDistance;

    public InfoResponse(ArrayList<Integer> path, double totalDistance) {
        this.path = path;
        this.totalDistance = totalDistance;
    }

    public ArrayList<Integer> getPath() { 
        return path; 
    } 
    public double getTotalDistance() { 
        return totalDistance; 
    }
}
