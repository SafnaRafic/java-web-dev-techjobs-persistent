package org.launchcode.javawebdevtechjobspersistent.controllers;

import org.launchcode.javawebdevtechjobspersistent.models.Employer;
import org.launchcode.javawebdevtechjobspersistent.models.data.EmployerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Controller
@RequestMapping("employers")
public class EmployerController {
    @Autowired
    private EmployerRepository employerRepository;

    @GetMapping("allEmployers")
    public String displayAllEmployer(Model model) {
        model.addAttribute("title", "All Employer");
        model.addAttribute("employers", employerRepository.findAll());
        return "employers/allEmployers";
    }

    @GetMapping("add")
    public String displayAddEmployerForm(Model model) {
        model.addAttribute(new Employer());
        return "employers/add";
    }

    @PostMapping("add")
    public String processAddEmployerForm(@ModelAttribute @Valid Employer newEmployer,
                                    Errors errors, Model model) {

        if (errors.hasErrors()) {
            return "employers/add";
        }
        employerRepository.save(newEmployer);
        return "employers/view";
    }

    @GetMapping("view/{employerId}")
    public String displayViewEmployer(Model model, @PathVariable int employerId) {

        Optional optEmployer = employerRepository.findById(employerId);
        if (optEmployer.isPresent()) {
            Employer employer = (Employer) optEmployer.get();
            model.addAttribute("employer", employer);
            return "employers/view";
        } else {
            return "employers/allEmployers";
        }
    }
    @GetMapping("delete")
    public String displayDeleteEmployerForm(Model model) {
        model.addAttribute("title", "Delete Employer");
        model.addAttribute("employers", employerRepository.findAll());
        return "employers/delete";
    }

    @PostMapping("delete")
    public String processDeleteEmployerForm(@RequestParam(required = false) int[] employerId) {

        if (employerId != null) {
            for (int id : employerId) {
                //employerRepository.delete(employerRepository.get(Employer.class,employerId));
                employerRepository.deleteById(id);
            }
        }

        return "redirect:/";
    }

    @GetMapping("update/{employerId}")
    public String displayUpdateEmployerForm(Model model,@PathVariable int employerId) {
        Optional employerToUpdate = employerRepository.findById(employerId);
         if (employerToUpdate.isPresent()) {
            Employer employer = (Employer) employerToUpdate.get();
            model.addAttribute("employer", employer);
            return "employers/update";
        } else {
            return "redirect:";
        }

    }

    @PostMapping("update")
    public String processUpdateEmployerForm(int employerId, String name, String location) {

        Optional employerToUpdate = employerRepository.findById(employerId);
        if (employerToUpdate.isPresent()) {
            Employer employer = (Employer) employerToUpdate.get();
            employer.setName(name);
            employer.setLocation(location);
            employerRepository.save(employer);
        return "redirect:";
        }

        return "redirect:../";
    }
}
