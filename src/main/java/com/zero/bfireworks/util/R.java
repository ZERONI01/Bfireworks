package com.zero.bfireworks.util;

import java.util.HashMap;
import java.util.Map;

public class R {

    /** 成功: {"ok": true, "key1": val1, ...} */
    @SafeVarargs
    public static Map<String, Object> ok(Object... pairs) {
        Map<String, Object> result = new HashMap<>();
        result.put("ok", true);
        for (int i = 0; i < pairs.length; i += 2) {
            result.put((String) pairs[i], pairs[i + 1]);
        }
        return result;
    }

    /** 失败: {"detail": "错误信息"} */
    public static Map<String, Object> error(String msg) {
        Map<String, Object> m = new HashMap<>();
        m.put("detail", msg);
        return m;
    }
}
