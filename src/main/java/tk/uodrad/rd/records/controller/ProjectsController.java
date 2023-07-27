package tk.uodrad.rd.records.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import tk.uodrad.rd.records.pojo.dto.ProjectsAddDTO;
import tk.uodrad.rd.records.pojo.dto.ProjectsUpdateDTO;
import tk.uodrad.rd.records.pojo.vo.ProjectsGetAllVO;
import tk.uodrad.rd.records.pojo.vo.ProjectsGetByObjectIdVO;
import tk.uodrad.rd.records.service.ProjectsService;
import tk.uodrad.rd.records.web.JsonResult;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/projects")
public class ProjectsController {
    @Resource
    private ProjectsService projectsService;

    @GetMapping("/getAll")
    public JsonResult<List<ProjectsGetAllVO>> getAll() {
        return JsonResult.ok(projectsService.getAllProject());
    }

    @GetMapping("/getProjectByObjectId")
    public JsonResult<ProjectsGetByObjectIdVO> getProjectByObjectId(@RequestParam("objectId") String objectId) {
        return JsonResult.ok(projectsService.getProjectByObjectId(objectId));
    }

    @PostMapping("/updateProjectByObjectId")
    public JsonResult<Void> updateProjectByObjectId(@RequestBody ProjectsUpdateDTO projectsUpdateDTO) {
        projectsService.updateByObjectId(projectsUpdateDTO);
        return JsonResult.ok();
    }

    @PostMapping("/add")
    public JsonResult<Void> addProject(@RequestBody ProjectsAddDTO projectsAddDTO) {
        projectsService.addProject(projectsAddDTO);
        return JsonResult.ok();
    }

    @GetMapping("/deleteByObjectIds")
    public JsonResult<Void> deleteByObjectIds(@RequestParam("objectIds") String[] objectIds) {
        projectsService.deleteProjectByObjectIds(objectIds);
        return JsonResult.ok();
    }
}

