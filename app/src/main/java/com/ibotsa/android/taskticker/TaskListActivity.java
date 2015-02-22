package com.ibotsa.android.taskticker;

import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;


public class TaskListActivity extends ActionBarActivity implements TaskAdapter.Observer {
    private RecyclerView mRecyclerView;
    private TaskAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasklist);

        mToolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(mToolbar);

        //getSupportActionBar().setIcon(R.mipmap.ic_launcher);

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);


        ArrayList<Task> taskList = new ArrayList<Task>();
        taskList.add(new Task(new Type(new Category("WM", R.mipmap.ic_category_washer, R.mipmap.ic_category_washer_selected), "30", R.mipmap.ic_type_30), 7000));
        for (int i = 0; i < 15; i++) {
            taskList.add(new Task(new Type(new Category("WT", R.mipmap.ic_category_dryer, R.mipmap.ic_category_dryer_selected), "ST", R.mipmap.ic_type_st), 6000));
        }
        mAdapter = new TaskAdapter(taskList);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.registerObserver(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (mAdapter.getSelectedTask() != null) {
            getMenuInflater().inflate(R.menu.menu_tasklist_selected, menu);
        } else {
            getMenuInflater().inflate(R.menu.menu_tasklist, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (mAdapter.getSelectedTask() != null) {
            mAdapter.deselectTask();
        } else {
            super.onBackPressed();
        }
    }

    public void taskListChanged(int changed) {
        if (changed == TaskAdapter.Observer.CHANGED_SELECTED_TASK) {
            invalidateOptionsMenu();
        }
    }

    public void addTask(View view) {
        TransitionDrawable drawable = (TransitionDrawable) ((ImageView) view).getDrawable();
        drawable.startTransition(2000);

        //EditText textView = (EditText) findViewById(R.id.textView);
        //mTaskAdapter.add(new Task(textView.getText().toString()));
        //textView.setText("");

        drawable.reverseTransition(2000);
    }
}
