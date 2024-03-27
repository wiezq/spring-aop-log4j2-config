# Приложение для демонстрации работы Spring AOP и конфигурации логирования через  log4j2

## Описание
В приложении написан простой сервис, в котором есть 3 сущности: User, Product, Order и CRUD операции для них.

Стек технологий:
- Spring Boot
- Spring Data JPA
- H2 Database
- Spring AOP
- log4j2
- JUnit
- Gradle
- Lombok

## Spring AOP
Для демонстрации работы Spring AOP добавлен аспект `LogginAspect`, которые логируют все CRUD операции c аннотацией
Loggable.

```java
@Aspect
@Component
@Slf4j
public class LoggingAspect {

    @Pointcut("within(@com.example.t1_task3.aspect.Loggable *)")
    public void loggingPointcut() {
    }

    // ... методы для логирования
}
```

Класс `LoggingAspect` и аннотация `Loggable`  находится в пакете `com.example.t1_task3.aspect`.

## Конфигурация логирования

Для добавления log4j2 в проект необходимо добавить зависимость в `gradle.build` и убрать зависимость от logback.

```gradle
implementation 'org.springframework.boot:spring-boot-starter-log4j2'
configurations {
	all {
		exclude group: 'org.springframework.boot', module: 'spring-boot-starter-logging'
	}
}
```

Для конфигурации логирования через log4j2 добавлены файлы `log4j2.xml` и `log4j2-test.xml` в папку `resources`.

Пример конфигурации логирования в файле `log4j2-test.xml`:
```xml
<?xml version="1.0" encoding="UTF-8"?>
<Configuration status="INFO">
    <Appenders>
        <File name="File" fileName="logs/test/spring-boot.log">
            <PatternLayout pattern="%d{yyyy-MM-dd HH:mm:ss.SSS} [%t] %-5level %logger{36} - %msg%n"/>
        </File>
        <File name="AspectFile" fileName="logs/test/aspect.log">
            <PatternLayout pattern="%d{yyyy-MM-dd HH:mm:ss.SSS} [%t] %-5level %logger{36} - %msg%n"/>
        </File>
    </Appenders>
    <Loggers>
        <Logger name="com.example.t1_task3.aspect.LoggingAspect" level="info" additivity="false">
            <AppenderRef ref="AspectFile"/>
        </Logger>
        <Root level="info">
            <AppenderRef ref="File"/>
        </Root>
    </Loggers>
</Configuration>
```
Для демонстрации работы логирования все логи приложения будут записываться в файл `spring-boot.log`, а логи аспекта в файл `aspect.log` в папке logs.

## Запуск приложения
Для запуска тестов приложения и просмотра настроенного аспекта необходимо выполнить команду в корне проекта:

Windows
```shell
.\gradlew.bat test
```
Linux / MacOS
```shell
./gradlew test
```
После выполнения тестов в папке проекта появится папка `logs` с файлами логов.

Пример лога аспекта:
```
2024-03-27 13:41:25.385 [Test worker] INFO  com.example.t1_task3.aspect.LoggingAspect - Calling method: createUser with parameters: (name=John, email=john@example.com)
2024-03-27 13:41:25.418 [Test worker] INFO  com.example.t1_task3.aspect.LoggingAspect - Method createUser returned: UserEntity{id=1, name='John', email='john@example.com'}
2024-03-27 13:41:25.418 [Test worker] INFO  com.example.t1_task3.aspect.LoggingAspect - Calling method: createProduct with parameters: (name=Product 1, price=100.0)
2024-03-27 13:41:25.420 [Test worker] INFO  com.example.t1_task3.aspect.LoggingAspect - Method createProduct returned: ProductEntity{id=1, name='Product 1', price=100.0}
2024-03-27 13:41:25.421 [Test worker] INFO  com.example.t1_task3.aspect.LoggingAspect - Calling method: createProduct with parameters: (name=Product 2, price=200.0)
```