/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtu.itinerary;

import dtu.ininerary.model.FlightBooking;
import dtu.lameduck.AirlineReservationService;
import dtu.lameduck.AirlineReservationService_Service;
import dtu.lameduck.Flight;
import dtu.lameduck.ParseException_Exception;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 *
 * @author Rasmus
 */
@WebService(serviceName = "FlightService")
public class FlightService {
    
    private SimpleDateFormat format;
    private GregorianCalendar calendar;
    private AirlineReservationService airlineService;
    XMLGregorianCalendar cal;
    
    public FlightService(){
        format = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
        calendar = new GregorianCalendar();
        airlineService = new AirlineReservationService_Service().getAirlineReservationServicePort();
    }
    
    @WebMethod(operationName = "getFlightList")
    public List<Flight> getFlightList(@WebParam(name = "start") String start, 
            @WebParam(name = "destination") String destination, @WebParam(name = "date") String date)
            throws ParseException_Exception, ParseException{
  
        List<Flight> flights = airlineService.getFlights(start, destination, date);
        return flights;
    }
    
    
    @WebMethod(operationName = "addFlight")
    public boolean addFlight(@WebParam(name = "itineraryID") String itineraryID,
            @WebParam(name = "bookingNumber") int bookingNumber, @WebParam(name = "start") String start,
            @WebParam(name = "destination") String destination, @WebParam(name = "price") int price, 
            @WebParam(name = "carrier") String carrier, @WebParam(name = "airline") String airline, 
            @WebParam(name = "takeoff") String takeoff, @WebParam(name = "landing") String landing) throws Exception{
        if(!ItineraryService.itineraries.containsKey(itineraryID))
            throw new Exception("Invalid itinerary-ID.");
        Flight flight = new Flight();
        flight.setAirline(airline);
        flight.setBookingNr(bookingNumber);
        flight.setCarrier(carrier);
        flight.setDestination(destination);
        
        calendar.setTime(format.parse(landing));
        cal = DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar);
        
        flight.setLanding(cal);
        flight.setPrice(price);
        flight.setStartAirport(start);
        
        calendar.setTime(format.parse(takeoff));
        cal = DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar);
        
        flight.setTakeoff(cal);
        FlightBooking booking = new FlightBooking(flight);
        ItineraryService.itineraries.get(itineraryID).addFlight(booking);
        return true;
    }
    
    @WebMethod(operationName = "removeFlight")
    public boolean removeFlight(@WebParam(name = "itineraryID") String itineraryID, 
            @WebParam(name = "flightID") int flightID) throws Exception{
        if(!ItineraryService.itineraries.containsKey(itineraryID))
            throw new Exception ("Invalid itinearary ID");
        List<FlightBooking> flights = ItineraryService.itineraries.get(itineraryID).getFlights();
        for(int i = 0; i < flights.size(); i++){
            if(flights.get(i).getFlight().getBookingNr() == flightID)
                flights.remove(i);
        }
        return true;
    }
}
