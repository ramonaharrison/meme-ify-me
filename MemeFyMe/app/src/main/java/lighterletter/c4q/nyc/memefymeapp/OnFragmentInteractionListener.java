package lighterletter.c4q.nyc.memefymeapp;

import android.view.View;

/**
 * Created by Luke on 6/5/2015.
 */
public interface OnFragmentInteractionListener {

     void onTextChanged(int pos, String text);

     void onSaveButtonClicked(View memeView, int width, int height);
}
