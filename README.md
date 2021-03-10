# Flutter总结

## Flutter简介

Flutter是Google使用Dart语言开发的移动应用（App）开发框架，使用一套Dart代码就能快速构建高性能、高保真的ios和Android应用程序，并且在排版、图标、滚动、点击等方面实现零差异。Flutter提供了丰富的组件、接口，开发者可以很快地为Flutter添加原生扩展。	

## Flutter的优势

### 1.文档丰富，能快速入手

相比较目前的混合开发方案，Flutter 提供了大量的文档，能非常快速且友好的让你加入到这个大家庭。

### 2.系统可定制性强

Flutter并不止 WebView，也用通过解释 JS 后去操作系统的原生控件，Flutter 核心只有一层轻量的 C/C++代码（Engine），Flutter 在 Dart 中实现了其他大部分系统（组合、手势、动画、框架、widget 等），因此，开发人员可以轻松地进行读取、更改、替换或移除等操作。这为开发人员提供了对系统的巨大可定制性。

![framework](https://pics3.baidu.com/feed/caef76094b36acaf1a73ed88e099d51600e99cfd.jpeg?token=87e1b5bd41fb32f69b6574d7f4c626cd&s=AFC3AA52CEEA1509542FF24C030070F5)

### 3.开发效率高

基于JIT的快速开发周期：Flutter在开发阶段采用JIT模式，这样就避免了每次改动都要进行编译，极大的节省了开发时间；（Dart运行时支持）

基于AOT的发布包：Flutter在发布时可以通过AOT生成高效的ARM代码以保证应用性能，而JavaScript则不具有这个能力。（编译器支持）

### 4.高性能

Flutter旨在提供流畅、高保真的UI体验。为了实现这一点，Flutter需要能够在每个动画帧中运行大量的代码，这意味着需要一种既能提供高性能的语言，又不会出现丢帧的周期性暂停，而Dart支持AOT，在这一点上可以做的比JavaScript更好。

### 5.快速内存分配

Flutter框架使用函数式流，这使得它在很大程度上依赖于底层的内存分配器。因此，拥有一个能够有效的处理琐碎任务的内存分配器将显得十分重要，如果Dart语言缺乏此功能，Flutter将无法有效地工作。当然Chrome V8的JavaScript引擎在内存分配上也已经做的很好了，事实上Dart开发团队的很多成员都是来自Chrome团队的，所以在内存分配上Dart并不能作为超越JavaScript的优势，但对于Flutter来说，它需要这样的特性，而Dart也正好满足而已。

### 6.类型安全

由于Dart是类型安全的语言，支持静态类型检测，所以可以在编译前发现一些类型的错误，并排除潜在问题，这一点对于前端开发者来说可能会更具有吸引力。与之不同的，JavaScript是一个弱类型语言，也因此前端社区出现了很多给JavaScript代码添加静态类型检测的扩展语言和工具，如：微软的TypeScript以及Facebook的Flow。相比之下，Dart本身就支持静态类型，就是它的一个重要优势。

### 7.Dart团队的支持

由于有Dart团队的积极投入，Flutter团队可以获得更多、更方便的支持，正如Flutter官网所诉：“我们正与Dart社区进行密切合作，以改进Dart在Flutter中的使用。例如，当我们最初使用Dart时，该语言并没有提供生成原生二进制文件的工具链（工具链对于实现可预测的高性能具有很大的帮助），但是现在它实现了，因为Dart团队专门为Flutter构建了它。同样，Dart VM之前已经针对吞吐量进行了优化，但团队现在正在优化VM的延迟时间，这对于Flutter的工作负载更为重要。”



总之，Flutter 作为新兴的混合开发解决方案，已经被广泛关注和使用，这不光是因为它有 Google 的支持，更因为它提供了更完善的文档和更高效的运行方式，开发者不必再把大量精力放到不同平台的展示上，更好的完成自己的业务，从企业管理者角度看，也大大降低了开发成本。

## Flutter的基本操作(Hello World)

```dart
import 'package:flutter/material.dart';
 
void main() {
  runApp(
    new Center(
      child: new Text(
        'Hello, world!',
        textDirection: TextDirection.ltr,
      ),
    ),
  );
}
```

* runApp()
  * 上面的实例代码中使用了 runApp() 方法，runApp 方法接收的指定参数类型为 Widget，即： runApp(Widget)。在 Flutter 的组件树(widget tree)中，会根据我们在 runApp 方法传入的 Widget (也就是Center控件)作为整个 App 的 根控件(root widget)。
* Widget
  * Center 控件使其子控件处于中间位置。
  * Text控件打印文本内容。
  * runApp 方法强制将根控件覆盖屏幕，这意味着文本“Hello World！”将显示在屏幕中心。
  * 布局控件（这里的Center）都有一个child属性用来接收子控件。

## 参考

[Flutter中文文档](https://flutter.cn/docs/get-started/flutter-for/android-devs)

[什么是Flutter](https://blog.csdn.net/weixin_33725722/article/details/91361838)

[为什么我们应该使用Flutter](https://blog.csdn.net/weixin_33725722/article/details/91361838)

[Flutter之HelloWord](https://blog.csdn.net/qq_18948359/article/details/82049734)