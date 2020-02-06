package org.launchcode.javawebdevtechjobspersistent.controllers;

import org.launchcode.javawebdevtechjobspersistent.models.*;
import org.launchcode.javawebdevtechjobspersistent.models.data.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

/**
 * Created by LaunchCode
 */
@Controller
public class HomeController {

    @Autowired
    private EmployerRepository employerRepository;

    @Autowired
    private SkillRepository skillRepository;

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private PositionRepository positionRepository;

    @RequestMapping("")
    public String index(Model model) {

        model.addAttribute("title", "My Jobs");
        model.addAttribute("jobs",jobRepository.findAll());
        return "index";
    }
    @GetMapping("jobs")
    public String displayAllJobs(Model model) {
        model.addAttribute("title", "All Jobs");
        model.addAttribute("jobs", jobRepository.findAll());
        return "jobs";
    }

    @GetMapping("add")
    public String displayAddJobForm(Model model) {
        model.addAttribute("title", "Add Job");
        model.addAttribute("employers",employerRepository.findAll());
        model.addAttribute("skills",skillRepository.findAll());
        model.addAttribute("locations",locationRepository.findAll());
        model.addAttribute("positions",positionRepository.findAll());
        model.addAttribute(new Job());
        return "add";
    }

    @PostMapping("add")
    public String processAddJobForm(@ModelAttribute @Valid Job newJob,
                                    Errors errors, Model model, @RequestParam int employerId, @RequestParam List<Integer> skills,
                                    @RequestParam int locationId,@RequestParam int positionId) {

        if (errors.hasErrors()) {
            model.addAttribute("title", "Add Job");
            return "add";
        }

        Optional<Employer> result = employerRepository.findById(employerId);
        if(result.isEmpty()){
            model.addAttribute("title", "Add Job");
            return "add";
        }
        Employer employer=(Employer)result.get();
        newJob.setEmployer(employer);
        model.addAttribute("employer",employer.getName());

        List<Skill> skillObjs = (List<Skill>) skillRepository.findAllById(skills);
        newJob.setSkills(skillObjs);

        Optional<Location> optLocation = locationRepository.findById(locationId);
        if(optLocation.isEmpty()){
            model.addAttribute("title", "Add Location");
            return "add";
        }
        Location location=(Location) optLocation.get();
        newJob.setLocation(location);
        model.addAttribute("location",location.getName());

        Optional<Position> optPosition = positionRepository.findById(positionId);
        if(optPosition.isEmpty()){
            model.addAttribute("title", "Add Position");
            return "add";
        }
        Position position=(Position) optPosition.get();
        newJob.setPosition(position);
        model.addAttribute("position",position.getName());

        jobRepository.save(newJob);
        return "redirect:";
    }

    @GetMapping("view/{jobId}")
    public String displayViewJob(Model model, @PathVariable int jobId) {
        Optional optJob = jobRepository.findById(jobId);
        if (optJob.isPresent()) {
            Job job = (Job) optJob.get();
            model.addAttribute("job", job);
            return "view";
        } else {
            return "redirect:";
        }
    }

    @GetMapping("delete/{jobId}")
    public String displayDeleteJobForm(Model model,@PathVariable int jobId) {
        Optional jobToDelete = jobRepository.findById(jobId);
        if (jobToDelete.isPresent()) {
            Job job = (Job) jobToDelete.get();
            model.addAttribute("job", job);
            return "delete";
        } else {
            return "redirect:";
        }
    }

    @PostMapping("delete")
    public String processDeleteJobForm(@RequestParam(required = false) int[] jobId) {

        if (jobId != null) {
            for (int id : jobId) {

                jobRepository.deleteById(id);
            }
        }

        return "redirect:";
    }

    @GetMapping("update/{jobId}")
    public String displayUpdateJobForm(Model model,@PathVariable int jobId) {
        model.addAttribute("title", "Update Job");
        model.addAttribute("employers",employerRepository.findAll());
        model.addAttribute("skills",skillRepository.findAll());
        //model.addAttribute(new Job());
        Optional jobToUpdate = jobRepository.findById(jobId);
        if (jobToUpdate.isPresent()) {
            Job job = (Job) jobToUpdate.get();
            model.addAttribute("job", job);
            return "update";
        } else {
            return "redirect:";
        }

    }

    @PostMapping("update")
    public String processUpdateJobForm(int jobId, String name,@RequestParam int employerId, @RequestParam List<Integer> skills) {
        Optional<Employer> result = employerRepository.findById(employerId);
        if (result.isEmpty()) {
            return "update";
        }
        Optional jobToUpdate = jobRepository.findById(jobId);
        if (jobToUpdate.isPresent()) {
            Job job = (Job) jobToUpdate.get();
            Employer employer = (Employer) result.get();
            job.setEmployer(employer);
//        model.addAttribute("employer",employer.getName());
            List<Skill> skillObjs = (List<Skill>) skillRepository.findAllById(skills);
            job.setSkills(skillObjs);
            job.setName(name);
            jobRepository.save(job);
            return "redirect:";
        }
        return "redirect:";
    }


}
