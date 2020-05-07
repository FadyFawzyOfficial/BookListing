package com.engineerfadyfawzi.booklisting;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class BookActivity extends AppCompatActivity
{
    
    @Override
    protected void onCreate( Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_book );
        
        // Create a fake list of books titles.
        ArrayList< String > books = new ArrayList<>();
        books.add( "Android How to Program" );
        books.add( "Java How to Program" );
        books.add( "Head First Java" );
        books.add( "Head First Android" );
        books.add( "Pro Git" );
        
        // Find a reference to the {@link ListView} in the layout
        ListView bookListView = findViewById( R.id.book_list_view );
        
        // Create a new {@link ArrayAdapter} of books
        ArrayAdapter< String > bookAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_list_item_1, books );
        
        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        bookListView.setAdapter( bookAdapter );
    }
}
