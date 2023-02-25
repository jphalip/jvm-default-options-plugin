package com.julienphalip.jvmdefaultoptions.settings;

import static com.julienphalip.jvmdefaultoptions.Constants.*;
import static com.julienphalip.jvmdefaultoptions.Constants.ENV_VARS;

import com.intellij.ide.util.PropertiesComponent;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.testFramework.fixtures.BasePlatformTestCase;

public abstract class BaseSettingsTest extends BasePlatformTestCase {

    public static void testSettingsReset(PropertiesComponent state, BaseSettings settings) {
        // Set the state
        state.setValue(VM_OPTIONS_ENABLED, true);
        state.setValue(VM_OPTIONS, "-Dbbb=purple -Dccc=orange");
        state.setValue(ENV_VARS_ENABLED, true);
        state.setValue(ENV_VARS, "BBB=brown CCC=lack");

        // Init the UI
        settings.reset();

        // Check values
        assertEquals("-Dbbb=purple -Dccc=orange", settings.vmOptionsTextField.getText());
        assertTrue(settings.enabledVmOptionsCheckBox.isSelected());
        assertEquals("BBB=brown CCC=lack", settings.envVarsTextField.getText());
        assertTrue(settings.enabledEnvVarsCheckBox.isSelected());
    }

    public static void testSettingsDisabled(PropertiesComponent state, BaseSettings settings) {
        // Set values in state
        state.setValue(VM_OPTIONS_ENABLED, false);
        state.setValue(ENV_VARS_ENABLED, false);

        // Init the UI
        settings.reset();

        // Check values
        assertFalse(settings.enabledVmOptionsCheckBox.isSelected());
        assertFalse(settings.enabledEnvVarsCheckBox.isSelected());
    }

    public static void testApply(PropertiesComponent state, BaseSettings settings)
            throws ConfigurationException {
        // Init the UI
        settings.reset();

        // Set some values
        settings.enabledVmOptionsCheckBox.setSelected(true);
        settings.vmOptionsTextField.setText("-Dhhh=uuu");
        settings.enabledEnvVarsCheckBox.setSelected(false);
        settings.envVarsTextField.setText("AAA=zzz");
        settings.apply();

        // Check values are saved in state
        assertEquals("-Dhhh=uuu", state.getValue(VM_OPTIONS));
        assertTrue(state.getBoolean(VM_OPTIONS_ENABLED));
        assertEquals("AAA=zzz", state.getValue(ENV_VARS));
        assertFalse(state.getBoolean(ENV_VARS_ENABLED));
    }
}
