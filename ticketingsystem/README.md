# 性能评测报告

### 设计思路
所需要实现的TicketingSystem的三个方法都是操作车票：买票，查票，退票。而操作车票其对应的其实就是对座位状态的
修改，因此每个座位就是我们需要共享的变量，所以定义座位Seat状态修改的方法，使用这些方法前都需要加锁，锁是属于
每个Seat对象的。Seat对象还包含一个区间属性对应该座位每个区间的售票情况。我实现的加锁的粒度为对一个座位Seat
状态修改时加锁，将这些属性与方法都封装在Seat对象中。其中在Seat的类里是使用的StampedLock读写锁，其特点是支持
三种模式，分别是写锁、悲观读锁和乐观读。允许多个线程同时获取悲观读锁，但是只允许一个线程获取写锁，写锁和悲观读锁
是互斥的，其相对于普通读写锁的一个优势是有一个乐观读，也就是其实现是不加锁的，其性能更优，其会在相关操作执行完后
检查相关的变量是否更改，若是期间被其他线程更改了，则需要重新进行一次操作。

在TicketingDS中，首先初始化变量，使用AtomicLong类生成唯一Tid号，使用ConcurrentHashMap类来存储之后售出的
车票，然后创建各个车次，各个车厢的所有座位。在之后的buyTicket，inquiry，refundTicket方法中均采用遍历的方式
对每个座位进行操作，每个操作都是调用Seat对象的方法。

因为最频繁的操作是查询余票操作，所以对于每次查询完对应车次区间的余票后，将查询结果通过TicketCache变量保存下来，若
之后对于相应的车次区间没有修改过，则下次再次查询相应的区间就可以直接返回结果。也就不用在去进行一次耗时的遍历查询了。

### 正确性和性能
#### 正确性
单线程情况下，本代码通过了老师提供的单线程正确性测试。因为buyTicket，inquiry，refundTicket都是采用遍历座位的方式
来运行，因此其正确性很容易保证，只需要确保Seat修改的正确就行了。通过使用StampedLock锁，Seat的tryBuy与free方法都是
使用写锁，保证了互斥，canSelled方法先使用乐观读遍历一边座位的区间，遍历完后检查遍历途中是否有其他线程更改本座位的相应区
间状态如果有则重新遍历，其过程直接使用悲观读锁来加锁已确保不会再被干扰。每次生成车票的tid是使用AtomicLong实现，可保证其
唯一性。退票后修改相应Seat的状态，再次购票后使用的新得到的tid，原tid作废了。购票时通过遍历的方法查询每一个座位是否满足购票
请求，只要存在相应满足区间的座位，系统就会满足该购票请求。遍历完所有座位，如果没有满足的，则返回null代表车票售空，不得超卖。
#### 可线性化

#### 性能
通过实现Test.java程序来评测系统的性能，按照60%查询余票，30%购票和10%退票的比率反复调用TicketingDS类的三种方法10000
次，并分别测量线程数为4，8，16，32，64的情况下每种方法的调用的平均执行时间与系统的总吞吐率。测量结果如下：
ThreadNum: 4 retpcAvgTime(ns): 3270 BuyAvgTime(ns): 47760 InquiryAvgTime(ns): 28343 ThroughOut(t/s): 116000
ThreadNum: 8 retpcAvgTime(ns): 1503 BuyAvgTime(ns): 34577 InquiryAvgTime(ns): 21615 ThroughOut(t/s): 321000
ThreadNum: 16 retpcAvgTime(ns): 5616 BuyAvgTime(ns): 47189 InquiryAvgTime(ns): 34009 ThroughOut(t/s): 336000
ThreadNum: 32 retpcAvgTime(ns): 1864 BuyAvgTime(ns): 71614 InquiryAvgTime(ns): 52917 ThroughOut(t/s): 459000
ThreadNum: 64 retpcAvgTime(ns): 5190 BuyAvgTime(ns): 151192 InquiryAvgTime(ns): 120902 ThroughOut(t/s): 389000