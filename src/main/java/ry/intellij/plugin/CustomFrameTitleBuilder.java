package ry.intellij.plugin;

import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.wm.impl.FrameTitleBuilder;
import com.intellij.openapi.wm.impl.PlatformFrameTitleBuilder;
import com.intellij.util.xmlb.XmlSerializerUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author Ruslan Yaniuk
 * @date Nov 2020
 */
@State(
        name = "ry.idea.plugin.CustomFrameTitleBuilder",
        storages = {@Storage("WindowTitlePlugin.xml")}
)
public class CustomFrameTitleBuilder extends FrameTitleBuilder
        implements PersistentStateComponent<CustomFrameTitleBuilder> {
    private static final FrameTitleBuilder DEFAULT_BUILDER = new PlatformFrameTitleBuilder();

    public String titlePrefix = "IntelliJ IDEA - ";

    public static CustomFrameTitleBuilder getInstance() {
        return (CustomFrameTitleBuilder) ServiceManager.getService(FrameTitleBuilder.class);
    }

    @Override
    public String getProjectTitle(@NotNull Project project) {
        return titlePrefix + DEFAULT_BUILDER.getProjectTitle(project);
    }

    @Override
    public String getFileTitle(@NotNull Project project, @NotNull VirtualFile virtualFile) {
        return virtualFile.getPath();
    }

    @Nullable
    @Override
    public CustomFrameTitleBuilder getState() {
        return this;
    }

    @Override
    public void loadState(@NotNull CustomFrameTitleBuilder customFrameTitleBuilder) {
        XmlSerializerUtil.copyBean(customFrameTitleBuilder, this);
    }
}
