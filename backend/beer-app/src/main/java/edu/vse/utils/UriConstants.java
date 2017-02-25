package edu.vse.utils;

import org.springframework.web.util.UriTemplate;

public class UriConstants {

    public static final UriTemplate user = new UriTemplate("/api/users/{id}");
    public static final UriTemplate userAddresses = new UriTemplate("/api/addresses?user={id}");
    public static final UriTemplate userOrders = new UriTemplate("/api/orders?user={id}");
    public static final UriTemplate userRoles = new UriTemplate("/api/roles?user={id}");

    public static final UriTemplate roles = new UriTemplate("/api/roles");
    public static final UriTemplate role = new UriTemplate("/api/roles/{id}");

    public static final UriTemplate address = new UriTemplate("/api/addresses/{id}");

    public static final UriTemplate country = new UriTemplate("/api/countries/{id}");

}
