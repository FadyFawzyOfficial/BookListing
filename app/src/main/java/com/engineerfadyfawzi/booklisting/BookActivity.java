package com.engineerfadyfawzi.booklisting;

import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.app.LoaderManager.LoaderCallbacks;
import androidx.loader.content.Loader;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class BookActivity extends AppCompatActivity implements LoaderCallbacks< List< Book > >,
        SharedPreferences.OnSharedPreferenceChangeListener
{
    /**
     * Tag for the log messages
     */
    private static final String TAG = BookActivity.class.getSimpleName();
    
    /**
     * URL for earthquake data from the Google Books APi data set
     *
     * Now we're using UriBuilder.appendQueryParameter() methods to add additional parameters to
     * the URI (such as topic, order by, max number of results download and filter).
     */
    private static final String BOOKS_REQUEST_URL =
            "https://www.googleapis.com/books/v1/volumes";
    
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
    
    /**
     * ProgressBar that is displayed when the app loading (internet connections is poor).
     */
    private ProgressBar loadingIndicator;
    
    /**
     * EditText that the user enter the keyword or search topic in.
     */
    private EditText searchEditText;
    
    /**
     * Button that the user click to preform search with that keyword.
     */
    private Button searchButton;
    
    /**
     * The "word" that the user enter in input text field to search for.
     */
    private String searchKeyword;
    
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
    
        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        bookListView.setAdapter( bookAdapter );
    
        // Obtain a reference to the SharedPreference file for this app,
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences( this );
        // And register to be notified of preference changes,
        // So we know when the user has adjusted the query settings.
        sharedPreferences.registerOnSharedPreferenceChangeListener( this );
        
        // Find a reference to the {@link EditText} in the layout
        searchEditText = findViewById( R.id.search_edit_text );
        
        // Find a reference to the {@link Button} to preform search operation
        searchButton = findViewById( R.id.search_button );
        
        // Initialize and set the value fo this global loading indicator (spinner)
        loadingIndicator = findViewById( R.id.loading_indicator );
        
        // To avoid the "No books found." message blinking on the screen when the when the app first
        // launches, we can leave the empty state TextView blank, until the first load completes.
        // In the onLoadFinished callback method, we can set the text to be the string
        // "No books found." I'ts okay if this text is set every time the loader finishes
        // because it's not too expensive of an operation. There's always trade offs, and this user
        // experience is better.
        emptyStateTextView = findViewById( R.id.empty_view );
        bookListView.setEmptyView( emptyStateTextView );
    
        // Get the user search input "word" to search for it.
        searchKeyword = String.valueOf( searchEditText.getText() );
        
        // If there is a network connection, fetch data
        if ( isConnected() )
        {
            // Get a reference to the LoaderManager, in order to interact with loaders.
            LoaderManager loaderManager = getSupportLoaderManager();
            
            // Initialize the loader. pass in the int ID constant defined above and pass in null for
            // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
            // because this activity implements the LoaderCallbacks interface).
            Log.i( TAG, "TEST: calling initLoader() ..." );
            loaderManager.initLoader( BOOK_LOADER_ID, null, this );
        }
        else // Otherwise, display error
        {
            // First, hide loading indicator (spinner) so error message will be visible
            loadingIndicator.setVisibility( View.GONE );
            
            // Update the empty state with no connection error message
            emptyStateTextView.setText( R.string.no_internet_connection );
        }
        
        // Perform search when the user click the search button
        searchButton.setOnClickListener( new View.OnClickListener()
        {
            @Override
            public void onClick( View view )
            {
                performSearch();
            }
        } );
    }
    
    /**
     * Helper method to perform search by restarting the loader (wherever it's called),
     * which will recreate {@link BookLoader} with new URL containing the user input for search.
     */
    private void performSearch()
    {
        // Get the user search input "word" to search for it.
        searchKeyword = String.valueOf( searchEditText.getText() );
        
        // If there is a network connection, fetch data
        if ( isConnected() )
        {
            // Clear the ListView as a new query will be kicked off
            bookAdapter.clear();
            
            // Hide the empty state text view as the loading indicator will be displayed.
            emptyStateTextView.setVisibility( View.GONE );
            
            // Show the loading indicator while new data is being fetched.
            loadingIndicator.setVisibility( View.VISIBLE );
            
            // Get a reference to the LoaderManager, in order to interact with loaders.
            LoaderManager loaderManager = getSupportLoaderManager();
            
            // Initialize the loader. pass in the int ID constant defined above and pass in null for
            // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
            // because this activity implements the LoaderCallbacks interface).
            Log.i( TAG, "TEST: perform() method calls restartLoader() ..." );
            loaderManager.restartLoader( BOOK_LOADER_ID, null, this );
        }
        else // Otherwise, display error
        {
            // Clear the ListView if there are old data as an error message will be displayed
            bookAdapter.clear();
            
            // First, hide loading indicator (spinner) so error message will be visible
            loadingIndicator.setVisibility( View.GONE );
            
            // Update the empty state with no connection error message
            emptyStateTextView.setText( R.string.no_internet_connection );
        }
    }
    
    /**
     * Check if there is a network connection or not.
     *
     * @return
     */
    private boolean isConnected()
    {
        // Get a reference to the ConnectivityManager to check state of network connectivity
        ConnectivityManager connectivityManager =
                ( ConnectivityManager ) getSystemService( Context.CONNECTIVITY_SERVICE );
        
        // Get details on the currently active default data network
        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
        
        // Check if there is a network connection or not and store the result in boolean variable.
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
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
        
        // Construct a proper URI with the users' preference, and then
        // create a new loader for the URL builder (buildUrlParameters() method)
        return new BookLoader( this, buildUrlParameters() );
    }
    
    /**
     * Helper method read the user's latest preference and build the query URL parameters.
     *
     * @return string url with the user's preferences query parameters added.
     */
    private String buildUrlParameters()
    {
        // Read the user's latest preferences for the order by and maximum results preferences
        // Read from SharedPreferences and check the value associated with the key.
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences( this );
        
        // SharedPreference class's getString method: returns the preferences value if it exists,
        // or default value which is the second argument of this method
        // (Value to return if this preference doesn't exist).
        String orderBy = sharedPreferences.getString(
                getString( R.string.settings_order_by_key ),
                getString( R.string.settings_order_by_default ) );
        
        String maxResults = sharedPreferences.getString(
                getString( R.string.settings_max_results_key ),
                getString( R.string.settings_max_results_default ) );
        
        Uri baseUri = Uri.parse( BOOKS_REQUEST_URL );
        Uri.Builder uriBuilder = baseUri.buildUpon();
        
        uriBuilder.appendQueryParameter( "q", searchKeyword );
        uriBuilder.appendQueryParameter( "maxResults", maxResults );
        uriBuilder.appendQueryParameter( "orderBy", orderBy );
        
        return uriBuilder.toString();
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
        loadingIndicator.setVisibility( View.GONE );
        
        if ( TextUtils.isEmpty( searchKeyword ) )
            // Set empty state text to display search message
            emptyStateTextView.setText( R.string.search_message );
        else
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
    
    @Override
    public boolean onCreateOptionsMenu( Menu menu )
    {
        getMenuInflater().inflate( R.menu.main, menu );
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected( MenuItem item )
    {
        int itemId = item.getItemId();
        
        if ( itemId == R.id.action_settings )
        {
            Intent settingsIntent = new Intent( this, SettingsActivity.class );
            startActivity( settingsIntent );
            return true;
        }
        
        return super.onOptionsItemSelected( item );
    }
    
    /**
     * This method called when a shared preference is changed, added or removed.
     * This may be called even if a preference is set to its existing value.
     * This callback will be run on your main thread (UI thread).
     *
     * @param sharedPreferences The SharedPreference that received the change.
     * @param preferenceKey     The key of the preference that was changed, added or removed.
     */
    @Override
    public void onSharedPreferenceChanged( SharedPreferences sharedPreferences, String preferenceKey )
    {
        if ( preferenceKey.equals( getString( R.string.settings_order_by_key ) ) ||
                preferenceKey.equals( getString( R.string.settings_max_results_key ) ) )
        {
            // Clear the ListView as a new query will be kicked off
            bookAdapter.clear();
            
            // Hide the empty state text view as the loading indicator will be displayed.
            emptyStateTextView.setVisibility( View.GONE );
            
            // Show the loading indicator while new data is being fetched.
            loadingIndicator.setVisibility( View.VISIBLE );
            
            // Restart the loader to re query the Google Books API as the query settings have been updated.
            getSupportLoaderManager().restartLoader( BOOK_LOADER_ID, null, this );
        }
    }
}