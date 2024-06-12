package com.backend.BackendJWT.Controllers.Shop;


import com.backend.BackendJWT.Models.Shop.Payment.UserBuyer;
import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.preference.PreferenceBackUrlsRequest;
import com.mercadopago.client.preference.PreferenceClient;
import com.mercadopago.client.preference.PreferenceItemRequest;
import com.mercadopago.client.preference.PreferenceRequest;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.preference.Preference;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/pago")
public class MercadoPagoController {

    @PostMapping("/mp")
    public ResponseEntity<String> createPreference(@RequestBody UserBuyer userBuyer) {
        if (userBuyer == null) {
            return ResponseEntity.badRequest().body("Error: JSON de entrada inválido.");
        }

        String title = userBuyer.getTitle();
        int quantity = userBuyer.getQuantity();
        int price = userBuyer.getUnit_price();

        try {
            PreferenceItemRequest itemRequest = PreferenceItemRequest.builder()
                    .title(title)
                    .quantity(quantity)
                    .unitPrice(new BigDecimal(price))
                    .currencyId("CLP")
                    .build();
            List<PreferenceItemRequest> items = new ArrayList<>();
            items.add(itemRequest);

            PreferenceBackUrlsRequest backUrls = PreferenceBackUrlsRequest.builder()
                    .success("https://www.success.com")
                    .pending("https://www.pending.com")
                    .failure("https://www.failure.com")
                    .build();

            PreferenceRequest preferenceRequest = PreferenceRequest.builder()
                    .items(items)
                    .backUrls(backUrls)
                    .build();

            PreferenceClient client = new PreferenceClient();
            Preference preference = client.create(preferenceRequest);

            return ResponseEntity.ok(preference.getId());
        } catch (MPException | MPApiException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor: " + e.getMessage());
        }
    }
}



