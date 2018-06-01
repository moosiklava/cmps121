package com.example.mdelc.pettracker;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


///**
// * A simple {@link Fragment} subclass.
// * Activities that contain this fragment must implement the
// * {@link TaskFragment.OnFragmentInteractionListener} interface
// * to handle interaction events.
// * Use the {@link TaskFragment#newInstance} factory method to
// * create an instance of this fragment.
// */
public class TaskFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";

    ArrayList<PetTask> taskList = new ArrayList<>();
    ArrayList<QuickTask> quickList = new ArrayList<>();

    RecyclerView taskRecyclerView;
    RecyclerView.Adapter taskAdapter;
    RecyclerView.LayoutManager taskManager;

    RecyclerView quickRecyclerView;
    RecyclerView.Adapter quickAdapter;
    RecyclerView.LayoutManager quickLayoutManager;

    private CompletedTaskFragment myCompletedTaskFrag;
    TextView undoButton;
    View line;
    private int index = 0;


//    private OnFragmentInteractionListener mListener;

    public TaskFragment() {
        // Required empty public constructor
    }

//    /**
//     * Use this factory method to create a new instance of
//     * this fragment using the provided parameters.
//     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
//     * @return A new instance of fragment TaskFragment.
//     */

    // TODO: Rename and change types and number of parameters
    public static TaskFragment newInstance(CompletedTaskFragment myCTF) {
        TaskFragment fragment = new TaskFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, myCTF);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            myCompletedTaskFrag = (CompletedTaskFragment) getArguments().getSerializable(ARG_PARAM1);
        }


    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        // Inflates the task list layout for this fragment if list is not empty
        View myView = inflater.inflate(R.layout.fragment_task, container, false);

        taskRecyclerView = myView.findViewById(R.id.pet_group_task_list);
        quickRecyclerView = myView.findViewById(R.id.quick_task_list);
        undoButton = myView.findViewById(R.id.undo_button);
        line = myView.findViewById(R.id.line);

        taskManager = new LinearLayoutManager(getContext());
        quickLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);

        taskRecyclerView.setLayoutManager(taskManager);
        quickRecyclerView.setLayoutManager(quickLayoutManager);

        taskAdapter = new PetTaskAdapter();
        quickAdapter = new QuickTaskAdapter();

        taskRecyclerView.setAdapter(taskAdapter);
        quickRecyclerView.setAdapter(quickAdapter);

        TextView addTask = myView.findViewById(R.id.add_task_button);

        quickList.add(new QuickTask("Pet Prynce", index++));
        quickList.add(new QuickTask("Feed Prynce", index++));
        quickList.add(new QuickTask("Walk Prynce", index++));
        quickList.add(new QuickTask("Show Prynce some love", index++));


        addTask.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                createNewTaskAndPost("Tell us what task you would like to create...");
            }
        });

        taskList.add(new PetTask((getResources().getString(R.string.empty_pet_task)), R.drawable.profile_pic_dummy, R.drawable.blank_person));

        return myView;
    }



    class PetTaskAdapter extends RecyclerView.Adapter<PetTaskAdapter.ViewHolder> {


        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            View myView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.pet_task_item, parent, false);

            return new ViewHolder(myView);
        }

        @Override
        public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

            final PetTask task = taskList.get(position);

            holder.creatorImage.setImageResource(task.taskCreator);
            holder.designeeImage.setImageResource(task.taskDesignee);
            holder.taskString.setText(task.taskDescription);

            if(task.isCompleted) {
                holder.checkBox.setImageResource(R.drawable.green_check_mark);
                strikeThroughText(holder.taskString);
            }else holder.checkBox.setImageResource(R.drawable.unchecked_checkmark);

            // All executed when a task is checked
            holder.checkBox.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {

                    if(!task.taskDescription.equals(getResources().getString(R.string.empty_pet_task))) {

                        // Checks task as completed and draws as line through textView
                        holder.checkBox.setImageResource(R.drawable.green_check_mark);
                        task.isCompleted = true;
                        strikeThroughText(holder.taskString);

                        final Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {

                            @Override
                            public void run() {

                                if(taskList.size()>0){

                                    // Removes task from the tasks taskList after 0.25 seconds = 250 milliseconds
                                    final int position = holder.getAdapterPosition();
                                    taskList.remove(position);
                                    taskAdapter.notifyItemRemoved(position);
                                    removeStrikeThroughText(holder.taskString);

                                    // When item is removed, show UNDO button
                                    line.setVisibility(View.VISIBLE);
                                    undoButton.setVisibility(View.VISIBLE);

                                    undoButton.setOnClickListener(new View.OnClickListener() {

                                        //When undo button is clicked, add the task back
                                        @Override
                                        public void onClick(View view) {

                                            taskList.add(position, new PetTask(task.taskDescription, task.taskCreator, task.taskDesignee));
                                            taskAdapter.notifyItemInserted(position);
                                            taskAdapter.notifyDataSetChanged();

                                            myCompletedTaskFrag.popFromCompletedList();

                                        }
                                    });
                                }


                            }
                        }, 250);

                        // After 5 seconds, make UNDO button disappear
                        handler.postDelayed(new Runnable() {

                            @Override
                            public void run() {

                                undoButton.setVisibility(View.GONE);
                                line.setVisibility(View.GONE);

                            }
                        }, 5000);

                        Date time = Calendar.getInstance().getTime();
                        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a", getResources().getConfiguration().locale);


                        // Add the task to the completed tasks taskList
                        CompletedPetTask completedTask = new CompletedPetTask(task.taskDescription, task.taskCreator, timeFormat.format(time));
                        myCompletedTaskFrag.addToCompletedList(completedTask);
                    }
                }
            });

            holder.taskString.setOnLongClickListener(new View.OnLongClickListener() {

                @Override
                public boolean onLongClick(View view) {

                    final AlertDialog.Builder alertBuilder = new AlertDialog.Builder(getContext());
                    alertBuilder.setTitle("Remove Task");
                    alertBuilder.setMessage("Are you sure you want to delete this task?");

                    alertBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int i) {
                            int position = holder.getAdapterPosition();
                            taskList.remove(position);
                            taskAdapter.notifyItemRemoved(position);
                            dialog.cancel();
                        }
                    });

                    alertBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int i) {
                            dialog.cancel();

                        }
                    });

                    AlertDialog alert = alertBuilder.create();
                    alert.show();

                    return true;
                }
            });

            holder.taskString.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {

                    final PetTask current = taskList.get(position);

                    final Dialog alertDialog = new Dialog(getContext());
                    final View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.fragment_create_task, null);
                    alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    alertDialog.setContentView(dialogView);
                    alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    alertDialog.show();


                    final EditText editText = dialogView.findViewById(R.id.create_task_edit);
                    TextView postButton = dialogView.findViewById(R.id.create_task_post_button);
                    TextView addQuickTask = dialogView.findViewById(R.id.create_quick_task_button);
                    TextView prompt = dialogView.findViewById(R.id.create_task_question);

                    prompt.setText(R.string.edit_task_prompt);

                    if(!current.taskDescription.equals(getResources().getString(R.string.empty_pet_task))) {
                        editText.setText(current.taskDescription);
                    }

                    postButton.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View view) {

                            String description = editText.getText().toString();

                            if(!description.equals("")) {
                                current.taskDescription = description;

                                holder.taskString.setText(description);
                                editText.setText(null);
                                alertDialog.dismiss();
                            }
                        }
                    });


                    addQuickTask.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View view) {

                            String description = editText.getText().toString();

                            if(!description.equals("")) {
                                QuickTask newQuickTask = new QuickTask(description, index++);
                                quickList.add(0, newQuickTask);
                                quickAdapter.notifyDataSetChanged();

                                editText.setText(null);
                                alertDialog.dismiss();
                            }
                        }
                    });


                }
            });

        }

        @Override
        public int getItemCount() {
            return taskList.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            ImageView creatorImage;
            ImageView designeeImage;
            ImageView checkBox;
            TextView taskString;
            LinearLayout taskLayout;

            ViewHolder(View itemView) {
                super(itemView);

                creatorImage = itemView.findViewById(R.id.task_creator_pic);
                designeeImage = itemView.findViewById(R.id.task_designee_pic);
                checkBox = itemView.findViewById(R.id.task_checkbox);
                taskString = itemView.findViewById(R.id.task_edit_text);
                taskLayout = itemView.findViewById(R.id.pet_task_layout);
            }
        }

    }

   private class QuickTaskAdapter extends RecyclerView.Adapter<QuickTaskAdapter.ViewHolder> {

        @NonNull
        @Override
        public QuickTaskAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            View view = LayoutInflater.from(getContext())
                    .inflate(R.layout.quick_task_item, parent, false);

            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

            final QuickTask quickTask = quickList.get(position);

            holder.taskText.setText(quickTask.quickText);
            holder.taskText.setTextColor(getMagnitudeColor(quickTask.indexColor));

            GradientDrawable quickTaskBackground = (GradientDrawable) holder.taskText.getBackground();
            quickTaskBackground.setStroke(4, getMagnitudeColor(quickTask.indexColor));

            holder.taskText.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {

                    QuickTask current = quickList.get(position);
                    int listEnd = quickList.size()-1;

                    // Adds QuickTask to the task List
                    if(taskList.size()==1 && taskList.get(0).taskDescription.equals(getResources().getString(R.string.empty_pet_task))){
                        PetTask firstTask = taskList.get(0);
                        firstTask.taskDescription = current.quickText;
                        taskAdapter.notifyDataSetChanged();
                    }else {
                        taskList.add(new PetTask(quickTask.quickText, R.drawable.profile_pic_dummy, R.drawable.blank_person));
                        taskAdapter.notifyDataSetChanged();
                    }

                    // Removes QuickTask from its position
                    quickList.remove(position);
                    quickAdapter.notifyItemRemoved(position);

                    // Inserts QuickTask at the end
                    quickList.add(listEnd, current);
                    quickAdapter.notifyDataSetChanged();


                }
            });

        }





        @Override
        public int getItemCount() {
            return quickList.size();
        }

       private int getMagnitudeColor(int position){
           int magnitudeColorResId;

           switch (position%10) {
               case 0:
                   magnitudeColorResId = R.color.magnitude1;
                   break;
               case 1:
                   magnitudeColorResId = R.color.magnitude2;
                   break;
               case 2:
                   magnitudeColorResId = R.color.magnitude3;
                   break;
               case 3:
                   magnitudeColorResId = R.color.magnitude4;
                   break;
               case 4:
                   magnitudeColorResId = R.color.magnitude5;
                   break;
               case 5:
                   magnitudeColorResId = R.color.magnitude6;
                   break;
               case 6:
                   magnitudeColorResId = R.color.magnitude7;
                   break;
               case 7:
                   magnitudeColorResId = R.color.magnitude8;
                   break;
               case 8:
                   magnitudeColorResId = R.color.magnitude9;
                   break;
               case 9:
                   magnitudeColorResId = R.color.colorAccent;
                   break;
               default:
                   magnitudeColorResId = R.color.magnitude10plus;
                   break;
           }

           return ContextCompat.getColor(getContext(), magnitudeColorResId);

       }

       class ViewHolder extends RecyclerView.ViewHolder{

            TextView taskText;

            ViewHolder(View itemView) {
                super(itemView);
                taskText = itemView.findViewById(R.id.quick_task_text);

            }
        }
    }

    private void strikeThroughText(TextView string) {
        string.setPaintFlags(string.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
    }

    private void removeStrikeThroughText(TextView string) {
        string.setPaintFlags(string.getPaintFlags() & (~ Paint.STRIKE_THRU_TEXT_FLAG));
    }

    private void createNewTaskAndPost(String title){

        final Dialog alertDialog = new Dialog(getContext());
        final View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.fragment_create_task, null);
        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        alertDialog.setContentView(dialogView);
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.show();


        final EditText editText = dialogView.findViewById(R.id.create_task_edit);
        TextView postButton = dialogView.findViewById(R.id.create_task_post_button);
        TextView addQuickTask = dialogView.findViewById(R.id.create_quick_task_button);
        TextView prompt = dialogView.findViewById(R.id.create_task_question);

        prompt.setText(title);

        postButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                String description = editText.getText().toString();

                if(!description.equals("")) {
                    taskList.add(new PetTask(description, R.drawable.profile_pic_dummy, R.drawable.blank_person));
                    taskAdapter.notifyDataSetChanged();
                    editText.setText(null);
                    alertDialog.dismiss();
                }
            }
        });

        addQuickTask.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                String description = editText.getText().toString();

                if(!description.equals("")) {
                    QuickTask newQuickTask = new QuickTask(description, index++);
                    quickList.add(0, newQuickTask);
                    quickAdapter.notifyDataSetChanged();

                    editText.setText(null);
                    alertDialog.dismiss();
                }
            }
        });
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
