package android.a1ex.com.sklad_tsd.Fragments;


import android.a1ex.com.sklad_tsd.Directories.Cell;
import android.a1ex.com.sklad_tsd.Documents.DataDocument.ProductsOfDocument;
import android.a1ex.com.sklad_tsd.RecyclerAdapters.RecyclerAdapterCells;
import android.a1ex.com.sklad_tsd.RecyclerAdapters.RecyclerAdapterProducts;
import android.a1ex.com.sklad_tsd.RecyclerAdapters.RecyclerAdapterProductsCell;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.a1ex.com.sklad_tsd.R;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

public class CellProducts extends Fragment {
    public static final String EXTRA_PRODUCTS_OF_DOCUMENT = "android.a1ex.com.sklad_tsd.extra.PRODUCTS_OF_DOCUMENT";
    public static final String EXTRA_CELL = "android.a1ex.com.sklad_tsd.extra.EXTRA_CELL";

    private ArrayList<ProductsOfDocument> mProductsOfDocuments;
    private RecyclerView mRecyclerView;
    private EditText mBarCode;
    private TextView textViewCell;
    private Cell mCell;

    public static final CellProducts newCellProducts(Cell cell, ArrayList<ProductsOfDocument> mProductsOfDocuments) {
        CellProducts cellProducts = new CellProducts();

        Bundle args = new Bundle();
        args.putParcelableArrayList(EXTRA_PRODUCTS_OF_DOCUMENT, mProductsOfDocuments);
        args.putParcelable(EXTRA_CELL, cell);
        cellProducts.setArguments(args);

        return cellProducts;
    }

    public CellProducts() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mProductsOfDocuments = getArguments().getParcelableArrayList(EXTRA_PRODUCTS_OF_DOCUMENT);
        mCell = getArguments().getParcelable(EXTRA_CELL);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cell_products, container, false);

        textViewCell = view.findViewById(R.id.textViewCell);
        textViewCell.setText(mCell.getAddress());

        mRecyclerView = view.findViewById(R.id.recyclerCellsDocument);

        view.findViewById(R.id.buttonBack).setOnClickListener(backClick);
        view.findViewById(R.id.buttonAddBarCode).setOnClickListener(addBarCodeClick);

        mBarCode = view.findViewById(R.id.barCode);

        init();

        return view;
    }

    private void init() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);

        RecyclerAdapterProductsCell adapter = new RecyclerAdapterProductsCell(getActivity(), R.layout.product_cell_item, mProductsOfDocuments);
        mRecyclerView.setAdapter(adapter);
    }

    private View.OnClickListener backClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (mListener != null) {
                mListener.back();
            }
        }
    };

    private View.OnClickListener addBarCodeClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (mListener != null) {
                mListener.addBarCode(mBarCode.getText().toString());
            }
        }
    };

    public interface ActionListener {
        public void back();

        public void addBarCode(String barCode);
    }

    private ActionListener mListener;

    public void setActionListener(ActionListener actionListener) {
        mListener = actionListener;
    }
}
