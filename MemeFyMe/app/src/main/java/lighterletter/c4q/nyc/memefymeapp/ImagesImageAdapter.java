package lighterletter.c4q.nyc.memefymeapp;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;

/**
 * Created by c4q-marbella on 7/17/15.
 */
public class ImagesImageAdapter extends ArrayAdapter<Image> {
    Context context;
    int layoutResourceId;
    ArrayList<Image> data=new ArrayList<Image>();
    public ImagesImageAdapter(Context context, int layoutResourceId, ArrayList<Image> data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ImageHolder holder = null;
        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            holder = new ImageHolder();
            holder.txtTitle = (TextView)row.findViewById(R.id.txtTitle);
            holder.imgIcon = (ImageView)row.findViewById(R.id.image);
            row.setTag(holder);
        }
        else
        {
            holder = (ImageHolder)row.getTag();
        }
        Image picture = data.get(position);
        holder.txtTitle.setText(picture ._name);
//convert byte to bitmap take from contact class
        byte[] outImage=picture._image;
        ByteArrayInputStream imageStream = new ByteArrayInputStream(outImage);
        Bitmap theImage = BitmapFactory.decodeStream(imageStream);
        holder.imgIcon.setImageBitmap(theImage);
        return row;
    }
    static class ImageHolder
    {
        ImageView imgIcon;
        TextView txtTitle;
    }
}

