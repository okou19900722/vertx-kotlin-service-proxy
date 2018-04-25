# vertx-service-proxy-kotlin
vertx-service-proxy for kotlin

TODO

https://gist.github.com/nsk-mironov/8c9c3092e9844233cdbb
- [ ] AnnotationProcessor暂时没法区分List和MutableList，并且kotlin的泛型实现有通配符，而codegen不允许参数有通配符
- [ ] 构建kotlin的service method信息
- [ ] 

- [ ] 生成service proxy文件到指定目录
- [ ] 可配置codegen.output参数
- [ ] 重新构建kotlin信息(vertx-codegen提供的api参数信息有限)
- [ ] kotlin 的参数和返回值需要处理是否可空
- [ ] 完成handler.templ和proxy.templ
- [ ] 如果有时间，完成doc
- [ ] 如果有时间，看是否生成rx的代码