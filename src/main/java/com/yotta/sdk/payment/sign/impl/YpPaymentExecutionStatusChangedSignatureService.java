package com.yotta.sdk.payment.sign.impl;

import com.yotta.sdk.payment.domain.YpPaymentExecutionStatusChangedRequest;
import com.yotta.sdk.payment.sign.YpSignatureEncoder;
import org.jetbrains.annotations.NotNull;

public class YpPaymentExecutionStatusChangedSignatureService extends YpAbstractSignatureService<YpPaymentExecutionStatusChangedRequest> {

    public YpPaymentExecutionStatusChangedSignatureService(String secret,
                                                           YpSignatureEncoder signatureEncoder) {
        super(secret, signatureEncoder);
    }

    @Override
    public @NotNull String encodeSignature(@NotNull YpPaymentExecutionStatusChangedRequest object) {
        return getSignatureEncoder().encodeSignature(
                object.getYottaTransactionId(),
                object.getMerchantTransactionId(),
                object.getMerchantIdentifier(),
                object.getCustomerId(),
                object.getAmount(),
                object.getCurrency(),
                object.getResponseCode(),
                getSecret()
        );
    }
}
