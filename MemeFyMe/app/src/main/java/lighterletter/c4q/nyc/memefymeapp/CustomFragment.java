package lighterletter.c4q.nyc.memefymeapp;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;

import java.io.File;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link CustomFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CustomFragment extends Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "imageUri";
    private static final String ARG_PARAM4 = "bottomText";
    private static final String ARG_PARAM5 = "isNewProject";


    private Uri imageUri;
    private String bottomText;
    private boolean isNewProject;
    private ColorPicker colorPicker;
    private FrameLayout memeView;
    private ImageView backgroundImageView;
    private EditText bottomTextView;
    private Button saveButton;
    private Button useColor;
    private Button clear;
    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param imageUri Uri for editing image.
     * @param isNewProject true = new image and black text, false = pull bg layer from drawable, populate text
     * @return A new instance of fragment Vanilla.
     */

    public static CustomFragment newInstance(Uri imageUri, boolean isNewProject, String bottomText) {
        CustomFragment fragment = new CustomFragment();
        Bundle args = new Bundle();

        args.putParcelable(ARG_PARAM1, imageUri);
        args.putString(ARG_PARAM4, bottomText);
        args.putBoolean(ARG_PARAM5, isNewProject);
        fragment.setArguments(args);

        return fragment;
    }

    public CustomFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            imageUri = getArguments().getParcelable(ARG_PARAM1);
            bottomText = getArguments().getString(ARG_PARAM4);
            isNewProject = getArguments().getBoolean(ARG_PARAM5);
        } else {
            bottomText = "";
        }



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_custom, container, false);

        // Find all the views
        clear = (Button) view.findViewById(R.id.clear);
        useColor = (Button) view.findViewById(R.id.pickColor);
        colorPicker = (ColorPicker) view.findViewById(R.id.colorWheel);
        memeView = (FrameLayout) view.findViewById(R.id.customView);
        backgroundImageView = (ImageView) view.findViewById(R.id.customBackgroundImage);
        bottomTextView = (EditText) view.findViewById(R.id.customBottomText);
        saveButton = (Button) view.findViewById(R.id.custom_save_button);

        // Populate the EditTexts
        bottomTextView.setText(bottomText);

        // Set up image to be edited
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(new File(imageUri.getPath()).getAbsolutePath(), options);

        backgroundImageView.setImageURI(imageUri);

        clear.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

            }
        });

        useColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DrawingView.drawPaint.setColor(colorPicker.getColor());
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                // Save text
                mListener.onTextChanged(2, bottomTextView.getText().toString());

                // Hide cursors
                bottomTextView.setCursorVisible(false);

                final int width = memeView.getWidth();
                final int height = memeView.getHeight();

                mListener.onSaveButtonClicked(memeView, width, height);
            }
        });

        return view;


    }

    @Override
    public void onPause() {
        mListener.onTextChanged(2, bottomTextView.getText().toString());
        super.onPause();
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

}
