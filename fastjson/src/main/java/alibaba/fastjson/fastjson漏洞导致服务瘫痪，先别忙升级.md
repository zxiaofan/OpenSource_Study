# 1、背景
&emsp;&emsp;2019年9月5日，fastjson修复了当字符串中包含\\x转义字符时可能引发OOM的问题。建议广大用户升级fastjson版本至少到1.2.60。    
&emsp;&emsp;一个bug这么恐怖，竟然直接OOM，亲身体验下吧。测试代码如下：

```
JSON.parse("[{\"a\":\"a\\x]");
```
实验效果：**4分钟 堆内存 占用上升达2G；**    

![fastjson_x_oom](http://tc.zxiaofan.com/tc/a/1910/1910fastjson_x_oom.gif)

&emsp;&emsp;这么牛掰，甲方爸爸高度重视，火速把自己负责的服务的fastjson版本升级到1.2.60，线上运行也相安无事。    

&emsp;&emsp;如果这就结束了，本文也就不用写了。⊙﹏⊙‖∣

# 2、fastjson升级后业务异常
&emsp;&emsp;fastjson升级几天后，一老系统业务发生异常，异常信息如下：

```ruby
Exception in thread "xxx" com.alibaba.fastjson.JSONException: expect ':' at 0, actual =
	at com.alibaba.fastjson.parser.DefaultJSONParser.parseObject(DefaultJSONParser.java:290)
	at com.alibaba.fastjson.parser.DefaultJSONParser.parse(DefaultJSONParser.java:1380)
	at com.alibaba.fastjson.parser.DefaultJSONParser.parse(DefaultJSONParser.java:1346)
	at com.alibaba.fastjson.JSON.parse(JSON.java:156)
	at com.alibaba.fastjson.JSON.parse(JSON.java:166)
	at com.alibaba.fastjson.JSON.parse(JSON.java:135)
	at com.alibaba.fastjson.JSON.parseObject(JSON.java:227)
	at alibaba.fastjson.FastJsonBug.main(FastJsonBug.java:70)
```
&emsp;&emsp;看这错误，肯定是json字符串格式有误，应该是冒号的地方实际上是等号了，然后导致反序列化异常，果断排查接口入参，结果入参一切正常。纳尼。。。    
&emsp;&emsp;好吧，那就本地debug吧，结果竟然在本地复现异常了，震惊！！！再次检查接口入参，没有问题，和以前正常运行的入参是一致的。想到最近升级fastjson了，还原fastjson版本试试吧。还原后还真是正常了！！！    

&emsp;&emsp;**难道fastjson版本升级出了大bug？**    

![黑人问号](http://tc.zxiaofan.com/tc/a/emoji/heirenwenhao.jpg)

&emsp;&emsp;**本着对阿里技术的信任，我决定一探究竟。**

# 3、一探究竟
&emsp;&emsp;待反序列化的数据，其格式是2层List嵌套，测试代码已做脱敏处理（完整源码见后文github地址）：

```
String json = "{\"bvos\":[{\"names\":[\"zxiaofan\"]}]}";
JSONObject jsonObjectB1 = GSON.fromJson(json, JSONObject.class);
JSONArray jsonArrayB = jsonObjectB1.getJSONArray("bvos");
JSONObject jsonObjectB2 = JSONObject.parseObject(jsonArrayB.get(0).toString());
// 上面这行代码直接异常了，异常信息如下：
// com.alibaba.fastjson.JSONException: expect ':' at 0, actual =
```
&emsp;&emsp;好奇宝宝们就不要纠结于为什么没有定义好实体再使用TypeReference一步到位啦，千年老代码确实是这样的，这也不是本文的重点。    
&emsp;&emsp;经过debug发现，jsonArrayB.get(0).toString()的值是 {names=[zxiaofan]}。注意了，names后面是等号，不是冒号，这也就能解释为什么异常是“expect ':' at 0, actual =”了。    
&emsp;&emsp;但为什么升级后就异常，没升级就一切正常呢？继续研究下，梳理后发现如下值得注意的地方：
- 1、fastjson版本时1.2.54时正常，大于1.2.54后便会异常；
- 2、运行代码是Google的Gson和阿里的fastjson混用的（json处理全部换成fastjson一切正常）；    

&emsp;&emsp;莫非，是fastjson升级后和Google的Gson不兼容导致？

**仿佛看到了曙光。**    

![看到了曙光](http://tc.zxiaofan.com/tc/a/emoji/kandaoleshuguang.jpg)

&emsp;&emsp;对比分析了fastjson 1.2.54版本和其之后的版本（以下以1.2.55版本为例），发现getJSONArray(String key)还真有区别。

```java
//  fastjson <version>1.2.54</version>

    public JSONArray getJSONArray(String key) {
        Object value = this.map.get(key);
        if (value instanceof JSONArray) {
            return (JSONArray)value;
        } else {
            return value instanceof String ? (JSONArray)JSON.parse((String)value) : (JSONArray)toJSON(value);
        }
    }
```

```java
//  fastjson <version>1.2.55</version>

    public JSONArray getJSONArray(String key) {
        Object value = this.map.get(key);
        if (value instanceof JSONArray) {
            return (JSONArray)value;
        } else if (value instanceof List) {
            return new JSONArray((List)value);
        } else {
            return value instanceof String ? (JSONArray)JSON.parse((String)value) : (JSONArray)toJSON(value);
        }
    }
```

&emsp;&emsp;经过调试后发现，1.2.54版本在getJSONArray(String key)方法中使用的是(JSONArray)toJSON(value)，而1.2.55版本在getJSONArray(String key)方法中使用的是return new JSONArray((List)value)。两者处理后返回的数据也确实不同。     

**fastjson 1.2.54 版本：**    

![fastjson 1.2.54 版本](http://tc.zxiaofan.com/tc/a/1910/1910fastjson1.2.54.png)    


**fastjson 1.2.55 版本：**    

![fastjson 1.2.55 版本](http://tc.zxiaofan.com/tc/a/1910/1910fastjson1.2.55.png)    

&emsp;&emsp;从调试情况看，1.2.54版本最终返回的是JSONObect，1.2.55版本返回的是LinkedTreeMap。Map结构toString()的结构肯定是“key=value”，而不是json结构。    
&emsp;&emsp;但是如果将测试代码中的GSON.fromJson替换成JSON.parseObject，那么不论fastjson的版本高低，都能正常运行。

&emsp;&emsp;至此，我们知道了，**fastjson在升级到1.2.55及以上版本后，getJSONArray方法对Google的Gson处理后的数据兼容性降低，或许本文的名字叫做《fastjson与Gson混用引发的bug》更合适。**    
&emsp;&emsp;也不知道这算不算是bug，给官方提了个issue：
> fastjson版本升级降低了对Gson的兼容性 #2814。

# 4、学习下fastjson对各种数据类型的处理
&emsp;&emsp;在分析的过程中，看了fastjson中getJSONArray方法对各种数据类型的处理方式，和自己以前写的类似代码相比fastjson的代码更优雅，值得学习。相关方法com.alibaba.fastjson.JSON.toJSON()，有兴趣的同学可以看看。

```Java
// 此处代码仅展示核心结构，如需查阅完整代码请前往github/fastjson查看。
// toJSON简直是 数据类型分类处理的模板。@zxiaofan
@SuppressWarnings("unchecked")
    public static Object toJSON(Object javaObject, SerializeConfig config) {
        if (javaObject == null) {
            return null;
        }
        if (javaObject instanceof JSON) {
            return javaObject;
        }
        if (javaObject instanceof Map) {
            if (map instanceof LinkedHashMap) {
            } else if (map instanceof TreeMap) {
            } else {
                innerMap = new HashMap(size);
            }
            return json;
        }

        if (javaObject instanceof Collection) {
            for (Object item : collection) {
            }
            return array;
        }

        if (javaObject instanceof JSONSerializable) {
            return JSON.parse(json);
        }

        Class<?> clazz = javaObject.getClass();

        if (clazz.isEnum()) {
            return ((Enum<?>) javaObject).name();
        }

        if (clazz.isArray()) {
            for (int i = 0; i < len; ++i) {
            }
            return array;
        }

        if (ParserConfig.isPrimitive2(clazz)) {
            return javaObject;
        }
        ObjectSerializer serializer = config.getObjectWriter(clazz);
        if (serializer instanceof JavaBeanSerializer) {
            return json;
        }
        String text = JSON.toJSONString(javaObject);
        return JSON.parse(text);
    }
```

# 5、总结
- 正如文中总结，fastjson在升级到1.2.55及以上版本后，getJSONArray方法对Google的Gson处理后的数据兼容性降低，或许本文的名字叫做《fastjson与Gson混用引发的bug》更合适。
- 代码规范：同一模块代码不允许混用Json解析工具；
- 保持敬畏：生产发布，一定要保持敬畏，对变更充分回归；
- 问题很简单，重要的是思考方式，在寻找答案的过程中学到更多。

> 敬畏生命，敬畏职责，敬畏规章。    
当你认为没有错误的时候，错误一定会来找你。    
--《中国机长》

本文相关分析代码：https://github.com/zxiaofan/OpenSource_Study/blob/master/fastjson/src/main/java/alibaba/fastjson/FastJsonBug.java    


    
---
>祝君好运！<br>
Life is all about choices！<br>
将来的你一定会感激现在拼命的自己！<br>
【[CSDN](https://blog.csdn.net/u010887744)】【[GitHub](https://github.com/zxiaofan)】【[OSCHINA](https://my.oschina.net/zxiaofan)】【[掘金](https://juejin.im/user/5b61e64df265da0f4d0d90f8/activities)】【[微信公众号](http://tc.zxiaofan.com/tc/zxiaofan_dyh.jpg)】    
<img src="http://tc.zxiaofan.com/tc/zxiaofan_dyh.jpg"  height="150" width="150" alt="欢迎订阅zxiaofan的微信公众号，扫码或直接搜索zxiaofan">
