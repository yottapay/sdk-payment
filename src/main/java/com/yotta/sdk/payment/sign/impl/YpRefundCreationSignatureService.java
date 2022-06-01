package com.yotta.sdk.payment.sign.impl;

import com.yotta.sdk.payment.domain.YpRefundCreation;
import com.yotta.sdk.payment.sign.YpSignatureEncoder;
import org.jetbrains.annotations.NotNull;

public class YpRefundCreationSignatureService extends YpAbstractSignatureService<YpRefundCreation> {

    private final String merchantIdentity;

    public YpRefundCreationSignatureService(String merchantIdentity, String secret, YpSignatureEncoder signatureEncoder) {
        super(secret, signatureEncoder);
        this.merchantIdentity = merchantIdentity;
    }

    public String getMerchantIdentity() {
        return merchantIdentity;
    }

    @Override
    public @NotNull String encodeSignature(@NotNull YpRefundCreation object) {
        return getSignatureEncoder().encodeSignature(
                getMerchantIdentity(),
                object.getUrlPaymentRefundResult(),
                object.getYottaTransactionId(),
                object.getReceiverFullName(),
                object.getReceiverAccNumber(),
                object.getReceiverSortCode(),
                object.getReceiverComment(),
                getSecret()
        );
    }
}
