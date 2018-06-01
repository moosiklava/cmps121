package com.example.mdelc.pettracker;

import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


///**
// * A simple {@link Fragment} subclass.
// * Activities that contain this fragment must implement the
// * {@link CompletedTaskFragment.OnFragmentInteractionListener} interface
// * to handle interaction events.
// * Use the {@link CompletedTaskFragment#newInstance} factory method to
// * create an instance of this fragment.
// */
public class CompletedTaskFragment extends Fragment implements Serializable {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    public ArrayList<CompletedPetTask> completedList = new ArrayList<>();
    public ListView completedListView;
    public CompletedPetTaskAdapter completedListAdapter = new CompletedPetTaskAdapter();

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

//    private OnFragmentInteractionListener mListener;

    public CompletedTaskFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CompletedTaskFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CompletedTaskFragment newInstance(String param1, String param2) {

        CompletedTaskFragment fragment = new CompletedTaskFragment();

        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View myView = inflater.inflate(R.layout.fragment_completed_tasks, container, false);

        completedListView = myView.findViewById(R.id.completed_task_list);

        Date time = Calendar.getInstance().getTime();
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a", getResources().getConfiguration().locale);

//        for(int i=0; i<4; i++){
//            list.add(new CompletedPetTask("Pet the damn dog!", R.drawable.profile_pic_dummy, timeFormat.format(time)));
//        }

        completedListView.setAdapter(completedListAdapter);
        completedListView.setDivider(null);

        return myView;
    }

    class CompletedPetTaskAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return completedList.size();
        }

        @Override
        public Object getItem(int i) {
            return completedList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {

            view = getLayoutInflater().inflate(R.layout.completed_task_layout, null);

            final CompletedPetTask task = completedList.get(i);

            ImageView completerImage = view.findViewById(R.id.task_completed_pic);
            final TextView completedTime = view.findViewById(R.id.task_completed_time);
            final TextView completedTask = view.findViewById(R.id.task_completed_text);

            completerImage.setImageResource(task.taskCompleter);
            completedTime.setText(task.timeCompleted);
            completedTask.setText(task.taskDescription);

            strikeThroughText(completedTask);

            return view;
        }
    }

    public void addToCompletedList(CompletedPetTask completedTask){
        completedList.add(0, completedTask);
        completedListAdapter.notifyDataSetChanged();
    }

    public void popFromCompletedList(){

        if(!completedList.isEmpty()) {
            completedList.remove(0);
            completedListAdapter.notifyDataSetChanged();
        }
    }

    private void strikeThroughText(TextView string){
        string.setPaintFlags(string.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
    }


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
}
