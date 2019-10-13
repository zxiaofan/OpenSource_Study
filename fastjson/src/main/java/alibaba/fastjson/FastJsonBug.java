package alibaba.fastjson;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: zxiaofan
 * Date: 2019/10/12
 * Time: 20:14
 * Desc: fastjson与GSON混用，解析嵌套list引发的异常.
 * <dependency>
 * <groupId>com.alibaba</groupId>
 * <artifactId>fastjson</artifactId>
 * <version>1.2.55</version>
 * <!--<version>1.2.54</version>-->
 * </dependency>
 *
 * 《fastjson漏洞导致服务瘫痪，先别忙升级》
 * 相关文章分析请前往csdn.zxiaofan.com 或者订阅公众号zxiaofan查看。
 */
public class FastJsonBug {
    private static final Gson GSON;

    static {
        GsonBuilder builder = new GsonBuilder();
        builder.setDateFormat("yyyy-MM-dd HH:mm:ss");
        builder.disableHtmlEscaping();
        GSON = builder.create();
    }

    public static void main(String[] args) {
/**
 * {
 * 	"bvos": [{
 * 			"names": ["zxiaofan"]
 *                }
 * 	]
 * }
 */
        AVo aVo = new AVo(); // 这是一个双层List对象
        // 初始化双层List对象
        List<BVo> bvos = new ArrayList<>();
        BVo bvo = new BVo();
        List<String> names = new ArrayList<>();
        names.add("zxiaofan");
        bvo.setNames(names);
        bvos.add(bvo);
        aVo.setBvos(bvos);
        String json = JSON.toJSONString(aVo);
        System.out.println(json); // {"bvos":[{"names":["zxiaofan"]}]}

        // 完全使用fastjson处理一切正常
        JSONObject jsonObjectA1 = JSON.parseObject(json, JSONObject.class);
        JSONArray jsonArrayA = jsonObjectA1.getJSONArray("bvos");
        JSONObject jsonObjectA2 = JSONObject.parseObject(jsonArrayA.get(0).toString());
        System.out.println("【完全使用fastjson处理一切正常】" + jsonObjectA2.toJSONString()); // {"names":["zxiaofan"]}
        //
        // 使用Gson反序列化，再用 fastjson（version<=1.2.54）处理 则会触发bug
        System.out.println("【使用Gson反序列化，再用 fastjson（version<=1.2.54）处理 则会触发bug】：");
        JSONObject jsonObjectB1 = GSON.fromJson(json, JSONObject.class);
        JSONArray jsonArrayB = jsonObjectB1.getJSONArray("bvos");
        JSONObject jsonObjectB2 = JSONObject.parseObject(jsonArrayB.get(0).toString()); // com.alibaba.fastjson.JSONException: expect ':' at 0, actual =
        //
        // 异常追溯：1.2.55版本变更在于方法 getJSONArray(String key)
        // 54：(JSONArray)toJSON(value)
        // 55： new JSONArray((List)value);

        // 建议：
        // com.alibaba.fastjson.TypeReference<AVo<BVo>> typeReference = new com.alibaba.fastjson.TypeReference<AVo<BVo>>() { };
        // AVo<BVo> aVoBVo = JSON.parseObject(json, typeReference);


    }

}

class AVo<T> {
    @Getter
    @Setter
    private List<T> bvos;
}

class BVo {
    @Getter
    @Setter
    private List<String> names;
}
