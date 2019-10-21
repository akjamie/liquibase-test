# Useful command
- mvn liquibase:update -Dliquibase.updateCount=1 -Denv.context=h2 -Pliquibase
- mvn liquibase:rollback -Dliquibase.rollbackCount=1 -Denv.context=h2 -Pliquibase
- mvn liquibase:update -Dliquibase.updateCount=1 -Denv.context=mysql -Pliquibase

seems param for update will not take effect.  

<tag>${maven.build.timestamp}</tag>  
mvn clean liquibase:tag -Dliquibase.tag=20191021-v1 -Denv.context=mysql -Pliquibase

