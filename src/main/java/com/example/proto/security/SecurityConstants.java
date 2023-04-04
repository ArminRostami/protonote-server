package com.example.proto.security;

public final class SecurityConstants {

    public static final String AUTH_LOGIN_URL = "/users/login";
    public static final String AUTH_SIGNUP_URL = "/users/sign-up";

    // Signing key for HS512 algorithm
    public static final String JWT_SECRET = "!z%C*F-JaNdRgUkXp2r5u8x/A?D(G+KbPeShVmYq3t6v9y$B&E)H@McQfTjWnZr4";

    // JWT token defaults
    public static final long EXPIRATION_TIME = 864_000_000;
    public static final String TOKEN_HEADER = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String TOKEN_TYPE = "JWT";
}