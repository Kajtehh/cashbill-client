# CashBill Client

Unofficial Java client for the **CashBill API**.  
Allows creating transactions, checking transaction details, and fetching payment channels.

---

## ⚠️ Disclaimer

This library is an **unofficial Java client** for the CashBill API.  
The author is **not affiliated** with CashBill and cannot guarantee compatibility if the API changes in the future.

---

## API Documentation

The full CashBill API documentation (endpoints, JSON formats, examples) is available here:  
[Payment Gateway API Documentation](https://api.cashbill.pl/category/api/payment-gateway)

---

## Installation

### Maven

Add the GitHub Packages repository to your `pom.xml`:

```xml
<repositories>
    <repository>
        <id>github</id>
        <name>GitHub Packages</name>
        <url>https://maven.pkg.github.com/Kajtehh/cashbill-client</url>
    </repository>
</repositories>
```

Then add the dependency:

```xml
<dependencies>
    <dependency>
        <groupId>pl.kajteh.cashbill</groupId>
        <artifactId>client</artifactId>
        <version>1.0.0</version>
    </dependency>
</dependencies>
```

---

## Configuration

Initialize the client with your `shopId` and `secretKey`:

```java
CashBillClient client = CashBillClient.sandbox("SHOP_ID", "SECRET_KEY");

// or production environment
CashBillClient clientProduction = CashBillClient.production("SHOP_ID", "SECRET_KEY");
```

---

## Usage

### Create a transaction

```java
CashBillPayment payment = new CashBillPayment("Title", 123.4) // required
        .description("Lorem ipsum dolor sit amet")
        .returnUrl("https://github.com/Kajtehh")
        .negativeReturnUrl("https://github.com")
        .paysafecard(); // or use .paymentChannel(...) / .blik()

CashBillTransaction transaction = client.createTransaction(payment);
System.out.println(transaction.id() + " " + transaction.redirectUrl());
```

### Get transaction details

```java
CashBillTransactionDetails transactionDetails =
        client.transactionDetails("transactionId"); // or pass CashBillTransaction

System.out.println(transactionDetails.status());
```

### List payment channels

```java
List<CashBillPaymentChannel> channels = client.paymentChannels("en");
channels.forEach(channel -> System.out.println(channel.name()));
```

### Verify transaction from status change notification

```java
CashBillTransactionDetails verifiedTransaction = 
        client.verifyTransaction("transactionId", signature);
```

---

## Transaction statuses

| Status                 | Description                            | Final |
| ---------------------- | -------------------------------------- | ----- |
| PRE_START              | Payment started, no channel chosen yet | No    |
| START                  | Payment started, customer not paid     | No    |
| NEGATIVE_AUTHORIZATION | Payment channel refused payment        | No    |
| ABORT                  | Customer aborted payment               | No    |
| FRAUD                  | Payment channel flagged as fraud       | Yes   |
| POSITIVE_AUTHORIZATION | Payment channel accepted transaction   | No    |
| POSITIVE_FINISH        | Payment confirmed by channel           | Yes   |
| NEGATIVE_FINISH        | Payment rejected by channel            | Yes   |

## Support

For issues or questions, please open an issue on GitHub.