# первый вспомогательный этап
FROM amazoncorretto:21-alpine AS builder
WORKDIR application
COPY target/*.jar app.jar

# используем специальный режим запуска Spring Boot приложения,
# который активирует распаковку итогового jar-файла на составляющие
RUN java -Djarmode=layertools -jar app.jar extract

# создаем финальный образ
FROM amazoncorretto:21-alpine

# поочередно копируем необходимые для приложения файлы,
# котрые были распакованы из артефакта на предыдущем этапе;
# при этом каждая инструкция COPY создает новый слой
COPY --from=builder /application/dependencies/ ./
COPY --from=builder /application/spring-boot-loader ./
COPY --from=builder /application/snapshot-dependencies ./
COPY --from=builder /application/application ./

# в качестве команды указываем запуск специального загрузчика
ENTRYPOINT ["java", "org.springframework.boot.loader.launch.JarLauncher"]