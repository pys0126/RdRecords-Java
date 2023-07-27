package tk.uodrad.rd.records.pojo.vo;

import lombok.Data;

import java.io.Serializable;

/*
* 研发记录实体类
*/
@Data
public class RdRecordsGetNicknameAndEmailVO implements Serializable {
    private String nickname;
    private String email;
}
