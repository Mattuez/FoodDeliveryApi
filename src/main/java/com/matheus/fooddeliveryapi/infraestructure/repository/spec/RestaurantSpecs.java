package com.matheus.fooddeliveryapi.infraestructure.repository.spec;

import com.matheus.fooddeliveryapi.domain.filter.RestaurantFilter;
import com.matheus.fooddeliveryapi.domain.model.Restaurant;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class RestaurantSpecs {

    public static Specification<Restaurant> withFreeDeliveryFee() {
        return ((root, query, builder) ->
                builder.equal(root.get("deliveryFee"), BigDecimal.ZERO)
        );
    }

    public static Specification<Restaurant> withSimilarName(String name) {
       return ((root, query, builder) ->
               builder.like(root.get("name"), "%" + name + "%")
       );
    }

    public static Specification<Restaurant> usingFilter(RestaurantFilter filter) {
        return (((root, query, builder) -> {

            if (Restaurant.class.equals(query.getResultType())) {
                root.fetch("cuisine");
                root.fetch("address").fetch("city").fetch("state");
            }

            List<Predicate> predicates = new ArrayList<>();

            if (filter.getName() != null) {
                predicates.add(builder.like(root.get("name"), "%" + filter.getName() + "%"));
            }

            if (filter.isFreeDeliveryFee()) {
                predicates.add(builder.equal(root.get("deliveryFee"), BigDecimal.ZERO));
            }

            if (filter.isVerified()) {
                predicates.add(builder.equal(root.get("verified"), 1));
            }

            if (filter.getCuisineId() != null) {
                predicates.add(builder.equal(root.get("cuisine"), filter.getCuisineId()));
            }

            if (filter.getAcceptedPaymentMethodId() != null) {
                predicates.add(builder.isMember(
                        filter.getAcceptedPaymentMethodId(), root.get("paymentMethods").get("id")
                ));
            }

            return builder.and(predicates.toArray(new Predicate[0]));
        }));
    }
}
