package org.launchcode.javawebdevtechjobspersistent.models;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Skill extends AbstractEntity {
    @NotBlank(message = "Description is required")
    @Size(max = 250, message = "Description is too long!")
    private String description;

    @ManyToMany(mappedBy = "skills")
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


    //No arg constructor
    public Skill() {
    }
}