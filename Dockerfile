FROM openjdk

EXPOSE 8081

ARG JAR_FILE=target/*.jar

COPY ${JAR_FILE} quotation-management.jar

ENTRYPOINT ["java","-jar","/quotation-management.jar"]