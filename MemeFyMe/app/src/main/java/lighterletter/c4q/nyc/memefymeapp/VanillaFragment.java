package lighterletter.c4q.nyc.memefymeapp;

import android.app.Activity;
import android.app.Fragment;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
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
import android.widget.TextView;

import java.io.File;
import java.io.IOException;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link VanillaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class VanillaFragment extends Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "imageUri";
    private static final String ARG_PARAM2 = "topText";
    private static final String ARG_PARAM3 = "middleText";
    private static final String ARG_PARAM4 = "bottomText";
    private static final String ARG_PARAM5 = "isNewProject";


    private Uri imageUri;
    private String topText;
    private String middleText;
    private String bottomText;
    private boolean isNewProject;

    private FrameLayout memeView;
    private ImageView backgroundImageView;
    private EditText topTextView;
    private EditText middleTextView;
    private EditText bottomTextView;
    private Button saveButton;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param imageUri Uri for editing image.
     * @param isNewProject true = new image and black text, false = pull bg layer from drawable, populate text
     * @return A new instance of fragment Vanilla.
     */

    public static VanillaFragment newInstance(Uri imageUri, boolean isNewProject, String topText, String middleText, String bottomText) {
        VanillaFragment fragment = new VanillaFragment();
        Bundle args = new Bundle();

        args.putParcelable(ARG_PARAM1, imageUri);
        args.putString(ARG_PARAM2, topText);
        args.putString(ARG_PARAM3, middleText);
        args.putString(ARG_PARAM4, bottomText);
        args.putBoolean(ARG_PARAM5, isNewProject);
        fragment.setArguments(args);

        return fragment;
    }

    public VanillaFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            imageUri = getArguments().getParcelable(ARG_PARAM1);
            topText = getArguments().getString(ARG_PARAM2);
            middleText = getArguments().getString(ARG_PARAM3);
            bottomText = getArguments().getString(ARG_PARAM4);
            isNewProject = getArguments().getBoolean(ARG_PARAM5);
        } else {
            // TODO: update to deal with isNewProject boolean
            topText = "";
            middleText = "";
            bottomText = "";
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_vanilla, container, false);

        // Find all the views
        memeView = (FrameLayout) view.findViewById(R.id.memeView);
        backgroundImageView = (ImageView) view.findViewById(R.id.vanillaBackgroundImage);
        topTextView = (EditText) view.findViewById(R.id.topText);
        middleTextView = (EditText) view.findViewById(R.id.middleText);
        bottomTextView = (EditText) view.findViewById(R.id.bottomText);
        saveButton = (Button) view.findViewById(R.id.save_button);

        // Populate the EditTexts
        topTextView.setText(topText);
        middleTextView.setText(middleText);
        bottomTextView.setText(bottomText);

        //Give font bold since the Asset Manager doesn't cooperate
        topTextView.setTypeface(Typeface.DEFAULT_BOLD);
        middleTextView.setTypeface(Typeface.DEFAULT_BOLD);
        bottomTextView.setTypeface(Typeface.DEFAULT_BOLD);

        // Set up image to be edited

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(new File(imageUri.getPath()).getAbsolutePath(), options);

//        final int width = options.outWidth;
//        final int height = options.outHeight;

        backgroundImageView.setImageURI(imageUri);

//        try {
//            Bitmap picture = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), imageUri);
//            backgroundImageView.setImageBitmap(picture);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }



        saveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                // Save text
                mListener.onTextChanged(0, topTextView.getText().toString());
                mListener.onTextChanged(1, middleTextView.getText().toString());
                mListener.onTextChanged(2, bottomTextView.getText().toString());

                // Hide cursors
                topTextView.setCursorVisible(false);
                middleTextView.setCursorVisible(false);
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
        mListener.onTextChanged(0, topTextView.getText().toString());
        mListener.onTextChanged(1, middleTextView.getText().toString());
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
