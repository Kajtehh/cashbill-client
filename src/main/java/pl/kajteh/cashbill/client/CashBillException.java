package pl.kajteh.cashbill.client;

public class CashBillException extends Exception {
    private final int errorCode;

    public CashBillException(final String message) {
        this(message, -1);
    }

    public CashBillException(final String message, final int errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }
}