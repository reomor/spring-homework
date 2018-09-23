package task13.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

/*
// https://medium.com/@joeclever/using-multiple-datasources-with-spring-boot-and-spring-data-6430b00c02e7
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "aclEntityManagerFactory",
        transactionManagerRef = "aclTransactionManager",
        basePackages = {"task13.acl.repository"}
)
//*/
public class AclDbConfig {
    /*
    @Primary
    @Bean(name = "aclDataSource")
    @ConfigurationProperties(prefix = "acl.spring.datasource")
    public DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "aclEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean barEntityManagerFactory(
            EntityManagerFactoryBuilder builder,
            @Qualifier("aclDataSource") DataSource dataSource
    ) {
        return builder.dataSource(dataSource)
                .packages("task13.acl.domain")
                .persistenceUnit("acl")
                .build();
    }

    @Bean(name = "aclTransactionManager")
    public PlatformTransactionManager barTransactionManager(
            @Qualifier("aclEntityManagerFactory") EntityManagerFactory aclEntityManagerFactory) {
        return new JpaTransactionManager(aclEntityManagerFactory);
    }
    //*/
}
