package cn.lacknb.blog.actions;

import cn.lacknb.blog.service.AIService;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.ActionGroup;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowManager;
import com.intellij.psi.*;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.ui.content.Content;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;

/**
 * @author gitsilence
 */
public class AIMethodActionGroup extends ActionGroup {

    @Override
    public AnAction @NotNull [] getChildren(@Nullable AnActionEvent e) {
        return new AnAction[]{
            new ExplainCodeAction(),
            new GenerateCommentAction(),
            new GenerateTestAction()
        };
    }

    private static class ExplainCodeAction extends AnAction {
        public ExplainCodeAction() {
            super("解释代码");
        }

        @Override
        public void actionPerformed(@NotNull AnActionEvent e) {
            Project project = e.getProject();
            if (project != null) {
                Editor editor = FileEditorManager.getInstance(project).getSelectedTextEditor();
                if (editor != null) {
                    // TODO: 实现代码解释逻辑
                }
            }
        }
    }

    private static class GenerateCommentAction extends AnAction {
        public GenerateCommentAction() {
            super("生成注释");
        }

        @Override
        public void actionPerformed(@NotNull AnActionEvent e) {
            Project project = e.getProject();
            if (project != null) {
                Editor editor = FileEditorManager.getInstance(project).getSelectedTextEditor();
                if (editor != null) {
                    // TODO: 实现注释生成逻辑
                }
            }
        }
    }

    private static class GenerateTestAction extends AnAction {
        public GenerateTestAction() {
            super("生成单元测试");
        }

        @Override
        public void actionPerformed(@NotNull AnActionEvent e) {
            Project project = e.getProject();
            if (project != null) {
                Editor editor = FileEditorManager.getInstance(project).getSelectedTextEditor();
                if (editor != null) {
                    // 获取当前方法的PsiElement
                    PsiFile psiFile = PsiDocumentManager.getInstance(project).getPsiFile(editor.getDocument());
                    if (psiFile != null) {
                        int offset = editor.getCaretModel().getOffset();
                        PsiElement element = psiFile.findElementAt(offset);
                        if (element != null) {
                            PsiMethod method = PsiTreeUtil.getParentOfType(element, PsiMethod.class);
                            if (method != null) {
                                // 选中当前方法的代码
                                int startOffset = method.getTextRange().getStartOffset();
                                int endOffset = method.getTextRange().getEndOffset();
                                editor.getSelectionModel().setSelection(startOffset, endOffset);

                                // 获取方法的完整代码
                                String methodCode = method.getText();
                                
                                // 构建prompt
                                String prompt = "请为以下Java方法生成单元测试代码：\n" + methodCode;
                                
                                // 调用AI服务生成单元测试
                                AIService aiService = project.getService(AIService.class);
                                String testCode = aiService.generateUnitTest(prompt);
                                
                                // 在工具窗口中显示生成的单元测试代码
                                ToolWindow toolWindow = ToolWindowManager.getInstance(project).getToolWindow("AI Assistant");
                                if (toolWindow != null) {
                                    toolWindow.show(() -> {
                                        Content content = toolWindow.getContentManager().getSelectedContent();
                                        if (content != null) {
                                            JComponent component = content.getComponent();
                                            if (component instanceof JPanel) {
                                                JPanel panel = (JPanel) component;
                                                Component[] components = panel.getComponents();
                                                for (Component comp : components) {
                                                    if (comp instanceof JScrollPane) {
                                                        JScrollPane scrollPane = (JScrollPane) comp;
                                                        if (scrollPane.getViewport().getView() instanceof JTextArea) {
                                                            JTextArea chatArea = (JTextArea) scrollPane.getViewport().getView();
                                                            chatArea.append("生成的单元测试代码:\n" + testCode + "\n");
                                                            break;
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    });
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}