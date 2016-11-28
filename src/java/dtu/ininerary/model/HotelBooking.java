/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtu.ininerary.model;

import dtu.niceview.Hotel;

/**
 *
 * @author Rasmus
 */
public class HotelBooking {
 
    private Hotel hotel;
    private boolean isBooked;
    
    public HotelBooking(){}
    
    public HotelBooking(Hotel hotel){
        this.hotel = hotel;
        this.isBooked = false;
    }
    
    public Hotel getHotel(){
        return hotel;
    }
    
    public void setHotel(Hotel hotel){
        this.hotel = hotel;
    }
    
    public boolean isIsBooked(){
        return isBooked;
    }
    
    public void setIsBooked(boolean isBooked){
        this.isBooked = isBooked;
    }
}