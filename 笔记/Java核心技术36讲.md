# 开篇词 | 以面试题为切入点，有效提升你的Java内功

更要对底层源代码层面的掌握，并对分布式、安全、性能等领域能力有进一步的要求。

但坦白说表现出的能力水平却不足以通过面试

* 知其然不知其所以然 技术选择背后的逻辑
* 知识碎片化，不成系统。

**五大模块**

1. Java基础
2. Java进阶
3. java应用开发扩展
4. java安全基础
5. java性能基础
   
#  1 | 探探你对Java平台的理解

## 典型回答

1. 书写一次，到处运行
2. 垃圾收集（GC）

对于“Java是解释执行”这句话，不太准确。  通过Javac编译成字节码通过JVM内嵌的解释器将字节码转换为机器码。 但是Oracle JDK 提供的Hotspot JVM，都提供了JIT编译器，也就是通常所说的动态编译器，JIT是编译执行。

## 考点分析

表现出自己的思维深入并系统化，Java知识理解得也比较全面。

## 知识扩展

Java特性：

* 面向对象（封装，继承，多态）
* 平台无关性（JVM运行.class文件）
* 语言（泛型，Lambda）
* 类库（集合，并发，网络，IO/NIO）
* JRE（Java运行环境，JVM，类库）
* JDK（Java开发工具，包括JRE，javac， 诊断工具）
  
 # 2 | Exception 和 Error 有什么区别？

 请对比 Exception 和 Error，另外，运行时异常与一般异常有什么区别？

 ## 典型回答

 Exception 和 Error 都是继承了Throwable类， 在Java中只有Throwable实例才能被throw和catch。

Exception 和 Error 提现了 java 平台对不同异常情况的分类。Exception 是程序正常运行中，可以预料的意外情况，可以被捕获，进行相应处理。

Error 是正常情况下，不大可能出现的情况，绝大部分的Error都会导致程序（比如 JVM 自身）处于非正常的，不可恢复状态。既然是非正常情况，所以不便于也不需要捕获，常见的比如OutOfMemoryError 之类 都是Error 的子类。

## 考点分析

1. 理解 Throwable、Exception、Error的设计和分类
2. 理解 Java 语言中操作 Throwable 的元素和实践。

## 知识扩展

1. 尽量不要捕获Exception这样的通用异常，而是应该捕获特定异常。
2. 不要生吞（swallow）异常

