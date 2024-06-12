package com.backend.BackendJWT.Config;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MercadoPagoConfig {

    @Value("${mercadopago.access_token}")
    private String mercadoLibreToken;

    @PostConstruct
    public void init() {
        com.mercadopago.MercadoPagoConfig.setAccessToken(mercadoLibreToken);
    }
}
