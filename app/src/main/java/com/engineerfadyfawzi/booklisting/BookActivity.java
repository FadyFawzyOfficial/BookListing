package com.engineerfadyfawzi.booklisting;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class BookActivity extends AppCompatActivity
{
    
    @Override
    protected void onCreate( Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_book );
        
        // Get the list of books from {@link QueryUtils}
        ArrayList< Book > books = QueryUtils.extractBooks();
        
        // Find a reference to the {@link ListView} in the layout
        ListView bookListView = findViewById( R.id.book_list_view );
        
        // Create a new{@link BookAdapter} that takes the list of books as input.
        // The adapter knows how to create list items for each item in the list.
        BookAdapter bookAdapter = new BookAdapter( this, books );
        
        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        bookListView.setAdapter( bookAdapter );
    }
}