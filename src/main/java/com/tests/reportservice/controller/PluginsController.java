package com.tests.reportservice.controller;

import com.tests.plugin.sdk.IPlugin;
import com.tests.plugin.sdk.objects.InputData;
import com.tests.plugin.sdk.objects.InternalData;
import com.tests.plugin.sdk.objects.ReportData;
import com.tests.reportservice.services.PluginManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class PluginsController {

    private final PluginManager pluginManager;

    @Autowired
    public PluginsController(PluginManager pluginManager) {
        this.pluginManager = pluginManager;
    }

    @GetMapping("/plugins")
    public ResponseEntity<List<String>> listPlugins() {
        return ResponseEntity.ok(pluginManager.getInitializedPlugins()
                                                    .stream()
                                                    .map(IPlugin::getPluginName)
                                                    .collect(Collectors.toList()));
    }

    @GetMapping("/plugins/{pluginName}")
    public ResponseEntity<String> executePlugin(@PathVariable String pluginName,
                                                @RequestParam String data) {

        IPlugin plugin = pluginManager.getPluginByName(pluginName);
        InputData inputData = new InputData();
        inputData.setBytes(data.getBytes());
        InternalData internalData = plugin.getAnalyzer().analyzeInputData(inputData);
        ReportData reportingData = plugin.getReporter().getReportingData(internalData);
        return ResponseEntity.ok(new String(reportingData.getBytes()));
    }
}
