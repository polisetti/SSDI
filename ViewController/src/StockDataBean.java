import oracle.adf.view.faces.bi.event.ClickEvent;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;


// nextInt is normally exclusive of the top value,
// so add 1 to make it inclusive


public class StockDataBean {
    public StockDataBean() {
    }

    public List<Object[]> getTabularData()
    {
       List<Object[]> tabularData = new ArrayList<Object []>();
       //List<WeatherForecast> weatherForecastList = getWeatherForecastList();
       // loop through the list and build up the tabular data. Then cache it.
       //for(WeatherForecast wf : weatherForecastList)
       //{
       //List<ForecastDay> forecastDayList = wf.getForecastDayList();
       //String location = wf.getLocation();
         
       //time, stock id, price  
       int time = 900;
       Random ran = new Random();
       List <String> Locations = new ArrayList <String>();
       Locations.add("Amazon");
       Locations.add("BankOfAmerica");
       Locations.add("MasterCard");
       while (time < 1500) {
           for (String loc: Locations) {
               tabularData.add(new Object[]{time, loc, Double.valueOf(ran.nextInt(40) + 25)});    
           }
           time += 100;
         }   
        return tabularData;
     }
}