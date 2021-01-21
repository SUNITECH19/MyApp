package com.sunitech.myapp.activity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.sunitech.myapp.R;

public class ActivityNotification extends AppCompatActivity {

    private TextView textViewNoNotifications;
    private RecyclerView recyclerViewNotifications;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState );
        setContentView(R.layout.activity_notification);

        textViewNoNotifications = findViewById(R.id.text_view_no_notification_text);
        recyclerViewNotifications = findViewById(R.id.recycler_view_notifications);
        // Set Toolbar
        setToolbar();


    }

    private void setToolbar(){
        Toolbar toolbar = findViewById( R.id.appToolbar );
        setSupportActionBar( toolbar );
        try {
            getSupportActionBar().setDisplayShowTitleEnabled( true );
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Notification");
        }catch (NullPointerException ignored){ }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if ( id == android.R.id.home ){
            onBackPressed( );
            return true;
        }
        return super.onOptionsItemSelected( item );
    }



}
