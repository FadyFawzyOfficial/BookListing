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
        
        // Create a list of books
        ArrayList< Book > books = new ArrayList<>();
        books.add( new Book( "Android How to Program", "Fady Fawzi", 3.5, 300 ) );
        books.add( new Book( "Java How to Program", "Fady Fawzi", 5.0, 350 ) );
        books.add( new Book( "Head First Java", "Max Payne", 4.5, 230 ) );
        books.add( new Book( "Head First Android", "Max Payne", 5.0, 159 ) );
        books.add( new Book( "Pro Git", "John Doe", 4.0, 450 ) );
        
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