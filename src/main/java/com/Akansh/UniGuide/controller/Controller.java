//By Akansh Grover

package com.Akansh.UniGuide.controller;

import com.Akansh.UniGuide.model.InfoResponse;
import com.Akansh.UniGuide.model.LocRequest;
import com.Akansh.UniGuide.model.LocResponse;
import com.Akansh.UniGuide.model.InfoRequest;
import com.Akansh.UniGuide.service.FindPath;
import com.Akansh.UniGuide.service.NearestVertice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class Controller {
    private final FindPath findPath;
    @Autowired
    public Controller(FindPath findPath) {
        this.findPath = findPath;
    }
    @PostMapping("/getPath")
    public InfoResponse getPath(@RequestBody InfoRequest request) {
        return FindPath.getPathandDis(request.getSource(), request.getDestination());
    }
    @PostMapping("/getLoc")
    public LocResponse getLoc(@RequestBody LocRequest request) {
        return NearestVertice.get_nearest_place(request.getLatitude(), request.getLongitude());
    }
}
