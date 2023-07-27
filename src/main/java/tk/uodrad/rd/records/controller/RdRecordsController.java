package tk.uodrad.rd.records.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import tk.uodrad.rd.records.pojo.dto.RdRecordsAddDTO;
import tk.uodrad.rd.records.pojo.dto.RdRecordsUpdateDTO;
import tk.uodrad.rd.records.pojo.vo.RdRecordsGetAllVO;
import tk.uodrad.rd.records.pojo.vo.RdRecordsGetByObjectIdVO;
import tk.uodrad.rd.records.pojo.vo.RdRecordsGetNicknameAndEmailVO;
import tk.uodrad.rd.records.service.RdRecordsService;
import tk.uodrad.rd.records.web.JsonResult;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/rdRecords")
public class RdRecordsController {
    @Resource
    private RdRecordsService rdRecordsService;

    @GetMapping("/getAll")
    public JsonResult<List<RdRecordsGetAllVO>> getAll() {
        return JsonResult.ok(rdRecordsService.getAllRdRecords());
    }

    @GetMapping("/getRdRecordsByObjectId")
    public JsonResult<RdRecordsGetByObjectIdVO> getRdRecordsByObjectId(@RequestParam("objectId") String objectId) {
        return JsonResult.ok(rdRecordsService.getRdRecordsByObjectId(objectId));
    }

    @PostMapping("/updateRdRecordsByObjectId")
    public JsonResult<Void> updateRdRecordsByObjectId(@RequestBody RdRecordsUpdateDTO rdRecordsUpdateDTO) {
        rdRecordsService.updateByObjectId(rdRecordsUpdateDTO);
        return JsonResult.ok();
    }

    @GetMapping("/getNicknameAndEmail")
    public JsonResult<List<RdRecordsGetNicknameAndEmailVO>> getNicknameAndEmail() {
        return JsonResult.ok(rdRecordsService.getNicknameAndEmail());
    }

    @GetMapping("/getAllByNicknameAndEmail")
    public JsonResult<List<RdRecordsGetAllVO>> getAllByNicknameAndEmail(
            @RequestParam(value = "nickname") String nickname,
            @RequestParam(value = "email") String email
    ) {
        return JsonResult.ok(rdRecordsService.getAllRdRecordsByNicknameAndEmail(nickname, email));
    }

    @PostMapping("/add")
    public JsonResult<Void> addRdRecords(@RequestBody RdRecordsAddDTO rdRecordsAddDTO) {
        rdRecordsService.addRdRecords(rdRecordsAddDTO);
        return JsonResult.ok();
    }

    @GetMapping("/deleteByObjectIds")
    public JsonResult<Void> deleteByObjectIds(@RequestParam("objectIds") String[] objectIds) {
        rdRecordsService.deleteByObjectIds(objectIds);
        return JsonResult.ok();
    }
}

