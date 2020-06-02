package com.engineerfadyfawzi.booklisting;

import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.app.LoaderManager.LoaderCallbacks;
import androidx.loader.content.Loader;

import android.os.Bundle;
import android.widget.ListView;

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
        
        // Get a reference to the LoaderManager, in order to interact with loaders.
        LoaderManager loaderManager = getSupportLoaderManager();
        
        // Initialize the loader. pass in the int ID constant defined above and pass in null for
        // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
        // because this activity implements the LoaderCallbacks interface).
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
        // Loader reset, so we can clear out our existing data.
        // Clear teh adatper of previous books data
        bookAdapter.clear();
    }
}