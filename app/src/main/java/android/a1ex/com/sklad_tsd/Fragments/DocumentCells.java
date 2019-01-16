package android.a1ex.com.sklad_tsd.Fragments;


import android.a1ex.com.sklad_tsd.Directories.Cell;
import android.a1ex.com.sklad_tsd.R;
import android.a1ex.com.sklad_tsd.RecyclerAdapters.RecyclerAdapterCells;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

public class DocumentCells extends Fragment {
    private static final String EXTRA_CELLS = "android.a1ex.com.sklad_tsd.extra.CELLS";
    private ArrayList<Cell> mCells;
    private RecyclerView mRecyclerView;

    public static final DocumentCells newDocumentCells(ArrayList<Cell> mCells) {
        DocumentCells documentCells = new DocumentCells();

        Bundle args = new Bundle();
        args.putParcelableArrayList(EXTRA_CELLS, mCells);
        documentCells.setArguments(args);

        return documentCells;
    }

    public DocumentCells() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mCells = getArguments().getParcelableArrayList(EXTRA_CELLS);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_document_cells, container, false);

        mRecyclerView = view.findViewById(R.id.recyclerCellsDocument);
        init();

        return view;
    }

    private void init() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);

        RecyclerAdapterCells adapter = new RecyclerAdapterCells(getActivity(), R.layout.cells_item, mCells);
        mRecyclerView.setAdapter(adapter);
        adapter.setActionListener(mClick);
    }

    private RecyclerAdapterCells.ActionListener mClick = new RecyclerAdapterCells.ActionListener() {
        @Override
        public void onClick(Cell cell) {
            mActionListener.edit(cell);
        }
    };

    public interface ActionListener {
        public void edit(Cell cell);
    }

    private ActionListener mActionListener;

    public void setActionListener(ActionListener actionListener) {
        mActionListener = actionListener;
    }
}
