package pl.kajteh.cashbill.client.transaction;

import pl.kajteh.cashbill.client.data.CashBillAmountData;
import pl.kajteh.cashbill.client.data.CashBillPersonalData;

import java.util.Map;

public record CashBillTransactionDetails(
        String id,
        String paymentChannel,
        CashBillAmountData amount,
        CashBillAmountData requestedAmount,
        String title,
        String description,
        CashBillPersonalData personalData,
        String additionalData,
        CashBillTransactionStatus status,
        Map<String, Object> details
) {
    public String bankId() {
        return this.details != null ? (String) this.details.get("bankId") : null;
    }
}
