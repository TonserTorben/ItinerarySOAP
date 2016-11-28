/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtu.ininerary.model;

import dtu.lameduck.Flight;

/**
 *
 * @author Rasmus
 */
public class FlightBooking {
    
    private Flight flight;
    private boolean isBooked;
    
    public FlightBooking(){}
    
    public FlightBooking(Flight flight){
        this.flight = flight;
        this.isBooked = false;
    }
    
    public Flight getFlight(){
        return flight;
    }
    
    public void setFlight(Flight flight){
        this.flight = flight;
    }
    
    public boolean isIsBooked(){
        return isBooked;
    }
    
    public void setIsBooked(boolean isBooked){
        this.isBooked = isBooked;
    }
}
