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

### 1.4 Java 虚拟机家族

#### 1.4.1 Sun Classic/Exact VM

#### 1.4.2 HotSpot VM

#### 1.4.3 Mobile/Embedded VM

#### 1.4.4 BEA JRockit/IBM J9 VM

#### 1.4.5 BEA Liquid VM/Azul VM

#### 1.4.6 Apache Harmony/Google Android Daivik VM

#### 1.4.7 Microsoft JVM

#### 1.4.8 其他

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

#### 1.6.5 在 IDE 工具中进行源码调试

### 1.7 本章小结

## 2 Java 内存区域与内存溢出异常

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

```

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

Code: 014, 0358 就是程序计数器

个人理解：程序计数器就是字节码的断点？

#### 2.2.2 Java 虚拟机栈

