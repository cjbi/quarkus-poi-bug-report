# quarkus-poi-bug-report

https://github.com/quarkiverse/quarkus-poi/issues/95

## Reproducer

1. build native image

```shell
./mvnw -B -U clean package -Dmaven.test.skip=true -Pnative -Dquarkus.container-image.build=true -Dquarkus.native.builder-image=quay.io/quarkus/ubi-quarkus-mandrel-builder-image:jdk-21 -Dquarkus.container-image.image=cjbi/quarkus-poi-test
```

2. docker run

```shell

docker run cjbi/quarkus-poi-test

```

3. then, occurred exception

```
__  ____  __  _____   ___  __ ____  ______ 
 --/ __ \/ / / / _ | / _ \/ //_/ / / / __/ 
 -/ /_/ / /_/ / __ |/ , _/ ,< / /_/ /\ \   
--\___\_\____/_/ |_/_/|_/_/|_|\____/___/   
2024-01-23 09:21:49,060 WARN  [io.qua.config] (main) Unrecognized configuration key "quarkus.http.host" was provided; it will be ignored; verify that the dependency extension for this configuration is set or that you did not make a typo
2024-01-23 09:21:49,061 INFO  [io.quarkus] (main) quarkus-poi-bug-report 1.0.0-SNAPSHOT native (powered by Quarkus 3.6.6) started in 0.007s. 
2024-01-23 09:21:49,061 INFO  [io.quarkus] (main) Profile prod activated. 
2024-01-23 09:21:49,062 INFO  [io.quarkus] (main) Installed features: [awt, cdi, poi, smallrye-context-propagation]
2024-01-23 09:21:49,062 INFO  [ListenerBean] (main) ------------Application started
Exception in thread "main" java.lang.NoClassDefFoundError: Could not initialize class java.awt.Toolkit
        at java.desktop@21.0.1/java.awt.Color.<clinit>(Color.java:277)
        at org.apache.poi.hssf.util.HSSFColor$HSSFColorPredefined.<init>(HSSFColor.java:113)
        at org.apache.poi.hssf.util.HSSFColor$HSSFColorPredefined.<clinit>(HSSFColor.java:55)
        at org.apache.poi.hssf.model.InternalWorkbook.createExtendedFormat(InternalWorkbook.java:1406)
        at org.apache.poi.hssf.model.InternalWorkbook.createCellXF(InternalWorkbook.java:892)
        at org.apache.poi.hssf.usermodel.HSSFWorkbook.createCellStyle(HSSFWorkbook.java:1303)
        at org.apache.poi.hssf.usermodel.HSSFWorkbook.createCellStyle(HSSFWorkbook.java:126)
        at Main$ExcelDataExporter.title(Main.java:74)
        at Main$ExcelDataExporter.run(Main.java:58)
        at Main_ExcelDataExporter_ClientProxy.run(Unknown Source)
        at io.quarkus.runtime.ApplicationLifecycleManager.run(ApplicationLifecycleManager.java:132)
        at io.quarkus.runtime.Quarkus.run(Quarkus.java:71)
        at io.quarkus.runtime.Quarkus.run(Quarkus.java:44)
        at Main.main(Main.java:25)
```
