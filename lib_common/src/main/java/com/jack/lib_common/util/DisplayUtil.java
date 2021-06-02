package com.jack.lib_common.util;

import android.content.Context;

/**
 * @fileName: DisplayUtil
 * @author: susy
 * @date: 2021/5/30 18:42
 * @description: 显示操作
 */
public class DisplayUtil {
    public static int dp2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }
}
