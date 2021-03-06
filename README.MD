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


2. Запуск приложения для MySQL:


_**java -Dspring.datasource.url=jdbc:mysql://localhost:3306/app -jar artifacts/aqa-shop.jar**_


Запуск приложения для PostgreSQL:


_**java -Dspring.datasource.url=jdbc:postgresql://localhost:5432/app -jar artifacts/aqa-shop.jar**_


3. В новом терминале запустить авто-тесты командой для MySQL:


_**gradlew clean test -Ddb.url=jdbc:mysql://localhost:3306/app**_


для PostgreSQL:


_**gradlew clean test -Ddb.url=jdbc:postgresql://localhost:5432/app**_


4. Сгенерировать отчеты двумя командами:


_**gradlew allureReport**_



_**gradlew allureServe**_


5. Для перезапуска приложения, тестов и/или отчёта (также при использования другой БД) необходимо выполнить остановку их работы, нажав в соответствующих вкладках терминала Ctrl+С или закрыть вкладки.

6. Остановить и удалить все контейнеры командой:


_**docker-compose down**_
