package com.shareride.ridebooking.api.controller;

import com.shareride.ridebooking.client.olamaps.auth.OlaMapsAuthClient;
import com.shareride.ridebooking.client.olamaps.auth.TokenResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.shareride.ridebooking.utils.Constants.Routes.API_V1;
import static com.shareride.ridebooking.utils.Constants.Routes.AUTH;
import static com.shareride.ridebooking.utils.Constants.Routes.OLAMAPS_TOKEN;

@RestController
@RequestMapping(API_V1 + AUTH)
public class OlaMapsAuthController {
    private final OlaMapsAuthClient olamapsAuthClient;

    public OlaMapsAuthController(OlaMapsAuthClient olamapsAuthClient) {
        this.olamapsAuthClient = olamapsAuthClient;
    }

    @GetMapping(OLAMAPS_TOKEN)
    public TokenResponse getToken() {
        return olamapsAuthClient.getTokenResponse();

    }
}
