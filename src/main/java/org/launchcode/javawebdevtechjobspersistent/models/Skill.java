package org.launchcode.javawebdevtechjobspersistent.models;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Skill extends AbstractEntity {
    @Size(max = 250, message = "Description is too long!")
    private String description;
    @OneToMany
    @JoinColumn
    private final List<Job> jobs=new ArrayList<>();


    //getter and setter

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Job> getJobs() {
        return jobs;
    }
//     public void addJob(){
//        jobs.add();
//     }

    //No arg constructor
    public Skill() {
    }
}