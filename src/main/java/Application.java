import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import javax.persistence.*;
import javax.persistence.criteria.*;
import javax.persistence.metamodel.Metamodel;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Application {
    public static void main(String[] args) {
       SessionFactory sessionFactory = buildSessionFactory();
        System.out.println("Session created!");
        Author author = new Author("Damian","Mazur");
        Author author2 = new Author("Mateusz","Grudzień");
        Book book1 = new Book(author,"HB od podstaw",2017);
        Book book2 = new Book(author,"Java",2016);
        Book book3 = new Book(author2,"SQL",2015);

      Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        session.save(author);
        session.save(author2);
        session.save(book1);
        session.save(book2);
        session.save(book3);
        System.out.println("Saved!");
        session.flush();
        tx.commit();
        System.out.println("Commited!");

        System.out.println("Get books ! -> > "+getBooksByTitle("Java",session));
    }
    private static SessionFactory buildSessionFactory() {
        StandardServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .applySetting("hibernate.dialect", "org.hibernate.dialect.MariaDBDialect")
                .applySetting("hibernate.connection.driver_class", "com.mysql.jdbc.Driver")
                .applySetting("hibernate.connection.url", "jdbc:mysql://localhost:3306/hbExample")
                .applySetting("hibernate.connection.username", "root")
                .applySetting("hibernate.show_sql", true)
                .applySetting("hibernate.hbm2ddl.auto", "create")
                .build();
        Metadata metadata = new MetadataSources(serviceRegistry)
                .addAnnotatedClass(Author.class)
                .addAnnotatedClass(Book.class)
                .buildMetadata();
        return metadata.buildSessionFactory();
    }
    private static List<Book> getBooksByTitle(String title,Session session){
        Query query = session.createQuery("from Book where title = :title ");
        query.setParameter("title", title);
        List<Book> books = query.getResultList();
        return books;
    }
}
