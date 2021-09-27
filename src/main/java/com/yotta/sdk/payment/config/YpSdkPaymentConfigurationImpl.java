package com.yotta.sdk.payment.config;

import com.yotta.sdk.core.config.YpSdkAbstractConfiguration;
import org.jetbrains.annotations.Nullable;

public class YpSdkPaymentConfigurationImpl extends YpSdkAbstractConfiguration implements YpSdkPaymentConfiguration {

    @Override
    protected @Nullable Class<?>[] getPropertiesUtilityClasses() {
        return new Class[]{PaymentProperties.class};
    }
}
