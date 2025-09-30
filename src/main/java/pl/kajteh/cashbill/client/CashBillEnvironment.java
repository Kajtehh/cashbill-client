package pl.kajteh.cashbill.client;

public enum CashBillEnvironment {
    PRODUCTION("https://pay.cashbill.pl/ws/rest"),
    TEST("https://pay.cashbill.pl/testws/rest");

    private final String baseUrl;

    CashBillEnvironment(final String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String baseUrl() {
        return this.baseUrl;
    }
}
