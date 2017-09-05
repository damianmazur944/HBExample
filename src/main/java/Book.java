import javax.persistence.*;

@Entity
public class Book {
    @Id
    @Column
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;
    @Column
    private String title;
    @Column
    private Integer year;
    @OneToOne
    private Author author;

    public Book(Author author, String title, Integer year) {
        this.author = author;
        this.title = title;
        this.year = year;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

}
