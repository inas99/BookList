package com.task1.task1.entity;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "catalog")
public  class Catalog
{
    private List<Book> books;
    public Catalog() {}

    public Catalog(List<Book> books) {
        super();
        this.books = books;
    }

    @XmlElement(name="book")
    public List<Book> getBooks() {
        return books;
    }
    public void setBooks(List<Book> books) {
        this.books = books;
    }
}