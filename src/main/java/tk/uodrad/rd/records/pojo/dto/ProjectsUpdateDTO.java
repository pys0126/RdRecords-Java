package tk.uodrad.rd.records.pojo.dto;

import lombok.Data;

import java.io.Serializable;

/*
* 研发项目实体类
*/
@Data
public class ProjectsUpdateDTO implements Serializable {
    private String objectId;
    private String projectName;
}
