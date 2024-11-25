package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.imports.xml.JobSeedRootDTO;
import softuni.exam.models.entity.Company;
import softuni.exam.models.entity.Job;
import softuni.exam.repository.CompanyRepository;
import softuni.exam.repository.JobRepository;
import softuni.exam.service.JobService;
import softuni.exam.util.ValidationUtil;
import softuni.exam.util.xmlParser.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class JobServiceImpl implements JobService {
    private static final String FILE_PATH = "src/main/resources/files/xml/jobs.xml";

    private final JobRepository jobRepository;
    private final CompanyRepository companyRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final XmlParser xmlParser;

    public JobServiceImpl(JobRepository jobRepository, CompanyRepository companyRepository, ModelMapper modelMapper, ValidationUtil validationUtil, XmlParser xmlParser) {
        this.jobRepository = jobRepository;
        this.companyRepository = companyRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.xmlParser = xmlParser;
    }

    @Override
    public boolean areImported() {
        return this.jobRepository.count() > 0;
    }

    @Override
    public String readJobsFileContent() throws IOException {
        return Files.readString(Path.of(FILE_PATH));
    }

    @Override
    public String importJobs() throws IOException, JAXBException {
        StringBuilder result = new StringBuilder();

        JobSeedRootDTO jobSeedRootDTO = this.xmlParser
                .fromFile(readJobsFileContent(), JobSeedRootDTO.class);

        jobSeedRootDTO.getJobs().stream()
                .map(dto -> {
                    if (!this.validationUtil.isValid(dto)) {
                        result.append("Invalid job").append(System.lineSeparator());
                        return null;
                    }

                    Company company = this.companyRepository.findById(dto.getCompanyId())
                            .orElse(null);

                    if (company == null) {
                        result.append("Invalid job").append(System.lineSeparator());
                        return null;
                    }

                    Job job = this.modelMapper.map(dto, Job.class);
                    job.setCompany(company);

                    this.jobRepository.saveAndFlush(job);

                    return job;
                })
                .toList();

        return result.toString();
    }

    @Override
    public String getBestJobs() {
        StringBuilder result = new StringBuilder();

        List<Job> bestJobs = this.jobRepository.findBestJobs();

        for (Job job : bestJobs) {
            result.append(String.format("Job title %s%n", job.getTitle()))
                    .append(String.format("-Salary: %.2f$%n", job.getSalary()))
                    .append(String.format("--Hours a week: %.2fh.%n", job.getHoursAWeek()));
        }

        return result.toString();
    }
}
