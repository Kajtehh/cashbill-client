package pl.kajteh.cashbill.client.payment;

import java.util.List;

public record CashBillPaymentChannel(String id, List<String> availableCurrencies, String name, String description, String logoUrl) {
}
