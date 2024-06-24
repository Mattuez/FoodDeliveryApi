package com.matheus.fooddeliveryapi.api.controller;

import com.matheus.fooddeliveryapi.core.openapi.controllersDocumentation.HostCheckOpenApi;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.InetAddress;
import java.net.UnknownHostException;

@RestController
public class HostCheckController implements HostCheckOpenApi {

    @GetMapping("/host-check")
    public String checkHost() throws UnknownHostException {
        return InetAddress.getLocalHost().getHostAddress()
                + " - " + InetAddress.getLocalHost().getHostName();
    }
}
