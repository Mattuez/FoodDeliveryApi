package com.matheus.fooddeliveryapi.domain.repository;

import com.matheus.fooddeliveryapi.domain.model.CuisinePicture;
import org.springframework.stereotype.Repository;

@Repository
public interface CuisinePictureRepository extends CustomJpaRepository<CuisinePicture, Long> {
}
