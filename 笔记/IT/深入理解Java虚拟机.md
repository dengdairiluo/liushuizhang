# 深入理解 Java 虚拟机

## 1. 走近 Java

### 1.1 概述

### 1.2 Java 技术体系

* Java 程序设计语言
* 各种硬件平台上的 Java 虚拟机实现
* Class 文件格式
* Java 类库 API
* 来自商业机构和开源社区的第三方 Java 类库
  
### 1.3 Java 发展史

### 1.4 Java 虚拟机家族v8 

#### 1.4.1 虚拟机始祖 Sun Classic/Exact VM

#### 1.4.2 武林盟主 HotSpot VM

#### 1.4.3 小家碧玉 Mobile/Embedded VM

#### 1.4.4 天下第二 BEA JRockit/IBM J9 VM

#### 1.4.5 软硬合璧 BEA Liquid VM/Azul VM

#### 1.4.6 挑战者 Apache Harmony/Google Android Daivik VM

#### 1.4.7 没有成功，但并非失败 Microsoft JVM

#### 1.4.8 百家争鸣

### 1.5 展望Java技术的未来

#### 1.5.1 无语言倾向

#### 1.5.2 新一代即时编译器

#### 1.5.3 向 Native 迈进

#### 1.5.4 灵活的胖子

#### 1.5.5 语言语法持续增强

### 1.6 实战：自己编译JDK

#### 1.6.1 获取源码

#### 1.6.2 系统需求

#### 1.6.3 构建编译环境

#### 1.6.4 进行编译

#### 1.6.5 在IDE工具中进行源码调试

### 1.7 本章小结

## 2 Java内存区域与内存溢出异常

### 2.1 概述

### 2.2 运行时数据区域

#### 2.2.1 程序计数器

**程序计数器**是一块较小的内存空间，它可以看作是当前线程所执行的字节码的行号指示器。在 Java 虚拟机的概念模型里，字节码解释器工作时就是通过改变这个计数器的值来选取下一条需要执行的字节码指令，它是程序控制流的指示器，分支、循环、跳转、异常处理、线程恢复等基础功能都需要依赖这个计数器完成。

如果线程正在执行的是一个 Java 方法，这计数器记录的是正在执行的虚拟机字节码指令的地址，如果正在执行的是本地(Native)方法，这个计数器值应为空(undefined)。此内存区域是唯一一个在《Java 虚拟机规范》中没有规定任何OutOfMemoryError情况的区域。

关键名词：**概念模型**、 **线程私有**

.java 文件编译成 .class 文件后

原 Java 代码：

```java
package com.yuren.test;

public class HelloWorld {

    public static void main(String[] args) {
        System.out.println("Hello, World!");
    }
}
```

编译后字节码：

``` bytecode

public class com.yuren.test.HelloWorld {
  public com.yuren.test.HelloWorld();
    Code:
       0: aload_0
       1: invokespecial #1                  // Method java/lang/Object."<init>":()V
       4: return

  public static void main(java.lang.String[]);
    Code:
       0: getstatic     #2                  // Field java/lang/System.out:Ljava/io/PrintStream;
       3: ldc           #3                  // String Hello, World!
       5: invokevirtual #4                  // Method java/io/PrintStream.println:(Ljava/lang/String;)V
       8: return
}

```

Code: 014, 0358 就是行号

个人理解：程序计数器就是字节码的断点？

#### 2.2.2 Java 虚拟机栈

线程私有

**虚拟机栈**每个方法被执行的时候，Java 虚拟机都会同步创建一个栈帧用于存储局部变量表、操作数栈、动态链接、方法出口等信息。每一个方法被调用直至执行完毕的过程，就对应着有一个栈帧在虚拟机栈中从入栈到出栈的过程。

如果线程请求的栈深度大于虚拟机所允许的深度，将抛出StackOverflowError异常；如果 Java 虚拟机栈容量可以动态扩展，当栈扩展时无法申请到足够的内存会抛出OutOfMemoryError异常。  

虚拟机栈包括：

  1. 局部变量表
  2. 操作数栈
  3. 动态链接
  4. 方法出口

