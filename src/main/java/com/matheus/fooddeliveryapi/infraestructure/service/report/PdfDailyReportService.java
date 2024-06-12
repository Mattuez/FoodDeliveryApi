package com.matheus.fooddeliveryapi.infraestructure.service.report;

import com.matheus.fooddeliveryapi.domain.filter.DailySaleFilter;
import com.matheus.fooddeliveryapi.domain.service.DailySaleQueryService;
import com.matheus.fooddeliveryapi.domain.service.DailySaleReportService;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Locale;

@Service
public class PdfDailyReportService implements DailySaleReportService {

    private DailySaleQueryService dailySaleQueryService;


    public PdfDailyReportService(DailySaleQueryService dailySaleQueryService) {
        this.dailySaleQueryService = dailySaleQueryService;
    }

    @Override
    public byte[] issueDailySales(DailySaleFilter dailySaleFilter, String timeOffset) {

        try {
            var inputStream = this.getClass().getResourceAsStream("/reports/dailySales.jasper");

            var parameters = new HashMap<String, Object>();
            parameters.put("REPORT_LOCALE", new Locale("pt", "BR"));

            var dailySales = dailySaleQueryService.findDailySales(dailySaleFilter, timeOffset);
            var dataSource = new JRBeanCollectionDataSource(dailySales);

            var jasperPrint = JasperFillManager.fillReport(inputStream, parameters, dataSource);

            return JasperExportManager.exportReportToPdf(jasperPrint);
        } catch (Exception e) {
            throw new ReportException(e.getMessage(), e.getCause());
        }
    }
}
