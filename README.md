Advanced Java EE project - library
==================================
The goal of this project is demonstrate knowledge gained during summer semester 2013 at fi.muni.cz. Usage of jboss java techs is required.

- CDI
- JSF
- JPA2

How to lunch DB in netbeans:

1. New connection
  - in services add new database. Right click on Database and than New Connection. Select Java DB (Network) and click next. On next page insert host as 'localhost' (127.0.0.1), port '1527', database: 'jboss-book', username: 'root' and password: 'toor'.
  - start the DB with right click on jboss-book under Databases and click 'Start server'
  - test jboss-book-persistence projekt and everything should be working

2. Use already present connection drivers 
Other solution is to use the present driver by just changing properties of the connection
  - right click on Java DB in services panel under Databases
  - in this screen change the Java DB Installation path to db-derby under jboss-book folder ('YOUR_PATH_TO_PROJECT/jboss-book/db-derby') and Database location path to database folder under jboss-book/db-derby ('YOUR_PATH_TO_PROJECT/jboss-book/db-derby/database')

The most important part is to start db server.
