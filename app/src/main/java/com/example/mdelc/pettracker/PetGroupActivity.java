package com.example.mdelc.pettracker;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class PetGroupActivity extends FragmentActivity {

    ArrayList<Fragment> listOfFrags = new ArrayList<>();
    ViewPager mViewPager;
    TabLayout mTabLayout;
    CircleImageView profilePic;
    ImageView groupPic;
    TextView petName;
    TextView petOwner;
    TextView numMembers;
    CompletedTaskFragment myCompletedTaskFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_group);

        mViewPager = findViewById(R.id.pet_group_viewpager);
        mTabLayout = findViewById(R.id.tab_layout);
        profilePic = findViewById(R.id.pet_group_creator);
        groupPic = findViewById(R.id.pet_group_pic);
        petName = findViewById(R.id.pet_name);
        petOwner = findViewById(R.id.pet_group_creator_name);
        numMembers = findViewById(R.id.pet_group_members);

        myCompletedTaskFragment = new CompletedTaskFragment();
    }

    @Override
    protected void onResume() {
        super.onResume();

        listOfFrags.add(TaskFragment.newInstance(myCompletedTaskFragment));
        listOfFrags.add(myCompletedTaskFragment);

        mViewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager()));
        mTabLayout.setupWithViewPager(mViewPager);

        mTabLayout.getTabAt(0).setText("Tasks");
        mTabLayout.getTabAt(1).setText("Completed");

        wrapTabIndicatorToTitle(mTabLayout, 100, 10);
    }
    
    private void setMargin(ViewGroup.MarginLayoutParams layoutParams, int start, int end) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            layoutParams.setMarginStart(start);
            layoutParams.setMarginEnd(end);
        } else {
            layoutParams.leftMargin = start;
            layoutParams.rightMargin = end;
        }

    }

    class ViewPagerAdapter extends FragmentPagerAdapter{

        ViewPagerAdapter(FragmentManager fm){
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return listOfFrags.get(position);
        }

        @Override
        public int getCount() {
            return listOfFrags.size();
        }
    }

    void wrapTabIndicatorToTitle(TabLayout tabLayout, int externalMargin, int internalMargin) {
        View tabStrip = tabLayout.getChildAt(0);
        if (tabStrip instanceof ViewGroup) {
            ViewGroup tabStripGroup = (ViewGroup) tabStrip;
            int childCount = ((ViewGroup) tabStrip).getChildCount();
            for (int i = 0; i < childCount; i++) {
                View tabView = tabStripGroup.getChildAt(i);
                //set minimum width to 0 for instead for small texts, indicator is not wrapped as expected
                tabView.setMinimumWidth(0);
                // set padding to 0 for wrapping indicator as title
                tabView.setPadding(0, tabView.getPaddingTop(), 0, tabView.getPaddingBottom());
                // setting custom margin between tabs
                if (tabView.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
                    ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) tabView.getLayoutParams();
                    if (i == 0) {
                        // left
                        setMargin(layoutParams, externalMargin, internalMargin);
                    } else if (i == childCount - 1) {
                        // right
                        setMargin(layoutParams, internalMargin, externalMargin);
                    } else {
                        // internal
                        setMargin(layoutParams, internalMargin, internalMargin);
                    }
                }
            }

            tabLayout.requestLayout();
        }
    }


}

