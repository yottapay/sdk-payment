package com.yotta.sdk.payment.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class YpPaymentCreationResult {
    @JsonProperty("url_process_payment_intent")
    private String urlProcessPaymentIntent;

    @JsonProperty("yottapay_transaction_identifier")
    private String yottaTransactionId;

    @JsonProperty("qr_code_url")
    private String qrCodeUrl;
}
