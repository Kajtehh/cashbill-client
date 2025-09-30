package pl.kajteh.cashbill.client.transaction;

import com.google.gson.annotations.SerializedName;

public enum CashBillTransactionStatus {
    @SerializedName("PreStart")
    PRE_START(false),

    @SerializedName("Start")
    START(false),

    @SerializedName("NegativeAuthorization")
    NEGATIVE_AUTHORIZATION(false),

    @SerializedName("Abort")
    ABORT(false),

    @SerializedName("Fraud")
    FRAUD(true),

    @SerializedName("PositiveAuthorization")
    POSITIVE_AUTHORIZATION(false),

    @SerializedName("PositiveFinish")
    POSITIVE_FINISH(true),

    @SerializedName("NegativeFinish")
    NEGATIVE_FINISH(true);

    private final boolean isFinal;

    CashBillTransactionStatus(boolean isFinal) {
        this.isFinal = isFinal;
    }

    public boolean isFinal() {
        return isFinal;
    }
}
