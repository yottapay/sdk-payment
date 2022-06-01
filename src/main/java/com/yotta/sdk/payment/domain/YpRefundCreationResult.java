package com.yotta.sdk.payment.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class YpRefundCreationResult {
    @JsonProperty("ok")
    private boolean ok;

    @JsonProperty("error_message")
    private String errorMessage;
}
