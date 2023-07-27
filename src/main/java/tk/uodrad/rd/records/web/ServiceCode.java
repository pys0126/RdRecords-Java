package tk.uodrad.rd.records.web;

public enum ServiceCode {
    SUCCESS(2000),
    ERROR(5000),
    ERROR_BAD_REQUEST(-1),
    ERROR_NOT_FOUND(40400),
    ERROR_CONFLICT(40900),
    ERROR_UNAUTHENTICATED(40100),
    ERROR_UNAUTHENTICATED_DISABLED(40101),
    ERROR_FORBIDDEN(40300),
    ERROR_INSERT(50000),
    ERROR_UPDATE(50100),
    ERROR_DELETE(50200),
    ERROR_JWT_EXPIRED(60000),
    ERROR_JWT_MALFORMED(60100),
    ERROR_JWT_SIGNATURE(60200),
    ERROR_UNKNOWN(99999);

    private Integer code;

    ServiceCode(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return this.code;
    }
}
