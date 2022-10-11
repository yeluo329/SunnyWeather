package com.pushpendra.sssssss;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private RecyclerView chorusRv;
    private Button button;
    private ChorusGameResultAdapter gameResultAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        chorusRv = findViewById(R.id.result_rv);
        chorusRv.setLayoutManager(new LinearLayoutManager(this));
        gameResultAdapter = new ChorusGameResultAdapter();
        chorusRv.setAdapter(gameResultAdapter);

        ArrayList<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);
        list.add(6);
        gameResultAdapter.refresh(list);

        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameResultAdapter.refreshPartItem(3);
            }
        });
    }


}