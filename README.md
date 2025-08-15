# TechnicalChallenge_SegSat

## Sobre

Este repositório contém o desafio técnico “SegSat”.

## Como executar o projeto

### 1) Clonar o repositório: 
```bash
git clone https://github.com/xupenio04/TechnicalChallenge_SegSat.git
cd sensordata
```

### 2) Para executar o docker,cdentro do diretório `/sensordata`, digite no terminal:

```bash
   docker compose up -d
```

### 3) Executar o servidor: para executar o serviço oferecido pelo `Spring Boot`, basta executar o seguinte comando dentro do diretório `/sensordata`:

```bash
   ./mvnw spring-boot:run
```

### 4) Para executar o tópico Kafka e enviar mensagem, é necessário dar primeiro um split no terminal, de forma que deixe o serviço do `Spring Boot` rodando.


### 5) Para rodar o kafka, execute o seguinte comando: 
 ```bash
    docker exec -it kafka kafka-console-producer  
    --topic sensor-topic   
    --bootstrap-server localhost:9092
```
#### Após isso, é possível enviar, via tópico Kafka, mensagens que serão ser armazenadas tanto no `Kafka` quanto no bando de dados do `PostgreSQL`

#### 6) Após rodar o Kafka, podemos passa informações via terminal no kafka para que elas sejam armazenadas no banco de dados. Podemos inserir informações de um sensor, como por exemplo:
```bash
   1,sensor-1,23.5,45.2
   2,sensor-2,25.1,50.0    
```
#### Isso para o tópico Kafka as informações daquele sensor, como ID, nome identificador, temperatura e umidade, respectivamente.

### 7) Para rodar o PostgreSQL, execute o seguinte comando: 
 ```bash
    docker exec -it sensor-postgres psql -U postgres -d sensordb -c "SELECT * FROM sensor_data;"
```

#### Esse comando mostrará todo o banco de dados com todas as informações do sensores, como temperatura, umidade e identicadores.

### 8) Para os endpoints requisitos, foram testados por meio da extensão do `POSTMAN`, sendo todas as requisições do tipo `GET`. Aqui seguem alguns do EndPoint:

## Endpoints da API

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| **GET** | `/api/sensor-data` | Retorna todos os dados registrados |
| **GET** | `/api/sensor-data/by-sensor/{sensorId}` | Retorna todos os dados de um sensor específico |
| **GET** | `/api/sensor-data/stats/{sensorId}` | Retorna estatísticas (média, mínima e máxima) de um sensor |
| **GET** | `/api/sensor-data/by-sensor/{sensorId}/latest` | Retorna a última leitura de um sensor |

### Exemplos usando `curl`

#### Obter todos os dados:
```bash
curl "http://localhost:8080/api/sensor-data"
```

#### Obter dados de um sensor específico:
```bash
curl "http://localhost:8080/api/sensor-data/by-sensor/sensor-1"
```

####  Obter estatísticas de um sensor:
```bash
curl "http://localhost:8080/api/sensor-data/stats/sensor-1"
```

#### Obter a última leitura de um sensor:
```bash
curl "http://localhost:8080/api/sensor-data/by-sensor/sensor-1/latest"
```



### 9) Ferramentas de IA, como ChatGPT, foram utilizadas para a direcionamento de alguns arquivos, como o `docker-compose.yml` e também o `application.yml`, principalmente para tirar dúvidas de certas campos na construção e também em como conseguir configurar o Kafka na maneira que os dados foram extraídos. 











