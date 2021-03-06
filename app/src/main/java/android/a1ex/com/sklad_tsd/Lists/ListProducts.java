package android.a1ex.com.sklad_tsd.Lists;

import android.a1ex.com.sklad_tsd.DataBase.DataBaseHelper;
import android.a1ex.com.sklad_tsd.Directories.Product;
import android.a1ex.com.sklad_tsd.R;
import android.a1ex.com.sklad_tsd.RecyclerAdapters.RecyclerAdapterProducts;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class ListProducts extends AppCompatActivity {
    private DataBaseHelper helper;
    private RecyclerView recycler;
    private ArrayList<Product> mProducts = new ArrayList<>();
    private RecyclerAdapterProducts adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_products);

        recycler = findViewById(R.id.recyclerProducts);
        helper = new DataBaseHelper(this);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recycler.setLayoutManager(layoutManager);

        adapter = new RecyclerAdapterProducts(this, R.layout.products_item, mProducts);
        recycler.setAdapter(adapter);

        adapter.setActionListener(new RecyclerAdapterProducts.ActionListener() {
            @Override
            public void onClick(Product product) {
//                Intent intent = new Intent(ListCells.this, ActivityGroup.class);
//                intent.putExtra(EXTRA_GROUP, group);
//                startActivityForResult(intent, REQUEST_CODE);
            }
        });

        initList();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initList() {
        ArrayList<Product> items = helper.getProducts();

        mProducts.clear();
        mProducts.addAll(items);

        adapter.notifyDataSetChanged();
    }
}
