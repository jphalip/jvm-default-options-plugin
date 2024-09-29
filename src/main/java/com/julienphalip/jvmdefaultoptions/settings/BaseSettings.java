package com.julienphalip.jvmdefaultoptions.settings;

import static com.julienphalip.jvmdefaultoptions.Constants.*;

import com.google.common.annotations.VisibleForTesting;
import com.intellij.ide.util.PropertiesComponent;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.options.SearchableConfigurable;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import java.awt.*;
import javax.swing.*;
import org.jetbrains.annotations.Nullable;

public abstract class BaseSettings implements SearchableConfigurable {

    @VisibleForTesting protected JPanel panel;
    @VisibleForTesting JTextArea vmOptionsTextField;
    @VisibleForTesting protected JTextArea envVarsTextField;
    @VisibleForTesting protected JCheckBox enabledVmOptionsCheckBox;
    @VisibleForTesting protected JCheckBox enabledEnvVarsCheckBox;
    protected JTextPane helptext;

    protected PropertiesComponent state;

    protected void initListeners() {
        enabledVmOptionsCheckBox.addChangeListener(
                event -> {
                    vmOptionsTextField.setEnabled(enabledVmOptionsCheckBox.isSelected());
                });
        enabledEnvVarsCheckBox.addChangeListener(
                event -> {
                    envVarsTextField.setEnabled(enabledEnvVarsCheckBox.isSelected());
                });
    }

    @Override
    public @Nullable JComponent createComponent() {
        return panel;
    }

    @Override
    public boolean isModified() {
        return (!enabledVmOptionsCheckBox.getText().equals(state.getValue(VM_OPTIONS_ENABLED))
                || !vmOptionsTextField.getText().equals(state.getValue(VM_OPTIONS))
                || !enabledEnvVarsCheckBox.getText().equals(state.getValue(ENV_VARS_ENABLED))
                || !envVarsTextField.getText().equals(state.getValue(ENV_VARS)));
    }

    @Override
    public void reset() {
        enabledVmOptionsCheckBox.setSelected(state.getBoolean(VM_OPTIONS_ENABLED));
        vmOptionsTextField.setEnabled(enabledVmOptionsCheckBox.isSelected());
        vmOptionsTextField.setText(state.getValue(VM_OPTIONS));
        enabledEnvVarsCheckBox.setSelected(state.getBoolean(ENV_VARS_ENABLED));
        envVarsTextField.setEnabled(enabledEnvVarsCheckBox.isSelected());
        envVarsTextField.setText(state.getValue(ENV_VARS));
    }

    @Override
    public void apply() throws ConfigurationException {
        state.setValue(VM_OPTIONS_ENABLED, enabledVmOptionsCheckBox.isSelected());
        state.setValue(VM_OPTIONS, vmOptionsTextField.getText());
        state.setValue(ENV_VARS_ENABLED, enabledEnvVarsCheckBox.isSelected());
        state.setValue(ENV_VARS, envVarsTextField.getText());
    }

