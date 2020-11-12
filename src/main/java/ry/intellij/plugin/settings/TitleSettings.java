package ry.intellij.plugin.settings;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.options.Configurable;
import com.intellij.ui.components.JBLabel;
import com.intellij.ui.components.JBTextField;
import com.intellij.util.ui.FormBuilder;
import org.jetbrains.annotations.Nullable;
import ry.intellij.plugin.CustomFrameTitleBuilder;

import javax.swing.*;

/**
 * @author Ruslan Yaniuk
 * @date Nov 2020
 */
public class TitleSettings implements Configurable {
    private final JPanel panel;
    private final JBTextField titlePrefix = new JBTextField();

    public TitleSettings() {
        JLabel hint = new JLabel("Changes will take an effect after the restart");

        hint.setEnabled(false);
        panel = FormBuilder.createFormBuilder()
                .addLabeledComponent(new JBLabel("Title prefix: "), titlePrefix, 1, false)
                .addComponent(hint, 5)
                .addComponentFillVertically(new JPanel(), 0)
                .getPanel();
    }

    @Override
    public String getDisplayName() {
        return "Window Title";
    }

    @Nullable
    @Override
    public JComponent createComponent() {
        return panel;
    }

    @Override
    public boolean isModified() {
        CustomFrameTitleBuilder service = CustomFrameTitleBuilder.getInstance();

        return !titlePrefix.getText().equals(service.titlePrefix);
    }

    @Override
    public void apply() {
        String text = titlePrefix.getText() == null ? "" : titlePrefix.getText();
        CustomFrameTitleBuilder service = CustomFrameTitleBuilder.getInstance();

        service.titlePrefix = text;
        ApplicationManager.getApplication().restart();
    }

    @Override
    public void reset() {
        CustomFrameTitleBuilder service = CustomFrameTitleBuilder.getInstance();

        titlePrefix.setText(service.titlePrefix);
    }
}
