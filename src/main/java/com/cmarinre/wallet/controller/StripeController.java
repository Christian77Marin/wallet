package com.cmarinre.wallet.controller;

import static com.stripe.net.ApiResource.GSON;

import com.cmarinre.wallet.exception.ValidationException;
import com.cmarinre.wallet.service.WalletService;
import com.google.gson.JsonSyntaxException;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.model.Event;
import com.stripe.model.PaymentIntent;
import com.stripe.model.StripeObject;
import com.stripe.net.Webhook;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/")
@Slf4j
public class StripeController {

  @Value("${stripe.secret-key}")
  private String stripeSecretKey;

  private final WalletService walletService;

  @PostMapping(path = "/webhook")
  private ResponseEntity<Object> handlePostPaymentEvent(@RequestHeader("Stripe-Signature") String sigHeader,
      @RequestBody String payload) {


    Event event = null;
    try {
      event = GSON.fromJson(payload, Event.class);
    } catch (JsonSyntaxException e) {
      log.info("⚠️ Webhook error while parsing basic request.");
    }

    if (stripeSecretKey != null && sigHeader != null) {
      try {
        event = Webhook.constructEvent(payload, sigHeader, stripeSecretKey);
      } catch (SignatureVerificationException e) {
        log.info("⚠️ Webhook error while validating signature.");
        return ResponseEntity.badRequest().build();
      }
    }

    if (event != null) {

      StripeObject stripeObject = event.getDataObjectDeserializer().getObject().orElseThrow(
          () -> new ValidationException("Null Stripe Object")
      );

      PaymentIntent paymentIntent;

      switch (event.getType()) {
        case "payment_intent.succeeded":
          paymentIntent = (PaymentIntent) stripeObject;

          log.info(StripeController.class.getName() + " -- Calling WalletService with a payment succeeded");
          walletService.paymentSucceed(paymentIntent.getClientSecret(), paymentIntent.getAmount());
          break;
        case "payment_intent.payment_failed":
          paymentIntent = (PaymentIntent) stripeObject;
          break;
        case "payment_intent.canceled":
          paymentIntent = (PaymentIntent) stripeObject;
          break;
        default:
          log.info("Unhandled event type: " + event.getType());
          break;
      }
    }

    return ResponseEntity.ok("");
  }

}
