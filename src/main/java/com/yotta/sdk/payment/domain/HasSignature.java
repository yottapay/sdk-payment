package com.yotta.sdk.payment.domain;

public interface HasSignature<T extends HasSignature<T>> {
    String getSignature();

    void setSignature(String signature);
}
