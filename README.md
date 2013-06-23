#Advanced Java EE project - library

The goal of this project is demonstrate knowledge gained during summer semester 2013 at fi.muni.cz. Usage of jboss java techs is required.

- [x] Team 4 members
- [x] github account
- [x] UML specification
- [x] 3 layer implementation
- [x] JSF
- [x] EJB
- [x] JPA2 persistence layer
- [x] CDI
- [x] 3 security roles
- [ ] Bean Validation
- [x] Seam 3
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
&lt;datasources&gt;
  &lt;datasource jndi-name="java:/DerbyDS" pool-name="DerbyDS" enabled="true" use-ccm="false"&gt;
    &lt;connection-url&gt;jdbc:derby://localhost:1527/jboss-book;create=true&lt;/connection-url&gt;
    &lt;driver&gt;org.apache.derby&lt;/driver&gt;
    &lt;security&gt;
      &lt;user-name&gt;root&lt;/user-name&gt;
      &lt;password&gt;toor&lt;/password&gt;
    &lt;/security&gt;
    &lt;validation&gt;
      &lt;validate-on-match&gt;false&lt;/validate-on-match&gt;
      &lt;background-validation&gt;false&lt;/background-validation&gt;
    &lt;/validation&gt;
    &lt;statement&gt;
      &lt;share-prepared-statements&gt;false&lt;/share-prepared-statements&gt;
    &lt;/statement&gt;
  &lt;/datasource&gt;
&lt;/datasources&gt;

&lt;drivers&gt;
 &lt;driver name="org.apache.derby" module="org.apache.derby"&gt;
   &lt;xa-datasource-class&gt;org.apache.derby.jdbc.ClientXADataSource&lt;/xa-datasource-class&gt;
 &lt;/driver&gt;
&lt;/drivers&gt;
</pre>

After that you can build the project and deploy ear archive to the server.

