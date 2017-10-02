package com.comp9323.Food.FoodPlace;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.comp9323.AsycnTask.FoodPlaceAsycn;
import com.comp9323.RestAPI.Beans.FoodPlace;
import com.comp9323.RestAPI.DataHolder.SingletonDataHolder;
import com.comp9323.myapplication.R;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFoodPlaceInteractionListener}
 * interface.
 */
public class FoodPlaceFragment extends Fragment {

    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 1;
    private static int mPage = 1;
    protected SwipeRefreshLayout mSwipeRefreshLayout;
    protected static MyFoodPlaceRecyclerViewAdapter mAdapter;
    private static OnListFoodPlaceInteractionListener mListener;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public FoodPlaceFragment() {
    }

    @SuppressWarnings("unused")
    public static FoodPlaceFragment newInstance(int columnCount) {
        FoodPlaceFragment fragment = new FoodPlaceFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
        mListener = new OnListFoodPlaceInteractionListener() {
            @Override
            public void onListFoodPlaceInteraction(FoodPlace item) {
                //TODO
            }
        };
        mAdapter = new MyFoodPlaceRecyclerViewAdapter(mListener);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_foodplace_list, container, false);

        // Set the adapter
        Context context = view.getContext();
        final RecyclerView recyclerView = view.findViewById(R.id.foodplace_list);
        if (mColumnCount <= 1) {
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
        } else {
            recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
        }
        recyclerView.setAdapter(mAdapter);

        final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy){
                super.onScrolled(recyclerView, dx, dy);
                if (!mAdapter.ifReachEnd()) {
                    int totalItemCount = linearLayoutManager.getItemCount();
                    int lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
                    int visibleThreshold = 1;
                    if (!mAdapter.ifLoading() && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                        mPage++;
                        new FoodPlaceAsycn(mAdapter).execute(FoodPlaceAsycn.GET_LIST, mPage + "");
                    }
                }
            }
        });
        //set refresh listener
        mSwipeRefreshLayout = view.findViewById(R.id.foodplace_swiperefresh);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                SingletonDataHolder.getInstance().clearFoodPlaceList();
                mAdapter.setIsReachEnd(false);
                pullList(1);
            }
        });

        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
        SingletonDataHolder.getInstance().clearFoodPlaceList();
    }

    private void pullList(int newPage){
        //TODO
        mPage = newPage;
        new FoodPlaceAsycn(mAdapter).execute(FoodPlaceAsycn.GET_LIST, newPage+"");
        mSwipeRefreshLayout.setRefreshing(false);
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFoodPlaceInteractionListener {
        // TODO: Update argument type and name
        void onListFoodPlaceInteraction(FoodPlace item);
    }
}
