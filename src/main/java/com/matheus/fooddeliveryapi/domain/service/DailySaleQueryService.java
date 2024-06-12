package com.matheus.fooddeliveryapi.domain.service;

import com.matheus.fooddeliveryapi.domain.filter.DailySaleFilter;
import com.matheus.fooddeliveryapi.domain.model.dto.DailySale;

import java.util.List;

public interface DailySaleQueryService {

    List<DailySale> findDailySales(DailySaleFilter filter, String timeOffSet);
}
