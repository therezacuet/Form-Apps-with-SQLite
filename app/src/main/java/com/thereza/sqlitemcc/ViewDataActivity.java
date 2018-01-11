package com.thereza.sqlitemcc;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.thereza.sqlitemcc.data.FormDataModel;
import com.thereza.sqlitemcc.helper.MyDBHelper;
import com.thereza.sqlitemcc.helper.RecycleAdapter;

import java.util.ArrayList;
import java.util.List;

public class ViewDataActivity extends AppCompatActivity {

    MyDBHelper dbHelper;
    RecyclerView recyclerView;
    RecycleAdapter recycler;
    List<FormDataModel> datamodel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_data);
        datamodel =new ArrayList<FormDataModel>();
        recyclerView = (RecyclerView) findViewById(R.id.recycle);


                dbHelper = new MyDBHelper(ViewDataActivity.this);
                datamodel=  dbHelper.getAllStudents();
                recycler =new RecycleAdapter(datamodel);


                Log.i("HIteshdata",""+datamodel);
                RecyclerView.LayoutManager reLayoutManager =new LinearLayoutManager(getApplicationContext());
                recyclerView.setLayoutManager(reLayoutManager);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.setAdapter(recycler);


    }
}
