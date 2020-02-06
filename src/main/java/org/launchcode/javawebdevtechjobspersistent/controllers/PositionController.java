package org.launchcode.javawebdevtechjobspersistent.controllers;

import org.launchcode.javawebdevtechjobspersistent.models.Position;
import org.launchcode.javawebdevtechjobspersistent.models.data.JobRepository;
import org.launchcode.javawebdevtechjobspersistent.models.data.PositionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("positions")
public class PositionController {
    @Autowired
    private PositionRepository positionRepository;

    @Autowired
    private JobRepository jobRepository;

    @GetMapping("allPositions")
    public String displayAllPositionsForm(Model model){
        model.addAttribute("title","ALl Positions");
        model.addAttribute("positions",positionRepository.findAll());
        return "positions/allPositions";
    }

    @GetMapping("add")
    public String displayAddPositionForm(Model model){
        model.addAttribute("title","Add Positions");
        model.addAttribute(new Position());
        return "positions/add";
    }

    @PostMapping("add")
    public String processAddPositionForm(@ModelAttribute @Valid Position newPosition, Errors errors,
                                         Model model){
        if(errors.hasErrors()){
            return "positions/add";
        }
        positionRepository.save(newPosition);
        return "redirect:../positions/allPositions";
    }

    @GetMapping("view/{positionId}")
    public String displayViewPositionForm(Model model, @PathVariable int positionId){
        Optional optPosition=positionRepository.findById(positionId);
        if(optPosition.isPresent()){
            Position position=(Position) optPosition.get();
            model.addAttribute("position",position);
            return "positions/view";
        }
        return "redirect:../";
    }

    @GetMapping("delete/{positionId}")
    public String displayDeletePositionForm(@PathVariable int positionId,Model model){
        Optional positionToDelete=positionRepository.findById(positionId);
        if(positionToDelete.isPresent()) {
            Position position = (Position) positionToDelete.get();
            model.addAttribute("position",position);
            return "positions/delete";
        }
        return "redirect:../";
    }

    @PostMapping("delete")
    public String processDeletePositionForm(@RequestParam(required=false) int[] positionId){
       if(positionId != null) {
           for (int id : positionId) {
                positionRepository.deleteById(id);
           }
       }
        return "redirect:../positions/allPositions";
    }

    @GetMapping("update/{positionId}")
    public String displayUpdatePositionForm(@PathVariable int positionId,Model model){
        Optional positionToUpdate=positionRepository.findById(positionId);
        if(positionToUpdate.isPresent()) {
            Position position = (Position) positionToUpdate.get();
            model.addAttribute("position", position);
            return "positions/update";
        }
        return "redirect:../";

    }

    @PostMapping("update")
    public String processUpdatePositionForm(int positionId,String name){
        Optional positionToUpdate=positionRepository.findById(positionId);
        if(positionToUpdate.isPresent()) {
            Position position = (Position) positionToUpdate.get();
            position.setName(name);
            positionRepository.save(position);
            return "redirect:../positions/allPositions";
        }
        return "redirect:../";
    }



}
