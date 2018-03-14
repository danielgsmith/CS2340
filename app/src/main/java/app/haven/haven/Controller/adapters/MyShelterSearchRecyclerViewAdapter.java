package app.haven.haven.Controller.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import app.haven.haven.Controller.fragments.ShelterSearchFragment;
import app.haven.haven.Model.shelters.Shelter;
import app.haven.haven.R;

import java.util.ArrayList;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Shelter} and makes a call to the
 * specified {@link ShelterSearchFragment.OnListFragmentInteractionListener}.
 */
public class MyShelterSearchRecyclerViewAdapter extends RecyclerView.Adapter<MyShelterSearchRecyclerViewAdapter.ViewHolder> {

    private final ArrayList<Shelter> mValues;
    private final ShelterSearchFragment.OnListFragmentInteractionListener mListener;

    public MyShelterSearchRecyclerViewAdapter(ArrayList<Shelter> items, ShelterSearchFragment.OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_shelter_search, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mIdView.setText(mValues.get(position).getShelterName());
        //holder.mContentView.setText(mValues.get(position).getPhone());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final TextView mContentView;
        public Shelter mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.id);
            mContentView = (TextView) view.findViewById(R.id.content);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
