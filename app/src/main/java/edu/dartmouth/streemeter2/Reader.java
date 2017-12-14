package edu.dartmouth.streemeter2;

import android.os.Environment;
import android.util.Log;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Zizi on 1/25/2017.
 */

public class Reader {
    File yourFile;

    public Reader(String fileName) throws FileNotFoundException {
        File dir = Environment.getExternalStorageDirectory();
        yourFile = new File(dir, fileName);
        InputStream is=new FileInputStream(yourFile);
    }

    public List<String[]> read() throws IOException{
        List<String[]> resultString=new ArrayList<String[]>();
        BufferedReader reader=new BufferedReader(new FileReader(yourFile));

        try{
            String csvLine;
            while((csvLine = reader.readLine())!=null)
            {
                Log.d("Line", csvLine);
                String row[]=csvLine.split(",");
                resultString.add(row);
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return resultString;
    }
}
