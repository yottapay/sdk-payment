package com.yotta.sdk.payment.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class YpRefundStatusChangedRequest  implements HasSignature<YpRefundStatusChangedRequest>{
    private static final String TYPE = "refundcompleted";

    @JsonProperty("yottapay_transaction_identifier")
    private String yottaTransactionId;

    @JsonProperty("shop_transaction_identifier")
    private String merchantTransactionId;

    @JsonProperty("merchant_identifier")
    private String merchantIdentifier;

    @JsonProperty("customer_identifier")
    private String customerId;

    @JsonProperty("signature")
    private String signature;

}
