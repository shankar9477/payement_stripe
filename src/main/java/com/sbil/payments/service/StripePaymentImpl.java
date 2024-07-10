package com.sbil.payments.service;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentLink;
import com.stripe.model.Price;
import com.stripe.param.PaymentLinkCreateParams;
import com.stripe.param.PriceCreateParams;
import org.springframework.stereotype.Service;

@Service
public class StripePaymentImpl implements  PaymentService
{

    @Override
    public String makePayment(String orderId, Long amount) throws StripeException
    {

        Stripe.apiKey = "sk_test_51PTfVK07RUa4Tt5RCtsQZm5VWTc0rg45QF17ff429FqfocyGc5JPiuqesNg3Kc4H0h9exKHH3hXUhSTiR8iKsgXb00Jk8Css26";
        PriceCreateParams params =
                PriceCreateParams.builder()
                        .setCurrency("inr")
                        .setUnitAmount(amount)
                        /*.setRecurring(
                                PriceCreateParams.Recurring.builder()
                                        .setInterval(PriceCreateParams.Recurring.Interval.MONTH)
                                        .build()
                        )*/
                        .setProductData(
                                PriceCreateParams.ProductData.builder().setName(orderId).build()
                        )
                        .build();
        Price price = Price.create(params);

        PaymentLinkCreateParams paymentParams =
                PaymentLinkCreateParams.builder()
                        .addLineItem(
                                PaymentLinkCreateParams.LineItem.builder()
                                        .setPrice(price.getId())
                                        .setQuantity(1L)
                                        .build()
                        )
                        .setAfterCompletion(
                      PaymentLinkCreateParams.AfterCompletion.builder()
                    .setType(PaymentLinkCreateParams.AfterCompletion.Type.REDIRECT)
                    .setRedirect(
                            PaymentLinkCreateParams.AfterCompletion.Redirect.builder()
                                    .setUrl("https://credwise.co.in/")
                                    .build()
                    )
                    .build()
    )
                        .build();

        PaymentLink paymentLink = PaymentLink.create(paymentParams);


        return paymentLink.getUrl();


    }
}
