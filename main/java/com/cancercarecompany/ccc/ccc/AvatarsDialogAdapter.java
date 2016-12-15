package com.cancercarecompany.ccc.ccc;

/**
 * Created by Josef on 2016-12-15.
 */
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class AvatarsDialogAdapter extends BaseAdapter{
    private Context mContext;
    private final Integer[] avatars;

    public AvatarsDialogAdapter(Context c, Integer[] avatars ) {
        mContext = c;
        this.avatars = avatars;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return avatars.length;
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        View grid;
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            grid = inflater.inflate(R.layout.avatar_grid_view, null);
            ImageView imageView = (ImageView)grid.findViewById(R.id.image_avatar);
            imageView.setImageResource(avatars[position]);
        } else {
            grid = (View) convertView;
        }

        return grid;
    }
}