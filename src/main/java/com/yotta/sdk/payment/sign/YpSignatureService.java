package com.yotta.sdk.payment.sign;

import org.jetbrains.annotations.NotNull;

public interface YpSignatureService<T> {
    @NotNull String encodeSignature(@NotNull T object);

    boolean isSignatureValid(@NotNull T object, @NotNull String expected);
}
