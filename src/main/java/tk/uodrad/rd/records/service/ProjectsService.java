package tk.uodrad.rd.records.service;

import tk.uodrad.rd.records.pojo.dto.ProjectsAddDTO;
import tk.uodrad.rd.records.pojo.dto.ProjectsUpdateDTO;
import tk.uodrad.rd.records.pojo.vo.ProjectsGetAllVO;
import tk.uodrad.rd.records.pojo.vo.ProjectsGetByObjectIdVO;

import java.util.List;

public interface ProjectsService {
    List<ProjectsGetAllVO> getAllProject();

    ProjectsGetByObjectIdVO getProjectByObjectId(String objectId);

    void updateByObjectId(ProjectsUpdateDTO projectsUpdateDTO);

    void addProject(ProjectsAddDTO projectsAddDTO);

    void deleteProjectByObjectIds(String... objectIds);
}
