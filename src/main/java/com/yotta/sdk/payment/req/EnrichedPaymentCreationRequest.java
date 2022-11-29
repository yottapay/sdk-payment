package com.yotta.sdk.payment.req;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.yotta.sdk.payment.domain.HasSignature;
import com.yotta.sdk.payment.domain.YpPaymentCreation;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
class EnrichedPaymentCreationRequest extends YpPaymentCreation implements HasSignature<EnrichedPaymentCreationRequest> {
    @JsonProperty("merchant_identifier")
    private String merchantIdentifier;

    @JsonProperty("signature")
    private String signature;

    @JsonProperty("type")
    @Setter(AccessLevel.NONE)
    private String type = "creation";

    protected EnrichedPaymentCreationRequest(YpPaymentCreation other) {
        super(other);
    }
}
