package com.yotta.sdk.payment;

import com.yotta.sdk.core.req.YpRequest;
import com.yotta.sdk.payment.domain.*;
import com.yotta.sdk.payment.sign.YpSignatureService;
import org.jetbrains.annotations.NotNull;

public class YpSdkPaymentImpl implements YpSdkPayment {

    private final YpRequest<YpPaymentCreation, YpPaymentCreationResult> createPaymentRequest;
    private final YpRequest<YpRefundCreation, YpRefundCreationResult> createRefundRequest;
    private final YpSignatureService<YpPaymentExecutionStatusChangedRequest> paymentStatusChangedSignatureService;
    private final YpSignatureService<YpRefundStatusChangedRequest> refundStatusChangedSignatureService;

    public YpSdkPaymentImpl(YpRequest<YpPaymentCreation, YpPaymentCreationResult> createPaymentRequest,
                            YpRequest<YpRefundCreation, YpRefundCreationResult> createRefundRequest,
                            YpSignatureService<YpPaymentExecutionStatusChangedRequest> paymentStatusChangedSignatureService,
                            YpSignatureService<YpRefundStatusChangedRequest> refundStatusChangedSignatureService) {
        this.createPaymentRequest = createPaymentRequest;
        this.createRefundRequest = createRefundRequest;
        this.paymentStatusChangedSignatureService = paymentStatusChangedSignatureService;
        this.refundStatusChangedSignatureService = refundStatusChangedSignatureService;
    }



    @Override
    public @NotNull YpPaymentCreationResult createPayment(@NotNull YpPaymentCreation payment) {
        return createPaymentRequest.sendRequest(payment);
    }

    @Override
    public @NotNull YpRefundCreationResult createRefundRequest(@NotNull YpRefundCreation refundCreation) {
        return createRefundRequest.sendRequest(refundCreation);
    }

    @Override
    public boolean checkSignature(@NotNull YpPaymentExecutionStatusChangedRequest req) {
        return paymentStatusChangedSignatureService.isSignatureValid(req, req.getSignature());
    }

    @Override
    public boolean checkSignature(@NotNull YpRefundStatusChangedRequest req) {
        return refundStatusChangedSignatureService.isSignatureValid(req, req.getSignature());
    }
}
