package com.yotta.sdk.payment.sign.impl;

import com.yotta.sdk.payment.domain.YpPaymentCreation;
import com.yotta.sdk.payment.sign.YpSignatureEncoder;
import org.jetbrains.annotations.NotNull;

public class YpPaymentCreationSignatureService extends YpAbstractSignatureService<YpPaymentCreation> {

    public static YpPaymentCreationSignatureService createDefault(String merchantId, String secret) {
        return new YpPaymentCreationSignatureService(
                merchantId,
                secret,
                YpSignatureEncoder.DEFAULT
        );
    }

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
                object.getReference(),
                getMerchantIdentity(),
                object.getCustomerId(),
                object.getAmount(),
                object.getDuration(),
                object.getCurrency(),
                object.getUrlPaymentResult(),
                object.getUrlPaymentSuccess(),
                object.getUrlPaymentCancel(),
                object.getNotificationId(),
                object.getTerminalCode(),
                object.getCancellationUrl(),
                getSecret()
        );
    }
}
