package com.example.pyrov.recyclerview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements MyAdapter.ListItemClickListener {

    private static final int NUM_LIST_ITEM = 200;

    private MyAdapter myAdapter;
    private RecyclerView recyclerView;
    private Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_numbers);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        myAdapter = new MyAdapter(NUM_LIST_ITEM, this);

        recyclerView.setAdapter(myAdapter);
    }

    @Override
    public void onListItemClick(int clickedItemIndex) {
        if (toast != null){
            toast.cancel();
        }
        toast = Toast.makeText(this, "Нажата " + clickedItemIndex + " строка", Toast.LENGTH_LONG);
        toast.show();
    }
}
