//By Akansh Grover

package com.Akansh.UniGuide.controller;

import com.Akansh.UniGuide.model.InfoResponse;
import com.Akansh.UniGuide.model.InfoRequest;
import com.Akansh.UniGuide.service.FindPath;
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
}
