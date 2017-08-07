package com.tests.reportservice.plugins;


import com.tests.plugin.sdk.IAnalyzer;
import com.tests.plugin.sdk.IPlugin;
import com.tests.plugin.sdk.IReporter;


// This is a default plugin for report-service.
//@ReportServicePlugin   -- is used in case of spring annotation
public class DefaultPlugin implements IPlugin {
    @Override
    public String getPluginName() {
        return "default-plugin";
    }

    @Override
    public long getVersion() {
        return 1;
    }

    @Override
    public String getCustomerIdentifier() {
        return "default";
    }

    @Override
    public void init() {

    }

    @Override
    public void shutdown() {

    }

    @Override
    public IAnalyzer getAnalyzer() {
        return new DefaultAnalyzer();
    }

    @Override
    public IReporter getReporter() {
        return new DefaultReporter();
    }
}
