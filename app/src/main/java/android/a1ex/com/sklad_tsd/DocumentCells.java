package android.a1ex.com.sklad_tsd;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.a1ex.com.sklad_tsd.R;

public class DocumentCells extends Fragment {


    public DocumentCells() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_document_cells, container, false);
    }

}
