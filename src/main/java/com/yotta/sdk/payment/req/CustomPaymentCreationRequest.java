package com.yotta.sdk.payment.req;


import com.yotta.sdk.core.req.YpRequest;
import com.yotta.sdk.payment.config.YpSdkPaymentConfiguration;
import com.yotta.sdk.payment.domain.YpPaymentCreation;
import com.yotta.sdk.payment.domain.YpPaymentCreationResult;
import com.yotta.sdk.payment.sign.impl.YpPaymentCreationSignatureService;

import java.util.function.Function;


public class CustomPaymentCreationRequest {

    private CustomPaymentCreationRequest() {
    }

    public static YpRequest<YpPaymentCreation, YpPaymentCreationResult> make(YpSdkPaymentConfiguration configuration, Function<YpPaymentCreation, YpPaymentCreationResult> processFunction) {

        return new YpRequest<YpPaymentCreation, YpPaymentCreationResult>() {
            private final YpPaymentCreationSignatureService signatureService = YpPaymentCreationSignatureService.createDefault(configuration.getMerchantIdentity(), configuration.getSecret());

            @Override
            public YpPaymentCreationResult sendRequest(YpPaymentCreation input) {
                EnrichedPaymentCreationRequest creationRequest = new EnrichedPaymentCreationRequest(input);
                creationRequest.setMerchantIdentifier(configuration.getMerchantIdentity());
                creationRequest.setSignature(signatureService.encodeSignature(input));
                return processFunction.apply(creationRequest);
            }
        };
    }


}