**局部变量表**放了编译期可知的各种Java虚拟机基本数据类型、
对象引用(一个指向对象起始地址的引用指针，指向一个代表对象的句柄，或者其他与此对象相关的位置)
returnAddress类型（指向一条字节码指令的地址）

**操作数栈**：

**动态链接**：

* 每个栈帧都包含一个指向运行时常量池（JVM 运行时数据区域）中该栈帧所属性方法的引用，持有这个引用是为了支持方法调用过程中的动态连接。
* 在 Class 文件格式的常量池（存储字面量和符号引用）中存有大量的符号引用（1.类的全限定名，2.字段名和属性，3.方法名和属性），字节码中的方法调用指令就以常量池中指向方法的符号引用为参数。这些符号引用一部分会在类加载过程的解析阶段的时候转化为直接引用（指向目标的指针、相对偏移量或者是一个能够直接定位到目标的句柄），这种转化称为静态解析。另外一部分将在每一次的运行期期间转化为直接引用，这部分称为动态连接。

#### 2.2.3 本地方法栈

**本地方法栈**与虚拟机栈所发挥的作用是非常相似的，其区别只是虚拟机执行 Java 方法，而本地方法栈则是为虚拟机使用到的本地(Native)方法服务。

#### 2.2.4 Java堆

**Java堆**是被所有线程共享的一块内存区域，在虚拟机启动时创建。此内存区域的唯一目的就是存放对象实例。Java 世界里“几乎”所有的对象实例都在这里分配内存。

#### 2.2.5 方法区

**方法区**与 Java 堆一样，是各个线程共享的内存区域，它用于存储已被虚拟机加载的**类型信息**、**常量**、**静态变量**、**即时编译器编译后的代码缓存数据**

#### 2.2.6 运行时常量池

**运行时常量池**是方法区的一部分。Class文件中除了有类的版本、字段、方法、接口等描述信息外、还有一项信息是常量池表，用于存放编译期生成的各种字面量与符号引用，这部分内容将在类加载后存放到方法区的运行时常量池中。

运行时常量池相对于Class文件常量池的另外一个重要特征是具备动态性，Java语言并不要求常量一定只有编译期才能产生，也就是说，并非预置入Class文件中常量池的内容才能进入方法区运行时常量池，运行期间也可以将新的常量放入翅中，这种特性被开发人员利用得比较多的便是 String 类的intern() 方法。

#### 2.2.7 直接内存

### 2.3 HotSpot 虚拟机对象探秘

#### 2.3.1 对象的创建

当 Java 虚拟机遇到一条字节码 new 指令时，首先将去检查这个指令的参数是否能在常量池中定位到一个类的符号引用，并且检查这个符号引用代表的类是否已被加载、解析和初始化过。如果没有，那必须先执行相应的类加载过程。

#### 2.3.2 对象的内存布局

在 HotSpot 虚拟机里，对象在堆内存中的存储布局可以划分为三个部分：对象头(Header)、实例数据(instance)和对齐填充(Padding)。

#### 2.3.3 对象的访问定位

主流的访问方式主要有：

1. 使用句柄
2. 直接指针
  
### 2.4 实战：OutOfMemoryError异常

#### 2.4.1 Java 堆溢出

要解决这个内存区域的异常，常规的处理方法是首先通过内存映像分析工具对Dump出来的堆转储快照进行分析。第一步首先应确认内存中导致OOM的对象是否是必要的，也就是要先分清楚到底是出现了内存泄漏还是内存溢出。

如果是内存泄漏，可进一步通过工具查看泄漏对象到GC Roots 的引用链，找到泄漏对象是通过怎样的引用路径、与哪些GC roots相关联才导致垃圾收集器无法回收它们，根据泄漏对象的类型信息以及它到GC Roots引用链的信息，一般可以比较准确地定位到这些对象创建的位置，进而找出产生内存泄漏的代码的具体位置。

#### 2.4.2 虚拟机栈和本地方法栈溢出

1. 如果线程请求的栈深度大于虚拟机所允许的最大深度，将抛出StakOverflowError异常
2. 如果虚拟机的栈内存允许动态扩展，当扩展栈容量无法申请到足够的内存市，将抛出OutOfMemoryError异常

#### 2.4.3 方法区和运行时常量池溢出

