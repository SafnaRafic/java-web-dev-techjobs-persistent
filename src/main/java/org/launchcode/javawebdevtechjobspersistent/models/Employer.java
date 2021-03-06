package org.launchcode.javawebdevtechjobspersistent.models;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Employer extends AbstractEntity {

    @NotBlank(message = "Location is required")
    private String location;

    @OneToMany(mappedBy = "employer", cascade = CascadeType.ALL)

   // @JoinColumn("blah")
    private final List<Job> jobs=new ArrayList<>();

    // getters and setters

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<Job> getJobs() {
        return jobs;
    }
    //No arg constructor

    public Employer() {
    }
}
