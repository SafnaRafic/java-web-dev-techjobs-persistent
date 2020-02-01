package org.launchcode.javawebdevtechjobspersistent.controllers;

import org.launchcode.javawebdevtechjobspersistent.models.Employer;
import org.launchcode.javawebdevtechjobspersistent.models.Location;
import org.launchcode.javawebdevtechjobspersistent.models.data.EmployerRepository;
import org.launchcode.javawebdevtechjobspersistent.models.data.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("locations")
public class LocationController {
    @Autowired
    private LocationRepository locationRepository;

    @GetMapping("allLocations")
    public String displayAllLocations(Model model) {
        model.addAttribute("title","Locations");
        model.addAttribute("locations",locationRepository.findAll());
        return "locations/allLocations";
    }

    @GetMapping("add")
    public String displayAddLocationForm(Model model) {
        model.addAttribute("title","Add Location");
        model.addAttribute(new Location());
        return "locations/add";
    }

    @PostMapping("add")
    public String processAddLocationForm(@ModelAttribute @Valid Location newLocation, Errors errors,Model model) {
        if(errors.hasErrors()) {
            return "locations/add";
        }
        locationRepository.save(newLocation);
        return "locations/view";
    }

    @GetMapping("view/{locationId}")
    public String displayViewLocationForm(Model model,@PathVariable int locationId){
        Optional optLocation=locationRepository.findById(locationId);
        if(optLocation.isEmpty()){
            return "locations/add";
        }
        Location location=(Location) optLocation.get();
        model.addAttribute("location",location);
        return "locations/view";
    }

    @GetMapping("delete/{locationId}")
    public String displayDeleteLocationForm(Model model,@PathVariable int locationId){
        Optional locationToDelete=locationRepository.findById(locationId);
        if(locationToDelete.isEmpty()){
            return "redirect:";
        }
        Location location=(Location)locationToDelete.get();
        model.addAttribute("location",location);
        return "locations/delete";
    }

    @PostMapping("delete")
    public String processDeleteLocationForm(@RequestParam(required=false) int[] locationId){
        if(locationId != null){
            for(int id:locationId) {
                locationRepository.deleteById(id);
            }
        }
        return "redirect:../";
    }

    @GetMapping("update/{locationId}")
    public String displayUpdateLocationForm(Model model,@PathVariable int locationId){
        model.addAttribute("title","Update");
        Optional locationToUpdate=locationRepository.findById(locationId);
        if(locationToUpdate.isPresent()){
            Location location=(Location) locationToUpdate.get();
            model.addAttribute("location",location);
            return "locations/update";
        }
        return "redirect:";

    }

    @PostMapping("update")
    public String processUpdateLocationForm(int locationId,String name){
        Optional locationToUpdate=locationRepository.findById(locationId);
        if(locationToUpdate.isPresent()){
            Location location=(Location) locationToUpdate.get();
            location.setName(name);
            locationRepository.save(location);
            return "redirect:../";
        }

        return "redirect:../";
    }

}
