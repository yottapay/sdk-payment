package com.yotta.sdk.payment.sign;

import com.yotta.sdk.payment.sign.impl.YpSignatureEncoderImpl;

public interface YpSignatureEncoder {

    YpSignatureEncoder DEFAULT = new YpSignatureEncoderImpl();

    String encodeSignature(String raw);

    default String encodeSignature(String... parts) {
        return encodeSignature(String.join("", parts));
    }
}
