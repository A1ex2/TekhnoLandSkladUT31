package android.a1ex.com.sklad_tsd.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.a1ex.com.tekhnolandskladut31.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class CellProducts extends Fragment {


    public CellProducts() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cell_products, container, false);
    }

}