方法区溢出也是一种常见的内存溢出异常，一个类如果要被垃圾收集器回收，要达成的条件是比较苛刻的。

#### 2.4.4 本机直接内存溢出

### 2.5 本章小结

## 3 垃圾收集器与内存分配策略

### 3.1 概述

### 3.2 对象已死？

#### 3.2.1 引用计数法

在对象中添加一个引用计数器，每当有一个对方引用它时，计数器值就加一；当引用失效时，计数器值就减一；任何时刻计数器为零的对象就是不可能再被使用的。

#### 3.2.2 可达性分析法

这个算法的基本思路就是通过一系列称为“GC Roots”的根对象作为起始节点集，从这些节点开始，根据引用关系向下搜索，搜索过程所走过的路径称为“引用链”（Reference Chain）,如果某个对象到GC Roots间没有任何引用链相连，或者用图论的话来说就是从GC Roots到这个对象不可达时，则证明此对象是不可能再被使用的。

#### 3.2.3 再谈引用

* **强引用**是最传统的“引用”的定义，是指子啊程序代码之中普遍存在的引用复制，及类似“Object obj = new Object();”这种引用关系。无论任何情况下，只要强引用关系还存在，垃圾收集器就永远不会回收调被引用的对象。

* **软引用**是用来描述一些还有用，但非必须的对象。只被软引用关联着的对象，在系统将要发生内存溢出异常前，会被这些对象列进回收范围之中进行第二次回收，如果这次回收还没有足够的内存，才会抛出内存溢出异常。在JDK1.2版之后提供了SoftReference类来实现软引用。

* **弱引用**比软引用更弱，当垃圾收集器开始工作时，无论当前内存是否足够，都会回收掉只被弱引用关联的对象。 WeakReference类来实现。

* **虚引用** 最弱，为了内在这个对象被收集器回收时收到一个系统通知。

#### 3.2.4 生存还是死亡？

要真正宣告一个对象死亡，至少要经历过两次标记过程：如果对象可达性分析后发现没有与 GC Roots 相连接的引用链，那它将会被第一次标记，随后进行一次筛选，筛选的条件是此对象是否有必要执行 finalize() 方法，或者 finalize() 方法已经被虚拟机调用过。

#### 3.2.5 回收方法区

方法区的垃圾主要回收两部分内容：废弃的常量和不再使用的类型。

### 3.3 垃圾收集算法

#### 3.3.1 分代收集理论

分代假说：

1. **弱分代假说**： 绝大多数对象都是朝生夕灭的。
2. **强分代假说**： 熬过越多次垃圾收集过程的对象就越难以消亡。
3. **跨带引用假说**： 跨代引用相对于同代引用来说仅占极少数。

设计原则：收集器应该讲 Java 堆分划分出不同的区域，然后将回收对象依据其年龄（年龄即对象熬过垃圾收集过程的次数）分配到不同的区域之中存储。一个区域放朝生夕灭的对象，每次回收只关注如何保留对象。另一个区域放难以消亡的对象，虚拟机使用较低的频率来回收这个区域。

常见的垃圾回收：

* 按回收类型划分： Minor GC, Major GC, Full GC。

* 按存亡特征相匹配的垃圾收集算法：标记-复制算法，标记-清除算法，标记-整理算法。

新生代 Young Generation 老年代 Old Generation

#### 3.3.2 标记-清除算法

首先标记出所有需要回收的对象，在标记完成后，统一回收调所有被标记的对象，也可以反过来，标记存活的对象，统一回收所有未必标记的对象。

主要缺点：

1. 第一个是执行效率不稳定，如果 Java 堆中包含大量对象，而且其中大部分是需要被回收的，这是必须进行大量标记和清除的动作。导致标记和清除两个过程的执行效率都岁对象数量增长而降低；

2. 内存空间的碎片化问题，标记、清除之后会产生大量的不连续的内存碎片，空间碎片太多可能会导致当以后在程序运行过程中需要分配较大对象时无法找到足够的连续内存而不得不提出啊触发另一次垃圾收集动作。

#### 3.3.3 标记-复制算法

