Spring Cloud Consul Client
===========================

### Registration with Consul
A Spring Boot service can register with a Consul Agent by making the following
changes to `bootstrap.yml`,

```yaml
spring:
  cloud:
    consul:
      host: localhost
      port: 8500
```

The above changes can be made in the `application.yml` if your service doesn't
use Spring Cloud configuration functionalities of Consul.

### Health Check
A Consul Agent check the health of a Spring Boot application by using the
`/health` endpoint. 

If the service uses a non-default context path, the changes to the health endpoint
needs to be configured in `application.yml` . For example if the context path
is changed to `foo`, the following changes need to be made:

```yaml
spring:
  cloud:
    consul:
      discovery:
        healthCheckPath: foo/health
        healthCheckInterval: 15s
```

The interval used by Consul to check the health endpoint can also be configured. 

The latest version of Consul (1.0.2) shows a registered Spring Boot application 
to be in critical condition. 

![](./img/sevice-health-status-failing.png)

If you check the `/health` endpoint of your application, the consul health
will show up as down:

```json
{
    "consul": {
        "status": "DOWN",
        "services": {
            "consul": [
        
            ],
            "spring-consul-example": [
        
            ]
        },
        "error": "java.lang.IllegalArgumentException: Value must not be null"
    }
}
```

This problem can be mitigated by adding the following snippet in 
`bootstrap.yml`,

```yaml
management:
  security:
    enabled: false
  health:
    consul:
      enabled: false 
```

Once you make the above changes, the service shows up as passing.

![](./img/sevice-health-status-passing.png)