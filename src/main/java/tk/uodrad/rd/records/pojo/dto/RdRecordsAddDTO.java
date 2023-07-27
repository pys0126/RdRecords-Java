package tk.uodrad.rd.records.pojo.dto;

import lombok.Data;

import java.io.Serializable;

/*
* 研发记录实体类
*/
@Data
public class RdRecordsAddDTO implements Serializable {
    private String nickname;
    private String email;
    private String projectId;
    private Integer workingHours;
}
