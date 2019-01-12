package com.example.ayush.firebasedataloader;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;
    private ArrayList<User> users;
    private HashMap<String,User> hashMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        users=new ArrayList<>();
        hashMap=new HashMap<>();
        recyclerView=findViewById(R.id.recycler_view);
       // recyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        adapter=new MyAdapter(users);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(adapter);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
//        FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
//        DatabaseReference databaseReference=firebaseDatabase.getReference("message");
//        databaseReference.push().setValue("hi");
        populate();

    }

    void populate(){
        FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
        firebaseDatabase.setPersistenceEnabled(true);
        final DatabaseReference databaseReference=firebaseDatabase.getReference("USER/");
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                if(dataSnapshot.exists()){
                    Log.i("taggy","child Added");
                    User user=dataSnapshot.getValue(User.class);
                    hashMap.put(dataSnapshot.getKey(),user);
                    users.add(user);
                    Log.i("taggy",dataSnapshot.getKey());
                    //hashMap.put(key,index);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Log.i("taggy","child changed!");
                if(dataSnapshot.exists()){
                    User user=dataSnapshot.getValue(User.class);
                    String key=dataSnapshot.getKey();
                    int i=users.indexOf(hashMap.get(key));
                    hashMap.put(key,user);
                    users.set(i,user);
                    adapter.notifyDataSetChanged();
                    Log.i("taggy",key);
                    Log.i("taggy",user.getName());
                }
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                    users.remove(hashMap.get(dataSnapshot.getKey()));
                    adapter.notifyDataSetChanged();
                    hashMap.remove(dataSnapshot.getKey());
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("taggy","onResumed");
    }

}
