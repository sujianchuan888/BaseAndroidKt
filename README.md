# kotlin 语言编写的android MVVM模式基础框架（欢迎提意见帮助改进）

#### 介绍
该项目基于MVVM项目MVVMHabit，由于公司开发要求使用kotlin语言，个人封装作为日后开发框架，当前仅集成了MVVM基础框架，未添加其他基础工具类

#### 软件架构
基于MVVM框架的kotlin开发框架


#### 安装教程

1.  
项目根目录build.gradle中添加
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
2.  
项目module中导入远程依赖
	dependencies {
	        //implementation 'com.github.sujianchuan888:BaseAndroidKt:1.0'
	        
	}
3.  项目中使用


