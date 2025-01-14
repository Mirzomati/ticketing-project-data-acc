package com.cydeo.controller;

import com.cydeo.dto.ProjectDTO;
import com.cydeo.dto.UserDTO;
import com.cydeo.service.ProjectService;
import com.cydeo.service.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/project")
public class ProjectController {

    private final ProjectService projectService;
    private final UserService userService;

    public ProjectController(ProjectService projectService, UserService userService) {
        this.projectService = projectService;
        this.userService = userService;
    }

    @GetMapping("/create")
    public String createProject(Model model) {

        model.addAttribute("project", new ProjectDTO());
        model.addAttribute("projects", projectService.listAllProjects());
        model.addAttribute("managers", userService.findAllUsersByRole("MANAGER"));

        return "project/create";
    }

    @PostMapping("/create")
    public String insertProject(@Valid @ModelAttribute("project") ProjectDTO project,
                                BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {

            model.addAttribute("managers", userService.findAllUsersByRole("MANAGER"));
            model.addAttribute("projects", projectService.listAllProjects());

            return "/project/create";
        }

        projectService.save(project);

        return "redirect:/project/create";
    }

    @GetMapping("/update/{projectCode}")
    public String editProject(@PathVariable("projectCode") String projectCode, Model model) {

        model.addAttribute("project", projectService.getByProjectCode(projectCode));
        model.addAttribute("projects", projectService.listAllProjects());
        model.addAttribute("managers", userService.findAllUsersByRole("MANAGER"));

        return "project/update";
    }


    @PostMapping("/update")
    public String updateProject(@Valid @ModelAttribute("project") ProjectDTO project,
                                BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {

            model.addAttribute("managers", userService.findAllUsersByRole("MANAGER"));
            model.addAttribute("projects", projectService.listAllProjects());

            return "/project/update";
        }

        projectService.update(project);
        return "redirect:/project/create";
    }


    @GetMapping("/delete/{projectCode}")
    public String deleteProject(@PathVariable("projectCode") String projectCode) {

        projectService.delete(projectCode);

        return "redirect:/project/create";
    }


    @GetMapping("/complete/{projectCode}")
    public String completeProject(@PathVariable("projectCode") String projectCode) {

        projectService.complete(projectCode);

        return "redirect:/project/create";
    }

    @GetMapping("/manager/project-status")
    private String getProjectByManager(Model model) {



        model.addAttribute("projects", projectService.listAllProjectDetails());

        return "manager/project-status";

    }

    @GetMapping("/manager/complete/{projectCode}")
    public String managerCompleteProject(@PathVariable("projectCode") String projectCode) {

        projectService.complete(projectCode);

        return "redirect:/project/manager/project-status";
    }
}
