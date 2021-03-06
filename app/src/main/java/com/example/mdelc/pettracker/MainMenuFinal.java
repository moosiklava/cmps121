package com.example.mdelc.pettracker;

import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainMenuFinal extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private FirebaseDatabase firebaseDatabase;
    private FirebaseAuth firebaseAuth;

    RecyclerView homeListView;
    RecyclerView.Adapter petGroupAdapter;
    RecyclerView.LayoutManager petGroupManager;
    ArrayList<PetGroup> petGroups = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu_final);
        Toolbar toolbar = (Toolbar) findViewById(R.id.action_settings);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setImageResource(R.drawable.ic_add_black_24dp);
        fab.setColorFilter(getResources().getColor(R.color.colorWhite));

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Popup to create new pet group", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View headerView = navigationView.getHeaderView(0);
        TextView navUsername = (TextView) headerView.findViewById(R.id.usremail);

        TextView navName = (TextView) headerView.findViewById(R.id.usrname);
        firebaseAuth = firebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        navUsername.setText(user.getEmail() + "\n");
        navName.setText(user.getDisplayName());

        homeListView = findViewById(R.id.home_group_feed);
        petGroups.add(new PetGroup("Ivy", "(Owner) Remeal Holloway", R.drawable.bunny, R.drawable.profile_pic_dummy));
        petGroups.add(new PetGroup("Peaches", "(Owner) Marisela", R.drawable.cat, R.drawable.profile_pic_dummy));
        petGroups.add(new PetGroup("Prynce", "(Owner) Donny Jones", R.drawable.papillon_cute_puppies, R.drawable.profile_pic_dummy));

        petGroupManager = new LinearLayoutManager(this);
        homeListView.setLayoutManager(petGroupManager);

        petGroupAdapter = new PetGroupAdapter();
        homeListView.setAdapter(petGroupAdapter);


    }

    private class PetGroupAdapter extends RecyclerView.Adapter<PetGroupAdapter.ViewHolder> {

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_pet_group_layout, parent, false);

            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

            final PetGroup groupItem = petGroups.get(position);

            holder.petGroupImage.setImageResource(groupItem.petPic);
            holder.petGroupOwnerImage.setImageResource(groupItem.petOwnerPic);
            holder.petGroupName.setText(groupItem.groupPetName);
            holder.petGroupOwner.setText(groupItem.groupPetOwner);

            holder.petGroupCard.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(), PetGroupActivity.class);
                    intent.putExtra("petGroup", groupItem);
                    startActivity(intent);
                }
            });

        }


        @Override
        public int getItemCount() {
            return petGroups.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            TextView petGroupName;
            TextView petGroupOwner;
            ImageView petGroupImage;
            CircleImageView petGroupOwnerImage;
            RelativeLayout petGroupCard;

            ViewHolder(View itemView) {
                super(itemView);

                petGroupName = itemView.findViewById(R.id.item_pet_name);
                petGroupOwner = itemView.findViewById(R.id.item_pet_owner_name);
                petGroupImage = itemView.findViewById(R.id.item_pet_group_pic);
                petGroupOwnerImage = itemView.findViewById(R.id.item_group_creator);
                petGroupCard = itemView.findViewById(R.id.item_pet_group);

            }
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu_final, menu);
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
            //Toast.makeText(this, "WHAAAAAAAAAAAAAAAAAAAAT", Toast.LENGTH_LONG).show();
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.openDrawer(GravityCompat.START);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_logout) {
            firebaseAuth.signOut();
            startActivity(new Intent(this, LoginActivity.class));
        } else if (id == R.id.nav_profile) {

        } else if (id == R.id.nav_cal) {
            startActivity(new Intent(this, DisplayDateActivity.class));
        } else if (id == R.id.nav_send) {
            sendEmail();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //Example from TutorialsPoint
    protected void sendEmail() {
        String[] TO = {""};
        String[] CC = {""};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setData((Uri.parse("mailto:")));
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_CC, CC);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Your subject");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Write your message here");

        try {
            startActivity(Intent.createChooser(emailIntent, "Send message..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(this, "There is mo email client installed.", Toast.LENGTH_LONG).show();
        }
    }
}
