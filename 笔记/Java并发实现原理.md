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
