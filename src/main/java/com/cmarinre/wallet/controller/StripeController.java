package com.cmarinre.wallet.controller;

import static com.stripe.net.ApiResource.GSON;

import com.cmarinre.wallet.dtos.request.CreatePaymentRequest;
import com.cmarinre.wallet.exception.ValidationException;
import com.cmarinre.wallet.service.StripeService;
import com.cmarinre.wallet.service.WalletService;
import com.google.gson.JsonSyntaxException;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.exception.StripeException;
import com.stripe.model.Event;
import com.stripe.model.EventDataObjectDeserializer;
import com.stripe.model.PaymentIntent;
import com.stripe.model.PaymentLink;
import com.stripe.model.StripeObject;
import com.stripe.model.Subscription;
import com.stripe.net.ApiResource;
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
@RequestMapping("/api/v1/stripe")
@Slf4j
public class StripeController {

  @Value("${stripe.secret-key}")
  private String stripeSecretKey;

  @Value("${stripe.endpoint-secret}")
  private String endpointSecret;

  private final WalletService walletService;
  private final StripeService stripeService;


  @PostMapping(path = "/webhook2")
  public ResponseEntity<?> createPaymentLink(){
    return null;
  }

  @PostMapping(path = "/create-payment-link")
  public ResponseEntity<PaymentLink> createPaymentConfirmLink(@RequestBody CreatePaymentRequest createPaymentRequest)
      throws StripeException {
    return stripeService.createPaymentLink(createPaymentRequest);
  }



  @PostMapping(path = "/webhook")
  public ResponseEntity<?> handlePostPaymentEvent(@RequestHeader("Stripe-Signature") String sigHeader,
      @RequestBody String payload) throws StripeException {


    Event event = null;
    try {
      event = ApiResource.GSON.fromJson(payload, Event.class);
    } catch (JsonSyntaxException e) {
      log.info("⚠️ Webhook error while parsing basic request.");
    }

    if (stripeSecretKey != null && sigHeader != null) {
      try {
        event = Webhook.constructEvent(payload, sigHeader, endpointSecret);
      } catch (SignatureVerificationException e) {
        log.info("⚠️ Webhook error while validating signature.");
        System.out.println(e);
        return ResponseEntity.badRequest().build();
      }
    }


    if (event != null) {
      EventDataObjectDeserializer dataObjectDeserializer = event.getDataObjectDeserializer();

      StripeObject stripeObject = dataObjectDeserializer.getObject().orElseThrow();

      PaymentIntent paymentIntent;

      switch (event.getType()) {
        case "payment_intent.succeeded":
          paymentIntent = (PaymentIntent) stripeObject;

          log.info(StripeController.class.getName() + " -- Calling WalletService with a payment succeeded");
          stripeService.paymentSucceed(paymentIntent);
          break;
        case "payment_intent.payment_failed":
          paymentIntent = (PaymentIntent) stripeObject;
          break;
        case "payment_intent.canceled":
          paymentIntent = (PaymentIntent) stripeObject;
          break;
        case "customer.subscription.created":
          Subscription subscription = (Subscription) stripeObject;
        default:
          log.info("Unhandled event type: " + event.getType());
          break;
      }
    }

    return ResponseEntity.ok("");
  }



}


