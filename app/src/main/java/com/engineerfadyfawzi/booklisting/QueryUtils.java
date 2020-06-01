package com.engineerfadyfawzi.booklisting;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Helper methods related to requesting and receiving book data from Google Books.
 */
public class QueryUtils
{
    /**
     * Tag for the log messages
     */
    private static final String LOG_TAG = QueryUtils.class.getSimpleName();
    
    /**
     * Create a private constructor because no on should ever create a {@link QueryUtils} object.
     * This class is only meant to hold static variables and methods, which can be accessed
     * directly from the class name QueryUtils (and an object instance of QueryUtils is not needed).
     */
    private QueryUtils()
    {
        
    }
    
    /**
     * Query the Google Book API data set and return a list of {@link Book} objects.
     *
     * @param stringUrl
     *
     * @return
     */
    public static List< Book > fetchBookData( String stringUrl )
    {
        // Create URL object
        URL url = createUrl( stringUrl );
        
        // Perform HTTP request to the URL and receives a JSON response back
        String jsonResponse = makeHttpRequest( url );
        
        // Extract relevant fields from the JSON response and create a list of {@link List<Book>)s
        List< Book > books = extractItemsFromJson( jsonResponse );
        
        // Return the list of {@link Book}s
        return books;
    }
    
    /**
     * Returns new URL object form the given String URL.
     *
     * @param stringUrl
     *
     * @return
     */
    private static URL createUrl( String stringUrl )
    {
        URL url = null;
        
        try
        {
            url = new URL( stringUrl );
        }
        catch ( MalformedURLException malformedURLException )
        {
            Log.e( LOG_TAG, "Problem building the URL", malformedURLException );
            malformedURLException.printStackTrace();
        }
        
        return url;
    }
    
    /**
     * Make an HTTP request to the given URL and return a String as the response.
     *
     * @param url
     *
     * @return
     */
    private static String makeHttpRequest( URL url )
    {
        String jsonResponse = null;
        
        // If the URL is null, then return early
        if ( url == null )
            return jsonResponse;
        
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        
        try
        {
            urlConnection = ( HttpURLConnection ) url.openConnection();
            urlConnection.setRequestMethod( "GET" );
            urlConnection.setReadTimeout( 1000 /* milliseconds */ );
            urlConnection.setConnectTimeout( 15000 /* milliseconds */ );
            urlConnection.connect();
            
            // If the request was successful ( response code 200),
            // then read the input stream and parse the response.
            int responseCode = urlConnection.getResponseCode();
            if ( responseCode == 200 )
            {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream( inputStream );
            }
            else
                Log.e( LOG_TAG, "Error response code: " + responseCode );
        }
        catch ( IOException ioException )
        {
            Log.e( LOG_TAG, "Problem retrieving the book JSON results.", ioException );
            ioException.printStackTrace();
        }
        finally
        {
            if ( urlConnection != null )
                urlConnection.disconnect();
            
            if ( inputStream != null )
            {
                try
                {
                    // Closing the input stream could throw an IOException.
                    inputStream.close();
                }
                catch ( IOException ioException )
                {
                    Log.e( LOG_TAG, "Error closing input stream", ioException );
                    ioException.printStackTrace();
                }
            }
        }
        
        return jsonResponse;
    }
    
    /**
     * Convert the {@link InputStream} into a String which contains the whole JSON response form the
     * server.
     *
     * @param inputStream
     *
     * @return
     */
    private static String readFromStream( InputStream inputStream )
    {
        StringBuilder output = new StringBuilder();
        
        if ( inputStream != null )
        {
            InputStreamReader inputStreamReader = new InputStreamReader( inputStream, Charset.forName( "UTF-8" ) );
            BufferedReader bufferedReader = new BufferedReader( inputStreamReader );
            
            try
            {
                String line = bufferedReader.readLine();
                while ( line != null )
                {
                    output.append( line );
                    line = bufferedReader.readLine();
                }
            }
            catch ( IOException ioException )
            {
                Log.e( LOG_TAG, "Problem reading from stream", ioException );
                ioException.printStackTrace();
            }
            
        }
        
        return output.toString();
    }
    
