package tk.uodrad.rd.records.service.impl;

import cn.leancloud.LCObject;
import cn.leancloud.LCQuery;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import tk.uodrad.rd.records.ex.ServiceException;
import tk.uodrad.rd.records.pojo.dto.RdRecordsAddDTO;
import tk.uodrad.rd.records.pojo.dto.RdRecordsUpdateDTO;
import tk.uodrad.rd.records.pojo.entity.RdRecords;
import tk.uodrad.rd.records.pojo.vo.RdRecordsGetAllEmailVO;
import tk.uodrad.rd.records.pojo.vo.RdRecordsGetAllVO;
import tk.uodrad.rd.records.pojo.vo.RdRecordsGetByObjectIdVO;
import tk.uodrad.rd.records.pojo.vo.RdRecordsGetNicknameAndEmailVO;
import tk.uodrad.rd.records.service.RdRecordsService;
import tk.uodrad.rd.records.web.ServiceCode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
public class RdRecordsServiceImpl implements RdRecordsService {
    private LCQuery<LCObject> rdRecordsQuery;
    private LCQuery<LCObject> projectsQuery;

    private RdRecordsServiceImpl() {
        rdRecordsQuery = new LCQuery<>("rd_records");
        projectsQuery = new LCQuery<>("projects");
    }

    @Override
    public List<RdRecordsGetAllVO> getAllRdRecords() {
        log.info("执行【getAllRdRecords】");
        RdRecords rdRecords = new RdRecords();
        List<RdRecordsGetAllVO> rdRecordsGetAllVOList = new ArrayList<>();
        rdRecordsQuery.find().forEach(lcObject -> {
            RdRecordsGetAllVO rdRecordsGetAllVO = new RdRecordsGetAllVO();
            rdRecords.setObjectId(lcObject.getObjectId());
            rdRecords.setEmail(lcObject.getString("email"));
            rdRecords.setNickname(lcObject.getString("nickname"));
            rdRecords.setWorkingHours(lcObject.getInt("working_hours"));
            rdRecords.setProjectId(lcObject.getString("project_id"));
            rdRecords.setCreatedAt(lcObject.getCreatedAt());
            rdRecords.setUpdatedAt(lcObject.getUpdatedAt());
            BeanUtils.copyProperties(rdRecords, rdRecordsGetAllVO);
            String projectName = projectsQuery.get(rdRecords.getProjectId()).getString("project_name");
            rdRecordsGetAllVO.setProjectName(projectName);
            rdRecordsGetAllVOList.add(rdRecordsGetAllVO);
        });
        return rdRecordsGetAllVOList;
    }

    @Override
    public RdRecordsGetByObjectIdVO getRdRecordsByObjectId(String objectId) {
        log.info("执行【getProjectByObjectId】参数：{}", objectId);
        RdRecords rdRecords = new RdRecords();
        RdRecordsGetByObjectIdVO rdRecordsGetByObjectIdVO = new RdRecordsGetByObjectIdVO();
        LCObject lcObject;
        try {
            lcObject = rdRecordsQuery.get(objectId);
        } catch (RuntimeException e) {
            String message = "此objectId的数据不存在！";
            log.warn("【getRdRecordsByObjectId】出错了：{}", message);
            throw new ServiceException(ServiceCode.ERROR_NOT_FOUND, message);
        }
        rdRecords.setObjectId(lcObject.getObjectId());
        rdRecords.setEmail(lcObject.getString("email"));
        rdRecords.setNickname(lcObject.getString("nickname"));
        rdRecords.setWorkingHours(lcObject.getInt("working_hours"));
        rdRecords.setProjectId(lcObject.getString("project_id"));
        rdRecords.setCreatedAt(lcObject.getCreatedAt());
        rdRecords.setUpdatedAt(lcObject.getUpdatedAt());
        BeanUtils.copyProperties(rdRecords, rdRecordsGetByObjectIdVO);
        String projectName = projectsQuery.get(rdRecords.getProjectId()).getString("project_name");
        rdRecordsGetByObjectIdVO.setProjectName(projectName);
        return rdRecordsGetByObjectIdVO;
    }

    @Override
    public void updateByObjectId(RdRecordsUpdateDTO rdRecordsUpdateDTO) {
        log.info("执行【updateByObjectId】参数：{}", rdRecordsUpdateDTO);
        RdRecords rdRecords = new RdRecords();
        BeanUtils.copyProperties(rdRecordsUpdateDTO, rdRecords);
        try {
            rdRecordsQuery.get(rdRecords.getObjectId());
        } catch (RuntimeException e) {
            String message = "此objectId的数据不存在！";
            log.warn("【getRdRecordsByObjectId】出错了：{}", message);
            throw new ServiceException(ServiceCode.ERROR_NOT_FOUND, message);
        }
        try {
            projectsQuery.get(rdRecords.getProjectId());
        } catch (RuntimeException e) {
            String message = "该projectId对应的研发项目不存在！";
            log.warn("【updateByObjectId】出错了：{}", message);
            throw new ServiceException(ServiceCode.ERROR_NOT_FOUND, message);
        }
        LCObject object = rdRecordsQuery.get(rdRecords.getObjectId());
        object.put("email", rdRecords.getEmail());
        object.put("nickname", rdRecords.getNickname());
        object.put("working_hours", rdRecords.getWorkingHours());
        object.put("project_id", rdRecords.getProjectId());
        object.save();
    }

