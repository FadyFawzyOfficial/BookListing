package com.engineerfadyfawzi.booklisting;

/**
 * A {@link Book} object contains information related to a single book.
 */
public class Book
{
    /**
     * Title of the book
     */
    private String title;
    
    /**
     * Author(s) of the book
     */
    private String author;
    
    /**
     * Average rating of the book
     */
    private double averageRating;
    
    /**
     * Price of the book
     */
    private double price;
    
    /**
     * Constructors a new {@link Book} object.
     *
     * @param title         is the book title
     * @param author        is the book author or authors
     * @param averageRating is the average rating the book got
     * @param price         is the book price in local currency
     */
    public Book( String title, String author, double averageRating, double price )
    {
        this.title = title;
        this.author = author;
        this.averageRating = averageRating;
        this.price = price;
    }
    
    /**
     * Returns the title of the book.
     *
     * @return
     */
    public String getTitle()
    {
        return title;
    }
    
    /**
     * Returns the author(s) of the book.
     *
     * @return
     */
    public String getAuthor()
    {
        return author;
    }
    
    /**
     * Returns the average rating of the book.
     *
     * @return
     */
    public double getAverageRating()
    {
        return averageRating;
    }
    
    /**
     * Return the price (in local currency) of the book.
     *
     * @return
     */
    public double getPrice()
    {
        return price;
    }
}