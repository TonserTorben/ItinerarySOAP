/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtu.ininerary.model;

import java.util.List;
import java.util.ArrayList;

/**
 *
 * @author Rasmus
 */
public class Itinerary {
 
    private String id;
    private List<FlightBooking> flights;
    private List<HotelBooking> hotelBookings;
    
    
    public Itinerary(){
        
    }
    
    public Itinerary(String id){
        this.id = id;
        flights = new ArrayList<>();
        hotelBookings = new ArrayList<>();
    }
    
    public String getId(){
        return id;
    }
    
    public void setId(String id){
        this.id = id;
    }
    
    public void addFlight(FlightBooking flight){
        flights.add(flight);
    }
    
    public void addHotel(HotelBooking hotel){
        hotelBookings.add(hotel);
    }
    
    public List<FlightBooking> getFlights(){
        return flights;
    }
    
    public List<HotelBooking> getHotels(){
        return hotelBookings;
    }
}
