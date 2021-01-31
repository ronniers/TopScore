# Top Score Ranking API

This API will be used to keep scores for a certain game for a group of player with the following actions:

### Create Score

#### Request

###### Endpoint
```
POST /score
```

###### Payload

* **player** a case-insensitive String containing the name of player. Thus, "Edo" or "edo" or "EDO" is considered as the same player.
* **score** an Integer strictly superior to 0 that represents the score.
* **time** a String representing date and time. The expected format is _dd MMM yyyy HH:mm:ss_. 

```json
{
  "player": "player",
  "score": 10,
  "dateTime": "4 Feb 2021 15:12:00"
}
```

#### Response

This returns the created Score with the additional ID.

```json
{
  "id": 1,
  "score": 9.0,
  "player": "player",
  "dateTime": "04 Feb 2021 15:12:00",
  "_links": {
    "self": {
      "href": "http://localhost:8080/score/1"
    }
  }
}
```

### Get Score

Using a simple id you can retrieve a score.

#### Request

###### Endpoint
```
GET /score/{id}
```

#### Response
```json
{
  "id": 1,
  "score": 10.0,
  "player": "player",
  "dateTime": "04 Feb 2021 15:12:00",
  "_links": {
    "self": {
      "href": "http://localhost:8080/score/1"
    }
  }
}
```

### Delete Score

Using a simple id you can delete a score.

#### Request

###### Endpoint
```
DELETE /score/{id}
```

### Get list of scores

The client can request a list of scores.

The list can be filtered by date before or after given times. It can be filtered by player names (multiple names can be provided for one query).

The list must support pagination. Hint: please use pagination feature provided by Spring framework ;)

Possible query on this endpoint include (but are not limited to):

* "Get all scores by playerX"
* "Get all score after 1st November 2020"
* "Get all scores by player1, player2 and player3 before 1st december 2020"
* "Get all scores after 1 Jan 2020 and before 1 Jan 2021"

#### Request

###### Endpoint

```
GET /score
```

###### Payload

* **player list** a list of Strings containing the name of players.
* **start date** a String representing the start of the filter range. The expected format is _dd MMM yyyy HH:mm:ss_.
* **end date** a String representing the end of the filter range. The expected format is _dd MMM yyyy HH:mm:ss_.

```json
{
  "player": ["player1","player2"],
  "startTime": "25 Jan 2021 09:00:00",
  "endTime": "4 Feb 2021 18:00:00"
}
```

#### Response

```json
{
  "_embedded": {
    "scoreList": [
      {
        "id": 1,
        "score": 10.0,
        "player": "player",
        "dateTime": "04 Feb 2021 15:12:00"
      }
    ]
  },
  "_links": {
    "self": {
      "href": "http://localhost:8080/score?page=0&size=20"
    }
  },
  "page": {
    "size": 20,
    "totalElements": 1,
    "totalPages": 1,
    "number": 0
  }
}
```

### Get players' history

The client can request a player history.

#### Request

###### Endpoint

```
GET /player/{player}
```

#### Response

* **top score** (time and score) which the best ever score of the player.
* **low score** (time and score) worst score of the player.
* **average score** value for player
* list of all the scores (time and score) of this player.

```json
{
  "topScore": {
    "score": 10.0,
    "time": "2021-02-04T15:12:00.000+00:00"
  },
  "lowScore": {
    "score": 9.0,
    "time": "2021-02-03T19:08:00.000+00:00"
  },
  "averageScore": 9.5,
  "playerScoreList": [
    {
      "score": 9.0,
      "time": "2021-02-03T19:08:00.000+00:00"
    },
    {
      "score": 10.0,
      "time": "2021-02-04T15:12:00.000+00:00"
    }
  ]
}
```

## Tools Used
* Java 13.0.2
* Gradle 6.7.1
* Spring 5.3.3
* Spring Boot 2.4.2
* H2 Database 1.4.200
* JUnit 5.6.3
* IntelliJ Community Edition 2020.3

## How To

1. Open command prompt or IntelliJ's Terminal
2. Go to project's directory

### Build
1. Execute below command
```bash
gradlew --exclude-task test
```

### Run
1. Execute below command
```bash
gradlew bootRun
```

### Test
1. Execute below command
```bash
gradlew test
```
