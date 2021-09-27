package com.yotta.sdk.payment.req;

import com.yotta.sdk.core.req.YpAbstractHttpRequest;
import com.yotta.sdk.core.service.YpCloseableHttpClientSupplierService;
import com.yotta.sdk.core.service.YpObjectMapperService;
import com.yotta.sdk.payment.config.YpSdkPaymentConfiguration;
import org.jetbrains.annotations.NotNull;

public abstract class YpAbstractPaymentHttpRequest<T_IN, T_OUT> extends YpAbstractHttpRequest<T_IN, T_OUT> {
    public YpAbstractPaymentHttpRequest(@NotNull String url,
                                        @NotNull YpObjectMapperService objectMapperService,
                                        @NotNull YpCloseableHttpClientSupplierService httpClientSupplier) {
        super(url, objectMapperService, httpClientSupplier);
    }

    @Override
    protected final String getSdkName() {
        return YpSdkPaymentConfiguration.SDK_NAME;
    }
}
