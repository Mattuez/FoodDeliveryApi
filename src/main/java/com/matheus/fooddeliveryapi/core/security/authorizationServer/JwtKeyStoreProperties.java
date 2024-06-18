package com.matheus.fooddeliveryapi.core.security.authorizationServer;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

@Component
@Validated
@Getter
@Setter
@ConfigurationProperties("food-delivery.jwt.keystore")
public class JwtKeyStoreProperties {

    @NotNull
    private Resource resource;
    @NotNull
    private String pass;
    @NotNull
    private String alias;
}
