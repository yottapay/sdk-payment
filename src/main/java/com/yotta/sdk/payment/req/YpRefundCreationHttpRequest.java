package com.yotta.sdk.payment.req;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.yotta.sdk.core.domain.YpServiceResponse;
import com.yotta.sdk.core.exception.YpServiceResponseException;
import com.yotta.sdk.core.service.YpCloseableHttpClientSupplierService;
import com.yotta.sdk.core.service.YpObjectMapperService;
import com.yotta.sdk.payment.domain.HasSignature;
import com.yotta.sdk.payment.domain.YpRefundCreation;
import com.yotta.sdk.payment.domain.YpRefundCreationResult;
import com.yotta.sdk.payment.sign.YpSignatureService;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.apache.http.client.methods.HttpRequestBase;
import org.jetbrains.annotations.NotNull;

public class YpRefundCreationHttpRequest extends YpAbstractPaymentHttpRequest<YpRefundCreation, YpRefundCreationResult> {

    private final String merchantIdentifier;
    private final YpSignatureService<YpRefundCreation> signatureService;


    public YpRefundCreationHttpRequest(@NotNull String url, @NotNull YpObjectMapperService objectMapperService, @NotNull YpCloseableHttpClientSupplierService httpClientSupplier, @NotNull String merchantIdentifier, YpSignatureService<YpRefundCreation> signatureService) {
        super(url, objectMapperService, httpClientSupplier);
        this.merchantIdentifier = merchantIdentifier;
        this.signatureService = signatureService;
    }

    @Override
    protected HttpRequestBase createRequestObject(YpRefundCreation input) {
        return createEmptyPostRequest(input);
    }

    @Override
    protected Object convertToRequestBody(YpRefundCreation input) {
        RefundCreationRequest obj = new RefundCreationRequest(input);

        obj.setMerchantIdentifier(getMerchantIdentifier());

        String signature = getSignatureService().encodeSignature(input);

        obj.setSignature(signature);
        return obj;
    }

    protected String getMerchantIdentifier() {
        return merchantIdentifier;
    }

    protected YpSignatureService<YpRefundCreation> getSignatureService() {
        return signatureService;
    }

    @Override
    protected YpRefundCreationResult onOk(YpRefundCreation input, YpServiceResponse response) throws YpServiceResponseException {
        String responseBody = response.getResponseBody();
        return convertJsonToObject(responseBody, YpRefundCreationResult.class);
    }


    @Getter
    @Setter
    private static class RefundCreationRequest extends YpRefundCreation implements HasSignature<RefundCreationRequest> {
        @JsonProperty("merchant_identifier")
        private String merchantIdentifier;

        @JsonProperty("signature")
        private String signature;

        @JsonProperty("type")
        @Setter(AccessLevel.NONE)
        private String type = "refund";

        protected RefundCreationRequest(YpRefundCreation other) {
            super(other);
        }
    }
}
