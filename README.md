#Advanced Java EE project - library
==================================
The goal of this project is demonstrate knowledge gained during summer semester 2013 at fi.muni.cz. Usage of jboss java techs is required.

- [x] Team 4 members
- [x] github account
- [x] UML specification
- [x] 3 layer implementation
- [x] JSF
- [x] EJB
- [x] JPA2 persistence layer
- [x] CDI
- [ ] 3 security roles
- [ ] Bean Validation
- [ ] Seam 3
- [ ] Arquillian
- [ ] 2 clusters and demonstration of failure of one
- [ ] deployment to PaaS OpenShift

##DB INSTALLATION

Download derby libraries from http://db.apache.org/derby/derby_downloads.html

Run Derby from command line using command:
java -jar derbyrun.jar server start

Setup your JBOSS AS datasource for the project. Tested with JBOSS AS 7.1.1.Final
http://www.hameister.org/JBoss_DatasourceDerby.html
You need to set your datasource like this:

<pre>
<datasources>
  <datasource jndi-name="java:/DerbyDS" pool-name="DerbyDS" enabled="true" use-ccm="false">
    <connection-url>jdbc:derby://localhost:1527/jboss-book;create=true</connection-url>
    <driver>org.apache.derby</driver>
    <security>
      <user-name>root</user-name>
      <password>toor</password>
    </security>
    <validation>
      <validate-on-match>false</validate-on-match>
      <background-validation>false</background-validation>
    </validation>
    <statement>
      <share-prepared-statements>false</share-prepared-statements>
    </statement>
  </datasource>
</datasources>
<drivers>
 <driver name="org.apache.derby" module="org.apache.derby">
   <xa-datasource-class>org.apache.derby.jdbc.ClientXADataSource</xa-datasource-class>
 </driver>
</drivers>
</pre>

After that you can build the project and run ear archive on server.

