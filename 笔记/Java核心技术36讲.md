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

# 3 | 谈谈final、finally、finalize 有什么不同 ？

谈谈 final、finally、finalize 有什么不同？

## 典型回答

final 可以用来修饰类、方法、变量，分别有不同的意义，final修饰的class 代表不可以继承扩展，final的变量不可以修改的，而final的方法也是不可以重写（override）的。

finnally 则是Java保证重点代码一定要被执行的一种机制，我们可以使用try-finally或者try-catch-finally 来进行类似关闭JDBC连接、保证unlock锁等动作。

finalize 是基础类java.lang.Object 的一个方法，它的设计目的是保证对象在被垃圾收集前完成特定资源的回收。finalize机制现在已经不推荐使用，并且在JDK9开始被标记为deprecated。

## 考点分析

final

* 我们可以将方法或类声明为final，这样就明确告知别人，这些行为是不许修改的。
* 使用final修饰参数或者变量，也可以清楚地避免以外复制导致的编程错误，甚至，有人明确推荐将所有方法参数、本地变量、成员变量声明成final。
*  final产生了某种程度的不可 变的小锅，所以，可以用于保护只读数据，尤其在并发编程中，可以减少不必要的同步开销。


finalize 无法保证什么时候执行，执行符合预期。使用不当会影响性能，导致程序死锁挂起等。

## 知识扩展

**final 并不等同于 immutable**

# 4 | 强引用、软引用、弱引用、幻象引用有什么区别？

强引用、软引用、弱引用、幻象引用有什么区别？具体使用场景是什么？

## 典型回答

**强引用** ： 最常见的普通对象引用，只要还有强引用指向一个对象，就能表明对象还活着。强制复制为null 就可以被垃圾回收了。

**软引用**： 可以让对象豁免一些垃圾收集，只有当JVM认为内存不足时，才会试图回收软引用指向对象。

**弱引用**： 并不能使对象豁免垃圾收集，仅仅是提供一种访问在弱引用状态下对象的途径。

**幻象引用**： 幻象引用仅仅是提供一种1确保对象被finalize后，做某些事情的机制。

## 考点分析

## 知识扩展

# 5 | String、 StringBuffer、StringBuilder有什么区别？

理解Java字符串，String、 StringBuffer、StringBuilder有什么区别？ 

## 典型回答

String  是Java语言非常基础和重要的类，提供了构造和管理字符串的各种基本逻辑。

StrinBuffer 是为解决上面提到拼接产生太多中间对象的问题而提供的一个类。一个线程安全的可修改字符序列，它保证线程安全，也随之带来了额外的性能开销。

StringBuilder 1.5新增 = 去掉线程安全的StringBuffer。

## 考点分析

* 通过String和相关类，考察了基本的线程安全设计与实现，各种基础编程实践。
* 考察JVM对象缓存机制的理解以及如何良好地使用。
* 考察JVM优化Java代码的一些技巧。
* String相关类的演进，比如Java9中实现的巨大变化

## 知识扩展

### 1. 字符串设计和实现考量。
StringBuffer  和 StringBuilder 底层都是利用可修改的char（jdk9以后是byte）数组。
初始长度 16

### 2.字符串缓存

### 3. String自身的演化

JDK6 压缩字符串（有问题）

JDK9 byte

更小的内存占用、更快的操作速度。

