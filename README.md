[![Build Status][travis-badge]][travis-badge-url]

![](./img/consul-logo.png)

Spring HashiCorp Consul Example
==================================
This is an example of using [HashiCorp Consul](https://www.consul.io/) with a
Spring Boot application. 

### Introduction to Consul
A Consul is a tool for _service discovery_ and _service configuration_. 
It provides the following functionalities:

- **Service Discovery:** Consul can help a Spring REST service to discover
other services.

- **Health Check:** Consul provides ways to check the health of services 
registered with it.

- **KV Store:** Consul provides a _hierarchical key/value store_ for 
configuration and metadata.

#### Consul Architecture
Consul Agents servers are run in a cluster that communicates via 
[gossip protocol](https://www.consul.io/docs/internals/gossip.html) and uses
 [Raft consensus protocol](https://www.consul.io/docs/internals/consensus.html).

#### Consul Agent

- Configuration is loaded by a Sprint REST service during **bootstrap** phase.



### Consul K/V Configuration
Configurations in Consul K/V store are stored in `/config` folder 
(logical data folder - not physical) by default.

For example, the properties of an application named `spring-consul-example`
will be stored under folder,

`config/spring-consul-example/`

The properties of application named `spring-consul-example` and with 
`dev` profile will be stored under folder,

`config/spring-consul-example,dev/`

Proerties in `config/application` is applicable to all applications.

#### Configuring Spring REST Service to Use Consul Configuration
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
You can store data in YAML format as a blob with an appropriate data key. 
For example the properties for application `spring-consul-example` can be stored
as JSON with `data` as the data key

```
config/spring-consul-example/data
```

```yaml
cassandra:
  host: 127.0.0.1:9042,127.0.0.2:9042
  user: my_user
  password: my_pass
```

If you store data as a YAML blob, you need to set the following properties in
`bootstrap.yml`,

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

### Running Consul Agent
The Consul agent is started with the consul agent command. This command blocks, 
running forever or until forced to quit by sending the process an interrupt 
signal (usually `Ctrl-C`). 

If you want to persist Consul K/V data in the file system, the consul agent 
should be started in the `server` mode. If you don't care about peristence in
the file system, you can start in the `dev` mode.

```
./consul agent -server -bind=127.0.0.1 -data-dir=./data -bootstrap-expect=1 -node=agent-one -ui
```

- `-bind` - The IPv4 address bound to the agent. In this example, it is 
bound to the `localhost`.

- `-bootstrap-expect` - It represents the number of expected servers in the 
datacenter. This flag is required in `-server` mode. In this example, it is 
specified as `1`.

- `-data-dir` - This flag provides a data directory for the agent to store state.
In this example, it points to the `data` folder. It is located under the same
directory where the agent is running.

- `-node` - The name of this node in the cluster. This must be unique within 
the cluster. By default this is the hostname of the machine. In this example,
it is named `agent-one`.

- `-ui` - It enables the built-in web UI server and the required HTTP routes.

Once the consul agent starts up, you should notice the following on the terminal,

```
xyz$ ./consul agent -server -bind=127.0.0.1 -data-dir=./data -bootstrap-expect=1 -node=agent-one  -ui
  BootstrapExpect is set to 1; this is the same as Bootstrap mode.
  bootstrap = true: do not enable unless necessary
  ==> Starting Consul agent...
  ==> Consul agent running!
             Version: 'v1.0.1'
             Node ID: 'xyz01908-3c86-8534-442b-540fb885676d'
           Node name: 'agent-one'
          Datacenter: 'dc1' (Segment: '<all>')
              Server: true (Bootstrap: true)
         Client Addr: [127.0.0.1] (HTTP: 8500, HTTPS: -1, DNS: 8600)
        Cluster Addr: 127.0.0.1 (LAN: 8301, WAN: 8302)
             Encrypt: Gossip: false, TLS-Outgoing: false, TLS-Incoming: false
  
  ==> Log data will now stream in as it occurs:
  
      2017/12/14 22:31:18 [INFO] raft: Initial configuration (index=1): [{Suffrage:Voter ID:xyz01908-3c86-8534-442b-540fb885676d Address:127.0.0.1:8300}]
      2017/12/14 22:31:18 [INFO] raft: Node at 127.0.0.1:8300 [Follower] entering Follower state (Leader: "")
      2017/12/14 22:31:18 [INFO] serf: EventMemberJoin: agent-one.dc1 127.0.0.1
      2017/12/14 22:31:18 [WARN] serf: Failed to re-join any previously known node
      2017/12/14 22:31:18 [INFO] serf: EventMemberJoin: agent-one 127.0.0.1
      2017/12/14 22:31:18 [WARN] serf: Failed to re-join any previously known node
      2017/12/14 22:31:18 [INFO] consul: Handled member-join event for server "agent-one.dc1" in area "wan"
      2017/12/14 22:31:18 [INFO] consul: Adding LAN server agent-one (Addr: tcp/127.0.0.1:8300) (DC: dc1)
      2017/12/14 22:31:18 [INFO] agent: Started DNS server 127.0.0.1:8600 (tcp)
      2017/12/14 22:31:18 [INFO] agent: Started DNS server 127.0.0.1:8600 (udp)
      2017/12/14 22:31:18 [INFO] agent: Started HTTP server on 127.0.0.1:8500 (tcp)
      2017/12/14 22:31:18 [INFO] agent: started state syncer
```


### Build
To build the JAR, execute the following command from the parent directory:

```
mvn clean install
```

### Run
To run the application fromm command line,

```
java -jar target/spring-security-consul-example-1.0.0.jar
```

### Usage



[travis-badge]: https://travis-ci.org/indrabasak/spring-consul-example.svg?branch=master
[travis-badge-url]: https://travis-ci.org/indrabasak/spring-consul-example/