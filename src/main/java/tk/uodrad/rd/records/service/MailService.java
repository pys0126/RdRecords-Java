package tk.uodrad.rd.records.service;

public interface MailService {
    void sendMail(String to, String subject, String content);
}
