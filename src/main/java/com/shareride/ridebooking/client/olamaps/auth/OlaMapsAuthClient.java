package com.shareride.ridebooking.client.olamaps.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.Duration;
import java.time.Instant;

import static com.shareride.ridebooking.utils.Constants.CLIENT_ID;
import static com.shareride.ridebooking.utils.Constants.CLIENT_SECRET;
import static com.shareride.ridebooking.utils.Constants.GRANT_TYPE;
import static com.shareride.ridebooking.utils.Constants.Properties.OLAMAPS_API_BASE_URL;
import static com.shareride.ridebooking.utils.Constants.Properties.OLAMAPS_CLIENT_ID;
import static com.shareride.ridebooking.utils.Constants.Properties.OLAMAPS_CLIENT_SECRET;
import static com.shareride.ridebooking.utils.Constants.Properties.OLAMAPS_GRANT_TYPE;
import static com.shareride.ridebooking.utils.Constants.Properties.OLAMAPS_SCOPE;
import static com.shareride.ridebooking.utils.Constants.SCOPE;

@Component
public class OlaMapsAuthClient {

    private static final Logger logger = LoggerFactory.getLogger(OlaMapsAuthClient.class);

    private final String clientId;
    private final String clientSecret;
    private final String grantType;
    private final String scope;
    private final OlaMapsAuthApi olaMapsAuthApi;

    private TokenResponse cachedToken;
    private Instant tokenExpiryTime;

    public OlaMapsAuthClient(@Value(OLAMAPS_CLIENT_ID) String clientId,
                             @Value(OLAMAPS_CLIENT_SECRET) String clientSecret,
                             @Value(OLAMAPS_GRANT_TYPE) String grantType,
                             @Value(OLAMAPS_SCOPE) String scope,
                             OlaMapsAuthApi olaMapsAuthApi) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.grantType = grantType;
        this.scope = scope;
        this.olaMapsAuthApi = olaMapsAuthApi;
    }

    public synchronized TokenResponse getTokenResponse() {
        if(cachedToken == null || tokenExpiryTime.isBefore(Instant.now().plusSeconds(60))) {
            refreshToken();
        }
        return cachedToken;
    }

    private void refreshToken() {
        MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
        form.add(GRANT_TYPE, grantType);
        form.add(SCOPE, scope);
        form.add(CLIENT_ID, clientId);
        form.add(CLIENT_SECRET, clientSecret);

        this.cachedToken = olaMapsAuthApi.getToken(form);
        this.tokenExpiryTime = extractExpiryFromJwt(cachedToken.getAccessToken());
    }

    private Instant extractExpiryFromJwt(String jwtToken) {
        try {
            DecodedJWT decodedJWT = JWT.decode(jwtToken);
            Instant expiry = decodedJWT.getExpiresAtAsInstant();

            if (expiry == null) {
                // This case means the token is valid but has no 'exp' claim.
                logger.error("JWT is valid but does not contain an 'exp' (expiration) claim. Forcing refresh.");
                return Instant.now();
            }
            long secondsRemaining = Duration.between(Instant.now(), expiry).getSeconds();
            logger.info("Extracted OlaMaps JWT expiry: {})", expiry);
            return expiry;
        } catch (JWTDecodeException e) {
            // This case means the token string itself is malformed.
            logger.error("Failed to decode JWT token.", e);
            return Instant.now();
        }
    }


    @FeignClient(name = "olaMapsAuthApi", url = OLAMAPS_API_BASE_URL)
    public interface OlaMapsAuthApi {

        @PostMapping(value = "/auth/v1/token", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
        TokenResponse getToken(@RequestBody MultiValueMap<String, String> formParams);
    }
}
