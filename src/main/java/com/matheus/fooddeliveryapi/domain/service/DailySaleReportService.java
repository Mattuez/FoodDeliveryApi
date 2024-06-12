package com.matheus.fooddeliveryapi.domain.service;

import com.matheus.fooddeliveryapi.domain.filter.DailySaleFilter;

public interface DailySaleReportService {

    byte[] issueDailySales(DailySaleFilter dailySaleFilter, String timeOffset);

}
