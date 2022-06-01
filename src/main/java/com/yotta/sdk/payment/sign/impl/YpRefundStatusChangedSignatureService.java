package com.yotta.sdk.payment.sign.impl;

import com.yotta.sdk.payment.domain.YpPaymentExecutionStatusChangedRequest;
import com.yotta.sdk.payment.domain.YpRefundStatusChangedRequest;
import com.yotta.sdk.payment.sign.YpSignatureEncoder;
import org.jetbrains.annotations.NotNull;

public class YpRefundStatusChangedSignatureService extends YpAbstractSignatureService<YpRefundStatusChangedRequest> {

    public YpRefundStatusChangedSignatureService(String secret,
                                                 YpSignatureEncoder signatureEncoder) {
        super(secret, signatureEncoder);
    }

    @Override
    public @NotNull String encodeSignature(@NotNull YpRefundStatusChangedRequest object) {
        return getSignatureEncoder().encodeSignature(
                object.getYottaTransactionId(),
                object.getMerchantTransactionId(),
                object.getMerchantIdentifier(),
                object.getCustomerId(),
                getSecret()
        );
    }
}
