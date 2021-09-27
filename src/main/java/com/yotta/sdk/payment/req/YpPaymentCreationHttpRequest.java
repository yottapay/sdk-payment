package com.yotta.sdk.payment.req;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.yotta.sdk.core.domain.YpServiceResponse;
import com.yotta.sdk.core.service.YpCloseableHttpClientSupplierService;
import com.yotta.sdk.core.service.YpObjectMapperService;
import com.yotta.sdk.payment.domain.HasSignature;
import com.yotta.sdk.payment.domain.YpPaymentCreation;
import com.yotta.sdk.payment.domain.YpPaymentCreationResult;
import com.yotta.sdk.payment.sign.YpSignatureService;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.apache.http.client.methods.HttpRequestBase;
import org.jetbrains.annotations.NotNull;

public class YpPaymentCreationHttpRequest extends YpAbstractPaymentHttpRequest<YpPaymentCreation, YpPaymentCreationResult> {

    private final String merchantIdentifier;
    private final YpSignatureService<YpPaymentCreation> signatureService;

    public YpPaymentCreationHttpRequest(
            @NotNull String url,
            @NotNull YpObjectMapperService objectMapperService,
            @NotNull YpCloseableHttpClientSupplierService httpClientSupplier,
            @NotNull String merchantIdentifier,
            YpSignatureService<YpPaymentCreation> signatureService) {
        super(url, objectMapperService, httpClientSupplier);
        this.merchantIdentifier = merchantIdentifier;
        this.signatureService = signatureService;
    }

    @Override
    protected HttpRequestBase createRequestObject(YpPaymentCreation input) {
        return createEmptyPostRequest(input);
    }

    protected String getMerchantIdentifier() {
        return merchantIdentifier;
    }

    @Override
    protected Object convertToRequestBody(YpPaymentCreation input) {
        PaymentCreationRequest obj = new PaymentCreationRequest(input);

        obj.setMerchantIdentifier(getMerchantIdentifier());

        String signature = getSignatureService().encodeSignature(input);

        obj.setSignature(signature);
        return obj;
    }

    protected YpSignatureService<YpPaymentCreation> getSignatureService() {
        return signatureService;
    }

    @Override
    protected YpPaymentCreationResult onOk(YpPaymentCreation input, YpServiceResponse response) {
        String responseBody = response.getResponseBody();
        return convertJsonToObject(responseBody, YpPaymentCreationResult.class);
    }

    @Getter
    @Setter
    private static class PaymentCreationRequest extends YpPaymentCreation implements HasSignature<PaymentCreationRequest> {
        @JsonProperty("merchant_identifier")
        private String merchantIdentifier;

        @JsonProperty("signature")
        private String signature;

        @JsonProperty("type")
        @Setter(AccessLevel.NONE)
        private String type = "creation";

        protected PaymentCreationRequest(YpPaymentCreation other) {
            super(other);
        }
    }
}
