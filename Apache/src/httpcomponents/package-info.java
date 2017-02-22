/**
 * @author yunhai
 *
 */
package httpcomponents;
/*
 * httpcomponents:顾名思义http组件，建议取代原Commons HttpClient 3.x
 * 
 * 1. HttpComponents Core
 * 
 * HttpCore is a set of low level HTTP transport components that can be used to build custom client and server side HTTP services with a minimal footprint. HttpCore supports two I/O models: blocking
 * I/O model based on the classic Java I/O and non-blocking, event driven I/O model based on Java NIO. The blocking I/O model may be more appropriate for data intensive, low latency scenarios, whereas
 * the non-blocking model may be more appropriate for high latency scenarios where raw data throughput is less important than the ability to handle thousands of simultaneous HTTP connections in a
 * resource efficient manner.
 * 
 * HttPCore（HttpComponents Core）是一组能被用来创建制定化的HTTP客户端和服务器端服务的较低层级HTTP传输组件的集合。HTTPCore支持两种I/O模型：基于经典Java I/O的阻塞I/O模型和基于Java NIO事件驱动的非阻塞I/O模型。
 * 阻塞I/O模型也许更适合于数据集中的低延迟处理情况，相反非阻塞I/O模型也许更适合于那些对处理并发（成千上万）的HTTP请求能力高于对数据吞吐量要求的情况，与处理大量的并发HTTP请求相比，往往在这种情况下处理数据的延迟会比较高。
 * 
 * 2. HttpComponents Client
 * 
 * HttpClient is a HTTP/1.1 compliant HTTP agent implementation based on HttpCore. It also provides reusable components for client-side authentication, HTTP state management, and HTTP connection
 * management. HttpComponents Client is a successor of and replacement for Commons HttpClient 3.x. Users of Commons HttpClient are strongly encouraged to upgrade.
 * 
 * HttPClient（HttpComponents Client）以HttpCore为基础，是遵从HTTP代理的HTTP/1.1实现。同时提供了可重用的客户端验证、HTTP状态管理、HTTP连接管理组件。HTTPComponents Client是对Commons HttpClient 3.x的成功替代。我们强烈建议Commons HttpClient用户升级到HTTPComponents
 * Client来。
 * 
 * 3. HttpComponents AsyncClient
 * 
 * Asynch HttpClient is a HTTP/1.1 compliant HTTP agent implementation based on HttpCore NIO and HttpClient components. It is a complementary module to Apache HttpClient intended for special cases
 * where ability to handle a great number of concurrent connections is more important than performance in terms of a raw data throughput.
 * 
 * Asynch HttpClient以HttpCore NIO和HttpClient组件为基础，是遵从HTTP代理的HTTP/1.1实现。它是HttpClient组件的补充模块，主要用于处理大量并发连接数量比数据吞吐量重要的场景。
 * 
 * 4.Commons HttpClient (legacy) Commons HttpClient 3.x codeline is at the end of life. All users of Commons HttpClient 3.x are strongly encouraged to upgrade to HttpClient 4.1. Asynch
 * 
 * HttpClient以HttpCore NIO和HttpClient组件为基础，是遵从HTTP代理的HTTP/1.1实现。它是HttpClient组件的补充模块，主要用于处理大量并发连接数量比数据吞吐量重要的场景。 Commons HttpClient 3.x已经是Commons HttpClient的最终版。我们强烈建议所有该版本的用户升级到HTTPComponents Client来。
 * 
 */