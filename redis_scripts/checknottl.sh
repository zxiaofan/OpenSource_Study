#!/bin/bash
# 查询Redis中没有设置过期时间的key；
# 需在redis-cli 目录执行，或者修改脚本中的路径；
# checknottl.sh，默认每scan(ttl) 1000 个key即休眠0.1秒。
# 脚本参考网络，已做一定调整优化，更多Redis系列文章可前往：https://blog.csdn.net/u010887744/category_9356949.html；
# Note: 
#			shell脚本目前没有进度条，请耐心等待。
#			shell 脚本无法维护长连接，所以每次ttl都将创建连接，性能有一定影响。
# 		有想法使用lua脚本批量 ttl 提升性能，有兴趣的同学可以实现一下，欢迎分享反馈。

db_ip=127.0.0.1      # Redis ip
db_port=$1         # Redis 端口
password=$2     # Redis 密码
cursor=0             # 第一次游标
cnt=1000              # 每次迭代的数量
new_cursor=0         # 下一次游标
scan_num=0         # 已scan的key数量

./redis-cli -h $db_ip -p $db_port -a $password scan $cursor count $cnt > scan_tmp_result
new_cursor=`sed -n '1p' scan_tmp_result`             # 获取下一次游标
sed -n '2,$p' scan_tmp_result > scan_result          # 获取 keys

cat scan_result |while read line                     # 循环遍历所有 keys

# 2>/dev/null，将标准错误丢弃，Warning: Using a password with '-a' or '-u' option on the command line interface may not be safe.
do
    ttl_result=`./redis-cli -h $db_ip -p $db_port -a $password ttl $line 2>/dev/null`      # 使用 ttl 指令获取 key 过期时间
    # $scan_num +=1;
    if [[ $ttl_result == -1 ]];then                  # 判断过期时间，-1 是不过期
        echo $line >> no_ttlkey.log                     # 追加到指定文件
    fi
done

echo 'scan_num: '$scan_num
while [ $cursor -ne $new_cursor ]    # 若 游标 不为 0 ，则证明没有迭代完所有的 key，继续执行
do
    ./redis-cli -h $db_ip -p $db_port -a $password scan $new_cursor count $cnt 2>/dev/null> scan_tmp_result
    new_cursor=`sed -n '1p' scan_tmp_result`
    sed -n '2,$p' scan_tmp_result > scan_result
    cat scan_result |while read line
    do
        ttl_result=`./redis-cli -h $db_ip -p $db_port -a $password ttl $line 2>/dev/null`
        # $scan_num +=1;
        if [[ $ttl_result == -1 ]];then
            echo $line >> no_ttlkey.log
        fi
        
        #if [ $scan_num % 1000 == 0 ];then
        #	sleep 0.5
        #fi
    done
    sleep 0.1
done
rm -f scan_tmp_result
rm -f scan_result