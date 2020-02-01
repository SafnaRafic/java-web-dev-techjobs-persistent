package org.launchcode.javawebdevtechjobspersistent.models;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Location extends AbstractEntity {

    @OneToMany(mappedBy = "employer", cascade = CascadeType.ALL)
    private final List<Job> jobs=new ArrayList<>();


    public Location() {
    }

    public List<Job> getJobs() {
        return jobs;
    }
}
