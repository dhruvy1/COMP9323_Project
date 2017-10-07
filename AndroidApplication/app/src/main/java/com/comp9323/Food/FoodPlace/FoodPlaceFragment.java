package com.comp9323.Food.FoodPlace;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.comp9323.AsycnTask.FoodPlaceAsycn;
import com.comp9323.Food.FoodDeal.MyFoodDealRecyclerViewAdapter;
import com.comp9323.RestAPI.Beans.FoodPlace;
import com.comp9323.RestAPI.DataHolder.SingletonDataHolder;
import com.comp9323.myapplication.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.internal.IGoogleMapDelegate;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

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
    public static final View[] expandedView = {null};


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
        final Bundle copy = savedInstanceState;
        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
        mListener = new OnListFoodPlaceInteractionListener() {
            @Override
            public void onListFoodPlaceInteraction(FoodPlace item, View view, int position) {
                //expand and collapse item
                final FoodPlace place = item;
                LinearLayout detail = view.findViewById(R.id.FoodPlace_Item_Detail);
                if (detail.getVisibility() != View.VISIBLE) {
                    detail.setVisibility(View.VISIBLE);
                    if (expandedView[0] != null) { //collapse other view
                        expandedView[0].findViewById(R.id.FoodPlace_Item_Detail).setVisibility(View.GONE);
                    }
                    expandedView[0] = view;
                    //set map
                    MapView mapview = view.findViewById(R.id.FoodPlace_Map);
                    if (mapview != null) {
                        mapview.getMapAsync(new OnMapReadyCallback() {
                            @Override
                            public void onMapReady(GoogleMap googleMap) {
                                googleMap.clear();
                                LatLng position = new LatLng(Double.parseDouble(place.getLatitude()), Double.parseDouble(place.getLongitude()));
                                googleMap.addMarker(
                                        new MarkerOptions()
                                                .position(position)
                                                .title(place.getName())
                                );
                                googleMap.moveCamera(CameraUpdateFactory.newLatLng(position));
                            }
                        });
                    }
                    mapview.onCreate(copy);
                    mapview.onStart();

                    mapview.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Uri gmmIntentUri = Uri.parse("geo:"+place.getLatitude()+","+place.getLongitude());
                            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                            mapIntent.setPackage("com.google.android.apps.maps");
                            startActivity(mapIntent);
                        }
                    });
                    //set voting button
                    ImageButton dislike = view.findViewById(R.id.FoodPlace_Dislike_button);
                    if(! dislike.hasOnClickListeners())
                        dislike.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            int id = place.getId();
                            new FoodPlaceAsycn(mAdapter).execute(FoodPlaceAsycn.RATING, id+"", "-1");
                        }
                    });
                    ImageButton like = view.findViewById(R.id.FoodPlace_Like_button);
                    if(! like.hasOnClickListeners()){
                        like.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                int id = place.getId();
                                new FoodPlaceAsycn(mAdapter).execute(FoodPlaceAsycn.RATING, id+"", "1");
                            }
                        });
                    }
                }else {
                    detail.setVisibility(View.GONE);

                    expandedView[0] = null;
                }
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
                if (!MyFoodPlaceRecyclerViewAdapter.ifReachEnd()) {
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
                Log.d("Refrash", "call onRefresh");
                SingletonDataHolder.getInstance().clearFoodPlaceList();
                MyFoodPlaceRecyclerViewAdapter.setIsReachEnd(false);
                pullList(1);
                Log.d("Refresh", "end onRefresh");
            }
        });

        //pull item from server after launch if no item in the list
        if (SingletonDataHolder.getInstance().getFoodPlaceList().size() ==0){
            pullList(1);
        }
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
        Log.d("pull list", "start");
        new FoodPlaceAsycn(mAdapter).execute(FoodPlaceAsycn.GET_LIST, newPage+"");
        mSwipeRefreshLayout.setRefreshing(false);
        Log.d("pull list", "end");
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
        void onListFoodPlaceInteraction(FoodPlace item, View view, int position);
    }
}
