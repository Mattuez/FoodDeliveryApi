package com.matheus.fooddeliveryapi.infraestructure.repository.spec;

import com.matheus.fooddeliveryapi.domain.filter.OrderFilter;
import com.matheus.fooddeliveryapi.domain.model.Order;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

public class OrderSpecs {

    public static Specification<Order> usingFilter(OrderFilter orderFilter) {
        return ((root, query, builder) -> {

            if(Order.class.equals(query.getResultType())) {
                root.fetch("restaurant").fetch("cuisine");
                root.fetch("client");
            }

            List<Predicate> predicates = new ArrayList<>();

            if (orderFilter.getClientId() != null) {
                predicates.add(builder.equal(root.get("client"), orderFilter.getClientId()));
            }

            if (orderFilter.getInitialCreationDate() != null) {
                predicates.add(
                        builder.greaterThanOrEqualTo(root.get("creationDate"), orderFilter.getInitialCreationDate())
                );
            }

            if (orderFilter.getRestaurantId() != null) {
                predicates.add(builder.equal(root.get("restaurant"), orderFilter.getRestaurantId()));
            }

            if (orderFilter.getFinalCreationDate() != null) {
                predicates.add(
                        builder.lessThanOrEqualTo(root.get("creationDate"), orderFilter.getFinalCreationDate())
                );
            }

            return builder.and(predicates.toArray(new Predicate[0]));
        });
    }
}
