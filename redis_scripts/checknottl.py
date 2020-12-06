# encoding: utf-8
"""
modify: zxiaofan
date: 2020-12-05
func: 查找Redis中没有设置ttl的 key
脚本参考网络，已做一定调整优化，更多Redis系列文章可前往：https://blog.csdn.net/u010887744/category_9356949.html；
原脚本参考：http://blog.itpub.net/22664653/viewspace-2153419/；
Note:
	如果提示没有Redis模块“ImportError: No module named redis”，
    请执行“python -m pip install redis”安装Redis模块；
    默认每扫描1000个key即休眠0.5秒。
"""
import redis
import argparse
import time
import sys


class ShowProcess:
    """
    显示处理进度的类
    调用该类相关函数即可实现处理进度的显示
    """
    i = 0 # 当前的处理进度
    max_steps = 0 # 总共需要处理的次数
    max_arrow = 50 # 进度条的长度

    # 初始化函数，需要知道总共的处理次数
    def __init__(self, max_steps):
        self.max_steps = max_steps
        self.i = 0

    # 显示函数，根据当前的处理进度i显示进度
    # 效果为[>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>]100.00%
    def show_process(self, i = None):
        if i is not None:
            self.i = i
        else:
            self.i += 1
        num_arrow = int(self.i * self.max_arrow / self.max_steps) # 计算显示多少个'>'
        num_line = self.max_arrow - num_arrow # 计算显示多少个'-'
        percent = self.i * 100.0 / self.max_steps # 计算完成进度，格式为xx.xx%
        process_bar = '[' + '>' * num_arrow + ' ' * num_line + ']'\
                      + '%.2f' % percent + '%' + '\r' # 带输出的字符串，'\r'表示不换行回到最左边
        sys.stdout.write(process_bar) # 这两句打印字符到终端
        sys.stdout.flush()

    def close(self, words='done'):
        print ''
        print words
        self.i = 0


def check_ttl(redis_conn, no_ttl_file, dbindex, scannum_thensleep):
    start_time = time.time()
    no_ttl_num = 0
    scan_num = 0
    keys_num = redis_conn.dbsize()
    print "there are {num} keys in db[{index}] ".format(num=keys_num, index=dbindex)
    # 打印扫描db开始时间
    print "startTime of db[{index}] is ：{start_time}".format(index=dbindex, start_time=time.strftime("%Y-%m-%d %H:%M:%S", time.localtime()))
    process_bar = ShowProcess(keys_num)
    with open(no_ttl_file, 'a') as f:

        for key in redis_conn.scan_iter(count=1000):
            process_bar.show_process()
            
            scan_num +=1;
            if(scan_num % scannum_thensleep == 0): # scan指定数量后即休眠
                time.sleep(0.5);
                
            if redis_conn.ttl(key) == -1:
                no_ttl_num += 1
                if no_ttl_num < 1000:
                    f.write(key+'\n')
            else:
                continue
            
    process_bar.close()
    # 打印扫db描结束时间
    print "endTime of db[{index}] is ：{end_time}".format(index=dbindex, end_time=time.strftime("%Y-%m-%d %H:%M:%S", time.localtime()))
    print "It takes {sec} seconds :".format(sec=(time.time() - start_time))
    print "no ttl keys number is :", no_ttl_num
    print "the file of keys with no ttl: %s" % no_ttl_file


def main():
    parser = argparse.ArgumentParser()
    parser.add_argument('-p', type=int, dest='port', action='store', help='port of redis ')
    parser.add_argument('-d', type=str, dest='db_list', action='store', default=0,
                        help='ex : -d all or -d 1,2,3,4 ')
    parser.add_argument('-host', type=str, dest='host', action='store', default='127.0.0.1',
                        help=' Redis host') # 20201205，支持连接远程Redis
    parser.add_argument('-a', type=str, dest='password', action='store', default= None,
                        help=' Redis Password') # 20201205，支持传入Redis密码
    parser.add_argument('-sn', type=str, dest='scannum_thensleep', action='store', default=2000,
                        help='After the number of scanning keys reaches [scannum_thensleep], sleep for 1 second ') # 20201205，支持sacn指定数量的key后变休眠1秒
    args = parser.parse_args()
    port = args.port
    host = args.host
    password = args.password
    scannum_thensleep = int(args.scannum_thensleep)
    
    if args.db_list == 'all':
        db_list = [i for i in xrange(0, 16)]
    else:
        db_list = [int(i) for i in args.db_list.split(',')]
	db_list = list(set(db_list)) # 20201205，去重，避免用户重复输入db序号；
    
    for index in db_list:
        try:
            pool = redis.ConnectionPool(host=host, port=port, db=index, password=password) # 20201205，支持传入Redis密码
            r = redis.StrictRedis(connection_pool=pool)
        except redis.exceptions.ConnectionError as e:
            print e
        else:
            no_ttl_keys_file = "./{port}_{db}_no_ttl_keys.txt".format(port=port, db=index)
            check_ttl(r, no_ttl_keys_file, index, scannum_thensleep)


if __name__ == '__main__':
    main()