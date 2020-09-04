# circuit
一款支持注解的熔断器。

使用方法请参考com.circuit.circuitbreaker.sample包以及test目录的test2方法。

熔断器通过配置文件配置暂未实现。
com.circuit.circuitbreaker.breakpolicy包下的熔断策略实现了简单的滑动窗口策略。
com.circuit.circuitbreaker.openpolicy包下的主要是熔断恢复策略。目前实现的简单定时重启。
其他策略暂还不支持。
