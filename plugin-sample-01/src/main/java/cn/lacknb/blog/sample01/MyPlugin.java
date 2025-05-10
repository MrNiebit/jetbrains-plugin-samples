package cn.lacknb.blog.sample01;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.ui.Messages;
import org.jetbrains.annotations.NotNull;

/**
 * @author gitsilence
 * @description 这里插件的使用方式为：选中代码，然后右键菜单，点击Show Greeting按钮
 * 然后会出现弹窗显示你选中的代码内容
 *        注册一个右键菜单动作
 *         <action id="MyPlugin.Action" class="cn.lacknb.blog.sample01.MyPlugin" text="Show Greeting"
 *                 description="A demo action">
 *             <add-to-group group-id="EditorPopupMenu" anchor="last"/>
 *         </action>
 *
 *    在plugin.xml中注册这个动作
 */
public class MyPlugin extends AnAction {
    @Override
    public void actionPerformed(@NotNull AnActionEvent event) {
        // 弹窗显示消息
        Messages.showInfoMessage(
                "Hello from MyPlugin!\n" +
                        "Selected Text: " + getSelectedText(event),
                "My Plugin Demo"
        );
    }

    private String getSelectedText(AnActionEvent event) {
        // 获取编辑器选中的文本
        var editor = event.getData(CommonDataKeys.EDITOR);
        if (editor == null) {
            return "N/A";
        }
        return editor.getSelectionModel().getSelectedText();
    }

    @Override
    public void update(@NotNull AnActionEvent event) {
        // 仅在编辑器中有选中文本时显示动作
        event.getPresentation().setEnabled(
                event.getData(CommonDataKeys.EDITOR) != null
        );
    }
}
