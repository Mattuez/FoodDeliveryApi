package com.matheus.fooddeliveryapi.api.controller;

import com.matheus.fooddeliveryapi.domain.filter.DailySaleFilter;
import com.matheus.fooddeliveryapi.domain.model.dto.DailySale;
import com.matheus.fooddeliveryapi.domain.service.DailySaleQueryService;
import com.matheus.fooddeliveryapi.domain.service.DailySaleReportService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/statistics")
public class StatisticsController {

    private DailySaleQueryService dailySaleQueryService;

    private DailySaleReportService dailySaleReportService;

    public StatisticsController(DailySaleQueryService dailySaleQueryService,
                                DailySaleReportService dailySaleReportService) {
        this.dailySaleQueryService = dailySaleQueryService;
        this.dailySaleReportService = dailySaleReportService;
    }

    @GetMapping(path = "/daily-sales", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> findDailySalesPdf(DailySaleFilter filter,
                                                    @RequestParam(required = false, defaultValue = "+00:00") String timeOffSet) {

        byte[] bytesPdf = dailySaleReportService.issueDailySales(filter, timeOffSet);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=daily-sales.pdf");

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .headers(headers)
                .body(bytesPdf);
    }

    @GetMapping(path = "/daily-sales", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<DailySale> findDailySales(DailySaleFilter filter,
                                          @RequestParam(required = false, defaultValue = "+00:00") String timeOffSet) {
        return dailySaleQueryService.findDailySales(filter, timeOffSet);
    }
}
