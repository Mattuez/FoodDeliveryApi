package com.matheus.fooddeliveryapi.core.email;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

@Validated
@Setter
@Getter
@Component
@ConfigurationProperties("food-delivery-api.email")
public class EmailProperties {

    @NotNull
    private String sender;

    private SenderImplementation impl = SenderImplementation.SAND_BOX;

    public enum SenderImplementation {
        SMTP, SAND_BOX
    }
}
