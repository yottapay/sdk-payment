package com.yotta.sdk.payment.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.primitives.Longs;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

@Data
@Builder
public class YpPaymentCreation {
    @JsonProperty("shop_transaction_identifier")
    private String merchantTransactionId;

    @JsonProperty("order_reference")
    private String reference;

    @JsonProperty("customer_identifier")
    private String customerId;

    @JsonProperty("amount")
    private String amount;

    @JsonProperty("duration")
    private String duration;

    @JsonProperty("currency")
    private String currency;

    @JsonProperty("url_payment_result")
    private String urlPaymentResult;

    @JsonProperty("url_merchant_page_success")
    private String urlPaymentSuccess;

    @JsonProperty("url_merchant_page_cancel")
    private String urlPaymentCancel;

    @JsonProperty("yotta_notification_id")
    private String notificationId;

    YpPaymentCreation(String merchantTransactionId, String reference, String customerId,
                      String amount, String duration, String currency, String urlPaymentResult,
                      String urlPaymentSuccess, String urlPaymentCancel, String notificationId) {
        this.merchantTransactionId = merchantTransactionId;
        this.reference = reference;
        this.customerId = customerId;
        this.amount = amount;
        this.duration = duration;
        this.currency = currency;
        this.urlPaymentResult = urlPaymentResult;
        this.urlPaymentSuccess = urlPaymentSuccess;
        this.urlPaymentCancel = urlPaymentCancel;
        this.notificationId = notificationId;
    }

    protected YpPaymentCreation(YpPaymentCreation other) {
        this(
                other.merchantTransactionId,
                other.reference,
                other.customerId,
                other.amount,
                other.duration,
                other.currency,
                other.urlPaymentResult,
                other.urlPaymentSuccess,
                other.urlPaymentCancel,
                other.notificationId);
    }

    public static class YpPaymentCreationBuilder {
        private static final BigDecimal HUNDRED = BigDecimal.valueOf(100);

        public YpPaymentCreation build() {

            return new YpPaymentCreation(
                    validateNotEmpty(merchantTransactionId, "merchantTransactionId is required"),
                    makeNotNull(reference),
                    validateNotEmpty(customerId, "customerId is required"),
                    validateNotEmpty(amount, "amount is required"),
                    makeAtLeastZero(duration),
                    validateNotEmpty(currency, "currency is required"),
                    validateNotEmpty(urlPaymentResult, "urlPaymentResult is required"),
                    validateNotEmpty(urlPaymentSuccess, "urlPaymentSuccess is required"),
                    validateNotEmpty(urlPaymentCancel, "urlPaymentCancel is required"),
                    notificationId);
        }

        public YpPaymentCreationBuilder amount(String amount) {
            this.amount = amount;
            return this;
        }

        public YpPaymentCreationBuilder amount(long amount) {
            String amountStr = BigDecimal.valueOf(amount)
                    .setScale(2, RoundingMode.UNNECESSARY)
                    .divide(HUNDRED, RoundingMode.UNNECESSARY)
                    .toPlainString();

            return this.amount(amountStr);
        }

        private <T> T validateNotNull(T object, String message) {
            return Objects.requireNonNull(object, message);
        }

        private String makeNotNull(String s) {
            if(s == null) {
                return "";
            }
            return s;
        }

        private String makeAtLeastZero(String s) {
            if(s == null) {
                return "0";
            }
            if(Longs.tryParse(s) == null) {
                return "0";
            }
            return s;
        }

        private String validateNotEmpty(String object, String message) {
            if (validateNotNull(object, message).isEmpty()) {
                throw new IllegalArgumentException(message);
            }

            return object;
        }
    }
}
