package com.julienphalip.jvmdefaultoptions;

import com.intellij.execution.Executor;
import com.intellij.execution.JavaRunConfigurationBase;
import com.intellij.execution.configurations.JavaParameters;
import com.intellij.execution.configurations.RunProfile;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.project.Project;
import java.util.Map;

public class JavaPatcher extends com.intellij.execution.runners.JavaProgramPatcher {
    private static final Logger LOG = Logger.getInstance(GradlePatcher.class.getName());

    @Override
    public void patchJavaParameters(
            Executor executor, RunProfile configuration, JavaParameters javaParameters) {
        if (configuration instanceof JavaRunConfigurationBase) {
            LOG.info("Patching Java run configuration: " + configuration.getName());
            Project project = ((JavaRunConfigurationBase) configuration).getProject();
            Utils.updateJvmParams(project, javaParameters.getVMParametersList());
            Map<String, String> newEnv = Utils.updateEnvVars(project, javaParameters.getEnv());
            javaParameters.setEnv(newEnv);
        }
    }
}
