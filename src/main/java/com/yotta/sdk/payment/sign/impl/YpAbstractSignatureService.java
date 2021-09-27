package com.yotta.sdk.payment.sign.impl;

import com.yotta.sdk.payment.sign.YpSignatureEncoder;
import com.yotta.sdk.payment.sign.YpSignatureService;
import org.jetbrains.annotations.NotNull;

public abstract class YpAbstractSignatureService<T> implements YpSignatureService<T> {
    private final String secret;
    private final YpSignatureEncoder signatureEncoder;

    public YpAbstractSignatureService(String secret,
                                      YpSignatureEncoder signatureEncoder) {
        this.secret = secret;
        this.signatureEncoder = signatureEncoder;
    }

    protected String getSecret() {
        return secret;
    }

    protected YpSignatureEncoder getSignatureEncoder() {
        return signatureEncoder;
    }

    @Override
    public boolean isSignatureValid(@NotNull T object, @NotNull String expected) {
        return encodeSignature(object).equals(expected);
    }
}
