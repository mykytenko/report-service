package com.tests.reportservice.services;

import com.tests.plugin.sdk.IPlugin;
import com.tests.reportservice.plugins.DefaultPlugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.ServiceLoader;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang3.StringUtils.equalsIgnoreCase;

@Service
public class PluginManager {
    private static final Logger LOG = LoggerFactory.getLogger(PluginManager.class);

    private final ServiceLoader<IPlugin> serviceLoader = ServiceLoader.load(IPlugin.class);

    private List<IPlugin> plugins;

    @PostConstruct
    public void initPlugins() {
        plugins = getPluginsStream().filter(this::initializeAndFilerPlugin).collect(toList());
        plugins.add(new DefaultPlugin());
    }

    public List<IPlugin> getInitializedPlugins() {
        return plugins;
    }

    public IPlugin getPluginForCustomer(String customerId) {
        return plugins
                .stream()
                .filter(p -> equalsIgnoreCase(customerId, p.getCustomerIdentifier()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Customer " + customerId + " is not found"));
    }

    public IPlugin getPluginByName(String pluginName) {
        return plugins
                .stream()
                .filter(p -> equalsIgnoreCase(pluginName, p.getPluginName()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Plugin " + pluginName + " is not found"));
    }


    // ======== private stuff

    private Stream<IPlugin> getPluginsStream() {
        List<IPlugin> pluginsList = new ArrayList<>();
        Iterator<IPlugin> iterator = serviceLoader.iterator();
        while (iterator.hasNext()) {
            try {
                IPlugin plugin = iterator.next();
                pluginsList.add(plugin);
            } catch (Throwable e) {
                LOG.error("Cannot load plugin: {}", e.getMessage(), e);
            }
        }
        return pluginsList.stream().filter(Objects::nonNull);
    }

    private boolean initializeAndFilerPlugin(IPlugin plugin) {
        boolean isInitialized;
        try {
            plugin.init();
            isInitialized = true;
        } catch (Exception e) {
            LOG.error("Exception while initializing plugin {}", plugin.getPluginName(), e);
            isInitialized = false;
        }

        return isInitialized;
    }
}
