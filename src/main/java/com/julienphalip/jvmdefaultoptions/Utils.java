package com.julienphalip.jvmdefaultoptions;

import static com.julienphalip.jvmdefaultoptions.Constants.*;
import static com.julienphalip.jvmdefaultoptions.Constants.VM_OPTIONS;

import com.intellij.execution.configurations.ParametersList;
import com.intellij.ide.util.PropertiesComponent;
import com.intellij.openapi.project.Project;
import java.util.HashMap;
import java.util.Map;

public class Utils {

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

    public static void updateJvmParams(Project project, ParametersList parametersList) {
        String newParams = updateJvmParams(project, parametersList.getParametersString());
        parametersList.clearAll();
        parametersList.addParametersString(newParams);
    }

    public static String updateJvmParams(Project project, String parametersString) {
        PropertiesComponent globalSettings = PropertiesComponent.getInstance();
        PropertiesComponent projectSettings = PropertiesComponent.getInstance(project);
        String vmOptions = "";
        if (globalSettings.getBoolean(VM_OPTIONS_ENABLED, false)) {
            vmOptions = globalSettings.getValue(VM_OPTIONS, "");
        }
        if (projectSettings.getBoolean(VM_OPTIONS_ENABLED, false)) {
            vmOptions += " " + projectSettings.getValue(VM_OPTIONS, "");
        }
        if (parametersString != null) {
            vmOptions += " " + parametersString;
        }
        return vmOptions;
    }

    public static Map<String, String> updateEnvVars(Project project, Map<String, String> givenEnv) {
        PropertiesComponent globalSettings = PropertiesComponent.getInstance();
        PropertiesComponent projectSettings = PropertiesComponent.getInstance(project);
        Map<String, String> newEnv = new HashMap<>();
        if (globalSettings.getBoolean(ENV_VARS_ENABLED, false)) {
            newEnv = Utils.parseEnvVars(globalSettings.getValue(ENV_VARS, ""));
        }
        if (projectSettings.getBoolean(ENV_VARS_ENABLED, false)) {
            newEnv.putAll(Utils.parseEnvVars(projectSettings.getValue(ENV_VARS, "")));
        }
        newEnv.putAll(givenEnv);
        return newEnv;
    }
}
