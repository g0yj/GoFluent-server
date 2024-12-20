# English Channel Server
* [API Documentation](https://unialto.github.io/lms-api-doc/)

## Server Start
Windows Server\바탕화면\api-start 클릭

api-start.bat 내용
```bash
@echo off

chcp 65001

cd c:\workspace\lms-api

git pull & gradlew clean -x test build & java -Dspring.profiles.active=dev -jar build\libs\lms-api.jar
```

## 비밀번호 해시
* [Bcrypt 단방향 해시](https://ko.wikipedia.org/wiki/Bcrypt)
