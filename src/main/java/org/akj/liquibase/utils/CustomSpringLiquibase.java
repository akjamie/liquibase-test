package org.akj.liquibase.utils;

import liquibase.Contexts;
import liquibase.Liquibase;
import liquibase.configuration.ConfigurationProperty;
import liquibase.configuration.GlobalConfiguration;
import liquibase.configuration.LiquibaseConfiguration;
import liquibase.database.Database;
import liquibase.exception.DatabaseException;
import liquibase.exception.LiquibaseException;
import liquibase.integration.spring.SpringLiquibase;
import liquibase.logging.LogService;
import liquibase.logging.LogType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Component
@Slf4j
@ConfigurationProperties(prefix = "spring.liquibase")
public class CustomSpringLiquibase extends SpringLiquibase {
    @Value("${build.version}")
    private String version;

    @Autowired
    protected DataSource dataSource;

    @Override
    public void afterPropertiesSet() throws LiquibaseException {
        log.debug("enter {}.{}...", CustomSpringLiquibase.class, "afterPropertiesSet");
        ConfigurationProperty shouldRunProperty = LiquibaseConfiguration.getInstance()
                .getProperty(GlobalConfiguration.class, GlobalConfiguration.SHOULD_RUN);

        if (!shouldRunProperty.getValue(Boolean.class)) {
            LogService.getLog(getClass()).info(LogType.LOG, "Liquibase did not run because " + LiquibaseConfiguration
                    .getInstance().describeValueLookupLogic(shouldRunProperty) + " was set to false");
            return;
        }
        if (!shouldRun) {
            LogService.getLog(getClass()).info(LogType.LOG, "Liquibase did not run because 'shouldRun' " + "property " +
                    "was set " +
                    "to false on " + getBeanName() + " Liquibase Spring bean.");
            return;
        }

        Connection c = null;
        Liquibase liquibase = null;
        try {
            c = dataSource.getConnection();
            liquibase = createLiquibase(c);
            performUpdate(liquibase);

            //performTag
            performTag(liquibase);
        } catch (SQLException e) {
            throw new DatabaseException(e);
        } finally {
            Database database = null;
            if (liquibase != null) {
                database = liquibase.getDatabase();
            }
            if (database != null) {
                database.close();
            }
        }

        log.debug("exit {}.{}...", CustomSpringLiquibase.class, "afterPropertiesSet");
    }

    private void performTag(Liquibase liquibase) throws LiquibaseException {
        log.debug("enter {}.{}...",CustomSpringLiquibase.class, "performTag");
        if (liquibase.tagExists(version)) {
            liquibase.rollback(version, new Contexts(getContexts()));
            log.debug("tag {} exits, rollback database version to version:{}", version,version);
        } else {
            liquibase.tag(version);
            log.debug("tag database version to {}", version);
        }
    }
}
