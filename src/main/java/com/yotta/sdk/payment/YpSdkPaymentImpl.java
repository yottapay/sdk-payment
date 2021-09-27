package com.yotta.sdk.payment;

import com.yotta.sdk.core.req.YpRequest;
import com.yotta.sdk.payment.domain.YpPaymentCreation;
import com.yotta.sdk.payment.domain.YpPaymentCreationResult;
import com.yotta.sdk.payment.domain.YpPaymentExecutionStatusChangedRequest;
import com.yotta.sdk.payment.sign.YpSignatureService;
import org.jetbrains.annotations.NotNull;

public class YpSdkPaymentImpl implements YpSdkPayment {

    private final YpRequest<YpPaymentCreation, YpPaymentCreationResult> createPaymentRequest;
    private final YpSignatureService<YpPaymentExecutionStatusChangedRequest> paymentStatusChangedSignatureService;

    public YpSdkPaymentImpl(
            YpRequest<YpPaymentCreation, YpPaymentCreationResult> createPaymentRequest,
            YpSignatureService<YpPaymentExecutionStatusChangedRequest> paymentStatusChangedSignatureService) {
        this.createPaymentRequest = createPaymentRequest;
        this.paymentStatusChangedSignatureService = paymentStatusChangedSignatureService;
    }

    @Override
    public @NotNull YpPaymentCreationResult createPayment(@NotNull YpPaymentCreation payment) {
        return createPaymentRequest.sendRequest(payment);
    }

    @Override
    public boolean checkSignature(@NotNull YpPaymentExecutionStatusChangedRequest req) {
        return paymentStatusChangedSignatureService.isSignatureValid(req, req.getSignature());
    }
}
