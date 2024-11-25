package softuni.exam.models.dto.imports.xml;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "jobs")
public class JobSeedRootDTO {

    private List<JobSeedDTO> jobs;

    @XmlElement(name = "job")
    public List<JobSeedDTO> getJobs() {
        return jobs;
    }

    public void setJobs(List<JobSeedDTO> jobs) {
        this.jobs = jobs;
    }
}
