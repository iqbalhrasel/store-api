package com.pxc.store_api.payments;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CheckoutSession {
    private String checkoutUrl;
}
