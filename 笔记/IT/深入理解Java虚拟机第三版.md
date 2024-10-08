# 深入理解Java虚拟机第三版

## 1 走近Java

### 1.1 概述

### 1.2 Java技术体系

### 1.3 Java发展史

### 1.4 Java虚拟机家族

#### 1.4.1 虚拟机始祖：SunClassic/Exact VM

#### 1.4.2 武林盟主：HotSpot VM

#### 1.4.3 小家碧玉：Mobile/Embedded VM

#### 1.4.4 天下第二：BEA JRockit/IBM J9 VM

#### 1.4.5 软硬合璧：BEA Liquid VM/Azul VM

#### 1.4.6 挑战者：Apache Harmony/Google Android Dalvik VM

#### 1.4.7 没有成功，但并非失败：Mircosoft JVM及其他

#### 1.4.8 百家争鸣

### 1.5 展望Java技术的未来

#### 1.5.1 无语言倾向

#### 1.5.2 新一代即时编译器

#### 1.5.3 向Native迈进

#### 1.5.4 灵活的胖子

#### 1.5.5 语言语法持续增强

### 1.6 实战：自己编译JDK

#### 1.6.1 获取源码

#### 1.6.2 系统需求

#### 1.6.3 构建编译环境

#### 1.6.4 进行编译

### 1.7 本章小结

## 2 Java内存区域与内存溢出异常

### 2.1 概述

### 2.2 运行时数据区域

#### 2.2.1 程序计数器

#### 2.2.2 Java虚拟机栈

#### 2.2.3 本地方法栈

#### 2.2.4 Java堆

#### 2.2.5 方法区

#### 2.2.6 运行时常量池

#### 2.2.7 直接内存

### 2.3 HotSpot虚拟机对象探秘

#### 2.3.1 对象的创建

#### 2.3.2 对象的内存布局

#### 2.3.3 对象的访问定位

### 2.4 实战：OutOfMemoryError异常

#### 2.4.1 Java堆溢出

#### 2.4.2 虚拟机栈和本地方法栈溢出

#### 2.4.3 方法区和运行时常量池溢出

#### 2.4.4 本机直接内存溢出

### 2.5 本章小结

## 3 垃圾收集器与内存分配策略

### 3.1 概述

### 3.2 对象已死

#### 3.2.1 引用计数法

#### 3.2.2 可达性分析算法

#### 3.2.3 再谈引用

#### 3.2.4 生成还是死亡

#### 3.2.5 回收方法区

### 3.3 垃圾收集算法

#### 3.3.1 分代收集理论

#### 3.3.2 标记-清除算法

#### 3.3.3 标记-复制算法

#### 3.3.4 标记-整理算法

### 3.4 HotSpot的算法细节实现

#### 3.4.1 根节点枚举

#### 3.4.2 安全点

#### 3.4.3 安全区域

#### 3.4.4 记忆集与卡表

#### 3.4.5 写屏障

#### 3.4.6 并发的可达性分析

### 3.5 经典垃圾收集器

#### 3.5.1 Serial收集器

#### 3.5.2 ParNew收集器

#### 3.5.3 Parallel Scavenge收集器

#### 3.5.4 Serial Old收集器

#### 3.5.5 Prallel Old收集器

#### 3.5.6 CMS收集器

#### 3.5.7 Garbage First收集器

### 3.6 低延迟垃圾收集器

#### 3.6.1 Shenandoah收集器

#### 3.6.2 ZGC收集器

### 3.7 选择合适的垃圾收集器

#### 3.7.1 Epsilon收集器

#### 3.7.2 收集器的权衡

#### 3.7.3 虚拟机及垃圾收集器日志

#### 3.7.4 垃圾收集器参数总结

### 3.8 实战：内存分配与回收策略

#### 3.8.1 对象优先在Eden分配

#### 3.8.2 大对象直接进入老年代

#### 3.8.3 长期存活的对象进入老年代

#### 3.8.4 动态对象年龄判定

#### 3.8.5 空间分配担保

### 3.9 本章小结

## 4 虚拟机性能监控、故障处理工具

### 4.1 概述

### 4.2 基础故障处理工具

#### 4.2.1 jps:虚拟机进程状况工具

#### 4.2.2 jstat:虚拟机统计信息监视工具

#### 4.2.3 jinfo:Java配置信息工具

#### 4.2.4 jmap:Java内存映像工具

#### 4.2.5 jhat:虚拟机堆转储快照分析工具

#### 4.2.6 jstack:Java堆栈跟踪工具

#### 4.2.7 基础工具总结

### 4.3 可视化故障处理工具

#### 4.3.1 JHSDB：基于服务性代理的调试工具

#### 4.3.2 JConsole：Java监视与管理控制台

#### 4.3.3 VisualVM：多合-故障处理工具

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

#### 5.2.8 由安全点导致长时间停顿

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

#### 6.3.5 字段表集合

#### 6.3.6 方法表集合

#### 6.3.7 属性表集合

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

### 7.3 类加载的过程

#### 7.3.1 加载

#### 7.3.2 验证

#### 7.3.3 准备

#### 7.3.4 解析

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

#### 9.2.1 Tomcat:正统的类加载器架构

#### 9.2.2 OSGI：灵活的类加载器架构

#### 9.2.3 字节码生成技术与动态代理的实现

#### 9.2.4 Backport工具：Java的时光机器

### 9.3 实战：自动动手实现远程执行功能

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

### 10.3 Java语法糖的味道

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

#### 11.3.2 实战：Jaotc的提前编译

### 11.4 编译器优化技术

#### 11.4.1 优化技术概览

#### 11.4.2 方法内联

#### 11.4.3 逃逸分析

#### 11.4.4 公共子表达

#### 11.4.5 数组边界检查消除

### 11.5 实战：深入理解Graal编译器

#### 11.5.1 历史背景

#### 11.5.2 构建编译调试环境

#### 11.5.3 JVMCI编译器接口

#### 11.5.4 代码中间表示

#### 11.5.5 代码优化

### 11.6 本章小结

## 12 Java内存模型与线程

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

#### 12.5.3 Java的解决方案

### 12.6 本章小结

## 13 线程安全与锁优化

### 13.1 概述

### 13.2 线程安全

#### 13.2.1 Java语言中的线程安全

#### 13.2.2 线程安全的实现方法

### 13.3 锁优化

#### 13.3.1 自旋锁与自适应自旋

#### 13.3.2 锁消除

#### 13.3.3 锁粗化

#### 13.3.4 轻量级锁

#### 13.3.5 偏向锁

### 13.4 本章小结
