package com.yotta.sdk.payment;

import com.yotta.sdk.core.service.YpCloseableHttpClientSupplierService;
import com.yotta.sdk.core.service.YpObjectMapperService;
import com.yotta.sdk.payment.config.YpSdkPaymentConfiguration;
import com.yotta.sdk.payment.domain.*;
import com.yotta.sdk.payment.req.YpPaymentCreationHttpRequest;
import com.yotta.sdk.payment.req.YpRefundCreationHttpRequest;
import com.yotta.sdk.payment.sign.YpSignatureEncoder;
import com.yotta.sdk.payment.sign.impl.YpPaymentCreationSignatureService;
import com.yotta.sdk.payment.sign.impl.YpPaymentExecutionStatusChangedSignatureService;
import com.yotta.sdk.payment.sign.impl.YpRefundCreationSignatureService;
import com.yotta.sdk.payment.sign.impl.YpRefundStatusChangedSignatureService;
import org.jetbrains.annotations.NotNull;

public interface YpSdkPayment {
    static @NotNull YpSdkPayment createDefault(@NotNull String merchantIdentifier, @NotNull String secret) {
        YpSdkPaymentConfiguration configuration = YpSdkPaymentConfiguration.createDefault();

        configuration.setMerchantIdentity(merchantIdentifier);
        configuration.setSecret(secret);

        return createFromConfiguration(configuration);
    }

    static @NotNull YpSdkPayment createFromConfiguration(@NotNull YpSdkPaymentConfiguration configuration) {
        String merchantIdentity = configuration.getMerchantIdentity();
        String secret = configuration.getSecret();

        String serverBaseUrl = configuration.getServerBaseUrl();
        String createPaymentEndpoint = configuration.getCreatePaymentEndpoint();
        String createRefundEndpoint = configuration.getCreateRefundEndpoint();


        YpSignatureEncoder signatureEncoder = YpSignatureEncoder.DEFAULT;
        YpObjectMapperService objectMapperService = YpObjectMapperService.DEFAULT;
        YpCloseableHttpClientSupplierService clientSupplierService = YpCloseableHttpClientSupplierService.DEFAULT;

        return new YpSdkPaymentImpl(
                new YpPaymentCreationHttpRequest(
                        serverBaseUrl + createPaymentEndpoint,
                        objectMapperService,
                        clientSupplierService,
                        merchantIdentity,
                        new YpPaymentCreationSignatureService(
                                merchantIdentity,
                                secret,
                                signatureEncoder
                        )
                ),

                new YpRefundCreationHttpRequest(
                        serverBaseUrl + createRefundEndpoint,
                        objectMapperService,
                        clientSupplierService,
                        merchantIdentity,
                        new YpRefundCreationSignatureService(
                                merchantIdentity,
                                secret,
                                signatureEncoder
                        )
                ),

                new YpPaymentExecutionStatusChangedSignatureService(
                        secret,
                        signatureEncoder
                ),

                new YpRefundStatusChangedSignatureService(
                        secret,
                        signatureEncoder
                )
        );
    }

    @NotNull YpPaymentCreationResult createPayment(@NotNull YpPaymentCreation payment);

    @NotNull YpRefundCreationResult createRefundRequest(@NotNull YpRefundCreation refundCreation);

    boolean checkSignature(@NotNull YpPaymentExecutionStatusChangedRequest paymentExecutionStatusChanged);

    boolean checkSignature(@NotNull YpRefundStatusChangedRequest refundStatusChangedRequest);
}
