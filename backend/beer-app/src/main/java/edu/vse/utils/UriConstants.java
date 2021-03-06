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

    public static final UriTemplate supplier = new UriTemplate("/api/suppliers/{id}");
    public static final UriTemplate suppliers = new UriTemplate("/api/suppliers");

    public static final UriTemplate product = new UriTemplate("/api/products/{id}");
    public static final UriTemplate products = new UriTemplate("/api/products");
    public static final UriTemplate suppliersProducts = new UriTemplate("/api/products?supplier={id}");

    public static final UriTemplate wrappings = new UriTemplate("/api/wrappings");
    public static final UriTemplate wrapping = new UriTemplate("/api/wrappings/{id}");

    public static final UriTemplate _package = new UriTemplate("/api/packages/{id}");
    public static final UriTemplate ordersPackages = new UriTemplate("/api/packages?order={id}");

    public static final UriTemplate order = new UriTemplate("/api/orders/{id}");

    public static final UriTemplate category = new UriTemplate("/api/categories/{id}");
    public static final UriTemplate categories = new UriTemplate("/api/categories");
    public static final UriTemplate subCategories = new UriTemplate("/api/categories?mainCategory={id}");

    public static final UriTemplate image = new UriTemplate("/images/{id}");

    public static final UriTemplate shipping = new UriTemplate("/api/shippings/{id}");
    public static final UriTemplate shippings = new UriTemplate("/api/shippings");
}
