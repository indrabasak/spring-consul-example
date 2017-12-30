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

### Consul Architecture
Consul Agents servers are run in a cluster that communicates via 
[gossip protocol](https://www.consul.io/docs/internals/gossip.html) and uses
 [Raft consensus protocol](https://www.consul.io/docs/internals/consensus.html).


- **Agent** is the long running daemon on every member of the Consul cluster. 

- **Client** is an agent that forwards all RPCs to a server. It is relatively stateless. 

- **Server** is an agent with an extra set of responsibilities including  
participating in the Raft quorum, maintaining cluster state, responding to 
RPC queries, data persistence, etc.

- **Consensus** implies the consistency of a replicated state machine.

- **Gossip** is the protocol used by Consul for communication between nodes.

![](./img/consul-architecture.svg)
   
### Consusl Agent Installation
You can find instructions for installing a Consul Agent 
[here](https://www.consul.io/docs/install/index.html).

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

- `-bind` - binds the IPv4 address to the agent. In this example, it is 
bound to the `localhost`.

- `-bootstrap-expect` represents the number of expected servers in the 
datacenter. This flag is required in `-server` mode. In this example, it is 
specified as `1`.

- `-data-dir` provides a data directory for the agent to store state.
In this example, it points to the `data` folder. It is located under the same
directory where the agent is running.

- `-node` gives a name to this node in the cluster. This must be unique within 
the cluster. By default this is the hostname of the machine. In this example,
it is named `agent-one`.

- `-ui` enables the built-in web UI server and the required HTTP routes.

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

### Spring Boot Client 
You can find more information about Spring Boot Client [here](./client/README.md).


[travis-badge]: https://travis-ci.org/indrabasak/spring-consul-example.svg?branch=master
[travis-badge-url]: https://travis-ci.org/indrabasak/spring-consul-example/