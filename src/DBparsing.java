import org.jfree.chart.*;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


class DBparsing extends JFrame{

    private JPanel myPanel;
    private ChartPanel chartPanel;
    private JLabel myLabel;
    private JLabel myLabel1;
    private boolean back;

    public DBparsing() {
        initUI();
        back = false;
    }

    public String makeGETRequest(String urlName){
        BufferedReader rd = null;
        StringBuilder sb = null;
        String line = null;
        try {
            URL url = new URL(urlName);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            sb = new StringBuilder();
            while ((line = rd.readLine()) != null)
            {
                sb.append(line + '\n');
            }
            conn.disconnect();
            return sb.toString();
        }
        catch (MalformedURLException e){
            e.printStackTrace();
        }
        catch (ProtocolException e){
            e.printStackTrace();
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return "";

    }

    public int[] parseJSON(String jsonString){
        int[] consumption = new int[7];

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        String date = "";


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


                    String alarmDate = "";
                    for(int k=0; k<10; k++)
                    {
                        alarmDate += Character.toString(date.charAt(k));
                    }


                    if(fromdate.equals(alarmDate))
                    {
                        consumption[j] += curObject.getInt("consumption");
                    }
                }
            }
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        return consumption;
    }

    public int parseWeekly(String jsonString){
        int weekConsumption = 0;

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        String date = "";


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


                    String alarmDate = "";
                    for(int k=0; k<10; k++)
                    {
                        alarmDate += Character.toString(date.charAt(k));
                    }

                    if(fromdate.equals(alarmDate))
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

    public JPanel initUI()
    {
        String user = "ruben";
        CategoryDataset dataset = createDataset(user);
        int weekCons = parseWeekly(makeGETRequest("https://studev.groept.be/api/a21ib2b02/coffeeConsumptionPerDay/" + user));
        String temp = parseTemp(makeGETRequest("https://studev.groept.be/api/a21ib2b02/coffeeTemp/" + user));

        JFreeChart chart = createChart(dataset);
        JPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(800,500));

        myPanel = new JPanel();
        myLabel = new JLabel();
        myLabel1 = new JLabel();

        //myLabel.setText("Your weekly coffee consumption is " + weekCons + ".");
        //myLabel1.setText("You like having your coffee " + temp + ".");
        myPanel.add(chartPanel);
        myPanel.add(myLabel);
        myPanel.add(myLabel1);

        //setLocationRelativeTo(null);
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        return myPanel;
    }

    public CategoryDataset createDataset(String user)
    {
        var dataset = new DefaultCategoryDataset();
        int[] consumption = parseJSON(makeGETRequest("https://studev.groept.be/api/a21ib2b02/coffeeConsumptionPerDay/" + user));
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

        dataset.setValue(consumption[6], "Coffee Volume", date[6]);
        dataset.setValue(consumption[5], "Coffee Volume", date[5]);
        dataset.setValue(consumption[4], "Coffee Volume", date[4]);
        dataset.setValue(consumption[3], "Coffee Volume", date[3]);
        dataset.setValue(consumption[2], "Coffee Volume", date[2]);
        dataset.setValue(consumption[1], "Coffee Volume", date[1]);
        dataset.setValue(consumption[0], "Coffee Volume", date[0]);

        return dataset;
    }

    public JPanel getMyPanel() {
        return myPanel;
    }

    public boolean getBack() {
        return back;
    }

    public JFreeChart createChart(CategoryDataset dataset) {

        JFreeChart barChart = ChartFactory.createBarChart(
                "Daily Coffee Consumption In A Week",
                "",
                "Coffee Volume (cL)",
                dataset,
                PlotOrientation.VERTICAL,
                false, true, false);

        return barChart;
    }

    public ChartPanel getChartPanel() {
        return chartPanel;
    }

    public static void main(String[] args) throws Exception {
        EventQueue.invokeLater(() -> {
            var ex = new DBparsing();
            ex.setVisible(true);
        });

    }
}
