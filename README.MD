## **Документация**

* [План автоматизации тестирования](https://github.com/DmitrenkoAlex/QA_Remastered/blob/master/documentation/Plan.md)
* [Отчет по итогам автоматизированного тестирования](https://github.com/DmitrenkoAlex/QA_Remastered/blob/master/documentation/Report.md)
* [Отчет по итогам автоматизации](https://github.com/DmitrenkoAlex/QA_Remastered/blob/master/documentation/Summary.md)

## **Процедура запуска авто-тестов**

### Перед запуском авто-тестов необходимо установить:

[Docker Desktop](https://www.docker.com/products/docker-desktop)

### Запуск авто-тестов:

1. Запустить контейнеры СУБД MySQl, PostgerSQL и Node.js командой в терминале:

_**docker-compose up -d**_


2. Запустить SUT, работающей на СУБД MySQl командой:


_**java -Dspring.datasource.url=jdbc:mysql://localhost:3306/app -jar artifacts/aqa-shop.jar**_


на СУБД PostgreSQL командой:


_**java -**_


3. В новом терминале запустить авто-тесты командой для MySQL:


_**gradlew clean test -Ddb.url=jdbc:mysql://localhost:3306/app**_


для PostgreSQL:


_**gradlew clean test -Ddb.url=jdbc:postgresql://localhost:5432/app**_


4. Сгенерировать отчеты двумя командами:


_**gradlew allureReport**_



_**gradlew allureServe**_


5. Остановить и удалить все контейнеры командой:


_**docker-compose down**_
