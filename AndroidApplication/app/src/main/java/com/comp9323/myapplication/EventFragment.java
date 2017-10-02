//package com.comp9323.myapplication;
//
//import android.content.Context;
//import android.net.Uri;
//import android.os.Bundle;
//import android.support.v4.app.Fragment;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//
//import com.comp9323.RestAPI.Beans.EventBean;
//
//
///**
// * A simple {@link Fragment} subclass.
// * Activities that contain this fragment must implement the
// * {@link EventFragment.OnFragmentInteractionListener} interface
// * to handle interaction events.
// * Use the {@link EventFragment#newInstance} factory method to
// * create an instance of this fragment.
// */
//public class EventFragment extends Fragment {
//    // TODO: Rename parameter arguments, choose names that match
//    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String EVENT_NAME = "param1";
//    private static final String EVENT_LOCATION = "param2";
//    private static final String EVENT_START = "param3";
//    private static final String EVENT_END = "param4";
//    private static final String EVENT_IMG_SRC = "param4";
//    private static final String EVENT_DESC = "param5";
//    // TODO: Rename and change types of parameters
//    private String mParam1;
//    private String mParam2;
//    private String mParam3;
//    private String mParam4;
//    private String mParam5;
//
//    private OnFragmentInteractionListener mListener;
//
//    public EventFragment() {
//        // Required empty public constructor
//    }
//
//    /**
//     * Use this factory method to create a new instance of
//     * this fragment using the provided parameters.
//     *
//     * @param eventBean the event item data
//     * @return A new instance of fragment EventFragment.
//     */
//    // TODO: Rename and change types and number of parameters
//    public static EventFragment newInstance(EventBean eventBean) {
//        EventFragment fragment = new EventFragment();
//        Bundle args = new Bundle();
//        args.putString(EVENT_NAME, param1);
//        args.putString(EVENT_LOCATION, param2);
//        args.putString(EVENT_DATE, param3);
//        args.putString(EVENT_IMG_SRC, param4);
//        args.putString(EVENT_DESC, param5);
//        fragment.setArguments(args);
//        return fragment;
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//
//            TextView name = getView().findViewById(R.id.event_name);
//            name.setText(getArguments().getString(EVENT_NAME));
//
//            TextView loc = getView().findViewById(R.id.event_location);
//            loc.setText(getArguments().getString(EVENT_LOCATION));
//
//            TextView start_date = getView().findViewById(R.id.event_start);
//            start_date.setText(getArguments().getString(EVENT_START));
//
//            TextView end_date = getView().findViewById(R.id.event_end);
//            end_date.setText(getArguments().getString(EVENT_EMD));
////            mParam3 = getArguments().getString(EVENT_LOCATION);
////            mParam4 = getArguments().getString(EVENT_LOCATION);
////            mParam5 = getArguments().getString(EVENT_LOCATION);
//        }
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//
//        return inflater.inflate(R.layout.fragment_event, container, false);
//    }
//
//    // TODO: Rename method, update argument and hook method into UI event
//    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
//            mListener.onFragmentInteraction(uri);
//        }
//    }
//
//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
//    }
//
//    @Override
//    public void onDetach() {
//        super.onDetach();
//        mListener = null;
//    }
//
//    /**
//     * This interface must be implemented by activities that contain this
//     * fragment to allow an interaction in this fragment to be communicated
//     * to the activity and potentially other fragments contained in that
//     * activity.
//     * <p>
//     * See the Android Training lesson <a href=
//     * "http://developer.android.com/training/basics/fragments/communicating.html"
//     * >Communicating with Other Fragments</a> for more information.
//     */
//    public interface OnFragmentInteractionListener {
//        // TODO: Update argument type and name
//        void onFragmentInteraction(Uri uri);
//    }
//}
