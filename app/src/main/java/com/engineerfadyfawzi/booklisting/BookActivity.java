package com.engineerfadyfawzi.booklisting;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class BookActivity extends AppCompatActivity
{
    /**
     * Tag for the log messages
     */
    private static final String TAG = BookActivity.class.getSimpleName();
    
    /**
     * URL for earthquake data from the Google Books APi data set
     */
    private static final String BOOKS_REQUEST_URL =
            "https://www.googleapis.com/books/v1/volumes?q=android&maxResults=10";
    
    /**
     * Adapter for the list of books
     */
    private BookAdapter bookAdapter;
    
    @Override
    protected void onCreate( Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_book );
        
        // Find a reference to the {@link ListView} in the layout
        ListView bookListView = findViewById( R.id.book_list_view );
        
        // Create a new{@link BookAdapter} that takes an empty list of books as input.
        // The adapter knows how to create list items for each item in the list.
        bookAdapter = new BookAdapter( this, new ArrayList< Book >() );
        
        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        bookListView.setAdapter( bookAdapter );
        
        // Start the AsyncTask to fetch the book data
        new BookAsyncTask().execute( BOOKS_REQUEST_URL );
    }
    
    /**
     * {@link AsyncTask} to perform the network request on a background thread, and then update
     * the UI with the list of books in the response.
     *
     * AsyncTask has three generic parameters: the input type , a type used for progress updates, and
     * an output type. Our task will take a String URL , and return an Book.
     * We won't do progress updates, so the second generic is just Void.
     *
     * We'll only override two of the methods of AsyncTask: doInBackground() and onPostExecute().
     * The doInBackground() method runs on a background thread, so it can run long running code
     * (like network activity), without interfering with the responsiveness  of the app.
     * Then onPostExecute() is passed the result of doInBackground() method, but runs on the
     * UI thread (main thread), so it can use the produced data to update the UI.
     */
    private class BookAsyncTask extends AsyncTask< String, Void, List< Book > >
    {
        /**
         * This method runs on a background thread and performs the network request.
         * We should not update the UI from a background thread, so we return a list of
         * {@link Book}s as the result.
         *
         * @param urls
         *
         * @return
         */
        @Override
        protected List< Book > doInBackground( String... urls )
        {
            // Don't preform the request if there are no URLs, or the first URL is null.
            if ( urls.length < 1 || urls[ 0 ] == null )
                return null;
            
            // Preform the HTTP request for book data and process the response.
            // Get the list of books form {@link QueryUtils}
            List< Book > bookList = QueryUtils.fetchBookData( urls[ 0 ] );
            
            // Return the {@link Book} objects as the result of the {@link BookAsyncTask}
            return bookList;
        }
        
        /**
         * This method runs on the main UI thread after background work has been completed.
         * This method receives as input, the return value form the doInBackground() method.
         *
         * First we clear out the adapter , to get new list of Book data from a previous query to API.
         * Then we update the adapter with the new list of books,
         * which will trigger the ListView to re-populate its list items.
         *
         * @param bookList
         */
        @Override
        protected void onPostExecute( List< Book > bookList )
        {
            // Clear teh adapter of previous book data
            bookAdapter.clear();
            
            // If there is a valid list of {link Book}s , then add them to the adapters's data set.
            // This will trigger the ListView to update.
            if ( bookList != null && !bookList.isEmpty() )
                bookAdapter.addAll( bookList );
        }
    }
}