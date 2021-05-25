import com.ivanova.Flight;
import com.ivanova.parsers.FlightParser;
import com.ivanova.parsers.FlightTimeDetailsParser;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Demo {
    public static void main(String[] args) {
        String str = "3,1,10/3/16,AA,N786AA,10721,BOS,Massachusetts,12478,JFK,New York,556,-4,623,703,709,-6,0,,0,40,187\n";
        str = str.trim();
        Flight flight = new FlightParser().parse(str.split(","));
        System.out.println(flight);

    }
}
