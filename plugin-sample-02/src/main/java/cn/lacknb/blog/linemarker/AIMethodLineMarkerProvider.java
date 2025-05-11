package cn.lacknb.blog.linemarker;

import cn.lacknb.blog.actions.AIMethodActionGroup;
import com.intellij.codeInsight.daemon.RelatedItemLineMarkerInfo;
import com.intellij.codeInsight.daemon.RelatedItemLineMarkerProvider;
import com.intellij.codeInsight.navigation.NavigationGutterIconBuilder;
import com.intellij.icons.AllIcons;
import com.intellij.openapi.editor.markup.GutterIconRenderer;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiMethod;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Collections;

/**
 * AI方法行标记提供者
 * @author gitsilence
 */
public class AIMethodLineMarkerProvider extends RelatedItemLineMarkerProvider {

    @Override
    protected void collectNavigationMarkers(@NotNull PsiElement element,
                                           @NotNull Collection<? super RelatedItemLineMarkerInfo<?>> result) {
        // 只处理方法声明
        if (!(element instanceof PsiMethod)) {
            return;
        }

        PsiMethod method = (PsiMethod) element;
        
        // 创建图标构建器
        NavigationGutterIconBuilder<PsiElement> builder = NavigationGutterIconBuilder
                .create(AllIcons.Actions.IntentionBulb)
                .setTarget(method)
                .setTooltipText("AI助手")
                .setPopupTitle("AI助手功能")
                .setAlignment(GutterIconRenderer.Alignment.RIGHT)
                .setTargets(Collections.singletonList(method));

        // 添加行标记信息
        result.add(builder.createLineMarkerInfo(method.getNameIdentifier()));
    }
}