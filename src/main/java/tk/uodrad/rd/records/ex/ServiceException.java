package tk.uodrad.rd.records.ex;

import lombok.Getter;
import tk.uodrad.rd.records.web.ServiceCode;

public class ServiceException extends RuntimeException {
    @Getter
    private ServiceCode serviceCode;

    public ServiceException(ServiceCode serviceCode, String message) {
        super(message);
        this.serviceCode = serviceCode;
    }
}
