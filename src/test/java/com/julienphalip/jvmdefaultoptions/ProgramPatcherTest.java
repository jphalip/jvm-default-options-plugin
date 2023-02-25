package com.julienphalip.jvmdefaultoptions;

import static com.julienphalip.jvmdefaultoptions.Constants.*;

import com.intellij.execution.application.ApplicationConfiguration;
import com.intellij.execution.configurations.JavaParameters;
import com.intellij.ide.util.PropertiesComponent;
import com.intellij.testFramework.fixtures.BasePlatformTestCase;
import java.util.Map;
import java.util.stream.Collectors;
import org.junit.Test;

public class ProgramPatcherTest extends BasePlatformTestCase {

    @Test
    public void testEnabled() {
        ApplicationConfiguration configuration =
                new ApplicationConfiguration("MyApp", getProject());
        // Global default values
        PropertiesComponent globalSettings = PropertiesComponent.getInstance();
        globalSettings.setValue(VM_OPTIONS_ENABLED, true);
        globalSettings.setValue(VM_OPTIONS, "-Daaa=purple -Dbbb=orange -Dccc=turquoise");
        globalSettings.setValue(ENV_VARS_ENABLED, true);
        globalSettings.setValue(ENV_VARS, "AAA=brown BBB=black CCC=cyan");
        // Project default values
        PropertiesComponent projectSettings = PropertiesComponent.getInstance(getProject());
        projectSettings.setValue(VM_OPTIONS_ENABLED, true);
        projectSettings.setValue(VM_OPTIONS, "-Dccc=white -Dddd=magenta");
        projectSettings.setValue(ENV_VARS_ENABLED, true);
        projectSettings.setValue(ENV_VARS, "CCC=grey DDD=gold");
        // Run configuration values
        JavaParameters params = new JavaParameters();
        params.getVMParametersList().addParametersString("-Daaa=green -Dddd=blue -Deee=fuchsia");
        params.addEnv("AAA", "yellow");
        params.addEnv("DDD", "red");
        params.addEnv("EEE", "pink");
        // Do the patching
        new ProgramPatcher().patchJavaParameters(null, configuration, params);
        // Check the results
        assertEquals(
                "-Daaa=purple -Dbbb=orange -Dccc=turquoise -Dccc=white -Dddd=magenta -Daaa=green"
                        + " -Dddd=blue -Deee=fuchsia",
                params.getVMParametersList().getParametersString());
        assertEquals(
                "AAA=yellow BBB=black CCC=grey DDD=red EEE=pink", formatEnvCars(params.getEnv()));
    }

    @Test
    public void testProjectDisabled() {
        ApplicationConfiguration configuration =
                new ApplicationConfiguration("MyApp", getProject());
        // Global default values
        PropertiesComponent globalSettings = PropertiesComponent.getInstance();
        globalSettings.setValue(VM_OPTIONS_ENABLED, true);
        globalSettings.setValue(VM_OPTIONS, "-Daaa=purple -Dbbb=orange -Dccc=turquoise");
        globalSettings.setValue(ENV_VARS_ENABLED, true);
        globalSettings.setValue(ENV_VARS, "AAA=brown BBB=black CCC=cyan");
        // Project default values
        PropertiesComponent projectSettings = PropertiesComponent.getInstance(getProject());
        projectSettings.setValue(VM_OPTIONS_ENABLED, false);
        projectSettings.setValue(VM_OPTIONS, "-Dccc=white -Dddd=magenta");
        projectSettings.setValue(ENV_VARS_ENABLED, false);
        projectSettings.setValue(ENV_VARS, "CCC=grey DDD=gold");
        // Run configuration values
        JavaParameters params = new JavaParameters();
        params.getVMParametersList().addParametersString("-Daaa=green -Dddd=blue -Deee=fuchsia");
        params.addEnv("AAA", "yellow");
        params.addEnv("DDD", "red");
        params.addEnv("EEE", "pink");
        // Do the patching
        new ProgramPatcher().patchJavaParameters(null, configuration, params);
        // Check the results
        assertEquals(
                "-Daaa=purple -Dbbb=orange -Dccc=turquoise -Daaa=green -Dddd=blue -Deee=fuchsia",
                params.getVMParametersList().getParametersString());
        assertEquals(
                "AAA=yellow BBB=black CCC=cyan DDD=red EEE=pink", formatEnvCars(params.getEnv()));
    }

    @Test
    public void testGlobalAndProjectDisabled() {
        ApplicationConfiguration configuration =
                new ApplicationConfiguration("MyApp", getProject());
        // Global default values
        PropertiesComponent globalSettings = PropertiesComponent.getInstance();
        globalSettings.setValue(VM_OPTIONS_ENABLED, false);
        globalSettings.setValue(VM_OPTIONS, "-Daaa=purple -Dbbb=orange -Dccc=turquoise");
        globalSettings.setValue(ENV_VARS_ENABLED, false);
        globalSettings.setValue(ENV_VARS, "AAA=brown BBB=black CCC=cyan");
        // Project default values
        PropertiesComponent projectSettings = PropertiesComponent.getInstance(getProject());
        projectSettings.setValue(VM_OPTIONS_ENABLED, false);
        projectSettings.setValue(VM_OPTIONS, "-Dccc=white -Dddd=magenta");
        projectSettings.setValue(ENV_VARS_ENABLED, false);
        projectSettings.setValue(ENV_VARS, "CCC=grey DDD=gold");
        // Run configuration values
        JavaParameters params = new JavaParameters();
        params.getVMParametersList().addParametersString("-Daaa=green -Dddd=blue -Deee=fuchsia");
        params.addEnv("AAA", "yellow");
        params.addEnv("DDD", "red");
        params.addEnv("EEE", "pink");
        // Do the patching
        new ProgramPatcher().patchJavaParameters(null, configuration, params);
        // Check the results
        assertEquals(
                "-Daaa=green -Dddd=blue -Deee=fuchsia",
                params.getVMParametersList().getParametersString());
        assertEquals("AAA=yellow DDD=red EEE=pink", formatEnvCars(params.getEnv()));
    }

    public static String formatEnvCars(Map<String, String> envVars) {
        return envVars.entrySet().stream()
                .sorted(Map.Entry.comparingByKey()) // Sort by key
                .map(
                        entry ->
                                entry.getKey()
                                        + "="
                                        + entry.getValue()) // Format as "key=value" pairs
                .collect(Collectors.joining(" "));
    }
}
