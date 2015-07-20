功能 性能和设计是衡量软件的三个基本方面，Jivejdon开源代码展现的是这三者完美结合。

JiveJdon是按照国外最新设计思想"领域驱动设计"(Domain-Driven Design 简称DDD)、基于JdonFramework自主开发的复杂软件系统，其关键点是Domain Model + In-memory + Domain Events。

常驻内存(In-memory)的领域模型(Domain Model)通过领域事件(Domain Events)驱动技术实现各种功能，正如基因DNA是生命各种活动功能的核心一样，实现了以领域模型而不是数据表为核心的新的模型驱动开发架构(MDD)。

JiveJdon设计特点：

1.灵活的领域模型：基于领域驱动设计DDD实现领域建模；真正OO设计和编程；功能增加容易方便，可维护性强先进架构：面向构件的动态组件架构，最大化的可重用组件，基于JdonFramework的IOC(依赖注入DI)/AOP/DCI和Domain Events事件驱动架构EDA/CQRS/Event Sourcing。

2.可扩展的并发高性能，JiveJdon基于JdonFramework的Disruptor异步事件并发模型，充分利用多核多线程特点，且因为无锁，并发更快；结合基于In-Process/In-memory缓存的领域对象，将数据库等操作使用异步事件实现，回帖和帖子修改等写入性操作都是基于内存领域对象实现，性能大大提高，写性能几乎与读相差无几，Event Sourcing和CQRS架构的读写分离提升扩展性和性能，可平滑拓展到云计算平台。

3.良好的权限体系：用户认证权限系统与业务核心分离；界面与业务核心分离，数据库与业务核心分离

JiveJdon:http://www.jdon.com/jdonframework/jivejdon3/index.html

jivejdon新浪微博:http://weibo.com/jivejdon
