package com.shareride.ridebooking.utils;

public class Constants {

    public static final String GRANT_TYPE = "grant_type";
    public static final String CLIENT_ID = "client_id";
    public static final String CLIENT_SECRET = "client_secret";
    public static final String SCOPE = "scope";
    public static final String AUTHORIZATION = "Authorization";
    public static final String BEARER_ = "Bearer ";
    public static final String BEARER = "Bearer";
    public static final String ROLES = "roles";
    public static final String JWT_EXCEPTION = "JWTException";
    public static final String EMPTY = "";
    public static final String CAUSE = "cause";
    public static final String REASON = "reason";
    public static final String SUCCESS = "success";


    public static final class Properties {
        public static final String APP_BASE_URL = "${app.base.url}";
        public static final String ROLE_USER = "${roles.user}";
        public static final String ROLE_ADMIN = "${roles.admin}";
        public static final String ROLE_DRIVER = "${roles.driver}";
        public static final String OLAMAPS_CLIENT_ID = "${ola-maps.auth.client-id}";
        public static final String OLAMAPS_CLIENT_SECRET = "${ola-maps.auth.client-secret}";
        public static final String OLAMAPS_GRANT_TYPE = "${ola-maps.auth.grant-type}";
        public static final String OLAMAPS_SCOPE = "${ola-maps.auth.scope}";
        public static final String OLAMAPS_API_BASE_URL = "${ola-maps.api.base-url}";
        public static final String JWT_SECRET_KEY = "${jwt.secret.key}";

    }

    public static final class Routes {
        public static final String API_V1 = "/api/v1";
        public static final String AUTH = "/auth";
        public static final String MAPS = "/maps";
        public static final String ADMIN = "/admin";
        public static final String RIDES = "/rides";
        public static final String CLEANUP = "/cleanup";
        public static final String VEHICLES = "/vehicles";
        public static final String REGISTER = "/register";
        public static final String GET = "/get";
        public static final String CREATE = "/create";
        public static final String UPDATE_RIDE = "/update/{rideId}";
        public static final String SEARCH = "/search";
        public static final String RIDE_REQUEST = "/rides/{rideId}/requests";
        public static final String UPDATE_RIDE_REQUEST = "/rides/{rideId}/requests/{requestId}";
        public static final String OLAMAPS_TOKEN = "/olamaps/token";
        public static final String PLACES = "/places";
        public static final String GEOCODE = "/geocode";
        public static final String REVERSE_GEOCODE = "/reverse-geocode";
        public static final String ROUTING = "/routing";
        public static final String DIRECTIONS = "/directions";
    }
}
