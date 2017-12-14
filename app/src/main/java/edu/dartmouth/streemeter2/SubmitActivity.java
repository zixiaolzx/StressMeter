package edu.dartmouth.streemeter2;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.ImageView;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Date;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;


/**
 * Created by Zizi on 1/25/2017.
 */

public class SubmitActivity extends Activity{

    private int imageSet = -1;
    private int imagePos = -1;
    private String stress_data = Environment.getExternalStorageDirectory().getAbsolutePath() + "/stress_data";
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    @Override
    public void onCreate(Bundle savedInstancesState){
        super.onCreate(savedInstancesState);
        setContentView(R.layout.submit);
        //retrieve image data and show image
        imageSet = getIntent().getIntExtra("imageSet",1);
        imagePos = getIntent().getIntExtra("imagePos",0);

        ImageView imageView = (ImageView) findViewById(R.id.submit_Image);
        imageView.setImageResource(PSM.getGridById(imageSet)[imagePos]);

        File dir = new File(stress_data);
        if (!dir.exists()) {
            dir.mkdir();
        }
    }

    //return after click cancel button

    public void onCancelClicked(View view){
        finish();
    }
    //save data to file after click submit button

    public void onSubmitClicked(View v) {
        requestPermissions(this);
        Date date = new Date();
        String time = String.valueOf(date.getTime());
        String number = String.valueOf(PSM.getScore(imagePos));
        File psmFile = new File(Environment.getExternalStorageDirectory(), "stress_timestamp.csv");
        try
        {
            BufferedWriter bw = new BufferedWriter(new FileWriter(psmFile, true));
            bw.write(time + "," + number);
            bw.newLine();
            bw.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        Intent submitIntent = new Intent();
        submitIntent.putExtra("result", true);
        setResult(Activity.RESULT_OK, submitIntent);
        finish();
    }
    //get permission
    public void requestPermissions(Activity activity) {
        if (Build.VERSION.SDK_INT >= 23) {
            int permission = activity.checkSelfPermission(WRITE_EXTERNAL_STORAGE);
            if (permission != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(
                        activity,
                        PERMISSIONS_STORAGE,
                        REQUEST_EXTERNAL_STORAGE);
            }
        }
    }
}
