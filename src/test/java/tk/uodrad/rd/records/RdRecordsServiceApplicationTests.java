package tk.uodrad.rd.records;

import cn.leancloud.LCObject;
import cn.leancloud.LCQuery;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.test.context.SpringBootTest;
import tk.uodrad.rd.records.pojo.entity.Projects;
import tk.uodrad.rd.records.pojo.entity.RdRecords;
import tk.uodrad.rd.records.pojo.vo.RdRecordsGetAllVO;
import tk.uodrad.rd.records.service.MailService;

import javax.annotation.Resource;
import java.util.*;

@Slf4j
@SpringBootTest
class RdRecordsServiceApplicationTests {
    @Resource
    private MailService mailService;

    @Test
    void contextLoads() {
        LCQuery<LCObject> projectsQuery = new LCQuery<>("projects");
        Projects projects = new Projects();
        projectsQuery.find().forEach(lcObject -> {
            projects.setObjectId(lcObject.getObjectId());
            projects.setProjectName(lcObject.getString("project_name"));
            projects.setCreatedAt(lcObject.getCreatedAt());
            projects.setUpdatedAt(lcObject.getUpdatedAt());
        });
        System.out.println(projects);
    }

    @Test
    void test() {
        LCQuery<LCObject> rdRecordsQuery = new LCQuery<>("rd_records");
        LCQuery<LCObject> projectsQuery = new LCQuery<>("projects");
        List<RdRecordsGetAllVO> rdRecordsGetAllVOList = new ArrayList<>();
        rdRecordsQuery.whereEqualTo("nickname", "pys").find().forEach(lcObject -> {
            RdRecords rdRecords = new RdRecords();
            RdRecordsGetAllVO rdRecordsGetAllVO = new RdRecordsGetAllVO();
            rdRecords.setObjectId(lcObject.getObjectId());
            rdRecords.setNickname(lcObject.getString("nickname"));
            rdRecords.setEmail(lcObject.getString("email"));
            rdRecords.setWorkingHours(lcObject.getInt("working_hours"));
            rdRecords.setProjectId(lcObject.getString("project_id"));
            rdRecords.setCreatedAt(lcObject.getCreatedAt());
            rdRecords.setUpdatedAt(lcObject.getUpdatedAt());
            BeanUtils.copyProperties(rdRecords, rdRecordsGetAllVO);
            String projectName = projectsQuery.get(rdRecords.getProjectId()).getString("project_name");
            rdRecordsGetAllVO.setProjectName(projectName);
            rdRecordsGetAllVOList.add(rdRecordsGetAllVO);
        });
        rdRecordsGetAllVOList.forEach(recordsGetAllVO -> {
            log.info("查询结果为：{}", recordsGetAllVO.getObjectId());
        });
    }

    @Test
    void nicknameOrEmail() {
        LCQuery<LCObject> rdRecordsQuery = new LCQuery<>("rd_records");
        LCQuery<LCObject> lcQueryOr = LCQuery.or(Arrays.asList(
                rdRecordsQuery.whereEqualTo("nickname", null),
                new LCQuery<>("rd_records").whereEqualTo("email", null)
        ));
        lcQueryOr.find().forEach(lcObject -> {
            System.out.println(lcObject.getObjectId());
        });
    }

    @Test
    void findIds() {
        String[] ids = new String[]{"64a615c4f742a330ac10ce64"};
        LCQuery<LCObject> rdRecordsQuery = new LCQuery<>("rd_records");
        LCQuery<LCObject> objectLCQuery = rdRecordsQuery.whereContainedIn("objectId", Arrays.asList(ids));
        objectLCQuery.find().forEach(lcObject -> {
            System.out.println(lcObject.getObjectId());
        });
    }

    @Test
    void get() {
        LCQuery<LCObject> rdRecordsQuery = new LCQuery<>("rd_records");
        LCObject lcObject = rdRecordsQuery.get("64ae1c867a64727f6cfd6dec");
        lcObject.put("nickname", "pys");
        lcObject.save();
    }

    @Test
    void send_mail() {
        mailService.sendMail("2493919891@qcq.com", "填写记录", "填写记录");
    }

    @Test
    void getAllEmail() {
        LCQuery<LCObject> rdRecordsQuery = new LCQuery<>("rd_records");
        rdRecordsQuery.find().forEach(lcObject -> {
            String email = lcObject.getString("email");
            System.out.println(email);
        });
    }
}
