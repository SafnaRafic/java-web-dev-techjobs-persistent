package org.launchcode.javawebdevtechjobspersistent.controllers;

import org.launchcode.javawebdevtechjobspersistent.models.Employer;
import org.launchcode.javawebdevtechjobspersistent.models.Skill;
import org.launchcode.javawebdevtechjobspersistent.models.data.SkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("skills")
public class SkillController {
    @Autowired
    private SkillRepository skillRepository;

    @GetMapping("allSkills")
    public String displayAllSkill(Model model) {
        model.addAttribute("title", "All Skills");
        model.addAttribute("skills", skillRepository.findAll());
        return "skills/allSkills";
    }

    @GetMapping("add")
    public String displayAddSkillForm(Model model){
        model.addAttribute(new Skill());
        return "skills/add";
    }

    @PostMapping("add")
    public String processAddSkillForm(@ModelAttribute @Valid Skill newSkill, Errors errors, Model model){
        if(errors.hasErrors()){
            return "skills/add";
        }
        skillRepository.save(newSkill);
        return "skills/view";
    }

    @GetMapping("view/{skillId}")
    public String displayViewSkill(Model model, @PathVariable int skillId){
        Optional optSkill = skillRepository.findById(skillId);
        if (optSkill.isPresent()) {
            Skill skill = (Skill) optSkill.get();
            model.addAttribute("skill", skill);
            return "skills/view";
        } else {
            return "redirect:../skills/allSkills";
        }
    }

    @GetMapping("delete/{skillId}")
    public String displayDeleteSkillForm(Model model,@PathVariable int skillId) {
        Optional skillToDelete = skillRepository.findById(skillId);
        if (skillToDelete.isPresent()) {
            Skill skill = (Skill) skillToDelete.get();
            model.addAttribute("skill", skill);
            return "skills/delete";
        } else {
            return "redirect:";
        }
    }

    @PostMapping("delete")
    public String processDeleteSkillForm(@RequestParam(required = false) int[] skillId) {

        if (skillId != null) {
            for (int id : skillId) {
                skillRepository.deleteById(id);
            }
        }

        return "redirect:../skills/allSkills";
    }

    @GetMapping("update/{skillId}")
    public String displayUpdateSkillForm(Model model,@PathVariable int skillId) {
        Optional skillToUpdate = skillRepository.findById(skillId);
        if (skillToUpdate.isPresent()) {
            Skill skill = (Skill) skillToUpdate.get();
            model.addAttribute("skill", skill);
            return "skills/update";
        } else {
            return "redirect:";
        }

    }

    @PostMapping("update")
    public String processUpdateSkillForm(int skillId, String name, String description) {

        Optional skillToUpdate = skillRepository.findById(skillId);
        if (skillToUpdate.isPresent()) {
            Skill skill = (Skill) skillToUpdate.get();
            skill.setName(name);
            skill.setDescription(description);
            skillRepository.save(skill);
            return "redirect:../skills/allSkills";
        }

        return "redirect:../";
    }
}

