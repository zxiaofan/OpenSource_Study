
namespace java thrift.study

service HelloWorldService {
  string sayHello(1:string username)
  string getRandom()
}