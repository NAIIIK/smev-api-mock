# smev-api-mock

A minimal stand-in for a SMEV-style passport verification service, built for
the [passport-inspector-platform](https://github.com/NAIIIK/passport-inspector-platform).
Exists purely so the platform can be run and demoed end-to-end without
access to a real SMEV integration.

## Stack

Java 21, Spring Boot 4.1, Prometheus/Micrometer.

## Running standalone

```bash
./mvnw spring-boot:run
```

Default port: `8081`. No database or other dependencies required.

## API

| Method | Path                         | Purpose                                 |
|--------|------------------------------|-----------------------------------------|
| `POST` | `/smev-api/v1/CheckPassport` | Check a passport; returns valid/invalid |

The result is **randomized on every call** - this mock doesn't validate
anything real, it exists to exercise the async check flow in
`ms-passport-inspector` end-to-end. No authentication is enforced.

## Monitoring

`/actuator/health`, `/actuator/info`, and `/actuator/prometheus` are
available (this service has no security layer, so nothing is gated).