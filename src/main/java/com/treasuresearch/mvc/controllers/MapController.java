package com.treasuresearch.mvc.controllers;

import com.treasuresearch.labirint.Maze;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Controller
@RequestMapping("/map")
public class MapController {
    @Autowired
    private Maze maze;

    @Autowired
    private MessageSource messageSource;

    @RequestMapping({"/","/show"})
    public String showMap(Map<String,Object> model){
        if(maze.isLoadedField()){
            model.put("maze",maze);
            return "show";
        }
        else {
            return "initmaze";
        }
    }
    @RequestMapping("/loadfile")
    public String loadFromFile(){
        return "loadmaze";
    }
    @RequestMapping(value = "/loadfile/process",method = RequestMethod.POST)
    public String resultOfLoading(Map<String,Object> model, @RequestParam("file") MultipartFile file){
        try {
            maze.loadField(file.getInputStream());
            model.put("result","success");
        } catch (IOException e) {
            e.printStackTrace();
            model.put("result","error");
            model.put("error",e.getMessage());
        }
        return "showresult";
    }
}
