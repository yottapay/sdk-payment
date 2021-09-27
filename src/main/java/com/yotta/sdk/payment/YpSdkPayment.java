package com.yotta.sdk.payment;

import com.yotta.sdk.core.service.YpCloseableHttpClientSupplierService;
import com.yotta.sdk.core.service.YpObjectMapperService;
import com.yotta.sdk.payment.config.YpSdkPaymentConfiguration;
import com.yotta.sdk.payment.domain.YpPaymentCreation;
import com.yotta.sdk.payment.domain.YpPaymentCreationResult;
import com.yotta.sdk.payment.domain.YpPaymentExecutionStatusChangedRequest;
import com.yotta.sdk.payment.req.YpPaymentCreationHttpRequest;
import com.yotta.sdk.payment.sign.YpSignatureEncoder;
import com.yotta.sdk.payment.sign.impl.YpPaymentCreationSignatureService;
import com.yotta.sdk.payment.sign.impl.YpPaymentExecutionStatusChangedSignatureService;
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
                new YpPaymentExecutionStatusChangedSignatureService(
                        secret,
                        signatureEncoder
                )
        );
    }

    @NotNull YpPaymentCreationResult createPayment(@NotNull YpPaymentCreation payment);

    boolean checkSignature(@NotNull YpPaymentExecutionStatusChangedRequest paymentExecutionStatusChanged);
}
