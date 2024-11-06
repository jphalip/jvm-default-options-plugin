package com.julienphalip.jvmdefaultoptions;

import com.intellij.execution.Executor;
import com.intellij.execution.executors.DefaultRunExecutor;
import com.intellij.execution.runners.ExecutionEnvironment;
import com.intellij.openapi.externalSystem.model.execution.ExternalSystemTaskExecutionSettings;
import com.intellij.openapi.externalSystem.util.ExternalSystemUtil;
import com.intellij.openapi.project.Project;
import com.intellij.task.ExecuteRunConfigurationTask;
import org.jetbrains.plugins.gradle.execution.build.GradleExecutionEnvironmentProvider;
import org.jetbrains.plugins.gradle.service.execution.GradleRunConfiguration;
import org.jetbrains.plugins.gradle.util.GradleConstants;

import java.util.HashMap;
import java.util.Map;

import static com.julienphalip.jvmdefaultoptions.Utils.updateEnvVars;
import static com.julienphalip.jvmdefaultoptions.Utils.updateJvmParams;

public class GradlePatcher implements GradleExecutionEnvironmentProvider {

    @Override
    public boolean isApplicable(ExecuteRunConfigurationTask task) {
        return task.getRunProfile() instanceof GradleRunConfiguration;
    }

    @Override
    public ExecutionEnvironment createExecutionEnvironment(Project project, ExecuteRunConfigurationTask task, Executor executor) {
        GradleRunConfiguration configuration = (GradleRunConfiguration) task.getRunProfile();
        ExternalSystemTaskExecutionSettings settings = configuration.getSettings();

        // Update VM options
        String updatedVmOptions = updateJvmParams(project, settings.getVmOptions());
        settings.setVmOptions(updatedVmOptions);

        // Update environment variables
        Map<String, String> updatedEnv = new HashMap<>(settings.getEnv());
        updatedEnv = updateEnvVars(project, updatedEnv);
        settings.setEnv(updatedEnv);

        // Merge script parameters with VM options
        String scriptParams = settings.getScriptParameters();
        if (scriptParams == null) {
            scriptParams = "";
        }
        settings.setScriptParameters(updatedVmOptions + " " + scriptParams.trim());

        // Clear VM options as they are now part of script parameters
        settings.setVmOptions(null);

        // Create and return the ExecutionEnvironment
        String executorId = executor != null ? executor.getId() : DefaultRunExecutor.EXECUTOR_ID;
        return ExternalSystemUtil.createExecutionEnvironment(project, GradleConstants.SYSTEM_ID, settings, executorId);
    }
}