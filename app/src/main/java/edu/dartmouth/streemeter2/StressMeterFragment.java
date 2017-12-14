package edu.dartmouth.streemeter2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;

/**
 * Created by Zizi on 1/24/2017.
 */

public class StressMeterFragment extends Fragment {
    private GridView grid;
    private ImageAdapter imageAdapter;


    public class ImageAdapter extends BaseAdapter {
        private Context mContext;
        public int set = 0;
        private int[] currentGrid;

        public void moreImage() {
            set += 1;
            set %= 3;

        }

        public ImageAdapter(Context c) {
            mContext = c;
            currentGrid = PSM.getGridById(set);
        }

        public int getCount() {
            currentGrid = PSM.getGridById(set + 1);
            return currentGrid.length;
        }

        public Object getItem(int position) {

            return null;
        }

        public long getItemId(int position) {

            return 0;
        }

        //create a new ImageView for each item referenced by the Adapter
        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView imageView;
            if (convertView == null) {
                // if it's not recycled, initialize some attributes
                imageView = new ImageView(mContext);
                //set the grid layout
                imageView.setLayoutParams(new GridView.LayoutParams(350, 350));
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageView.setPadding(0, 0, 0, 0);

            } else {
                imageView = (ImageView) convertView;
            }

            imageView.setImageResource(currentGrid[position]);
            return imageView;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_stress_meter, container, false);
        super.onCreate(savedInstanceState);


        grid = (GridView) view.findViewById(R.id.grid);
        imageAdapter = new ImageAdapter(view.getContext());
        grid.setAdapter(imageAdapter);

        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                MainActivity.vib.cancel();
                MainActivity.endVib = true;
                MainActivity.mediaPlayer.stop();
                Intent intent = new Intent(getActivity(), SubmitActivity.class);
                intent.putExtra("rate", PSM.RATES[position]);
                intent.putExtra("imagePos", position);
                startActivity(intent);
            }
        });

        //Change Image set
        Button button = (Button) view.findViewById(R.id.more_Image);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                MainActivity.vib.cancel();
                MainActivity.endVib = true;
                MainActivity.mediaPlayer.stop();
                imageAdapter.moreImage();
                grid.setAdapter(imageAdapter);
            }
        });
        return view;
    }
}
