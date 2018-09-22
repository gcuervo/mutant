FROM  openjdk:8
ADD target/mutant.jar  mutant.jar
EXPOSE  8090
ENTRYPOINT  ["java","-jar","mutant.jar"]