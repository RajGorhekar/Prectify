package com.example.prectify;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static android.widget.Toast.makeText;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    SharedPreferences sp;
    SharedPreferences st;
    SharedPreferences sr;
    SharedPreferences spr;
    SharedPreferences fl;
    SharedPreferences sf;
    private ValueEventListener eventListener;
    SwipeRefreshLayout s;

    NavigationView navigationView;
    String tag;
    TextView  hemail;
    TextView hname;
    TextView huid;
    EditText txtsearch;
    MyAdapter myAdapter;
    String name;
    RecyclerView mRecyclerView;
    List<UserData> myUserList;
    UserData mUserData;
    private FirebaseDatabase firebaseDatabase;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    FirebaseUser firebaseUser;
    User User2;
    @Override
    protected void onCreate ( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
        mRecyclerView=findViewById( R.id.recyclerview );
        navigationView=findViewById(R.id.nav_view);
        View headerview=navigationView.getHeaderView(0);
        hemail=headerview.findViewById(R.id.TextView8);
        hname=headerview.findViewById(R.id.textView);
        fl=getSharedPreferences("Faclogin",MODE_PRIVATE);
        sf=getSharedPreferences("sflogin",MODE_PRIVATE);

        huid=headerview.findViewById(R.id.textView13);
        txtsearch=findViewById(R.id.search_bar);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if(firebaseUser != null){
            firebaseAuth = FirebaseAuth.getInstance();
            firebaseDatabase = FirebaseDatabase.getInstance();
            final String token = FirebaseAuth.getInstance().getUid();
            databaseReference = FirebaseDatabase.getInstance().getReference("users").child(token);
            setHeader();
        }
        else{
        setHeader();}
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        


        s= (SwipeRefreshLayout)findViewById(R.id.ref2);
        GridLayoutManager gridLayoutManager=new GridLayoutManager(MainActivity.this,1);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager( llm );


        myUserList = new ArrayList<>(  );

        s.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {

                    @Override

                    public void run() {

                        s.setRefreshing(false);
                    }

                },3000);
            }
        });


        myAdapter= new MyAdapter( MainActivity.this,myUserList );
        mRecyclerView.setAdapter(myAdapter);
        databaseReference = FirebaseDatabase.getInstance().getReference("Description");

        eventListener =databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                myUserList.clear();
                for(DataSnapshot itemsnapshot:dataSnapshot.getChildren()){
                    UserData userData=itemsnapshot.getValue(UserData.class);
                    myUserList.add(userData);
                }
                myAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        txtsearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });

        sp=getSharedPreferences("login",MODE_PRIVATE);
        st=getSharedPreferences("stlogin",MODE_PRIVATE);
        sr=getSharedPreferences("srlogin",MODE_PRIVATE);
        spr=getSharedPreferences("register",MODE_PRIVATE);

        Toolbar toolbar = findViewById( R.id.toolbar );
        setSupportActionBar( toolbar );
        DrawerLayout drawer = findViewById( R.id.drawer_layout );
        NavigationView navigationView = findViewById( R.id.nav_view );
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this , drawer , toolbar , R.string.navigation_drawer_open , R.string.navigation_drawer_close );
        drawer.addDrawerListener( toggle );
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener( this );
    }
    private void filter(String text){
        ArrayList<UserData> filterList = new ArrayList<>();
        for(UserData item : myUserList){
            if(item.getqTitle().toLowerCase().contains(text)){
                filterList.add(item);
            }
        }

        myAdapter.filteredList(filterList);
    }

    @Override
    public void onBackPressed () {
        DrawerLayout drawer = findViewById( R.id.drawer_layout );
        if (drawer.isDrawerOpen( GravityCompat.START )) {
            drawer.closeDrawer( GravityCompat.START );
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Do you want to Exit ?");
            builder.setMessage("-------------------------------------------------------------------");
            builder.setCancelable( false );
            builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    finishAndRemoveTask();
                }
            });
            builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.cancel();
                }
            });
            AlertDialog a=builder.create();
            a.show();
        }
    }
    public void setHeader(){
        if(firebaseUser == null){
            hemail.setVisibility(View.GONE);
            huid.setVisibility(View.GONE);
            hname.setText("FACULTY");
        }
        else {
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    User2 = dataSnapshot.getValue(User.class);
                    hemail.setText("Email : " + User2.getuserEmail());
                    hname.setText("Name : " + User2.getUsername());
                    huid.setText("UID :" + User2.getuserUid());
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(MainActivity.this, databaseError.getCode(), Toast.LENGTH_SHORT).show();
                }
            });
        }

    }


    @Override
    public boolean onCreateOptionsMenu ( Menu menu ) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate( R.menu.main , menu );
        return true;
    }

    @Override
    public boolean onOptionsItemSelected ( MenuItem item ) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_add) {
            if(FirebaseAuth.getInstance().getCurrentUser() == null){
                AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this);
                pictureDialog.setTitle("You are logged in as a Faculty");
                pictureDialog.show();
                item.setEnabled(false);
            }
            else {
                Intent intent;
                intent = new Intent(MainActivity.this, QueryType.class);
                startActivity(intent);
                finish();
            }
            return true;
        }
        if (id == R.id.search_button) {
            if(txtsearch.getVisibility()== View.VISIBLE){
                txtsearch.setVisibility(View.GONE);
                txtsearch.setText("");
            }
            else if (txtsearch.getVisibility()== View.GONE){
                txtsearch.setVisibility(View.VISIBLE);
            }
            return true;
        }
        if (id == R.id.action_settings) {
            return true;
        }
        else if (id == R.id.action_logout){
            sp.edit().putBoolean("logged",false).apply();
            st.edit().putBoolean("stlogged",false).apply();
            sr.edit().putBoolean("srlogged",false).apply();
            spr.edit().putBoolean("registered",false).apply();
            fl.edit().putBoolean("Faclogged",false).apply();
            sf.edit().putBoolean("sflogged",false).apply();
            FirebaseAuth mauth =FirebaseAuth.getInstance();
            mauth.signOut();

            Toast.makeText( MainActivity.this , "Logged out successfully" , Toast.LENGTH_SHORT ).show();
            Intent intent;
            intent = new Intent( MainActivity.this , SelectType.class );
            startActivity(intent);
            finish();
        }

        return super.onOptionsItemSelected( item );
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected ( MenuItem item ) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();


        if (id == R.id.nav_home) {
            if(FirebaseAuth.getInstance().getCurrentUser() == null){
                AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this);
                pictureDialog.setTitle("You are logged in as a Faculty");
                pictureDialog.show();
                item.setEnabled(false);
            }
            else {
                Intent intent;
                intent = new Intent(MainActivity.this, Myqueries.class);
                startActivity(intent);
            }

        } else if (id == R.id.nav_gallery) {
            if(FirebaseAuth.getInstance().getCurrentUser() == null){
                AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this);
                pictureDialog.setTitle("You are logged in as a Faculty");
                pictureDialog.show();
                item.setEnabled(false);
            }
            else {
                Intent intent;
                intent = new Intent(MainActivity.this, Userprofile.class);
                startActivity(intent);
            }
        } else if (id == R.id.nav_slideshow) {


        } else if (id == R.id.nav_share) {
            Intent sharingintent = new Intent(Intent.ACTION_SEND);
            sharingintent.setType("text/plain");
            String sharebody="your body here";
            String sharesubject="your subject here";
            sharingintent.putExtra(Intent.EXTRA_TEXT,sharebody);
            sharingintent.putExtra(Intent.EXTRA_SUBJECT,sharesubject);
            startActivity(Intent.createChooser(sharingintent,"Share using"));

        }else if (id == R.id.navc) {

        }
        else if (id == R.id.nav_send) {
        }

        DrawerLayout drawer = findViewById( R.id.drawer_layout );
        drawer.closeDrawer( GravityCompat.START );
        return true;
    }
}