# About Design And Architecture

According to hierarchical structure of data, the importance of data relationships, for example traverse between company nodes structure (parent - children) and performance issues to work with tons of nodes, I decide to use graph database as data storage.

First, because this database is effective for handling data relationships.

Second one, graph database in its nature work with graph and tree. Exactly what assessment want, Tree Structure.

As a graph database, I choose neo4j because it is the first one in a graph market, has a big community and has great features like scalable architecture, ACID compliance and high performance in both storage and processing.

### A Note:

All this solution can be done with rdbms databases like Oracle or MySQL and … but the way rdbms databases work, Is not appropriate for handling tree base structure. Because in this structure requirements are moving and travers between rows. in other words, in rdbms, relationships between data means join and join is expensive. then If we deal with tons of data probably need many joins and its hell. Graph database store relationship along with data and accessing relationship is as immediate as accessing the data itself.

# Technologies Stack:

- Neo4j as database

- SprintBoot2 as backend application

- Neo4j ogm as object graph mapper

- Docker as software platform to create, deploy, and run applications by using containers

# Design Model and Domain Entities:

### From neo perspective: (Design Model)

- we have two labels, CompanyNode and RootNode.

- all nodes have CompanyNode label.

- only one node can have RootNode label and this business rule should check when create or update nodes.

- only root node has two labels, CompanyNode and RootNode label.

- Each node has id (generate by database) and Name property, with one relationship of “ rel ” type to another one.  (:CompanyNode:RootNode) - [ :rel ] -> ( :CompanyNode)

- each node has multiple relation with another nodes as children’s.

- Each node has only one node as parent except root with no parent.

### From java perspective: (Design Entities)

- For handle labels and relationship between them, two classes with names of CompanyNode and RootNode are created in domain package.

- each class is mapped to related label by @NodeEntity(label = ‘’)

- Because of RootNode class extends CompanyNode class, RootNode class is mapped to node in neo that has RootNode and CompanyNode labels. With business rule should check don’t insert two rootnode in to database.

### CompanyNode entity class has two properties type:

1. One type is mapped by ogm annotations to graph labels and relationship along properties:

 
- id: Long (Mapped to id property in database node)

- name: String (Mapped to Name property in database node)

- parentNode: CompanyNode (Mapped to relation of type rel)

- childrenNodes: List<CompanyNode> (Mapped to relation of type rel)
       

2. another one is transient: not insert or query to database. they are calculating.

- root: RootNode

- height: Long

# About Root And Height

By Considering pros and cons of store these two properties (root & height) in database or calculate them I decide to calculate them because for example if height property store for every node in database, if parent of each node changed then all the children of that node along children of children should update their height property, this is bad. Also for example if all node store relationship to root node, first its redundant and if root node change, same problem as height property is exist.

according to this issue the important thing to calculate these two properties is performance. I decide to use RootNode label for root node and because of that only one node can have RootNode label, then root node can calculate with good performance and for calculate height I use shortestpath() method in neo4j to find shortest path between given node and root node, then length of the path is height. I think This property is also calculating with good performance. (but it must be tested with volume of data)

Because I don’t want entities have dependency to repositories class, then for calculate these two properties, some methods created in CompanyNodeRepository with suffix of …WithHeightAndRoot, that these methods additionally fetch data, calculate these properties and set it in appropriate fields. For example we have findAll() method that fetch all CompanyNode without root and height values and there is another method with the name of findAllWithHeightAndRoot() that return all data along root and height values. I implement this option for performance consideration that if we don’t need height or root of Company Node in process then don’t calculate them. also there is findRootNode() and findHeightOfNode(nodeId) methods in CompanyNodeRepository that when we want height or root of node we can use it.

# application structure (SpringBoot2) under com.tradeshift.companystructure package:

- controllers: include controller class to handle http request as rest services (CompanyNodeController)

- domain: include labels and relationships of graph objects as entity (CompanyNode) (RootNode)

- repositories: include all CRUD and query methods of entities (CompanyNodeRepository)

- services: include business rules and services to other parts (CompanyNodeService)(CompanyNodeValidation)

- Project is packaging with maven.

### CompanyNodeRepository:

This interface is located in com.tradeshift.companystructure.repositories.companynode package. Include methods for crud and query operations on CompanyNode entity. CompanyNodeRepositoryImpl is a concreate class of this interface. This class is implemented by neo4j ogm session.

#### Performance Tip:

Depth parameter in CompanyNodeRepository methods is very important and specify depth of load data. It’s important for performance issues.

### CompanyNodeService & CompanyNodeValidation:

These interfaces are located in com.tradeshift.companystructure.services.companynode package. CompanyNodeService consist of methods that serve Company Structure business rules to other parts of system like controllers and CompanyNodeValidation consist of validation and rules for this domain.

### CompanyNodeController:

HTTP API:

For handling http request to return children of given node and change parent, I used @RestController of spring framework. Then CompanyNodeController is created with three methods.

#### CompanyNodeController Methods:

1. getAllChildrenOfGivenNode: get node id from parameter and return ResponseEntity<List<CompanyNode>>.

2. changeParentNodeOfGivenNode: get node id and new parent id from parameter and change it.

3. isAlive: check server or container is up and running.
 
### Tips:

- changeParentNodeOfGivenNode done within a Transaction. it means update operation is done with one Transaction that handle by CompanyNodeRepository. In other words, during update if exception occur then all changes roll backed.

### HTTP API Endpoints:

1. http://localhost:8080/api/v1/companynodes/{id}/children (GET)

2. http://localhost:8080/api/v1/companynodes/{id/parent/{parentId}  (PUT)

3. http://localhost:8080/api/v1/isAlive  (GET)

these endpoints are mapped with CompanyNodeController methods.

# Setup Environment to build and run containerized application:

First need to install Docker. Then to build spring boot company-structure image add DockerFile to root of project and add needed key value data to it. For example dependency ,volume, ports expose , .jar location and command to run jar file. After that with docker build company-structure:latest . command, build image and with docker run -d -p 8080:8080 company-structure . command, image loaded and run as container.

Usually more than one container needed in development. for example, in this project we need neo4j container too and Probably other containers may need to use. For handling multiple containers, use Docker-compose for running all container together. Then add docker-compose.yml file to handle and running multiple containers and with docker-compose up command all in docker-compose.yml run .

After running and testing application container, if everything is ok then should deploy application image to image registry like Docker hub for using in test or production environment.

# Unit Test :

- CompanyNodeController is tested by junit , mockito and mvcMock.
- CompanyNodeRepositoryImpl is tested by junit.
