package tk.uodrad.rd.records.service.impl;

import cn.leancloud.LCObject;
import cn.leancloud.LCQuery;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import tk.uodrad.rd.records.ex.ServiceException;
import tk.uodrad.rd.records.pojo.dto.ProjectsAddDTO;
import tk.uodrad.rd.records.pojo.dto.ProjectsUpdateDTO;
import tk.uodrad.rd.records.pojo.entity.Projects;
import tk.uodrad.rd.records.pojo.vo.ProjectsGetAllVO;
import tk.uodrad.rd.records.pojo.vo.ProjectsGetByObjectIdVO;
import tk.uodrad.rd.records.service.ProjectsService;
import tk.uodrad.rd.records.web.ServiceCode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
public class ProjectServiceImpl implements ProjectsService {
    @Override
    public List<ProjectsGetAllVO> getAllProject() {
        log.info("执行【getAllProject】");
        Projects projects = new Projects();
        List<ProjectsGetAllVO> projectsGetAllVOList = new ArrayList<>();
        new LCQuery<>("projects").find().forEach(lcObject -> {
            ProjectsGetAllVO projectsGetAllVO = new ProjectsGetAllVO();
            projects.setObjectId(lcObject.getObjectId());
            projects.setProjectName(lcObject.getString("project_name"));
            projects.setCreatedAt(lcObject.getCreatedAt());
            projects.setUpdatedAt(lcObject.getUpdatedAt());
            BeanUtils.copyProperties(projects, projectsGetAllVO);
            System.out.println(projects);
            System.out.println(projectsGetAllVO);
            projectsGetAllVOList.add(projectsGetAllVO);
        });
        return projectsGetAllVOList;
    }

    @Override
    public ProjectsGetByObjectIdVO getProjectByObjectId(String objectId) {
        log.info("执行【getProjectByObjectId】参数：{}", objectId);
        Projects projects = new Projects();
        ProjectsGetByObjectIdVO projectsGetByObjectIdVO = new ProjectsGetByObjectIdVO();
        LCObject lcObject;
        try {
            lcObject = new LCQuery<>("projects").get(objectId);
        } catch (RuntimeException e) {
            String message = "此objectId的数据不存在！";
            log.warn("【getProjectByObjectId】出错了：{}", message);
            throw new ServiceException(ServiceCode.ERROR_NOT_FOUND, message);
        }
        projects.setObjectId(lcObject.getObjectId());
        projects.setProjectName(lcObject.getString("project_name"));
        projects.setCreatedAt(lcObject.getCreatedAt());
        projects.setUpdatedAt(lcObject.getUpdatedAt());
        BeanUtils.copyProperties(projects, projectsGetByObjectIdVO);
        return projectsGetByObjectIdVO;
    }

    @Override
    public void updateByObjectId(ProjectsUpdateDTO projectsUpdateDTO) {
        log.info("执行【updateByObjectId】参数：{}", projectsUpdateDTO);
        Projects projects = new Projects();
        BeanUtils.copyProperties(projectsUpdateDTO, projects);
        try {
            new LCQuery<>("projects").get(projects.getObjectId());
        } catch (RuntimeException e) {
            String message = "此objectId的数据不存在！";
            log.warn("【updateByObjectId】出错了：{}", message);
            throw new ServiceException(ServiceCode.ERROR_NOT_FOUND, message);
        }
        LCQuery<LCObject> projectNameQuery = new LCQuery<>("projects").whereEqualTo("project_name", projects.getProjectName());
        if (projectNameQuery.find().size() > 1) {
            String message = "此项目名称以存在！";
            log.warn("【updateByObjectId】出错了：{}", message);
            throw new ServiceException(ServiceCode.ERROR_UPDATE, message);
        }
        LCObject object = new LCQuery<>("projects").get(projects.getObjectId());
        object.put("project_name", projects.getProjectName());
        object.save();
    }

    @Override
    public void addProject(ProjectsAddDTO projectsAddDTO) {
        log.info("执行【addProject】参数：{}", projectsAddDTO);
        Projects projects = new Projects();
        BeanUtils.copyProperties(projectsAddDTO, projects);
        LCObject lcObject = new LCObject("projects");
        lcObject.put("project_name", projects.getProjectName());
        lcObject.save();
    }

    @Override
    public void deleteProjectByObjectIds(String... objectIds) {
        log.info("执行【deleteProjectByObjectIds】参数：{}", Arrays.asList(objectIds));
        new LCQuery<>("projects").whereContainedIn("objectId", Arrays.asList(objectIds)).find().forEach(LCObject::delete);
    }
}
