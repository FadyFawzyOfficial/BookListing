package com.engineerfadyfawzi.booklisting;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * {@link BookAdapter} is an {@link ArrayAdapter} that can provide the layout for each list item
 * base on a data source, which is a list of {@link Book} objects.
 *
 * A{@link BookAdapter} know how to create a list item layout for each book in the data source
 * (a list of {@link Book} objects).
 *
 * These list item layouts will be provided to an adapter view like ListView
 * to be displayed to the user.
 */
public class BookAdapter extends ArrayAdapter< Book >
{
    /**
     * Create a new {@link BookAdapter} object.
     *
     * @param context is the current context (i.e Activity) that adapter is being created in.
     * @param books   is the list of {@link Book}s to be displayed.
     */
    public BookAdapter( Context context, ArrayList< Book > books )
    {
        super( context, 0, books );
    }
    
    /**
     * Returns a list item view that displays information about the book at the given position
     * (position on the phone screen) in the list of books
     *
     * @param position
     * @param convertView
     * @param parent
     *
     * @return
     */
    @Override
    public View getView( int position, View convertView, ViewGroup parent )
    {
        // Check if there is an existing list item view (called convertView) that we can reuse,
        // otherwise, if covertView is null, then inflate a new list item layout.
        View listItemView = convertView;
        if ( listItemView == null )
            listItemView = LayoutInflater.from( getContext() ).inflate(
                    R.layout.book_list_item, parent, false );
        
        // Get the {@link Book} object at the given position in the list of books
        Book currentBook = getItem( position );
        
        // Find the TextView in the book_list_item.xml layout with view id title_text_view
        TextView titleTextView = listItemView.findViewById( R.id.title_text_view );
        // Display the title of the current book in that TextView
        titleTextView.setText( currentBook.getTitle() );
        
        // Find the TextView with view id author_text_view
        TextView authorTextView = listItemView.findViewById( R.id.author_text_view );
        // Display the author(s) of the current book in that TextView
        authorTextView.setText( currentBook.getAuthor() );
        
        // Find the TextView with view id rate_text_view
        TextView rateTextView = listItemView.findViewById( R.id.rate_text_view );
        // Display the author(s) of the current book in that TextView
        rateTextView.setText( String.valueOf( currentBook.getAverageRating() ) );
        
        // Find the TextView with view id rate_text_view
        TextView priceTextView = listItemView.findViewById( R.id.price_text_view );
        // Display the author(s) of the current book in that TextView
        priceTextView.setText( String.valueOf( currentBook.getPrice() ) );
        
        // Return the whole list item layout so that it can shown the appropriate data
        // in the ListView
        return listItemView;
    }
}