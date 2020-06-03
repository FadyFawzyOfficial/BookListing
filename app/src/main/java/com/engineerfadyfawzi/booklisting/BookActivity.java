package com.engineerfadyfawzi.booklisting;

import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.app.LoaderManager.LoaderCallbacks;
import androidx.loader.content.Loader;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class BookActivity extends AppCompatActivity implements LoaderCallbacks< List< Book > >
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
     * Constant value for the earthquake loader ID. We can choose any integer.
     * This really only comes into play if you're using multiple loaders.
     */
    private static final int BOOK_LOADER_ID = 1; // First loader #1
    
    /**
     * Adapter for the list of books
     */
    private BookAdapter bookAdapter;
    
    /**
     * TextView that is displayed when the list is empty.
     */
    private TextView emptyStateTextView;
    
    @Override
    protected void onCreate( Bundle savedInstanceState )
    {
        Log.i( TAG, "TEST: onCreate() called ..." );
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_book );
        
        // Find a reference to the {@link ListView} in the layout
        ListView bookListView = findViewById( R.id.book_list_view );
        
        // Create a new{@link BookAdapter} that takes an empty list of books as input.
        // The adapter knows how to create list items for each item in the list.
        bookAdapter = new BookAdapter( this, new ArrayList< Book >() );
        
        // To avoid the "No books found." message blinking on the screen when the when the app first
        // launches, we can leave the empty state TextView blank, until the first load completes.
        // In the onLoadFinished callback method, we can set the text to be the string
        // "No books found." I'ts okay if this text is set every time the loader finishes
        // because it's not too expensive of an operation. There's always trade offs, and this user
        // experience is better.
        emptyStateTextView = findViewById( R.id.empty_view );
        bookListView.setEmptyView( emptyStateTextView );
        
        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        bookListView.setAdapter( bookAdapter );
        
        // Get a reference to the LoaderManager, in order to interact with loaders.
        LoaderManager loaderManager = getSupportLoaderManager();
        
        // Initialize the loader. pass in the int ID constant defined above and pass in null for
        // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
        // because this activity implements the LoaderCallbacks interface).
        Log.i( TAG, "TEST: calling initLoader() ..." );
        loaderManager.initLoader( BOOK_LOADER_ID, null, this );
    }
    
    /**
     * We need onCreateLoader(), for when the LoaderManager has determined that the loader with
     * our specified ID isn't running, so we should create a new one.
     *
     * @param id   of the loader
     * @param args
     *
     * @return
     */
    @Override
    public Loader< List< Book > > onCreateLoader( int id, Bundle args )
    {
        Log.i( TAG, "TEST: onCreateLoader() called ..." );
        
        // Create a new loader for the given URL
        return new BookLoader( this, BOOKS_REQUEST_URL );
    }
    
    /**
     * We need onLoadFinished(), where we'll do exactly what we did in onPostExecute(),
     * and use the earthquake data to update our UI - by updating the data set in the adapter.
     *
     * @param loader
     * @param bookList
     */
    @Override
    public void onLoadFinished( Loader< List< Book > > loader, List< Book > bookList )
    {
        Log.i( TAG, "TEST: onLoadFinished() called ..." );
        
        // Hide loading indicator because the data has been loaded
        View loadingIndicator = findViewById( R.id.loading_indicator );
        loadingIndicator.setVisibility( View.GONE );
        
        // Set empty state text to display "No books found."
        emptyStateTextView.setText( R.string.no_books );
        
        // Clear teh adapter of previous book data
        bookAdapter.clear();
        
        // If there is a valid list of {link Book}s , then add them to the adapters's data set.
        // This will trigger the ListView to update.
        if ( bookList != null && !bookList.isEmpty() )
            // Update teh UI with result
            bookAdapter.addAll( bookList );
    }
    
    /**
     * We need onLoadReset(), we're being informed that the data from our loader is no longer valid.
     * This isn't actually a case that's going to come up with our simple loader,
     * but the correct but the correct thing to do is to remove all the books data from our UI
     * by clearing out the adapter's data set.
     *
     * @param loader
     */
    @Override
    public void onLoaderReset( Loader< List< Book > > loader )
    {
        Log.i( TAG, "TEST: onLoaderReset() called ..." );
        
        // Loader reset, so we can clear out our existing data.
        // Clear teh adapter of previous books data
        bookAdapter.clear();
    }
}