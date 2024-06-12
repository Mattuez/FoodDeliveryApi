package com.matheus.fooddeliveryapi.infraestructure.service.query;

import com.matheus.fooddeliveryapi.domain.filter.DailySaleFilter;
import com.matheus.fooddeliveryapi.domain.model.Order;
import com.matheus.fooddeliveryapi.domain.model.OrderStatus;
import com.matheus.fooddeliveryapi.domain.model.dto.DailySale;
import com.matheus.fooddeliveryapi.domain.service.DailySaleQueryService;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
public class DailySaleQueryServiceImpl implements DailySaleQueryService {

    private EntityManager manager;

    public DailySaleQueryServiceImpl(EntityManager manager) {
        this.manager = manager;
    }

    @Override
    public List<DailySale> findDailySales(DailySaleFilter filter, String timeOffSet) {

        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<DailySale> query = builder.createQuery(DailySale.class);
        Root<Order> root = query.from(Order.class);

        Expression<Date> functionConvertTz = builder.function(
                "convert_tz", Date.class, root.get("creationDate"),
                builder.literal("+00:00"), builder.literal(timeOffSet)
        );
        Expression<Date> functionDate = builder.function(
                "date", Date.class, functionConvertTz
        );

        Selection<DailySale> selection = builder.construct(
                DailySale.class,
                functionDate,
                builder.count(root.get("id")),
                builder.sum(root.get("totalAmount"))
        );

        List<Predicate> predicates = new ArrayList<>();

        if (filter.getRestaurantId() != null) {
            predicates.add(builder.equal(root.get("restaurant"), filter.getRestaurantId()));
        }

        if (filter.getInitialCreationDate() != null) {
            predicates.add(builder.greaterThanOrEqualTo(root.get("creationDate"), filter.getInitialCreationDate()));
        }

        if (filter.getFinalCreationDate() != null) {
            predicates.add(builder.lessThanOrEqualTo(root.get("creationDate"), filter.getFinalCreationDate()));
        }

        List<OrderStatus> acceptedStatus = List.of(OrderStatus.CONFIRMED, OrderStatus.DELIVERED);

        predicates.add(root.get("orderStatus").in(acceptedStatus));

        query.select(selection);
        query.where(predicates.toArray(new Predicate[0]));
        query.groupBy(functionDate);

        return manager.createQuery(query).getResultList();
    }
}
