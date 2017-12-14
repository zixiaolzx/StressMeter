package edu.dartmouth.streemeter2;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.view.LineChartView;

/**
 * Created by Zizi on 1/24/2017.
 */

public class ResultsFragment extends Fragment {
    private List<String[]> data;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        try {
            //read file
            Reader reader =new Reader("stress_timestamp.csv");
            data =reader.read();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        View view = inflater.inflate(R.layout.fragment_results, container, false);
        super.onCreate(savedInstanceState);

        createChart(view);
        createTable(view);
        return view;
    }

    public void createChart(View view){
        //create chart
        //initialise chart
        LineChartView chart = (LineChartView) view.findViewById(R.id.chart);
        List<PointValue> values = new ArrayList<>();
        //retrieve data
        for(int i = 0; i < data.size(); i++){
            String[] string = data.get(i);
            Log.d(" ", string[1]);
            float stress = Float.valueOf(string[1]);
            values.add(new PointValue(i,stress));
        }
        //put values into chart
        //set chart attributes
        Line line = new Line(values).setColor(Color.BLUE).setCubic(true);
        List<Line> lines = new ArrayList<>();
        lines.add(line);
        LineChartData data = new LineChartData(lines);

        //set axis
        Axis axisX = new Axis();
        axisX.setName("Instances");
        data.setAxisXBottom(axisX);

        Axis axisY = new Axis().setHasLines(true);
        axisY.setName("Stress Level");
        data.setAxisYLeft(axisY);

        chart.setLineChartData(data);
    }

    public void createTable(View view){
        TableLayout table = (TableLayout) view.findViewById(R.id.results_summary);
        TextView list1, list2;
        //retrieve data and add to table
        //set table attributes
        for(int j = 0; j < data.size(); j++){
            String[] strings = data.get(j);
            TableRow row = new TableRow(getContext());
            list1 = new TextView(getActivity());
            list2 = new TextView(getActivity());
            list1.setText(strings[0] + " ");
            list1.setPadding(0,0,900,0);
            list2.setText(strings[1]);
            row.addView(list1);
            row.addView(list2);
            table.addView(row, new TableLayout.LayoutParams(
                    0, ViewGroup.LayoutParams.WRAP_CONTENT, 1));
        }
    }
}