将可用内存按容量划分为大小相等的两块，每次只使用其中一块。当这一块的内存用完了，就讲还存活着的对象复制到另外一块上面，然后再把已使用过的内存空间一次清理掉。 对于多数对象都是可回收的情况，算法需要复制的就是占少数的存活对象，而且每次都是针对整个搬去进行内存回收，分配内存时也就不用考虑有空间碎片的复杂情况，只要一动堆顶指针，按顺序分配即可。

缺点：内存缩小为原来的一半。

#### 3.3.4 标记-整理算法

标记-复制算法在对象存活率较高时就要进行较多的复制操作，效率将会降低。更关键的是，如果不想浪费50%的空间，将需要有额外的空间进行分配担保，以应对被使用的聂存中所有对象都100%存活的极端情况，所以在老年代一般不能直接选用这种算法。

标记-整理算法，其标记过程与标记-复制算法一样，但后续步骤是，让所有存活的对象都向内存空间一端移动，然后直接清理掉边界以外的内存。

如果移动存活对象并更新所有引用这些对象的操作将会是一种极为负重的操作，而且这种对象移动必须全程暂停用户应用程序才能进行。
如果完全不考虑移动和整理存活对象的话，弥散于堆中的存活对象导致空间碎片化问题就只能依赖更为复杂的内存分配器和内存访问器来解决。内存的访问是用户程序最频繁的操作，甚至都没有之一，假如子啊这个环节上增加了额外的负担，势必会直接影响应用程序的吞吐量。

基于以上两点，是否移动对象都存在弊端，移动则内存回收时会更复杂，不移动则内存分配时会更复杂。从垃圾收集的停顿时间来看，不移动对象停顿时间会更短，甚至可以不需要停顿，但是从整个程序的吞吐量来看，移动对象会更划算。此语境中，吞吐量的实质是赋值器与收集器的效率总和。即使不移动对象会使得收集器的效率提升一些，但因内存分配和访问相比垃圾收集频率要高得多，这部分的耗时增加，总吞吐量仍然是下降的。

另外，还有一种“和稀泥式”解决方案可以不在内存分配和访问上增加太大额外负担，做法是让虚拟机平时多数时间都采用标记-清除算法，暂时容忍内存碎片的存在，直到内存空间的碎片化程度已经大到影响对象分配时，再采用标记-整理算法手机一次，以获得规整的内存空间。前面提到的基于标记-清除算法的CMS收集器面临空间碎片过多时采用的就是这种处理办法。

### 3.4 HotSpot 的算法细节实现

#### 3.4.1 根节点枚举

在HotSpot的解决方案里，是使用一组称为OopMap的数据结构来达到这个目的。一旦类加载动作完成的时候，HotSpot就会把对象内什么偏移量上是什么类型的数据计算出来，在即时编译过程中，也会在特定的位置记录下栈里和寄存器里哪些位置是引用。这样收集器在扫描时就可以直接得知这些信息了，并不需要真正一个不漏地从方法区等GC Roots开始查找。

#### 3.4.2 安全点

实际上HotSpot也的确没有为每条指令都生成OopMap，前面已经提到，只是在“特定的位置”记录了这些信息，这些位置别成为安全点（SafePoint），强制要求必须执行到达安全点后才能够暂停。

#### 3.4.3 安全区域

**安全区域**是指能够确保在某一段代码片段之中，引用关系不会发生变化，因此，在这个区域中任意地方开始垃圾收集都是安全的。我们也可以吧安全区域看作被扩展拉伸的安全点。

#### 3.4.4 记忆集与卡表

记忆集是一种用于记录从非收集区域指向收集区域的指针集合的抽象数据结构。在垃圾收集的场景中，手气器只需要通过记忆集判断出某一块费收集区域是否存在有指向了手机区域的指针就可以了，并不需要了解这些跨代指针的全部细节。那设计者在实现记忆集的时候，便可以选择更为粗犷的记录粒度来节省记忆集的村塾和维护成本。

* 字长精度
* 对象精度
* 卡精度

卡精度所指的是用一种称为“卡表”的方式去实现记忆集，这些事目前最常用的一种记忆集实现方式。

#### 3.4.5 写屏障

#### 3.4.6 并发的可达性分析

三色标记法

