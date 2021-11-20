# Java 并发实现原理

## 1 多线程基础

### 1.1 线程的优雅关闭

#### 1.1.1 stop() 与 destory() 函数

#### 1.1.2 守护线程

#### 1.1.3 设置关闭的标志位

### 1.2 InterruptedException() 函数与interrupt()函数

#### 1.2.1 什么情况下会抛出Interrupted异常

#### 1.2.2 轻量级阻塞与重量级阻塞

#### 1.2.3 t.isInterrupted() 与 Thread.interrupted()

### 1.3 synchronized 关键字

#### 1.3.1 锁的对象是什么

#### 1.3.2 锁的本质是什么

#### 1.3.3 synchronized实现原理

### 1.4 wait() 与 notify()

#### 1.4.1 生产者-消费者模型

    1. 如何阻塞？
    2. 如何双向通知？

#### 1.4.2 为什么必须和synchronized一起使用

#### 1.4.3 为什么wait()的时候必须释放锁

#### 1.4.4 wait() 与 notify() 的问题

### 1.5 volatile 关键字

#### 1.5.1 64位写入的原子性(Half Write)

#### 1.5.2 内存的可见性

#### 1.5.4 重排序：DCL问题

### 1.6 JMM 与 happen-before

#### 1.6.1 为什么会存在“内存可见性”问题

#### 1.6.2 重排序与内存可见性的关系

#### 1.6.3 as-if-serial语义

1. 单线程程序的重排序规则
2. 多线程程序的重排序规则

#### 1.6.4 happen-before是什么

1. 单线程中的每个操作，happen-before对应线程中任意后续操作（也就是 as-if-serial语义保证）。

#### 1.6.5 happen-before的传递性

#### 1.6.6 C++中的volatile关键字

#### 1.6.7 JSR-133对volatile语义的增强

### 1.7 内存屏障

#### 1.7.1 Linux中的内存屏障

#### 1.7.2 JDK中的内存屏障

#### 1.7.3 volatile实现原理

### 1.8 final关键字

#### 1.8.1 构造函数溢出问题

#### 1.8.2 final的happen-before语义

#### 1.8.3 happen-before规则总结

### 1.9 综合应用：无锁编程

#### 1.9.1 一写一读的无锁队列：内存屏障

#### 1.9.2 一写多读的无锁队列：volatile关键字

#### 1.9.3 多谢多读的无锁队列：CAS

#### 1.9.4 无锁栈

#### 1.9.5 无锁链表

## 2 Atomic类

### 2.1 AtomicInteger 和 AtomicLong

#### 2.1.1 悲观锁和乐观锁

#### 2.1.2 Unsafe的CAS详解

#### 2.1.3 自旋与阻塞

### 2.2 AtomicBoolean和AtomicReference

#### 2.2.1 为什么需要AtomicBoolean

#### 2.2.2 如何支持boolean和double类型

### 2.3 AtomicStamped Reference和AtomicMarkable Reference

#### 2.3.1 ABA问题与解决办法

#### 2.3.2 为什么没有AtomicStampedInteger或AtomicStampedLong

#### 2.3.3 AtomicMarkableReference

### 2.4 AtomicIntegerFieldUpdater、AtomicLongFieldUpdater和AtomicReferenceFieldUpdater

#### 2.4.1 为什么需要AtomicXXXFieldUpdater

#### 2.4.2 限制条件

### 2.5 AtomicIntegerArray、AtomicLongArray和Atomic-ReferenceArray

#### 2.5.1 使用方式

#### 2.5.2 实现原理

### 2.6 Striped64 与LongAdder

#### 2.6.1 LongAdder原理

#### 2.6.2 最终一致性

#### 2.6.3 伪共享与缓存行填充

#### 2.6.4 LongAdde核心实现

#### 2.6.5 LongAccumulator

#### 2.6.6 DoubleAdder与DoubleAccumulator

## 3 Lock与Condition

### 3.1 互斥锁

#### 3.1.1 锁的可重入性

#### 3.1.2 类的继承层次

#### 3.1.3 锁的公平性与非公平性

#### 3.1.4 锁实现的基本原理

#### 3.1.5 公平与非公平的lock()的实现差异

#### 3.1.6 阻塞队列与唤醒机制

#### 3.1.7 unlock()视线分析

#### 3.1.8 lockInterruptibly()实现分析

#### 3.1.9 tryLock()实现分析

### 3.2 读写锁

#### 3.2.1 类继承层次

#### 3.2.2 读写锁实现的基本原理

#### 3.2.3 AQS的两对模板方法

#### 3.2.4 WriteLock公平与非公平实现

#### 3.2.5 ReadLock公平与非公平实现

### 3.3 Condition

#### 3.3.1 Condition与Lock的关系

#### 3.3.2 Condtion的使用场景

#### 3.3.3 Condition实现原理

#### 3.3.4 await()实现分析

#### 3.3.5 awaitUniterruptibly()实现分析

#### 3.3.6 notify()实现分析

### 3.4 StamedLock

#### 3.4.1 为什么引入StampedLock

#### 3.4.2 使用场景

#### 3.4.3 乐观读的实现原理

#### 3.4.4 悲观读/写

## 4 同步工具类

### 4.1 Smaphore

### 4.2 CountDownLatch

#### 4.2.1 CountDownLatch使用场景

#### 4.4.2 await() 实现分析
