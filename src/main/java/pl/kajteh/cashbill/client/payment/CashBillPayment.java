package pl.kajteh.cashbill.client.payment;

import pl.kajteh.cashbill.client.data.CashBillAmountData;
import pl.kajteh.cashbill.client.data.CashBillPersonalData;

import java.util.ArrayList;
import java.util.List;

public class CashBillPayment {

    private final String title;
    private final CashBillAmountData amount;
    private String description;
    private String additionalData;
    private String returnUrl;
    private String negativeReturnUrl;
    private String paymentChannel;
    private String languageCode;
    private CashBillPersonalData personalData;
    private String referer;
    private final List<CashBillPaymentOption> options = new ArrayList<>();

    public CashBillPayment(final String title, final double value, final String currencyCode) {
        this.title = title;
        this.amount = new CashBillAmountData(value, currencyCode);
    }

    public CashBillPayment(final String title, final double value) {
        this(title, value, "PLN");
    }

    public CashBillPayment additionalData(final String additionalData) {
        this.additionalData = additionalData;
        return this;
    }

    public CashBillPayment returnUrl(final String returnUrl) {
        this.returnUrl = returnUrl;
        return this;
    }

    public CashBillPayment description(final String description) {
        this.description = description;
        return this;
    }

    public CashBillPayment negativeReturnUrl(final String negativeReturnUrl) {
        this.negativeReturnUrl = negativeReturnUrl;
        return this;
    }

    public CashBillPayment paymentChannel(final String paymentChannel) {
        this.paymentChannel = paymentChannel;
        return this;
    }

    public CashBillPayment paysafecard() {
        return this.paymentChannel("paysafecard");
    }

    public CashBillPayment blik() {
        return this.paymentChannel("blik");
    }

    public CashBillPayment languageCode(final String languageCode) {
        this.languageCode = languageCode;
        return this;
    }

    public CashBillPayment personalData(final CashBillPersonalData personalData) {
        this.personalData = personalData;
        return this;
    }

    public CashBillPayment referer(final String referer) {
        this.referer = referer;
        return this;
    }

    public CashBillPayment addOption(final String name, final String value) {
        this.options.add(new CashBillPaymentOption(name, value));
        return this;
    }

    public CashBillAmountData amount() {
        return this.amount;
    }

    public String title() {
        return this.title;
    }

    public String description() {
        return this.description;
    }

    public String additionalData() {
        return this.additionalData;
    }

    public String returnUrl() {
        return this.returnUrl;
    }

    public String negativeReturnUrl() {
        return this.negativeReturnUrl;
    }

    public String paymentChannel() {
        return this.paymentChannel;
    }

    public String languageCode() {
        return this.languageCode;
    }

    public CashBillPersonalData personalData() {
        return this.personalData;
    }

    public String referer() {
        return this.referer;
    }

    public List<CashBillPaymentOption> options() {
        return this.options;
    }
}
