package ru.yandex.practicum.configuration;

import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
public class DataBaseSchemaInitializer {

    private final DataSource dataSource;

    public DataBaseSchemaInitializer(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @EventListener
    public void initDataBase(ContextRefreshedEvent contextRefreshedEvent) {
        ResourceDatabasePopulator resourceDatabasePopulator = new ResourceDatabasePopulator();
        resourceDatabasePopulator.addScript(new ClassPathResource("schema.sql"));
        resourceDatabasePopulator.execute(dataSource);
    }
}
