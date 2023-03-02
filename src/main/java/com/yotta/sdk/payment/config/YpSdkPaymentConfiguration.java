package com.yotta.sdk.payment.config;

import com.yotta.sdk.core.config.YpSdkConfiguration;
import com.yotta.sdk.core.exception.YpRequiredPropertyException;
import com.yotta.sdk.core.property.YpStringProperty;
import org.jetbrains.annotations.NotNull;

import static com.yotta.sdk.core.config.YpSdkConfiguration.propertyPath;

public interface YpSdkPaymentConfiguration extends YpSdkConfiguration {

    String SDK_NAME = "Yotta Pay SDK (Payments) (1.0.11)";

    static YpSdkPaymentConfiguration createDefault() {
        return new YpSdkPaymentConfigurationImpl();
    }

    @NotNull(exception = YpRequiredPropertyException.class)
    default String getServerBaseUrl()
            throws YpRequiredPropertyException {
        return PaymentProperties.SERVER_BASE_URL.get(this);
    }

    default void setServerBaseUrl(@NotNull(exception = YpRequiredPropertyException.class) String serverBaseUrl)
            throws YpRequiredPropertyException {
        PaymentProperties.SERVER_BASE_URL.set(this, serverBaseUrl);
    }

    @NotNull(exception = YpRequiredPropertyException.class)
    default String getMerchantIdentity()
            throws YpRequiredPropertyException {
        return PaymentProperties.MERCHANT_IDENTITY.get(this);
    }

    default void setMerchantIdentity(@NotNull(exception = YpRequiredPropertyException.class) String merchantIdentity)
            throws YpRequiredPropertyException {
        PaymentProperties.MERCHANT_IDENTITY.set(this, merchantIdentity);
    }

    @NotNull(exception = YpRequiredPropertyException.class)
    default String getSecret()
            throws YpRequiredPropertyException {
        return PaymentProperties.SECRET.get(this);
    }

    default void setSecret(@NotNull(exception = YpRequiredPropertyException.class) String secret)
            throws YpRequiredPropertyException {
        PaymentProperties.SECRET.set(this, secret);
    }

    @NotNull(exception = YpRequiredPropertyException.class)
    default String getCreatePaymentEndpoint()
            throws YpRequiredPropertyException {
        return PaymentProperties.ENDPOINT_CREATE_PAYMENT.get(this);
    }

    @NotNull(exception = YpRequiredPropertyException.class)
    default String getCreateRefundEndpoint()
            throws YpRequiredPropertyException {
        return PaymentProperties.ENDPOINT_CREATE_REFUND.get(this);
    }

    default void setCreatePaymentEndpoint(
            @NotNull(exception = YpRequiredPropertyException.class) String createPaymentEndpoint)
            throws YpRequiredPropertyException {
        PaymentProperties.ENDPOINT_CREATE_PAYMENT.set(this, createPaymentEndpoint);
    }

    default void setCreateRefundEndpoint(
            @NotNull(exception = YpRequiredPropertyException.class) String createRefundEndpoint)
            throws YpRequiredPropertyException {
        PaymentProperties.ENDPOINT_CREATE_REFUND.set(this, createRefundEndpoint);
    }

    class PaymentProperties {
        private static final String PREFIX = propertyPath(YpSdkConfiguration.GLOBAL_PREFIX, "payments");

        public static final YpStringProperty SERVER_BASE_URL = new YpStringProperty(
                propertyPath(PREFIX, "base-url"),
                true,
                "https://prod.yottapay.co.uk/launcher"
        );

        public static final YpStringProperty MERCHANT_IDENTITY = new YpStringProperty(
                propertyPath(PREFIX, "merchant-identity"),
                true
        );

        public static final YpStringProperty SECRET = new YpStringProperty(
                propertyPath(PREFIX, "secret"),
                true
        );

        public static final YpStringProperty ENDPOINT_CREATE_PAYMENT = new YpStringProperty(
                propertyPath(PREFIX, "endpoint.create-payment"),
                true,
                "/shop/paymentgateway/new"
        );

        public static final YpStringProperty ENDPOINT_CREATE_REFUND = new YpStringProperty(
                propertyPath(PREFIX, "endpoint.create-refund"),
                true,
                "/shop/paymentgateway/refund"
        );
    }
}
