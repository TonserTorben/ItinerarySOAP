package dtu.itinerary;


import dtu.ininerary.model.FlightBooking;
import dtu.ininerary.model.HotelBooking;
import dtu.ininerary.model.Itinerary;
import dtu.lameduck.AirlineReservationService;
import dtu.lameduck.AirlineReservationService_Service;
import dtu.niceview.HotelReservationService;
import dtu.niceview.HotelReservationService_Service;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Rasmus
 */
@WebService(serviceName = "ItineraryService")
public class ItineraryService {
    static HashMap<String, Itinerary> itineraries = initMap();
    private AirlineReservationService airlineService;
    private HotelReservationService hotelService;
    
    
    public ItineraryService(){
        airlineService = new AirlineReservationService_Service().getAirlineReservationServicePort();
        hotelService = new HotelReservationService_Service().getHotelReservationServicePort();
    }
    
    @WebMethod(operationName = "getItinerary")
    public Itinerary getItinerary(@WebParam(name = "id") String id) throws Exception{
        if (itineraries.containsKey(id))
            return itineraries.get(id);
        throw new Exception("No such itinerary");
    }
    
    @WebMethod(operationName = "getItineraryList")
    public List<Itinerary> getItineraryList(){
        List<Itinerary>itins = new ArrayList<>();
        for (Itinerary i : itineraries.values()){
            itins.add(i);
        }
        return itins;
    }
    
    @WebMethod(operationName = "createItinerary")
    public String createItinerary(){
        int rand = Math.abs(new Random().nextInt());
        Itinerary i = new Itinerary(String.valueOf(rand));
        itineraries.put(i.getId(), i);
        return i.getId();
    }
    
    @WebMethod(operationName = "bookItinerary")
    public boolean bookItinerary(@WebParam(name = "id") String id, @WebParam(name = "creditcardNumber") String creditcardNumber, 
            @WebParam(name = "creditcardName") String creditcardName, @WebParam(name = "expirationMonth") int month,
                    @WebParam(name = "expirationYear") int year) throws Exception{
    if(!itineraries.containsKey(id))
        throw new Exception("Invalid ID.");
    Itinerary itinerary = itineraries.get(id);
    for(FlightBooking f : itinerary.getFlights()){
        airlineService.bookFlight(f.getFlight().getBookingNr(), creditcardNumber, creditcardName, month, year);
        f.setIsBooked(true);
    }
    for(HotelBooking h : itinerary.getHotels()){
        hotelService.bookHotel(h.getHotel().getBookingNumber(), creditcardNumber, 
                creditcardName, month, year, h.getHotel().isCreditCardGuaranteedRequired());
        h.setIsBooked(true);
    }
    return true;
}
    
    @WebMethod(operationName = "cancelItinerary")
    public boolean cancelItinerary(@WebParam(name = "id") String id, @WebParam(name = "creditcardNumber") String creditcardNumber,
            @WebParam(name = "creditcardName") String creditcardName, @WebParam(name = "expirationMonth") int month, 
            @WebParam(name = "expirationYear") int year) throws Exception {
        if(!itineraries.containsKey(id))
            throw new Exception("Invalid Id.");
        Itinerary itinerary = itineraries.get(id);
        for(FlightBooking f : itinerary.getFlights()){
            airlineService.cancelFlight(f.getFlight().getBookingNr(), creditcardNumber, creditcardName, 
                    month, year, f.getFlight().getPrice());
            f.setIsBooked(false);
        }
        for(HotelBooking h : itinerary.getHotels()){
            hotelService.cancelHotel(h.getHotel().getBookingNumber());
            h.setIsBooked(false);
        }
        return true;
    }
    
    @WebMethod(operationName = "reset")
    public void resetItineraries(){
        itineraries = initMap();
    }
    
    private static HashMap<String, Itinerary> initMap() {
        itineraries = new HashMap<>();
        Itinerary i = new Itinerary("122347");
        itineraries.put(i.getId(), i);
        i = new Itinerary("3256");
        itineraries.put(i.getId(), i);
        i = new Itinerary("4357");
        itineraries.put(i.getId(), i);
        return itineraries;
    }
}
