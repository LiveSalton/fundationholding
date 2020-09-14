# 性能分析：
### 1. 崩溃
1. java崩溃:Thread.setDefaultUncaughtExceptionHandler(this);
2. native崩溃: SIGABRT\SIGBUS\SIGFPE\SIGILL\SIGSEGV\SIGTRAP\SIGSYS\SIGSTKFLT,开启子进程去dump对应的tombstone

### 2. 卡顿
1. leak：
    1. leakcanary->hprof:Debug.dumpHprofData(filename)
    2. KeyedWeakReference:可达性分析判断
    3. HeapAnalyzerService:子进程使用haha库分析
2. anr:
    1. ActivityMangerService发出SIGQUIT信号，然后dump对应的tombstone
    2. FileObserver:data/anr/
3. block:
    1. Looper.getMainLooper().setMessageLogging(mMonitorCore);
    2. print前后两次调用表示一次帧率刷新，如果一次帧率刷新时间超过300ms。
    3. 在一次帧率刷新期间，间隔100ms采集一次堆栈
4. 帧率：
    1. Choreographer.getInstance().postFrameCallback(this)
    2. 回调一次callback表示渲染一帧，一秒内应该有60帧。
    3. 统计一秒内渲染次数，确定是否丢帧
5. cpu使用率：
    1. 8.0以下：定时读取/proc/stat解析cpu使用率
    2. 8.0以上：Runtime.getRuntime().exec("top -n 1")
6. 耗时：
    1. hook:android.app.ActivityThread
    2. 取其中handler的callback，处理对应msg。
    3. 通过registerActivityLifeCycleCallback

### 3. 内存
1. 占用:
    1. 大于Q->Debug.getMemoryInfo
    2. mActivityManager.getProcessMemoryInfo(new int[]{Process.myPid()})

### 4. 线程
1. 线程监控：统计、创建、销毁