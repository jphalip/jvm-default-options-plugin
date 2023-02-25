package com.julienphalip.jvmdefaultoptions.settings;

import com.intellij.ide.util.PropertiesComponent;
import com.intellij.openapi.options.ConfigurationException;
import org.junit.Test;

public class GlobalSettingsTest extends BaseSettingsTest {

    @Test
    public void testSettingsReset() {
        BaseSettingsTest.testSettingsReset(PropertiesComponent.getInstance(), new GlobalSettings());
    }

    @Test
    public void testSettingsDisabled() {
        BaseSettingsTest.testSettingsDisabled(
                PropertiesComponent.getInstance(), new GlobalSettings());
    }

    @Test
    public void testApply() throws ConfigurationException {
        BaseSettingsTest.testApply(PropertiesComponent.getInstance(), new GlobalSettings());
    }
}
