package com.matheus.fooddeliveryapi.core.security;

import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

public @interface CheckSecurity {

    public @interface Cuisines {

        @PreAuthorize("hasAuthority('SCOPE_WRITE') and hasAnyAuthority('EDIT_CUISINES')")
        @Retention(RUNTIME)
        @Target(METHOD)
        public @interface CanEdit{}

        @PreAuthorize("hasAuthority('SCOPE_READ') and isAuthenticated()")
        @Retention(RUNTIME)
        @Target(METHOD)
        public @interface CanView{}
    }

    public @interface Restaurant {

        @PreAuthorize("hasAuthority('SCOPE_WRITE') and hasAuthority('MANAGE_RESTAURANTS')")
        @Retention(RUNTIME)
        @Target(METHOD)
        public @interface canManageAdministration {}

        @PreAuthorize("hasAuthority('SCOPE_WRITE') and " +
                "(hasAuthority('MANAGE_RESTAURANTS') or @authenticationSecurity.manageRestaurant(#restaurantId))")
        @Retention(RUNTIME)
        @Target(METHOD)
        public @interface canManageOperation {}

        @PreAuthorize("hasAuthority('SCOPE_READ') and isAuthenticated()")
        @Retention(RUNTIME)
        @Target(METHOD)
        public @interface canView {}
    }

    public @interface Order {

        @PreAuthorize("hasAuthority('SCOPE_READ') and " +
                "(hasAuthority('VIEW_ORDERS') or @authenticationSecurity.manageRestaurant(#orderFilter.restaurantId) or" +
                "@authenticationSecurity.hasOrder(#orderFilter.clientId))")
        @Retention(RUNTIME)
        @Target(METHOD)
        public @interface canList {}

        @PreAuthorize("hasAuthority('SCOPE_READ') and isAuthenticated()")
        @PostAuthorize("hasAuthority('VIEW_ORDERS') or @authenticationSecurity.userId == returnObject.client.id or" +
                "@authenticationSecurity.manageRestaurant(returnObject.restaurant.id)")
        @Retention(RUNTIME)
        @Target(METHOD)
        public @interface canSearch {}

        @PreAuthorize("hasAuthority('SCOPE_WRITE') and " +
                "(hasAuthority('MANAGE_ORDERS') or @authenticationSecurity.isAdminFromRestaurantOrder(#code))")
        @Retention(RUNTIME)
        @Target(METHOD)
        public @interface canManage {}

        @PreAuthorize("hasAuthority('SCOPE_WRITE') and isAuthenticated()")
        @Retention(RUNTIME)
        @Target(METHOD)
        public @interface canCreate {}
    }

    public @interface PaymentMethod {

        @PreAuthorize("hasAuthority('SCOPE_READ') and isAuthenticated()")
        @Retention(RUNTIME)
        @Target(METHOD)
        public @interface canView {}

        @PreAuthorize("hasAuthority('SCOPE_WRITE') and hasAuthority('UPDATE_PAYMENT_METHODS')")
        @Retention(RUNTIME)
        @Target(METHOD)
        public @interface canManage {}
    }

    public @interface City {

        @PreAuthorize("hasAuthority('SCOPE_READ') and isAuthenticated()")
        @Retention(RUNTIME)
        @Target(METHOD)
        public @interface canView {}

        @PreAuthorize("hasAuthority('SCOPE_WRITE') and hasAuthority('EDIT_CITIES')")
        @Retention(RUNTIME)
        @Target(METHOD)
        public @interface canManage {}
    }

    public @interface State {

        @PreAuthorize("hasAuthority('SCOPE_READ') and isAuthenticated()")
        @Retention(RUNTIME)
        @Target(METHOD)
        public @interface canView {}

        @PreAuthorize("hasAuthority('SCOPE_WRITE') and hasAuthority('EDIT_STATES')")
        @Retention(RUNTIME)
        @Target(METHOD)
        public @interface canManage {}
    }

    public @interface UserGroupAccessLevel {

        @PreAuthorize("hasAuthority('SCOPE_WRITE') and @authenticationSecurity.userId == #userId")
        @Retention(RUNTIME)
        @Target(METHOD)
        public @interface canAlterOwnPassword {}

        @PreAuthorize("hasAuthority('SCOPE_WRITE') and" +
                "(@authenticationSecurity.userId == #userId or hasAuthority('EDIT_USERS'))")
        @Retention(RUNTIME)
        @Target(METHOD)
        public @interface canAlterUser {}

        @PreAuthorize("hasAuthority('SCOPE_WRITE') and  hasAuthority('EDIT_USERS')")
        @Retention(RUNTIME)
        @Target(METHOD)
        public @interface canAlter {}

        @PreAuthorize("hasAuthority('SCOPE_READ') and  hasAuthority('VIEW_USERS')")
        @Retention(RUNTIME)
        @Target(METHOD)
        public @interface canView {}
    }

    public @interface Statistics {

        @PreAuthorize("hasAuthority('SCOPE_READ') and  hasAuthority('GENERATE_REPORTS')")
        @Retention(RUNTIME)
        @Target(METHOD)
        public @interface canView {}
    }
}
