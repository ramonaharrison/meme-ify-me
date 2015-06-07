package lighterletter.c4q.nyc.memefymeapp;

import android.app.Activity;
import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;

import java.io.File;
import java.io.IOException;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DemotivationalFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DemotivationalFragment extends Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "imageUri";
    private static final String ARG_PARAM2 = "bigText";
    private static final String ARG_PARAM3 = "subText";
    private static final String ARG_PARAM5 = "isNewProject";


    private Uri imageUri;
    private String bigText;
    private String subText;
    private boolean isNewProject;

    private FrameLayout memeView;
    private ImageView backgroundImageView;
    private EditText bigTextView;
    private EditText subTextView;
    private Button saveButton;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment Demotivational.
     */
    public static DemotivationalFragment newInstance(Uri imageUri, boolean isNewProject, String bigText, String subText) {
        DemotivationalFragment fragment = new DemotivationalFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PARAM1, imageUri);
        args.putString(ARG_PARAM2, bigText);
        args.putString(ARG_PARAM3, subText);
        args.putBoolean(ARG_PARAM5, isNewProject);
        fragment.setArguments(args);
        return fragment;
    }

    public DemotivationalFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            imageUri = getArguments().getParcelable(ARG_PARAM1);
            bigText = getArguments().getString(ARG_PARAM2);
            subText = getArguments().getString(ARG_PARAM3);
            isNewProject = getArguments().getBoolean(ARG_PARAM5);
        } else {
            // TODO: update to deal with isNewProject boolean
            bigText = "";
            subText = "";
            isNewProject = false;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_demotivational, container, false);
        // Find all the views
        memeView = (FrameLayout) view.findViewById(R.id.demotivationalView);
        backgroundImageView = (ImageView) view.findViewById(R.id.demotivationalBackgroundImage);
        bigTextView = (EditText) view.findViewById(R.id.bigText);
        subTextView = (EditText) view.findViewById(R.id.subText);
        saveButton = (Button) view.findViewById(R.id.demotivational_save_button);

        // Populate the EditTexts
        bigTextView.setText(bigText);
        subTextView.setText(subText);

        // Set up image to be edited
//        BitmapFactory.Options options = new BitmapFactory.Options();
//        options.inJustDecodeBounds = true;
//        BitmapFactory.decodeFile(new File(imageUri.getPath()).getAbsolutePath(), options);

        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), imageUri);
            bitmap = Bitmap.createScaledBitmap(bitmap, 750, 750, false);
            backgroundImageView.setImageBitmap(bitmap);

        } catch (IOException e) {
            e.printStackTrace();
        }



        backgroundImageView.setImageURI(imageUri);

        saveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                // Save text
                mListener.onTextChanged(0, bigTextView.getText().toString());
                mListener.onTextChanged(1, subTextView.getText().toString());

                // Hide cursors
                bigTextView.setCursorVisible(false);
                subTextView.setCursorVisible(false);

                final int width = memeView.getWidth();
                final int height = memeView.getHeight();

                mListener.onSaveButtonClicked(memeView, width, height);

            }
        });

        return view;
    }

    @Override
    public void onPause() {
        mListener.onTextChanged(0, bigTextView.getText().toString());
        mListener.onTextChanged(1, subTextView.getText().toString());
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
