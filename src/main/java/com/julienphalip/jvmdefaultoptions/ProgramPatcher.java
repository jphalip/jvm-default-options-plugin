package com.julienphalip.jvmdefaultoptions;

import static com.julienphalip.jvmdefaultoptions.Constants.*;

import com.intellij.execution.Executor;
import com.intellij.execution.JavaRunConfigurationBase;
import com.intellij.execution.configurations.JavaParameters;
import com.intellij.execution.configurations.RunProfile;
import com.intellij.execution.runners.JavaProgramPatcher;
import com.intellij.ide.util.PropertiesComponent;
import java.util.HashMap;
import java.util.Map;

public class ProgramPatcher extends JavaProgramPatcher {

    public static Map<String, String> parseEnvVars(String envVarString) {
        Map<String, String> envVars = new HashMap<>();
        String[] envVarArray = envVarString.split("\\s+");
        for (String envVar : envVarArray) {
            String[] keyValue = envVar.split("=");
            if (keyValue.length == 2) {
                envVars.put(keyValue[0], keyValue[1]);
            }
        }
        return envVars;
    }

    @Override
    public void patchJavaParameters(
            Executor executor, RunProfile configuration, JavaParameters javaParameters) {
        if (configuration instanceof JavaRunConfigurationBase) {
            JavaRunConfigurationBase appConf = (JavaRunConfigurationBase) configuration;
            PropertiesComponent globalSettings = PropertiesComponent.getInstance();
            PropertiesComponent projectSettings =
                    PropertiesComponent.getInstance(appConf.getProject());
            // Update the VM options
            String vmOptions = "";
            if (globalSettings.getBoolean(VM_OPTIONS_ENABLED, false)) {
                vmOptions = globalSettings.getValue(VM_OPTIONS, "");
            }
            if (projectSettings.getBoolean(VM_OPTIONS_ENABLED, false)) {
                vmOptions += " " + projectSettings.getValue(VM_OPTIONS, "");
            }
            vmOptions += " " + javaParameters.getVMParametersList().getParametersString();
            javaParameters.getVMParametersList().clearAll();
            javaParameters.getVMParametersList().addParametersString(vmOptions);
            // Update the env vars
            Map<String, String> envVars = new HashMap<>();
            if (globalSettings.getBoolean(ENV_VARS_ENABLED, false)) {
                envVars = parseEnvVars(globalSettings.getValue(ENV_VARS, ""));
            }
            if (projectSettings.getBoolean(ENV_VARS_ENABLED, false)) {
                envVars.putAll(parseEnvVars(projectSettings.getValue(ENV_VARS, "")));
            }
            envVars.putAll(javaParameters.getEnv());
            javaParameters.getEnv().clear();
            javaParameters.getEnv().putAll(envVars);
        }
    }
}
