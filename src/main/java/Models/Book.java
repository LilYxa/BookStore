package Models;

public class Book {
    private int id;
    private String title;
    private int author_id;
    private int category_id;
    private float price;

    public Book(String title, int author_id, int category_id, float price) {
        this.title = title;
        this.author_id = author_id;
        this.category_id = category_id;
        this.price = price;
    }

    public Book(int id, String title, int author_id, int category_id, float price) {
        this.id = id;
        this.title = title;
        this.author_id = author_id;
        this.category_id = category_id;
        this.price = price;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor_id(int author_id) {
        this.author_id = author_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public int getAuthor_id() {
        return author_id;
    }

    public int getCategory_id() {
        return category_id;
    }

    public float getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return id + "\t" + title + "\t" + author_id + "\t" + category_id + "\t" + price + "\n";
    }
}