    /**
     * Return a list of {@link Book} objects that has been built up from
     * parsing the given JSON response.
     *
     * @return
     */
    private static List< Book > extractItemsFromJson( String bookJSON )
    {
        // if the JSON string is empty or null, then return early
        if ( TextUtils.isEmpty( bookJSON ) )
            return null;
        
        // Create an empty ArrayList that we can start adding books to
        List< Book > books = new ArrayList<>();
        
        // Try to parse the JSON response string. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try
        {
            // Create a JSONObject from the JSON response string
            JSONObject baseJsonResponse = new JSONObject( bookJSON );
            
            // Extract the JSONArray associated with the key called "items",
            // with represents a list of items (or books).
            JSONArray bookArray = baseJsonResponse.getJSONArray( "items" );
            
            // For each book in the bookArray, create a {@link Book} object.
            for ( int i = 0; i < bookArray.length(); i++ )
            {
                // Get a single book at position i within the list of books
                JSONObject currentBook = bookArray.getJSONObject( i );
                
                // For a given book, extract the JSONObject associated with the
                // key called "volumeInfo", which represents a title, author and
                // average rate for that book (current book)
                JSONObject volumeInfo = currentBook.getJSONObject( "volumeInfo" );
                
                // Extract the value for the key called "title" (the book title).
                String title = volumeInfo.getString( "title" );
                
                // Extract the value for the key called "authors" (the book authors).
                String authors = getAuthors( volumeInfo );
                
                // Extract the value for the key called "averageRating" (the book authors).
                double averageRating = volumeInfo.getDouble( "averageRating" );
                
                // For a given book, extract the JSONObject associated with the
                // key called "saleInfo" and "retailPrice",
                // which represents price for that book (current book).
                JSONObject saleInfo = currentBook.getJSONObject( "saleInfo" );
                JSONObject retailPrice = saleInfo.getJSONObject( "retailPrice" );
                
                // Extract the value for the key called "amount" (the book price).
                double amount = retailPrice.getDouble( "amount" );
                
                // Extract the value for the key called "currencyCode" (the book local currency).
                String currencyCode = retailPrice.getString( "currencyCode" );
                
                // combine the price value (amount) with the local currency acronym (currencyCode)
                String localPrice = currencyCode + "  " + amount;
                
                // Extract the value for the key called "canonicalVolumeLink" (preview link or url)
                String previewUrl = volumeInfo.getString( "canonicalVolumeLink" );
                
                // Create a new {@link Book} object with the title, author, average rate, price
                // and preview link from the JSON response.
                Book book = new Book( title, authors, averageRating, localPrice, previewUrl );
                
                // Add the new {@link Book} to the list of books.
                books.add( book );
            }
        }
        catch ( JSONException jsonException )
        {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. print a log message
            // with the message from the exception.
            Log.e( "QueryUtils", "Problem parsing the book JSON results", jsonException );
            jsonException.printStackTrace();
        }
        
        // Return the list of books
        return books;
    }
    
    // Return comma separated author list when there is more than one author
    private static String getAuthors( JSONObject jsonObject )
    {
        try
        {
            JSONArray authors = jsonObject.getJSONArray( "authors" );
            int authorsNumber = authors.length();
            String[] authorStrings = new String[ authorsNumber ];
            for ( int i = 0; i < authorsNumber; i++ )
                authorStrings[ i ] = authors.getString( i );
            
            return TextUtils.join( ", ", authorStrings );
        }
        catch ( JSONException jsonException )
        {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. print a log message
            // with the message from the exception.
            Log.e( "QueryUtils.getAuthors()", "Problem parsing the book JSON results", jsonException );
            jsonException.printStackTrace();
            return "";
        }
    }
}