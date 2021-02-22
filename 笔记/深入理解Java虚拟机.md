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

**操作数栈**

**动态链接**

* 每个栈帧都包含一个指向运行时常量池（JVM 运行时数据区域）中该栈帧所属性方法的引用，持有这个引用是为了支持方法调用过程中的动态连接。
* 在 Class 文件格式的常量池（存储字面量和符号引用）中存有大量的符号引用（1.类的全限定名，2.字段名和属性，3.方法名和属性），字节码中的方法调用指令就以常量池中指向方法的符号引用为参数。这些符号引用一部分会在类加载过程的解析阶段的时候转化为直接引用（指向目标的指针、相对偏移量或者是一个能够直接定位到目标的句柄），这种转化称为静态解析。另外一部分将在每一次的运行期期间转化为直接引用，这部分称为动态连接。

#### 2.2.3 本地方法栈

**本地方法栈**与虚拟机栈所发挥的作用是非常相似的，其区别只是虚拟机执行 Java 方法，而本地方法栈则是为虚拟机使用到的本地(Native)方法服务。

#### 2.2.4 Java 堆

**Java 堆**是被所有线程共享的一块内存区域，在虚拟机启动时创建。此内存区域的唯一目的就是存放对象实例。Java 世界里“几乎”所有的对象实例都在这里分配内存。

#### 2.2.5 方法区

**方法区**与 Java 堆一样，是各个线程共享的内存区域，它用于存储已被虚拟机加载的**类型信息**、**常量**、**静态变量**、**即时编译器编译后的代码缓存数据**
