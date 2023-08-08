package com.yotta.sdk.payment.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class YpPaymentExecutionStatusChangedRequest implements HasSignature<YpPaymentExecutionStatusChangedRequest> {
    private static final String TYPE = "statuschange";

    @JsonProperty("yottapay_transaction_identifier")
    private String yottaTransactionId;

    @JsonProperty("shop_transaction_identifier")
    private String merchantTransactionId;

    @JsonProperty("merchant_identifier")
    private String merchantIdentifier;

    @JsonProperty("customer_identifier")
    private String customerId;

    @JsonProperty("amount")
    private String amount;

    @JsonProperty("loyalty_amount")
    private String loyaltyAmount;

    @JsonProperty("discount_amount")
    private String discountAmount;

    @JsonProperty("currency")
    private String currency = "GBP";

    @JsonProperty("response_code")
    private String responseCode;

    @JsonProperty("signature")
    private String signature;

    @JsonIgnore
    public String getType() {
        return TYPE;
    }
}
