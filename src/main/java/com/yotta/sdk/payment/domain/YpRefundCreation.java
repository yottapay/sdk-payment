package com.yotta.sdk.payment.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Objects;

@Data
public class YpRefundCreation {
    @JsonProperty("url_payment_refund_result")
    private String urlPaymentRefundResult;

    @JsonProperty("yottapay_transaction_identifier")
    private String yottaTransactionId;

    @JsonProperty("refund_receiver_full_name")
    private String receiverFullName;

    @JsonProperty("refund_receiver_account_number")
    private String receiverAccNumber;

    @JsonProperty("refund_receiver_sort_code")
    private String receiverSortCode;

    @JsonProperty("refund_receiver_comment")
    private String receiverComment = "";


    protected YpRefundCreation(YpRefundCreation other) {
        this(
                other.urlPaymentRefundResult,
                other.yottaTransactionId,
                other.receiverFullName,
                other.receiverAccNumber,
                other.receiverSortCode,
                other.receiverComment);
    }

    public YpRefundCreation(String urlPaymentRefundResult, String yottaTransactionId, String receiverFullName, String receiverAccNumber, String receiverSortCode, String receiverComment) {
        this.urlPaymentRefundResult = urlPaymentRefundResult;
        this.yottaTransactionId = yottaTransactionId;
        this.receiverFullName = receiverFullName;
        this.receiverAccNumber = receiverAccNumber;
        this.receiverSortCode = receiverSortCode;
        this.receiverComment = receiverComment;
    }

    public static YpRefundCreationBuilder builder() {
        return new YpRefundCreationBuilder();
    }


    public static class YpRefundCreationBuilder {
        private String urlPaymentRefundResult;
        private String yottaTransactionId;
        private String receiverFullName;
        private String receiverAccNumber;
        private String receiverSortCode;
        private String receiverComment;

        YpRefundCreationBuilder() {
        }

        public YpRefundCreation build() {
            return new YpRefundCreation(
                    validateNotEmpty(urlPaymentRefundResult, "urlPaymentRefundResult is required"),
                    validateNotEmpty(yottaTransactionId, "yottaTransactionId is required"),
                    validateNotEmpty(receiverFullName, "receiverFullName is required"),
                    validateNotEmpty(receiverAccNumber, "receiverAccNumber is required"),
                    validateNotEmpty(receiverSortCode, "receiverSortCode is required"),
                    makeNotNull(receiverComment)
            );
        }

        public YpRefundCreationBuilder receiverAccNumber(String receiverAccNumber) {
            this.receiverAccNumber = receiverAccNumber;
            return this;
        }

        public YpRefundCreationBuilder receiverComment(String receiverComment) {
            this.receiverComment = receiverComment;
            return this;
        }

        public YpRefundCreationBuilder receiverFullName(String receiverFullName) {
            this.receiverFullName = receiverFullName;
            return this;
        }

        public YpRefundCreationBuilder receiverSortCode(String receiverSortCode) {
            this.receiverSortCode = receiverSortCode;
            return this;
        }

        public String toString() {
            return "YpRefundCreation.YpRefundCreationBuilder(urlPaymentRefundResult=" + this.urlPaymentRefundResult + ", yottaTransactionId=" + this.yottaTransactionId + ", receiverFullName=" + this.receiverFullName + ", receiverAccNumber=" + this.receiverAccNumber + ", receiverSortCode=" + this.receiverSortCode + ", receiverComment=" + this.receiverComment + ")";
        }

        public YpRefundCreationBuilder urlPaymentRefundResult(String urlPaymentRefundResult) {
            this.urlPaymentRefundResult = urlPaymentRefundResult;
            return this;
        }

        public YpRefundCreationBuilder yottaTransactionId(String yottaTransactionId) {
            this.yottaTransactionId = yottaTransactionId;
            return this;
        }

        private String validateNotEmpty(String object, String message) {
            if (validateNotNull(object, message).isEmpty()) {
                throw new IllegalArgumentException(message);
            }

            return object;
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

    }
}
