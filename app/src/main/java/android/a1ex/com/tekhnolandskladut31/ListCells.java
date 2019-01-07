package android.a1ex.com.tekhnolandskladut31;

import android.a1ex.com.tekhnolandskladut31.DataBase.DataBaseHelper;
import android.a1ex.com.tekhnolandskladut31.Directories.Cell;
import android.app.ActivityGroup;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class ListCells extends AppCompatActivity {

    private DataBaseHelper helper;
    private RecyclerView recycler;
    private ArrayList<Cell> mCells = new ArrayList<>();
    private RecyclerAdapterCells adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_cells);

        recycler = findViewById(R.id.recyclerСells);
        helper = new DataBaseHelper(this);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recycler.setLayoutManager(layoutManager);

        adapter = new RecyclerAdapterCells(this, R.layout.cells_item, mCells);
        recycler.setAdapter(adapter);

        adapter.setActionListener(new RecyclerAdapterCells.ActionListener() {
            @Override
            public void onClick(Cell cell) {
//                Intent intent = new Intent(ListCells.this, ActivityGroup.class);
//                intent.putExtra(EXTRA_GROUP, group);
//                startActivityForResult(intent, REQUEST_CODE);
            }
        });

        initList();
    }

    private void initList() {
        ArrayList<Cell> items = helper.getCells();

        mCells.clear();
        mCells.addAll(items);

        adapter.notifyDataSetChanged();
    }
}
