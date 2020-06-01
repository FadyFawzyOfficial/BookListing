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
    private String localPrice;
    
    /**
     * URL of the book (On Google Play)
     */
    private String previewUrl;
    
    /**
     * Constructors a new {@link Book} object.
     *
     * @param title         is the book title
     * @param author        is the book author or authors
     * @param averageRating is the average rating the book got
     * @param localPrice    is the book localPrice in local currency
     * @param previewUrl    is the google play URL to find more details about the book
     */
    public Book( String title, String author, double averageRating, String localPrice, String previewUrl )
    {
        this.title = title;
        this.author = author;
        this.averageRating = averageRating;
        this.localPrice = localPrice;
        this.previewUrl = previewUrl;
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
     * Return the localPrice (in local currency) of the book.
     *
     * @return
     */
    public String getLocalPrice()
    {
        return localPrice;
    }
    
    /**
     * Return the google play URL to find more information about the book.
     *
     * @return
     */
    public String getPreviewUrl()
    {
        return previewUrl;
    }
}