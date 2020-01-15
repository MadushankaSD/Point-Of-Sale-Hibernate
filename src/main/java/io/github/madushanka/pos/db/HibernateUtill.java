package io.github.madushanka.pos.db;

import io.github.madushanka.pos.entity.Customer;
import io.github.madushanka.pos.entity.Item;
import io.github.madushanka.pos.entity.Order;
import io.github.madushanka.pos.entity.OrderDetail;
import lk.ijse.dep.crypto.DEPCrypt;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.model.naming.ImplicitNamingStrategyJpaCompliantImpl;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HibernateUtill {
    private static SessionFactory sessionFactory = buildSessionFactory();


    private static SessionFactory buildSessionFactory() {


        File file = new File("/home/madushanka/Desktop/layered-pos Hibernate/resources/application.properties");
        Properties properties = new Properties();


        try(FileInputStream fileInputStream = new FileInputStream(file)) {
            properties.load(fileInputStream);

        } catch (Exception e) {
            Logger.getLogger("io.github.madushanka.pos.db.HibernateUtill").log(Level.SEVERE,"null",e);
            System.exit(2);
        }

        StandardServiceRegistry standardRegistry = new StandardServiceRegistryBuilder()
                .loadProperties(file)
                .applySetting("hibernate.connection.username", DEPCrypt.decode(properties.getProperty("hibernate.connection.username"),"DEP4"))
                .applySetting("hibernate.connection.password",DEPCrypt.decode(properties.getProperty("hibernate.connection.password"),"DEP4"))
                .build();


        Metadata metadata = new MetadataSources(standardRegistry)
                .addAnnotatedClass(Customer.class)
                .addAnnotatedClass(Item.class)
                .addAnnotatedClass(Order.class)
                .addAnnotatedClass(OrderDetail.class)
                .getMetadataBuilder()
                .applyImplicitNamingStrategy(ImplicitNamingStrategyJpaCompliantImpl.INSTANCE)
                .build();


        return metadata.getSessionFactoryBuilder()
                .build();
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

}
