package com.julienphalip.jvmdefaultoptions;

import static com.julienphalip.jvmdefaultoptions.Constants.*;

import com.intellij.execution.executors.DefaultRunExecutor;
import com.intellij.execution.runners.ExecutionEnvironment;
import com.intellij.ide.util.PropertiesComponent;
import com.intellij.openapi.externalSystem.model.execution.ExternalSystemTaskExecutionSettings;
import com.intellij.task.ExecuteRunConfigurationTask;
import com.intellij.task.impl.ExecuteRunConfigurationTaskImpl;
import com.intellij.testFramework.fixtures.BasePlatformTestCase;
import org.jetbrains.plugins.gradle.service.execution.GradleExternalTaskConfigurationType;
import org.jetbrains.plugins.gradle.service.execution.GradleRunConfiguration;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class GradlePatcherTest extends BasePlatformTestCase {

    @Test
    public void testPatch() {
        GradleExternalTaskConfigurationType configurationType = new GradleExternalTaskConfigurationType();
        GradleRunConfiguration configuration = new GradleRunConfiguration(getProject(), configurationType.getConfigurationFactories()[0], "MyGradleTask");

        // Set up global and project settings
        setupSettings();

        // Run configuration values
        String externalProjectPath = getProject().getBasePath();
        configuration.getSettings().setExternalProjectPath(externalProjectPath);
        configuration.getSettings().setTaskNames(Arrays.asList("clean", "build"));
        configuration.getSettings().setScriptParameters("-Daaa=green -Dddd=blue -Deee=fuchsia");
        configuration.getSettings().setEnv(parseEnvString("AAA=yellow DDD=red EEE=pink"));

        // Create ExecuteRunConfigurationTaskImpl
        ExecuteRunConfigurationTask task = new ExecuteRunConfigurationTaskImpl(configuration);

        // Do the patching
        ExecutionEnvironment env = new GradlePatcher().createExecutionEnvironment(getProject(), task, DefaultRunExecutor.getRunExecutorInstance());

        // Get the patched configuration from the ExecutionEnvironment
        GradleRunConfiguration patchedConfiguration = (GradleRunConfiguration) env.getRunProfile();
        assertNotNull("Patched configuration should not be null", patchedConfiguration);

        ExternalSystemTaskExecutionSettings patchedSettings = patchedConfiguration.getSettings();
        assertNotNull("Patched settings should not be null", patchedSettings);

        // Check the results
        assertNull("VM options should be null", patchedSettings.getVmOptions());

        // Check that environment variables have been updated
        String expectedEnv = "AAA=yellow BBB=black CCC=grey DDD=red EEE=pink";
        String actualEnv = formatEnvVars(patchedSettings.getEnv());
        assertEquals("Environment variables should be updated", expectedEnv, actualEnv);

        // Check if script parameters have been modified correctly
        String expectedScriptParams = "-Daaa=purple -Dbbb=orange -Dccc=turquoise -Dccc=white -Dddd=magenta -Daaa=green -Dddd=blue -Deee=fuchsia";
        String actualScriptParams = patchedSettings.getScriptParameters();
        assertEquals("Script parameters should be modified", expectedScriptParams, actualScriptParams);

        // Verify other settings
        String expectedExecutionName = externalProjectPath + " [clean build]";
        assertEquals("ExecutionName should match", expectedExecutionName, patchedConfiguration.getName());
        assertEquals("ExternalProjectPath should match", externalProjectPath, patchedSettings.getExternalProjectPath());
        assertEquals("TaskNames should match", Arrays.asList("clean", "build"), patchedSettings.getTaskNames());
    }

    private void setupSettings() {
        PropertiesComponent globalSettings = PropertiesComponent.getInstance();
        globalSettings.setValue(VM_OPTIONS_ENABLED, true);
        globalSettings.setValue(VM_OPTIONS, "-Daaa=purple -Dbbb=orange -Dccc=turquoise");
        globalSettings.setValue(ENV_VARS_ENABLED, true);
        globalSettings.setValue(ENV_VARS, "AAA=brown BBB=black CCC=cyan");

        PropertiesComponent projectSettings = PropertiesComponent.getInstance(getProject());
        projectSettings.setValue(VM_OPTIONS_ENABLED, true);
        projectSettings.setValue(VM_OPTIONS, "-Dccc=white -Dddd=magenta");
        projectSettings.setValue(ENV_VARS_ENABLED, true);
        projectSettings.setValue(ENV_VARS, "CCC=grey DDD=gold");
    }

    private static Map<String, String> parseEnvString(String envString) {
        Map<String, String> result = new HashMap<>();
        for (String pair : envString.split(" ")) {
            String[] keyValue = pair.split("=");
            result.put(keyValue[0], keyValue[1]);
        }
        return result;
    }

    private static String formatEnvVars(Map<String, String> envVars) {
        return envVars.entrySet().stream()
            .sorted(Map.Entry.comparingByKey())
            .map(entry -> entry.getKey() + "=" + entry.getValue())
            .collect(Collectors.joining(" "));
    }
}