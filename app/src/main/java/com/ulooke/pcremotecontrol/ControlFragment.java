package com.ulooke.pcremotecontrol;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ControlFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ControlFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ControlFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ControlFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ControlFragment newInstance(String param1, String param2) {
        ControlFragment fragment = new ControlFragment();
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

    public int prevX;
    public int prevY;
    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.fragment_control, container, false);

        // Prevent background fragment touched
        rootView.setOnTouchListener(new ViewGroup.OnTouchListener(){
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return true;
            }
        });

        TextView touchAreaTextView = rootView.findViewById(R.id.touchAreaTextView);
        touchAreaTextView.setOnTouchListener(new TextView.OnTouchListener(){

            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int x = (int) motionEvent.getX();
                int y = (int) motionEvent.getY();
                int touchCount = motionEvent.getPointerCount();
                String type = "INVALID";
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        type = "DOWN";
                        break;
                    case MotionEvent.ACTION_MOVE:
                        type = "MOVE";
                        break;
                    case MotionEvent.ACTION_UP:
                        type = "UP";
                        break;
                }
                Log.d("TouchEvent", String.format("count : %d, type : %s, curX : %d, curY : %d, prevX : %d, prevY : %d", touchCount, type, x,y,prevX,prevY));
                prevX = x;
                prevY = y;

                return true;
            }
        });

        // Get textchanged event
        EditText inputBufferEditText = rootView.findViewById(R.id.inputBufferEditText);
        inputBufferEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Log.d("UlookEE", charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        // Open keyboard when click empty space
//        ConstraintLayout extraSpaceConstranintLayout = rootView.findViewById(R.id.extraSpaceConstranintLayout);
//        extraSpaceConstranintLayout.setOnTouchListener(new ConstraintLayout.OnTouchListener(){
//            @Override
//            public boolean onTouch(View view, MotionEvent motionEvent) {
//                showKeyboard(getActivity());
//                return false;
//            }
//        });


        return rootView;


    }

    @Override
    public void onStart() {
        super.onStart();
        hideKeyboard(getActivity());
        showKeyboard(getActivity());
    }

    public static void showKeyboard(Context context) {
        EditText toFocus = ((Activity)context).findViewById(R.id.inputBufferEditText);
        toFocus.requestFocus();
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(toFocus, InputMethodManager.SHOW_IMPLICIT);
    }

    public static void hideKeyboard(Activity activity) {

        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View focusedView = activity.getCurrentFocus();
        if(focusedView != null)
            imm.hideSoftInputFromWindow(focusedView.getWindowToken(), 0);
    }
}