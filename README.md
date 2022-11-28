# Comtrade Hello World App
This app gives user ability to see `Hello World` string translated
to multiple languages, manually or using Systran API.

## Docker usage

First build docker image with `docker build -t "hello" .`

Run docker image with `docker run -p 8007:1007 hello`.
You can change port by changing `8007` with port that you want.

## Usage with Maven
Build and run:
```
./mwnw spring-boot:run
```

## Profiles

In `src/main/java/resources/application.properties` use can choose
between two databases and two default language translation methods.

By changing first profile between `h2` and `postgres` he chooses if H2 or PostgreSQL is used as database.

By changing second profile between `api` and `database` he chooses if Systran API or database
is used as default translation method when clients user `/hello` and ``/secure/hello` endpoints.

## Configuration

If you need to change database params change them in `src/main/java/resources/application-h2.properties`
or `src/main/java/resources/application-postgres.properties`

## Systran

API key for Systran can be changed in `src/main/java/com/comtrade/helloApp/services/SystranService.java`.

## Secure pages

All secure pages are protected with:
- Username: `user`
- Password: `password`

## Pages and Endpoints

### `GET /hello-rest`
**Hello World REST endpoint**

Returns fixed JSON with `Hello world`

### `GET /hello`
**Hello World page**

Returns page with `Hello world` translated.

Translations can received by using query params.
- `language` Default translation is English (en).
- `source` Default translation source is defined by spring profile.

Examples:
- `GET /hello?language=it`
- `GET /hello?language=it&source=api`
- `GET /hello?language=it&source=database`

### `GET /secure/hello`
**Secure Hello World page**

Works the same as `GET /hello` including query params.


## Admin Page
Found at `GET /secure/translations`

It gives user ability to add and remove translations from database.