* 白色 表岁对象尚未被垃圾收集器访问过。
* 黑色 表示对象已经被垃圾收集器访问过，且这个对象的所有引用都已经扫描过。
* 灰色 表示对象已经被垃圾收集器访问过，但这个对象上至少存在一个引用还没有被扫描过。

### 3.5 经典垃圾收集器

#### 3.5.1 Serial收集器

单线程工作的收集器，它在进行垃圾收集时，必须暂停其他所有工作线程。

#### 3.5.2 ParNew收集器

实质上是Serial收集器的多线程并行版本

除了 serial收集器外，目前只有它能与CMS收集器配合工作。

#### 3.5.3 Parallel Scavenge收集器

它的关注点与其他收集器不同，CMS等收集器的关注点是尽可能地缩短垃圾收集时用户线程的停顿时间，而parallel Scavenge 收集器的目标则是达到一个可控制的吞吐量。

#### 3.5.4 Serial Old收集器

Serial 收集器的老年代版本

#### 3.5.5 Parallel Old收集器

Parallel Old 是 Parallel Scavenge 收集器的老年代版本，支持多线程并发收集。

#### 3.5.6 CMS收集器

CMS 收集器是一种以最短回收停顿时间为目标的收集器。目前很大一部分的 Java 应用集中在互联网网站或者基于浏览器的 B/S 系统的服务器端上，这类应用通常都会较为关注服务的响应速度，希望系统停顿时间尽可能短，已给用户带来良好的交互体验。CMS收集器就非常符合这类应用的需求。

它的运作过程分为四个步骤：

1. 初始标记
2. 并发标记
3. 重新标记
4. 并发清除

#### 3.5.7 Garbage First收集器

在 G1 收集器出现之前的所有其他收集器，包括CMS在内，垃圾收集的目标范围要么是整个新生代，要么就是整个老年代，再要么就是整个 Java 堆。而 G1 跳出了这个樊笼，它可以面向堆内存任何部分来组成回收集进行回收，衡量标准不再是它属于哪个分代，而是那块内存中存放的垃圾数量最多，回收收益最大，这就是G1收集器的 Mixed GC 模式。

* **初始标记**仅仅只是标记一下GC Roots 能直接关联到的对象，并且修改TAMS指针的值，让下一阶段用户线程并发运行时，能正确地在可用的 Region 中分配新对象。

* **并发标记**从 GC Root 开始对堆中对象进行可达性分析，递归扫描整个堆里的对象图，找出要回收的对象，这阶段耗时较长，但可与用户程序并发执行。

* **最终标记**对用户线程做另一个短暂的暂停，用于处理并发阶段结束后仍遗留下来的最后那少量 SATB 记录。

* **筛选回收**负责更新 Region 的统计数据，对各个 Region 的统计数据，对各个 Region 的回收价值和成本进行排序，根据用户所期望的停顿时间来制定回收计划，可以自由选择任意多个 Region 构成回收集，然后把决定回收的那一部分 Region 的存活对象复制到空的 Region 中，再清理掉整个旧 Region 的全部空间。

### 3.6 低延迟垃圾收集器

#### 3.6.1 Shenandoah收集器

#### 3.6.2 ZGC收集器

### 3.7 选择合适的垃圾收集器

#### 3.7.1 Epsilon收集器

#### 3.7.2 收集器的权衡

#### 3.7.3 虚拟机及垃圾收集器日志

#### 3.7.4 垃圾收集器参数总结

### 3.8 实战：内存分配与回收策略

#### 3.8.1 对象优化在Eden分配

#### 3.8.2 大对象直接进入老年代

#### 3.8.3 长期存活的对象进入老年代

#### 3.8.4 动态对象年龄判定

#### 3.8.5 空间分配担保

### 3.9 本章小结

## 4 虚拟机性能监控、故障处理工具

### 4.1 概述

### 4.2 基础故障处理工具

#### 4.2.1 jps: 虚拟机进程状况工具

#### 4.2.2 jstat: 虚拟机统计信息监视工具

#### 4.2.3 jinfo: Java配置信息工具

#### 4.2.4 jmap: Java内存映像工具

#### 4.2.5 jhat: 虚拟机堆转储快照分析工具

