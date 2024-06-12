package com.matheus.fooddeliveryapi.infraestructure.repository;

import com.matheus.fooddeliveryapi.domain.model.Restaurant;
import com.matheus.fooddeliveryapi.domain.repository.RestaurantRepository;
import com.matheus.fooddeliveryapi.domain.repository.RestaurantRepositoryQueries;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.matheus.fooddeliveryapi.infraestructure.repository.spec.RestaurantSpecs.*;

@Repository
public class RestaurantRepositoryImpl implements RestaurantRepositoryQueries {

    private EntityManager manager;
    private RestaurantRepository restaurantRepository;

    public RestaurantRepositoryImpl(EntityManager manager,
                                    @Lazy RestaurantRepository restaurantRepository) {
        this.manager = manager;
        this.restaurantRepository = restaurantRepository;
    }

    @Override
    public List<Restaurant> find(String name,
                                 BigDecimal inicialDeliveryFee, BigDecimal finalDeliveryFee) {

        var jpql = new StringBuilder();
        jpql.append("from Restaurant where 0 = 0 ");

        var parameters = new HashMap<String, Object>();

        if (StringUtils.hasLength(name)) {
            jpql.append("and name like :name ");
            parameters.put("name", "%" + name + "%");
        }

        if (inicialDeliveryFee != null) {
            jpql.append("and deliveryFee >= :inicialDeliveryFee ");
            parameters.put("inicialDeliveryFee", inicialDeliveryFee);
        }

        if (finalDeliveryFee != null) {
            jpql.append("and deliveryFee <= :finalDeliveryFee");
            parameters.put("finalDeliveryFee", finalDeliveryFee);
        }

        TypedQuery<Restaurant> query = manager
                .createQuery(jpql.toString(), Restaurant.class);

        parameters.forEach(query::setParameter);

        return query.getResultList();
    }

    @Override
    public List<Restaurant> findCriteriaApi(String name,
                                            BigDecimal initialDeliveryFee, BigDecimal finalDeliveryFee) {

        CriteriaBuilder  builder = manager.getCriteriaBuilder();
        CriteriaQuery<Restaurant> query = builder.createQuery(Restaurant.class);
        Root<Restaurant> root = query.from(Restaurant.class);

        ArrayList<Predicate> predicates = new ArrayList<>();

        if (StringUtils.hasText("name")) {
            predicates.add(builder.like(root.get("name"), "%" + name + "%"));
        }

        if (initialDeliveryFee != null) {
            predicates.add(builder.greaterThanOrEqualTo(root.get("deliveryFee"), initialDeliveryFee));
        }

        if (finalDeliveryFee != null) {
            predicates.add(builder.lessThanOrEqualTo(root.get("deliveryFee"), finalDeliveryFee));
        }

        query.where(predicates.toArray(new Predicate[]{}));

        TypedQuery<Restaurant> typedQuery = manager.createQuery(query);

        return typedQuery.getResultList();
    }

    @Override
    public List<Restaurant> findWithFreeDeliveryFee(String name) {
        return restaurantRepository.findAll(withFreeDeliveryFee().and(withSimilarName(name)));
    }
}
