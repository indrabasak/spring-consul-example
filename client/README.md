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

### Configuration with Consul
A Spring Boot service can take advantage of Consul's KV store for storing its
configuration properties. The Configurations in Consul KV store are 
usually stored under `/config` folder (data folder and not filse system folder).

For example, the properties of an application named `spring-consul-example`
will be stored under folder,

`config/spring-consul-example/`

The properties of application named `spring-consul-example` and with 
`dev` profile will be stored under folder,

`config/spring-consul-example,dev/`

Proerties in `config/application` is applicable to all applications.

The following changes need to be made to the `bootstrap.yml` to enable 
Consul configuration,

```yaml
spring:
  cloud:
    consul:
      config:
        enabled: true
        prefix: config
        profileSeparator: '::'
```

- Setting `enabled` to `true` enables Consul Config
- `prefix` sets the base folder for configuration values which in this example 
is `config`
- `profileSeparator` sets the value of the separator used to separate the 
profile name in property sources with profiles


#### YAML Configuration
The configuration properties can be stored as a YAML blob with an appropriate 
data key in Consul KV store. For example the properties for the application 
`spring-consul-example` can be stored as JSON blob with `data` as the data key,

```
config/spring-consul-example/data
```

The YAML blob can look something as shown below,

```yaml
cassandra:
  host: 127.0.0.1:9042,127.0.0.2:9042
  user: my_user
  password: my_pass
```

If the data is stored as a YAML blob, following properties need to be set in
the `bootstrap.yml`,

1. `spring.cloud.consul.config.format` to `YAML`
1. `spring.cloud.consul.config.data-key` to `data`

```yaml
spring:
  cloud:
    consul:
      config:
        format: YAML
        data-key: data
```   

The properties can be inserted either manually from the Consul UI or via REST 
call. To set the properties manually,  