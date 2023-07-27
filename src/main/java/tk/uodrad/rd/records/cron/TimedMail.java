package tk.uodrad.rd.records.cron;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import tk.uodrad.rd.records.pojo.vo.RdRecordsGetAllEmailVO;
import tk.uodrad.rd.records.service.MailService;
import tk.uodrad.rd.records.service.RdRecordsService;

import javax.annotation.Resource;
import java.util.List;

@Component
public class TimedMail {
    @Resource
    private MailService mailService;

    @Resource
    private RdRecordsService rdRecordsService;

    // 每天下午五点给每个邮箱发送邮件
    @Scheduled(cron = "0 0 17 * * ?")
    public void sendReminderEmail() {
        List<RdRecordsGetAllEmailVO> allEmail = rdRecordsService.getAllEmail();
        allEmail.forEach(rdRecordsGetAllEmailVO -> {
            String subject = "记录当天研发记录";
            String content = "五点了，该记录当天研发记录了！";
            String email = rdRecordsGetAllEmailVO.getEmail();
            mailService.sendMail(email, subject, content);
        });
    }
}