#### 4.2.6 jstack: Java堆栈跟踪工具

#### 4.2.7 基础工具总结

### 4.3 可视化故障处理

#### 4.3.1 JHSDB基于服务性代理的调试工具

#### 4.3.2 jConsole：Java监视与管理控制台

1. 启动JConsole
2. 内存监控
3. 线程监控

#### 4.3.3 VisualVM: 多合-故障处理工具

#### 4.3.4 Java Mission Control：可持续在线的监控工具

### 4.4 HotSpot虚拟机插件及工具

### 4.5 本章小结

## 5 调优案例分析与实战

### 5.1 概述

### 5.2 案例分析

#### 5.2.1 大内存硬件上的程序部署策略

#### 5.2.2 集群间同步导致的内存溢出

#### 5.2.3 堆外内存导致的溢出错误

#### 5.2.4 外部命令导致系统缓慢

#### 5.2.5 服务器虚拟机进程崩溃

#### 5.2.6 不恰当数据结构导致内存占用过大

#### 5.2.7 由Windows虚拟内存导致的长时间停顿

#### 5.2.8 由安全点导致的长时间停顿

### 5.3 实战：Eclipse运行速度调优

#### 5.3.1 调优前的程序运行状态

#### 5.3.2 升级JDK版本的性能变化及兼容问题

#### 5.3.3 编译时间和类加载时间的优化

#### 5.3.4 调整内存设置控制垃圾收集频率

#### 5.3.5 选择收集器降低延迟

### 5.4 本章小结

## 6 类文件结构

### 6.1 概述

### 6.2 无关性的基石

### 6.3 Class类文件的结构

#### 6.3.1 魔数与Class文件的版本

#### 6.3.2 常量池

#### 6.3.3 访问标志

#### 6.3.4 类索引、父类索引与接口索引集合

#### 6.3.5 字符表集合

#### 6.3.6 方法表集合

#### 6.3.7 属性表集合

1. Code属性
2. Exceptions属性
3. LineNumberTable属性
4. LocalVaribleTable及LocalVaribleTypeTable属性
5. SourceFile属性及SourceDebugExtension属性
6. ConstantValue属性
7. InnerClasses属性
8. Deprecated及Synthetic属性
9. StackMapTable属性
10. Signature属性
11. BootstrapMethods属性
12. MethodParameters属性
13. 模块化相关属性
14. 运行时注解相关属性

### 6.4 字节码指令简介

#### 6.4.1 字节码与数据类型

#### 6.4.2 加载和存储指令

#### 6.4.3 运算指令

#### 6.4.4 类型转换指令

#### 6.4.5 对象创建与访问指令

#### 6.4.6 操作数栈管理指令

#### 6.4.7 控制转移指令

#### 6.4.8 方法调用和返回指令

#### 6.4.9 异常处理指令

#### 6.4.10 同步指令

### 6.5 公有设计，私有实现

### 6.6 Class文件结构的发展

### 6.7 本章小结

## 7 虚拟机类加载机制

### 7.1 概述

### 7.2 类加载的时机

1. 加载
2. 验证
3. 准备
4. 解析
5. 初始化
6. 使用
7. 卸载

### 7.3 类加载的过程

#### 7.3.1 加载

#### 7.3.2 验证

1. 文件格式验证
2. 元数据验证
3. 字节码验证
4. 符号引用验证

#### 7.3.3 准备

#### 7.3.4 解析

1. 类或接口的解析
2. 字段解析
3. 方法解析
4. 接口方法解析

#### 7.3.5 初始化

### 7.4 类加载器

#### 7.4.1 类与类加载器

#### 7.4.2 双亲委派模型

#### 7.4.3 破坏双亲委派模型

### 7.5 Java模块化系统

#### 7.5.1 模块的兼容性

#### 7.5.2 模块化下的类加载器

### 7.6 本章小结

## 8 虚拟机字节码执行引擎

### 8.1 概述

### 8.2 运行时栈帧结构

#### 8.2.1 局部变量表

#### 8.2.2 操作数栈

#### 8.2.3 动态链接

#### 8.2.4 方法返回地址

#### 8.2.5 附加信息

### 8.3 方法调用

#### 8.3.1 解析

