package com.julienphalip.jvmdefaultoptions.settings;

import com.intellij.ide.util.PropertiesComponent;
import com.intellij.openapi.options.ConfigurationException;
import org.junit.Test;

public class ProjectSettingsTest extends BaseSettingsTest {

    @Test
    public void testSettingsReset() {
        BaseSettingsTest.testSettingsReset(
                PropertiesComponent.getInstance(getProject()), new ProjectSettings(getProject()));
    }

    @Test
    public void testSettingsDisabled() {
        BaseSettingsTest.testSettingsDisabled(
                PropertiesComponent.getInstance(getProject()), new ProjectSettings(getProject()));
    }

    @Test
    public void testApply() throws ConfigurationException {
        BaseSettingsTest.testApply(
                PropertiesComponent.getInstance(getProject()), new ProjectSettings(getProject()));
    }
}