    @Override
    public List<RdRecordsGetNicknameAndEmailVO> getNicknameAndEmail() {
        log.info("执行【getNicknameAndEmail】");
        RdRecords rdRecords = new RdRecords();
        List<RdRecordsGetNicknameAndEmailVO> rdRecordsGetNicknameAndEmailVOList = new ArrayList<>();
        List<LCObject> lcObjects = rdRecordsQuery.find();
        for (int i = 0; i < lcObjects.size(); i++) {
            LCObject lcObject = lcObjects.get(i);
            if (i > 0) {
                LCObject lastLCObject = lcObjects.get(i - 1);
                if (
                    lcObject.getString("email").equals(lastLCObject.getString("email"))
                    && lcObject.getString("nickname").equals(lastLCObject.getString("nickname"))
                ) continue;
            }
            RdRecordsGetNicknameAndEmailVO rdRecordsGetNicknameAndEmailVO = new RdRecordsGetNicknameAndEmailVO();
            rdRecords.setEmail(lcObjects.get(i).getString("email"));
            rdRecords.setNickname(lcObjects.get(i).getString("nickname"));
            BeanUtils.copyProperties(rdRecords, rdRecordsGetNicknameAndEmailVO);
            rdRecordsGetNicknameAndEmailVOList.add(rdRecordsGetNicknameAndEmailVO);
        }
        return rdRecordsGetNicknameAndEmailVOList;
    }

    @Override
    public List<RdRecordsGetAllVO> getAllRdRecordsByNicknameAndEmail(String nickname, String email) {
        log.info("执行【getAllRdRecordsByNicknameAndEmail】，参数：{} | {}", nickname, email);
        LCQuery<LCObject> lcQueryOr = LCQuery.and(Arrays.asList(
                rdRecordsQuery.whereEqualTo("nickname", nickname),
                new LCQuery<>("rd_records").whereEqualTo("email", email)
        ));
        List<LCObject> lcQueryOrList = lcQueryOr.find();
        if (lcQueryOrList.size() == 0) {
            String message = "该昵称和该邮箱的数据不存在！";
            log.warn("【getAllRdRecordsByNicknameAndEmail】出错了：{}", message);
            throw new ServiceException(ServiceCode.ERROR_NOT_FOUND, message);
        }
        RdRecords rdRecords = new RdRecords();
        List<RdRecordsGetAllVO> rdRecordsGetAllVOList = new ArrayList<>();
        lcQueryOrList.forEach(lcObject -> {
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
        return rdRecordsGetAllVOList;
    }

    @Override
    public void addRdRecords(RdRecordsAddDTO rdRecordsAddDTO) {
        log.info("执行【addRdRecords】参数：{}", rdRecordsAddDTO);
        try {
            projectsQuery.get(rdRecordsAddDTO.getProjectId());
        } catch (RuntimeException e) {
            String message = "该projectId对应的研发项目不存在！";
            log.warn("【addRdRecords】出错了：{}", message);
            throw new ServiceException(ServiceCode.ERROR_NOT_FOUND, message);
        }
        RdRecords rdRecords = new RdRecords();
        BeanUtils.copyProperties(rdRecordsAddDTO, rdRecords);
        LCObject lcObject = new LCObject("rd_records");
        lcObject.put("nickname", rdRecords.getNickname());
        lcObject.put("email", rdRecords.getEmail());
        lcObject.put("project_id", rdRecords.getProjectId());
        lcObject.put("working_hours", rdRecords.getWorkingHours());
        lcObject.save();
    }

    @Override
    public void deleteByObjectIds(String... objectIds) {
        log.info("执行【deleteByObjectIds】参数：{}", Arrays.asList(objectIds));
        rdRecordsQuery.whereContainedIn("objectId", Arrays.asList(objectIds)).find().forEach(LCObject::delete);
    }

    @Override
    public List<RdRecordsGetAllEmailVO> getAllEmail() {
        log.info("执行【getAllEmail】");
        LCQuery<LCObject> objectLCQuery = rdRecordsQuery.whereExists("email");
        List<RdRecordsGetAllEmailVO> rdRecordsGetAllEmailVOS = new ArrayList<>();
        RdRecords rdRecords = new RdRecords();
        objectLCQuery.find().forEach(lcObject -> {
            RdRecordsGetAllEmailVO rdRecordsGetAllEmailVO = new RdRecordsGetAllEmailVO();
            String email = lcObject.getString("email");
            rdRecords.setEmail(email);
            BeanUtils.copyProperties(rdRecords, rdRecordsGetAllEmailVO);
            rdRecordsGetAllEmailVOS.add(rdRecordsGetAllEmailVO);
        });
        return rdRecordsGetAllEmailVOS;
    }
}