#### 8.3.2 分派

1. 静态分派
2. 动态分派
3. 单分派与多分派
4. 虚拟机动态分派的实现

### 8.4 动态类型语言支持

#### 8.4.1 动态类型语言

#### 8.4.2 Java与动态类型

#### 8.4.3 java.lang.invoke包

#### 8.4.4 invokedynamic指令

#### 8.4.5 实战：掌控方法分派规则

### 8.5 基于栈的字节码解释执行引擎

#### 8.5.1 解释执行

#### 8.5.2 基于栈的指令集与基于寄存器的指令集

#### 8.5.3 基于栈的解释器执行过程

### 8.6 本章小结

## 9 类加载及执行子系统的案例与实战

### 9.1 概述

### 9.2 案例分析

#### 9.2.1 Tomcat：正统的类加载器架构

#### 9.2.2 OSGI灵活的类加载器架构

#### 9.2.3 字节码生成技术与动态代理的实现

#### 9.2.4 Backport 工具：Java的时光机器

### 9.3 实战：自己动手实现远程执行功能

#### 9.3.1 目标

#### 9.3.2 思路

#### 9.3.3 实现

#### 9.3.4 验证

### 9.4 本章小结

## 10 前端编译与优化

### 10.1 概述

### 10.2 Javac编译器

#### 10.2.1 Javac的源码与调试

#### 10.2.2 解析与填充符号表

#### 10.2.3 注解处理器

#### 10.2.4 语义分析与字节码生成

### 10.3 Java 语法糖的味道

#### 10.3.1 泛型

#### 10.3.2 自动装箱、拆箱与遍历循环

#### 10.3.3 条件编译

### 10.4 实战：插入式注解处理器

#### 10.4.1 实战目标

#### 10.4.2 代码实现

#### 10.4.3 运行与测试

#### 10.4.4 其他应用案例

### 10.5 本章小结

## 11 后端编译与优化

### 11.1 概述

### 11.2 即时编译器

#### 11.2.1 解释器与编译器

#### 11.2.2 编译对象与触发条件

#### 11.2.3 编译过程

#### 11.2.4 实战：查看及分析即时编译结果

### 11.3 提前编译器

#### 11.3.1 提前编译的优劣得失

#### 11.3.2 实战：jaotc的提前编译

### 11.4 编译器优化技术

#### 11.4.1 优化技术概览

#### 11.4.2 方法内联

#### 11.4.3 逃逸分析

#### 11.4.4 公共子表达式消除

#### 11.4.5 数组边界检查消除

### 11.5 实战：深入理解Graal编译器

#### 11.5.1 历史背景

#### 11.5.2 构建编译调试环境

#### 11.5.3 JVMCI编译器接口

#### 11.5.4 代码中间表示

#### 11.5.5 代码优化与生成

### 11.6 本章小结

## 12 Java 内存模型与线程

### 12.1 概述

### 12.2 硬件的效率与一致性

### 12.3 Java内存模型

#### 12.3.1 主内存与工作内存

#### 12.3.2 内存间交互操作

#### 12.3.3 对于volatile型变量的特殊规则

#### 12.3.4 针对long和double型变量的特殊规则

#### 12.3.5 原子性、可见性与有序性

#### 12.3.6 先行发生原则

### 12.4 Java与线程

#### 12.4.1 线程的实现

#### 12.4.2 Java线程调度

#### 12.4.3 状态转换

### 12.5 Java与协程

#### 12.5.1 内核线程的局限

#### 12.5.2 协程的复苏

#### 12.5.3 Java 的解决方案

### 12.6 本章小结

## 13 线程安全与锁优化

### 13.1 概述

### 13.2 线程安全

#### 13.2.1 Java语言中的线程安全

1. 不可变
2. 绝对线程安全
3. 相对线程安全
4. 线程对立

#### 13.2.2 线程安全的实现方法

1. 互斥同步
2. 非阻塞同步
3. 无同步方案

### 13.3 锁优化

#### 13.3.1 自旋锁与自适应自旋

#### 13.3.2 锁消除

#### 13.3.3 锁粗化

#### 13.3.4 轻量级锁

#### 13.3.5 偏向锁

### 13.4 本章小结

