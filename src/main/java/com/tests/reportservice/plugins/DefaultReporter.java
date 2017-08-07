package com.tests.reportservice.plugins;

import com.tests.plugin.sdk.IReporter;
import com.tests.plugin.sdk.objects.InternalData;
import com.tests.plugin.sdk.objects.ReportData;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DefaultReporter implements IReporter {
    @Override
    public ReportData getReportingData(InternalData internalData) {

        String data = new String(internalData.getBytes());

        String reportData = Stream.of(data.split(","))
                .map(d -> d + "-reported")
                .collect(Collectors.joining("!  "));

        ReportData report = new ReportData();
        report.setBytes(reportData.getBytes());
        return  report;
    }
}
