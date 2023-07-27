package tk.uodrad.rd.records.service;

import tk.uodrad.rd.records.pojo.dto.RdRecordsAddDTO;
import tk.uodrad.rd.records.pojo.dto.RdRecordsUpdateDTO;
import tk.uodrad.rd.records.pojo.entity.RdRecords;
import tk.uodrad.rd.records.pojo.vo.RdRecordsGetAllEmailVO;
import tk.uodrad.rd.records.pojo.vo.RdRecordsGetAllVO;
import tk.uodrad.rd.records.pojo.vo.RdRecordsGetByObjectIdVO;
import tk.uodrad.rd.records.pojo.vo.RdRecordsGetNicknameAndEmailVO;

import java.util.List;

public interface RdRecordsService {
    List<RdRecordsGetAllVO> getAllRdRecords();

    RdRecordsGetByObjectIdVO getRdRecordsByObjectId(String objectId);

    void updateByObjectId(RdRecordsUpdateDTO rdRecordsUpdateDTO);

    List<RdRecordsGetNicknameAndEmailVO> getNicknameAndEmail();

    List<RdRecordsGetAllVO> getAllRdRecordsByNicknameAndEmail(String nickname, String email);

    void addRdRecords(RdRecordsAddDTO rdRecordsAddDTO);

    void deleteByObjectIds(String... objectIds);

    // 未公开
    List<RdRecordsGetAllEmailVO> getAllEmail();
}