    {
        // GUI initializer generated by IntelliJ IDEA GUI Designer
        // >>> IMPORTANT!! <<<
        // DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer >>> IMPORTANT!! <<< DO NOT edit this method OR
     * call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        panel = new JPanel();
        panel.setLayout(new GridLayoutManager(4, 1, new Insets(0, 0, 0, 0), -1, -1));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(3, 2, new Insets(0, 0, 0, 0), -1, -1));
        panel.add(
                panel1,
                new GridConstraints(
                        2,
                        0,
                        1,
                        1,
                        GridConstraints.ANCHOR_CENTER,
                        GridConstraints.FILL_BOTH,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        null,
                        null,
                        null,
                        0,
                        false));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel1.add(
                panel2,
                new GridConstraints(
                        0,
                        1,
                        1,
                        1,
                        GridConstraints.ANCHOR_CENTER,
                        GridConstraints.FILL_BOTH,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        null,
                        null,
                        null,
                        0,
                        false));
        enabledVmOptionsCheckBox = new JCheckBox();
        enabledVmOptionsCheckBox.setText("Enabled");
        panel2.add(
                enabledVmOptionsCheckBox,
                new GridConstraints(
                        0,
                        0,
                        1,
                        1,
                        GridConstraints.ANCHOR_WEST,
                        GridConstraints.FILL_NONE,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        GridConstraints.SIZEPOLICY_FIXED,
                        null,
                        null,
                        null,
                        0,
                        false));
        vmOptionsTextField = new JTextArea();
        panel2.add(
                vmOptionsTextField,
                new GridConstraints(
                        1,
                        0,
                        1,
                        1,
                        GridConstraints.ANCHOR_CENTER,
                        GridConstraints.FILL_BOTH,
                        GridConstraints.SIZEPOLICY_WANT_GROW,
                        GridConstraints.SIZEPOLICY_WANT_GROW,
                        null,
                        new Dimension(150, 50),
                        null,
                        0,
                        false));
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel1.add(
                panel3,
                new GridConstraints(
                        2,
                        1,
                        1,
                        1,
                        GridConstraints.ANCHOR_CENTER,
                        GridConstraints.FILL_BOTH,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        null,
                        null,
                        null,
                        0,
                        false));
        enabledEnvVarsCheckBox = new JCheckBox();
        enabledEnvVarsCheckBox.setText("Enabled");
        panel3.add(
                enabledEnvVarsCheckBox,
                new GridConstraints(
                        0,
                        0,
                        1,
                        1,
                        GridConstraints.ANCHOR_WEST,
                        GridConstraints.FILL_NONE,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        GridConstraints.SIZEPOLICY_FIXED,
                        null,
                        null,
                        null,
                        0,
                        false));
        envVarsTextField = new JTextArea();
        panel3.add(
                envVarsTextField,
                new GridConstraints(
                        1,
                        0,
                        1,
                        1,
                        GridConstraints.ANCHOR_CENTER,
                        GridConstraints.FILL_BOTH,
                        GridConstraints.SIZEPOLICY_WANT_GROW,
                        GridConstraints.SIZEPOLICY_WANT_GROW,
                        null,
                        new Dimension(150, 50),
                        null,
                        0,
                        false));
        final JLabel label1 = new JLabel();
        label1.setText("VM Options:");
        panel1.add(
                label1,
                new GridConstraints(
                        0,
                        0,
                        1,
                        1,
                        GridConstraints.ANCHOR_NORTHEAST,
                        GridConstraints.FILL_NONE,
                        GridConstraints.SIZEPOLICY_FIXED,
                        GridConstraints.SIZEPOLICY_FIXED,
                        null,
                        null,
                        null,
                        0,
                        false));
        final JLabel label2 = new JLabel();
        label2.setText("Environment Variables:");
        panel1.add(
                label2,
                new GridConstraints(
                        2,
                        0,
                        1,
                        1,
                        GridConstraints.ANCHOR_NORTHWEST,
                        GridConstraints.FILL_NONE,
                        GridConstraints.SIZEPOLICY_FIXED,
                        GridConstraints.SIZEPOLICY_FIXED,
                        null,
                        null,
                        null,
                        0,
                        false));
        final JSeparator separator1 = new JSeparator();
        panel1.add(
                separator1,
                new GridConstraints(
                        1,
                        1,
                        1,
                        1,
                        GridConstraints.ANCHOR_CENTER,
                        GridConstraints.FILL_HORIZONTAL,
                        GridConstraints.SIZEPOLICY_WANT_GROW,
                        GridConstraints.SIZEPOLICY_WANT_GROW,
                        null,
                        null,
                        null,
                        0,
                        false));
        final Spacer spacer1 = new Spacer();
        panel.add(
                spacer1,
                new GridConstraints(
                        3,
                        0,
                        1,
                        1,
                        GridConstraints.ANCHOR_CENTER,
                        GridConstraints.FILL_VERTICAL,
                        1,
                        GridConstraints.SIZEPOLICY_WANT_GROW,
                        null,
                        null,
                        null,
                        0,
                        false));
        helptext = new JTextPane();
        panel.add(
                helptext,
                new GridConstraints(
                        0,
                        0,
                        1,
                        1,
                        GridConstraints.ANCHOR_CENTER,
                        GridConstraints.FILL_HORIZONTAL,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK
                                | GridConstraints.SIZEPOLICY_WANT_GROW,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        null,
                        new Dimension(150, 50),
                        null,
                        0,
                        false));
        final JSeparator separator2 = new JSeparator();
        panel.add(
                separator2,
                new GridConstraints(
                        1,
                        0,
                        1,
                        1,
                        GridConstraints.ANCHOR_SOUTH,
                        GridConstraints.FILL_HORIZONTAL,
                        GridConstraints.SIZEPOLICY_WANT_GROW,
                        GridConstraints.SIZEPOLICY_CAN_GROW,
                        null,
                        null,
                        null,
                        0,
                        false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return panel;
    }
}
