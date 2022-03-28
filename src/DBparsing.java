import org.jfree.chart.*;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.swing.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


class DBparsing extends JFrame{
    private Connection c;
    private String targetUser;

    public DBparsing(String targetUser) {
        c = new Connection();
        this.targetUser = targetUser;
    }

    public int parseWeekly(String jsonString){
        int weekConsumption = 0;

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        String date;

        try {
            JSONArray array = new JSONArray(jsonString);
            for (int i = 0; i < array.length(); i++)
            {
                JSONObject curObject = array.getJSONObject(i);
                date = curObject.getString("alarm_datetime");
                for(int j=0; j<7; j++)
                {
                    Calendar cal = Calendar.getInstance();
                    cal.add(Calendar.DATE, -j);
                    Date todate1 = cal.getTime();
                    String fromdate = dateFormat.format(todate1);


                    StringBuilder alarmDate = new StringBuilder();
                    for(int k=0; k<10; k++)
                    {
                        alarmDate.append(Character.toString(date.charAt(k)));
                    }

                    if(fromdate.equals(alarmDate.toString()))
                    {
                        weekConsumption += curObject.getInt("consumption");
                    }
                }
            }
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        return weekConsumption;
    }

    public String parseTemp(String jsonString){
        String temp = "";
        int hot = 0;
        int med = 0;
        int cold = 0;

        try {
            JSONArray array = new JSONArray(jsonString);
            for (int i = 0; i < array.length(); i++)
            {
                JSONObject curObject = array.getJSONObject(i);
                if(curObject.getString("temp").equals("HOT"))
                {
                    hot++;
                }
                else if(curObject.getString("temp").equals("MEDIUM"))
                {
                    med++;
                }
                else if(curObject.getString("temp").equals("COLD"))
                {
                    cold++;
                }
            }
        }
        catch (JSONException e) {
            e.printStackTrace();
        }

        if(hot>med && hot>cold)
        {
            temp = "hot";
        }
        else if(med>hot && med>cold)
        {
            temp = "warm";
        }
        else if(cold>med && cold>hot)
        {
            temp = "cold";
        }
        return temp;
    }

    public JFreeChart initChart()
    {
        CategoryDataset dataset = createDataset(targetUser);
        return createChart(dataset);
    }
    public String getSummaryA() {

        int weekCons = parseWeekly(c.makeGETRequest("https://studev.groept.be/api/a21ib2b02/coffeeConsumptionPerDay/" + targetUser));
        return "Your weekly coffee consumption is " + weekCons + "cl.";
    }

    public String getSummaryB() {
        String temp = parseTemp(c.makeGETRequest("https://studev.groept.be/api/a21ib2b02/coffeeTemp/" + targetUser));
        return "You like having your coffee " + temp + ".";
    }

    public CategoryDataset createDataset(String user)
    {
        var dataset = new DefaultCategoryDataset();
        String url = "https://studev.groept.be/api/a21ib2b02/coffeeConsumptionPerDay/" + user;
        ArrayList<Integer> consumption = c.parseJSONtoList(c.makeGETRequest(url), "consumption");
        String[] date = new String[7];
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        for(int i=0; i<7; i++)
        {
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DATE, -i);
            Date caldate = cal.getTime();
            String date1 = dateFormat.format(caldate);
            date[i] = date1;
        }

        dataset.setValue(consumption.get(6), "Coffee Volume", date[6]);
        dataset.setValue(consumption.get(5), "Coffee Volume", date[5]);
        dataset.setValue(consumption.get(4), "Coffee Volume", date[4]);
        dataset.setValue(consumption.get(3), "Coffee Volume", date[3]);
        dataset.setValue(consumption.get(2), "Coffee Volume", date[2]);
        dataset.setValue(consumption.get(1), "Coffee Volume", date[1]);
        dataset.setValue(consumption.get(0), "Coffee Volume", date[0]);

        return dataset;
    }

    public JFreeChart createChart(CategoryDataset dataset) {

        return ChartFactory.createBarChart(
                "Daily coffee consumption in a week",
                "",
                "Coffee Volume (cL)",
                dataset,
                PlotOrientation.VERTICAL,
                false, true, false);
    }
}
