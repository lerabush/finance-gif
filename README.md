# finance-gif

### Запуск приложения

```
java -jar finance-gif-0.0.1-SNAPSHOT.jar
```
Запущенное приложение доступно по URL
```
http://localhost:8080/finance-gif
```
### API Endpoints

Получить список доступных валют: 

```
http://localhost:8080/finance-gif/get-currencies
```

Получить гифку, исходя из валюты: 

```
http://localhost:8080/finance-gif/get-gif
```
## Docker
Для создания образа используется следующая команда:

```
docker build -t finance-gif:0.0.1 .
```
Для запуска контейнера:

```
docker run -d -p 8080:8080 -t finance-gif:0.0.1
```
