package android.a1ex.com.sklad_tsd.RecyclerAdapters;

import android.a1ex.com.sklad_tsd.Documents.Document;
import android.a1ex.com.sklad_tsd.R;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class RecyclerAdapterDocuments extends RecyclerView.Adapter<RecyclerAdapterDocuments.GroupViewHolder> {
    private int mResourse;
    private ArrayList<Document> mDocuments;
    private LayoutInflater mInflater;

    public RecyclerAdapterDocuments(Context context, int resourse, ArrayList<Document> documents){
        mResourse = resourse;
        mDocuments = documents;
        mInflater = LayoutInflater.from(context);
    }

    public interface ActionListener {
        void onClick(Document document);
    }

    private ActionListener mListener;

    public void setActionListener(ActionListener listener){
        mListener = listener;
    }

    @Override
    public GroupViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = mInflater.inflate(mResourse, null);
        return new GroupViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GroupViewHolder groupViewHolder, int i) {
        final Document document = mDocuments.get(i);
        groupViewHolder.set(document);

        if (mListener != null){
            groupViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onClick(document);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mDocuments.size();
    }

    public class GroupViewHolder extends RecyclerView.ViewHolder {
        private TextView mName;

        public GroupViewHolder( View itemView) {
            super(itemView);

            mName = itemView.findViewById(R.id.nameDocument);
        }

        public void set(Document document){
            mName.setText(document.toString());
        }
    }
}
