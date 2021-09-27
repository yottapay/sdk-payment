package com.yotta.sdk.payment.sign.impl;

import com.yotta.sdk.payment.domain.YpPaymentCreation;
import com.yotta.sdk.payment.sign.YpSignatureEncoder;
import org.jetbrains.annotations.NotNull;

public class YpPaymentCreationSignatureService extends YpAbstractSignatureService<YpPaymentCreation> {

    private final String merchantIdentity;

    public YpPaymentCreationSignatureService(String merchantIdentity,
                                             String secret,
                                             YpSignatureEncoder signatureEncoder) {
        super(secret, signatureEncoder);
        this.merchantIdentity = merchantIdentity;
    }

    public String getMerchantIdentity() {
        return merchantIdentity;
    }

    @Override
    public @NotNull String encodeSignature(@NotNull YpPaymentCreation object) {
        return getSignatureEncoder().encodeSignature(
                object.getMerchantTransactionId(),
                getMerchantIdentity(),
                object.getCustomerId(),
                object.getAmount(),
                object.getCurrency(),
                object.getUrlPaymentResult(),
                object.getUrlPaymentSuccess(),
                object.getUrlPaymentCancel(),
                object.getNotificationId(),
                getSecret()
        );
    }
}
