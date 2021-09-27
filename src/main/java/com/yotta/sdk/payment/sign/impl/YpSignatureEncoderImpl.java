package com.yotta.sdk.payment.sign.impl;

import com.google.common.hash.Hashing;
import com.yotta.sdk.payment.sign.YpSignatureEncoder;

import java.nio.charset.StandardCharsets;

public class YpSignatureEncoderImpl implements YpSignatureEncoder {
    @SuppressWarnings("UnstableApiUsage")
    @Override
    public String encodeSignature(String raw) {
        return Hashing.sha256().hashString(raw, StandardCharsets.UTF_8).toString();
    }
}
