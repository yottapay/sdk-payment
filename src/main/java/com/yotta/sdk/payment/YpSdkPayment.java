package com.yotta.sdk.payment;

import com.yotta.sdk.core.req.YpRequest;
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

import java.util.Objects;

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

    class Builder {

        private final YpSdkPaymentConfiguration configuration;
        private String merchantId;
        private String merchantSecret;

        private YpSignatureEncoder signatureEncoder = YpSignatureEncoder.DEFAULT;

        public @NotNull Builder signatureEncoder(@NotNull YpSignatureEncoder signatureEncoder) {
            this.signatureEncoder = signatureEncoder;
            return this;
        }
        private YpObjectMapperService objectMapperService = YpObjectMapperService.DEFAULT;

        public @NotNull Builder objectMapperService(@NotNull YpObjectMapperService objectMapperService) {
            this.objectMapperService = objectMapperService;
            return this;
        }

        private YpCloseableHttpClientSupplierService clientSupplierService = YpCloseableHttpClientSupplierService.DEFAULT;

        public @NotNull Builder clientSupplierService(@NotNull YpCloseableHttpClientSupplierService clientSupplierService) {
            this.clientSupplierService = clientSupplierService;
            return this;
        }

        private YpRequest<YpPaymentCreation, YpPaymentCreationResult> paymentCreationRequest;
        public @NotNull Builder paymentCreationRequest(@NotNull YpRequest<YpPaymentCreation, YpPaymentCreationResult> paymentCreationRequest) {
            this.paymentCreationRequest = paymentCreationRequest;
            return this;
        }

        private YpRequest<YpRefundCreation, YpRefundCreationResult> refundCreationRequest;
        public @NotNull Builder refundCreationRequest(@NotNull YpRequest<YpRefundCreation, YpRefundCreationResult> refundCreationRequest) {
            this.refundCreationRequest = refundCreationRequest;
            return this;
        }

        private YpPaymentExecutionStatusChangedSignatureService executionStatusChangedSignatureService;

        public @NotNull Builder executionStatusChangedSignatureService(@NotNull YpPaymentExecutionStatusChangedSignatureService executionStatusChangedSignatureService) {
            this.executionStatusChangedSignatureService = executionStatusChangedSignatureService;
            return this;
        }

        private YpRefundStatusChangedSignatureService refundStatusChangedSignatureService;

        public @NotNull Builder refundStatusChangedSignatureService(@NotNull YpRefundStatusChangedSignatureService refundStatusChangedSignatureService) {
            this.refundStatusChangedSignatureService = refundStatusChangedSignatureService;
            return this;
        }


        public YpSdkPayment build() {

            Objects.requireNonNull(merchantId, "You must set merchantId first.");
            Objects.requireNonNull(merchantSecret, "You must set merchantSecret first.");

            if(paymentCreationRequest == null) {
                paymentCreationRequest = new YpPaymentCreationHttpRequest(
                        configuration.getServerBaseUrl() + configuration.getCreatePaymentEndpoint(),
                        objectMapperService,
                        clientSupplierService,
                        merchantId,
                        new YpPaymentCreationSignatureService(
                                merchantId,
                                merchantSecret,
                                signatureEncoder
                        )
                );
            }

            if(refundCreationRequest == null) {
                refundCreationRequest = new YpRefundCreationHttpRequest(
                        configuration.getServerBaseUrl() + configuration.getCreateRefundEndpoint(),
                        objectMapperService,
                        clientSupplierService,
                        merchantId,
                        new YpRefundCreationSignatureService(
                                merchantId,
                                merchantSecret,
                                signatureEncoder
                        )
                );
            }

            if(executionStatusChangedSignatureService == null) {
                executionStatusChangedSignatureService = new YpPaymentExecutionStatusChangedSignatureService(
                        merchantSecret,
                        signatureEncoder
                );
            }

            if(refundStatusChangedSignatureService == null) {
                refundStatusChangedSignatureService = new YpRefundStatusChangedSignatureService(
                        merchantSecret,
                        signatureEncoder
                );
            }
            return new YpSdkPaymentImpl(
                    paymentCreationRequest,
                    refundCreationRequest,
                    executionStatusChangedSignatureService,
                    refundStatusChangedSignatureService
            );
        }


        public @NotNull Builder merchantId(@NotNull String merchantId) {
            this.merchantId = merchantId;
            return this;
        }

        public @NotNull Builder merchantSecret(@NotNull String merchantSecret) {
            this.merchantSecret = merchantSecret;
            return this;
        }



        public Builder() {
            this(YpSdkPaymentConfiguration.createDefault());
        }
        public Builder(@NotNull YpSdkPaymentConfiguration configuration) {
            this.configuration = configuration;
        }
    }

}
