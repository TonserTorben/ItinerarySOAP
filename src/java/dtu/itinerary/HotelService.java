/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtu.itinerary;

import dtu.ininerary.model.HotelBooking;
import dtu.niceview.ParseException_Exception;
import dtu.niceview.Hotel;
import dtu.niceview.HotelReservationService;
import dtu.niceview.HotelReservationService_Service;
import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

/**
 *
 * @author Rasmus
 */

@WebService(serviceName = "HotelService")
public class HotelService {
    
    private HotelReservationService hotelService;
    
    public HotelService(){
        hotelService = new HotelReservationService_Service().getHotelReservationServicePort();
    }
    
    @WebMethod(operationName = "getHotelList")
    public List<Hotel> getHotelList(@WebParam(name = "city") String city, 
            @WebParam(name = "arrival") String arrival, @WebParam(name = "departure") String departure)
            throws ParseException_Exception{
        List<Hotel> hotels = hotelService.getHotels(city, arrival, departure);
        return hotels;
    }
    
    @WebMethod(operationName = "addHotel")
    public boolean addHotel(@WebParam(name = "itineraryID") String itineraryID,
            @WebParam(name = "hotelID") int hotelID, @WebParam(name = "hotelAddress") String address,
            @WebParam(name = "hotelName") String hotelName, @WebParam(name = "price") int price,
            @WebParam(name = "creditcardGuarantee") boolean creditcardGuarantee) throws Exception{
        if(!ItineraryService.itineraries.containsKey(itineraryID))
            throw new Exception("Invalid itinerary-ID");
        else{
            Hotel hotel = new Hotel();
            hotel.setHotelName(hotelName);
            hotel.setHotelAddress(address);
            hotel.setPrice(price);
            hotel.setCreditCardGuaranteedRequired(creditcardGuarantee);
            hotel.setBookingNumber(hotelID);
            
            HotelBooking booking = new HotelBooking(hotel);
            ItineraryService.itineraries.get(itineraryID).addHotel(booking);            
        }
        return true;
    }
    
    @WebMethod(operationName = "removeHotel")
    public boolean removeHotel(@WebParam(name = "itineararyID") String itineraryID, 
            @WebParam(name = "hotelID") int hotelID) throws Exception{
        if(!ItineraryService.itineraries.containsKey(itineraryID))
            throw new Exception("Invalid itinerary ID");
        List<HotelBooking> hotels = ItineraryService.itineraries.get(itineraryID).getHotels();
        for(int i = 0; i < ItineraryService.itineraries.get(itineraryID).getHotels().size(); i++){
            if (hotels.get(i).getHotel().getBookingNumber() == hotelID)
                hotels.remove(i);
        }
        return true;
    }
}
