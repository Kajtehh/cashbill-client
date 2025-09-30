package pl.kajteh.cashbill.client;

import pl.kajteh.cashbill.client.payment.CashBillPayment;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public final class CashBillSignature {

    public static String generateSign(final CashBillPayment payment, final String secretKey) {
        final StringBuilder sb = new StringBuilder();

        sb.append(payment.title());
        sb.append(payment.amount().value());
        sb.append(payment.amount().currencyCode());
        sb.append(nullToEmpty(payment.returnUrl()));
        sb.append(nullToEmpty(payment.description()));
        sb.append(nullToEmpty(payment.negativeReturnUrl()));
        sb.append(nullToEmpty(payment.additionalData()));
        sb.append(nullToEmpty(payment.paymentChannel()));
        sb.append(nullToEmpty(payment.languageCode()));
        sb.append(nullToEmpty(payment.referer()));

        if (payment.personalData() != null) {
            sb.append(nullToEmpty(payment.personalData().firstName()));
            sb.append(nullToEmpty(payment.personalData().surname()));
            sb.append(nullToEmpty(payment.personalData().email()));
            sb.append(nullToEmpty(payment.personalData().country()));
            sb.append(nullToEmpty(payment.personalData().city()));
            sb.append(nullToEmpty(payment.personalData().postcode()));
            sb.append(nullToEmpty(payment.personalData().street()));
            sb.append(nullToEmpty(payment.personalData().house()));
            sb.append(nullToEmpty(payment.personalData().flat()));
            sb.append(nullToEmpty(payment.personalData().ip()));
        }

        sb.append(secretKey);

        return sha1Hex(sb.toString());
    }

    public static String generateSign(final String orderId, final String secretKey) {
        return sha1Hex(orderId + secretKey);
    }

    private static String nullToEmpty(final String s) {
        return s == null ? "" : s;
    }

    private static String sha1Hex(final String input) {
        try {
            final MessageDigest md = MessageDigest.getInstance("SHA-1");
            final byte[] digest = md.digest(input.getBytes(StandardCharsets.UTF_8));
            final StringBuilder hex = new StringBuilder();

            for (final byte b : digest) {
                hex.append(String.format("%02x", b));
            }

            return hex.toString();
        } catch (final NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-1 not supported", e);
        }
    }

    public static String generateNotificationSign(final String cmd, final String args, final String secretKey) {
        try {
            final String toSign = cmd + args + secretKey;
            final MessageDigest md = MessageDigest.getInstance("MD5");
            final byte[] digest = md.digest(toSign.getBytes(StandardCharsets.UTF_8));
            final StringBuilder hex = new StringBuilder();

            for (final byte b : digest) {
                hex.append(String.format("%02x", b));
            }

            return hex.toString();
        } catch (final NoSuchAlgorithmException e) {
            throw new RuntimeException("MD5 not supported", e);
        }
    }

    public static boolean verifySign(final String cmd, final String args, final String sign, final String secretKey) {
        final String expectedSign = generateNotificationSign(cmd, args, secretKey);
        return expectedSign.equalsIgnoreCase(sign);
    }
}