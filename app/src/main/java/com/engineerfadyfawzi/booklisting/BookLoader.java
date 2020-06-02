package com.engineerfadyfawzi.booklisting;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import java.util.List;

import androidx.loader.content.AsyncTaskLoader;

/**
 * To define the BookLoader class, we extend AsyncTaskLoader and specify List as the generic parameter,
 * which explains what type of data is expected to be loaded. In this case, the loader is loading
 * a list of Book objects. Then we take a String URL in the constructor, and in loadInBackground(),
 * we'll do the exact same operations as in doInBackground method back in BookAsyncTask.
 *
 * Loads a list of books by using an AsyncTaskLoader to prefrom the network request to the given URL,
 * in the background thread.
 */
public class BookLoader extends AsyncTaskLoader< List< Book > >
{
    /**
     * Tag for log messages
     */
    private static final String TAG = BookLoader.class.getSimpleName();
    
    /**
     * Query String url (query link)
     */
    private String url;
    
    /**
     * List of books that return from another thread (background thread).
     */
    private List< Book > bookList;
    
    /**
     * Constructors a new {@link BookLoader}
     *
     * @param context of the activity
     * @param url     to load data from
     */
    public BookLoader( Context context, String url )
    {
        super( context );
        this.url = url;
    }
    
    /**
     * Important: Notice that we also override the onStartLoading() method to call forceLoad()
     * which is a required step to actually trigger the loadInBackground() method to execute.
     *
     * I implicitly utilize this method to stop calling loadInBackground() method unnecessarily by
     * using deliverResult() method which delivers the result of the previous load to the registered
     * listener's onLoadFinished() method which in turn allows us to skip loadInBackground() call.
     */
    @Override
    protected void onStartLoading()
    {
        Log.i( TAG, "TEST: onStartLoading() called ..." );
        
        if ( bookList != null )
            // Use cached data
            deliverResult( bookList ); // skip loadInBackground() call
        else
            // We have no data, so kick off loading it
            forceLoad(); // call LoadInBackground()
    }
    
    /**
     * This is on a background thread.
     *
     * @return
     */
    @Override
    public List< Book > loadInBackground()
    {
        Log.i( TAG, "TEST: loadInBackground() called ..." );
        
        // Don't preform the request if the URL is null or empty.
        if ( TextUtils.isEmpty( url ) )
            return null;
        
        // Preform the network request, parse the response, and extract a list of books.
        // Preform the HTTP request for book data and process the response.
        // Get the list of books form {@link QueryUtils}
        List< Book > bookList = QueryUtils.fetchBookData( url );
        
        // Return the {@link Book} objects list as the result of the {@link BookLoader}
        return bookList;
    }
    
    @Override
    public void deliverResult( List< Book > data )
    {
        // We'll save the data for later retrieval
        bookList = data;
        // We can do any pre-processing we want here
        // Just remember this is on the UI thread so nothing lengthy!
        super.deliverResult( data );
    }
}