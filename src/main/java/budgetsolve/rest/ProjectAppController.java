package budgetsolve.rest;

import budgetsolve.model.Project;
import budgetsolve.model.ProjectDetails;
import budgetsolve.repository.ProjectRepository;
import budgetsolve.repository.UserRepository;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
public class ProjectAppController {

    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    public ProjectAppController(ProjectRepository projectRepository, UserRepository userRepository) {
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/project/user/{userId}")
    public List<Project> getUserProjects(@PathVariable("userId") Long user) {
        return projectRepository.findAllByCreatedById(user);
    }

    @GetMapping("/project/{projectId}")
    public Project getProject(@PathVariable("projectId") Long projectId) {
        Optional<Project> project = projectRepository.findById(projectId);
        return project.orElse(null);
    }


    @PostMapping("/project")
    public Project addProject(@RequestBody ProjectDetails details) {
        Project newProject = new Project();
        newProject.setName(details.getName());
        newProject.setCreatedBy(userRepository.getUserById(details.getCreatedBy().getId()));
        newProject.setCreated(LocalDateTime.now());
        newProject.setLastEdited(LocalDateTime.now());
        newProject.setBudget(details.getBudget());
        newProject.setProfit(details.getProfit());
        newProject.setValue(details.getValue());
        newProject.setCurrency(details.getCurrency());
        return projectRepository.save(newProject);
    }
}
