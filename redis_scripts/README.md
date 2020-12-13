**《玩转Redis》最新系列文章**请前往[公众号“**zxiaofan**”（点我点我）](http://tc.zxiaofan.com/tc/zxiaofan_dyh.jpg)查看，或[百度搜索“玩转Redis zxiaofan”（点我点我）](https://www.baidu.com/s?ie=utf-8&f=8&rsv_bp=1&rsv_idx=1&tn=baidu&wd=%E7%8E%A9%E8%BD%ACRedis%20zxiaofan)即可。   

shell脚本执行

```shell
[redis@xxx redis]$ time ./checknottl.sh 6378 password
Warning: Using a password with '-a' or '-u' option on the command line interface may not be safe.
scan_num: 0

real	12m14.810s
user	4m14.343s
sys	5m41.646s
```

python脚本执行：
```
[redis@xxx redis]$ python checknottl.py -p 6378 -d 0 -a password
there are 300005 keys in db[0] 
startTime of db[0] is ：2020-12-06 10:40:09
[>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>]100.00%
done
endTime of db[0] is ：2020-12-06 10:44:24
It takes 254.684270859 seconds :
no ttl keys number is : 300002
the file of keys with no ttl: ./6378_0_no_ttl_keys.txt
```

Redis Lua脚本：查询指定部门的所有上级部门，公众号 zxiaofan

// 数据初始化，1的上级是2,2的上级是3,3的上级是4；公众号 zxiaofan
```
127.0.0.1:6378> HMSET depttree:001 1 2 2 3 3 4 4 5 5 6 6 7 7 8 8 9 9 10
OK
```

// 执行脚本
```
[redis@redis redis]$ ./openredis.sh 6378 "--eval luascript/lua_getAllSupDept.lua depttree:001 1 3 66"
"1,2,3"
```
