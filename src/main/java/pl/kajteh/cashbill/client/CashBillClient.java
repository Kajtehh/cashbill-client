package pl.kajteh.cashbill.client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import okhttp3.*;
import pl.kajteh.cashbill.client.payment.CashBillPayment;
import pl.kajteh.cashbill.client.payment.CashBillPaymentChannel;
import pl.kajteh.cashbill.client.transaction.CashBillTransaction;
import pl.kajteh.cashbill.client.transaction.CashBillTransactionDetails;

import java.io.IOException;
import java.util.List;

public final class CashBillClient {

    private final String shopId;
    private final String secretKey;
    private final String baseUrl;

    private final OkHttpClient httpClient;

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final MediaType MEDIA_TYPE = MediaType.get("application/json; charset=utf-8");

    public static CashBillClient production(final String shopId, final String secretKey) {
        return new CashBillClient(shopId, secretKey, CashBillEnvironment.PRODUCTION);
    }

    public static CashBillClient sandbox(final String shopId, final String secretKey) {
        return new CashBillClient(shopId, secretKey, CashBillEnvironment.TEST);
    }

    public CashBillClient(final String shopId, final String secretKey, final CashBillEnvironment environment) {
        this(shopId, secretKey, environment, new OkHttpClient());
    }

    public CashBillClient(final String shopId, final String secretKey, final CashBillEnvironment environment, final OkHttpClient httpClient) {
        this.shopId = shopId;
        this.secretKey = secretKey;
        this.baseUrl = environment.baseUrl();
        this.httpClient = httpClient;
    }

    public CashBillTransaction createTransaction(final CashBillPayment payment) throws CashBillException {
        final var jsonObject = GSON.toJsonTree(payment).getAsJsonObject();

        jsonObject.addProperty("sign", CashBillSignature.generateSign(payment, this.secretKey));

        final var json = GSON.toJson(jsonObject);
        final var request = new Request.Builder()
                .url(this.baseUrl + "/payment/" + this.shopId)
                .post(RequestBody.create(json, MEDIA_TYPE))
                .addHeader("Accept", "application/json")
                .build();

        try (final var response = this.httpClient.newCall(request).execute()) {
            final var responseBody = response.body().string();

            if(!response.isSuccessful()) {
                throw new CashBillException(
                        "Error creating transaction: " + responseBody,
                        response.code()
                );
            }

            return GSON.fromJson(responseBody, CashBillTransaction.class);
        } catch (final IOException e) {
            throw new CashBillException("Unexpected error: " + e.getMessage());
        }
    }

    public CashBillTransactionDetails transactionDetails(final String orderId) throws CashBillException {
        final var request = new Request.Builder()
                .url(this.baseUrl + "/payment/" + this.shopId + "/" + orderId + "?sign=" + CashBillSignature.generateSign(orderId, this.secretKey))
                .addHeader("Accept", "application/json")
                .build();

        try (final var response = this.httpClient.newCall(request).execute()) {
            final var responseBody = response.body().string();

            if (!response.isSuccessful()) {
                throw new CashBillException(
                        "Error fetching transaction details: " + responseBody,
                        response.code()
                );
            }

            return GSON.fromJson(responseBody, CashBillTransactionDetails.class);
        } catch (final IOException e) {
            throw new CashBillException("Unexpected error: " + e.getMessage());
        }
    }

    public CashBillTransactionDetails transactionDetails(final CashBillTransaction transaction) throws CashBillException {
        return this.transactionDetails(transaction.id());
    }

    public List<CashBillPaymentChannel> paymentChannels(final String languageCode) throws CashBillException {
        final var request = new Request.Builder()
                .url(this.baseUrl + "/paymentchannels/" + this.shopId + "/" + languageCode)
                .addHeader("Accept", "application/json")
                .build();

        try (final var response = this.httpClient.newCall(request).execute()) {
            final var responseBody = response.body().string();

            if (!response.isSuccessful()) {
                throw new CashBillException(
                        "Error fetching channels: " + responseBody,
                        response.code()
                );
            }

            return GSON.fromJson(responseBody, new TypeToken<List<CashBillPaymentChannel>>(){}.getType());
        } catch (final IOException e) {
            throw new CashBillException("Unexpected error: " + e.getMessage());
        }
    }

    public CashBillTransactionDetails verifyTransactionStatusChange(final String cmd, final String orderId, final String sign) throws CashBillException {
        if(!cmd.equals("transactionStatusChanged")) {
            throw new CashBillException("Invalid command");
        }

        if (!CashBillSignature.verifySign("transactionStatusChanged", orderId, sign, this.secretKey)) {
            throw new CashBillException("Invalid signature for transaction: " + orderId);
        }

        return this.transactionDetails(orderId);
    }
}
