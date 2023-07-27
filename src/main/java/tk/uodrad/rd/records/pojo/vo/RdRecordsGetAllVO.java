package tk.uodrad.rd.records.pojo.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/*
* 研发记录实体类
*/
@Data
public class RdRecordsGetAllVO implements Serializable {
    private String objectId;
    private String nickname;
    private String email;
    private String projectName;
    private Integer workingHours;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createdAt;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updatedAt;
}
