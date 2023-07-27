package tk.uodrad.rd.records.pojo.dto;

import lombok.Data;

import java.io.Serializable;

/*
* 研发记录实体类
*/
@Data
public class RdRecordsUpdateDTO implements Serializable {
    private String objectId;
    private String nickname;
    private String email;
    private String projectId;
    private Integer workingHours;
}
