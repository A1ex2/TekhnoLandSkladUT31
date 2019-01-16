package android.a1ex.com.sklad_tsd.RecyclerAdapters;

import android.a1ex.com.sklad_tsd.Directories.Cell;
import android.a1ex.com.sklad_tsd.Directories.Product;
import android.a1ex.com.sklad_tsd.Documents.DataDocument.ProductsOfDocument;
import android.a1ex.com.sklad_tsd.R;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class RecyclerAdapterProductsCell extends RecyclerView.Adapter<RecyclerAdapterProductsCell.GroupViewHolder> {
    private int mResourse;
    private ArrayList<ProductsOfDocument> mProducts;
    private LayoutInflater mInflater;

    public RecyclerAdapterProductsCell(Context context, int resourse, ArrayList<ProductsOfDocument> products) {
        mResourse = resourse;
        mProducts = products;
        mInflater = LayoutInflater.from(context);
    }

    public interface ActionListener {
        void onClick(ProductsOfDocument products);
    }

    private ActionListener mListener;

    public void setActionListener(ActionListener listener) {
        mListener = listener;
    }

    @Override
    public GroupViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = mInflater.inflate(mResourse, null);
        return new GroupViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GroupViewHolder groupViewHolder, int i) {
        final ProductsOfDocument product = mProducts.get(i);
        groupViewHolder.set(product);

        if (mListener != null) {
            groupViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onClick(product);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mProducts.size();
    }

    public class GroupViewHolder extends RecyclerView.ViewHolder {
        private TextView nameProductCell;
        private TextView vendorCodeProductCell;
        private TextView quantityProductCell;

        public GroupViewHolder(View itemView) {
            super(itemView);

            nameProductCell = itemView.findViewById(R.id.nameProductCell);
            vendorCodeProductCell = itemView.findViewById(R.id.vendorCodeProductCell);
            quantityProductCell = itemView.findViewById(R.id.quantityProductCell);
        }

        public void set(ProductsOfDocument products) {
            nameProductCell.setText(products.getProduct().getName());
            vendorCodeProductCell.setText(products.getProduct().getVendorCode());
            quantityProductCell.setText(String.valueOf(products.getQuantity()));
        }
    }
}
