package com.example.learning.converter;

import com.example.learning.entity.RoomEntity;
import com.example.learning.model.Links;
import com.example.learning.model.Self;
import com.example.learning.model.response.ReservationRoomResponse;
import com.example.learning.rest.ResourceConstants;
import org.springframework.core.convert.converter.Converter;

public class RoomEntityToReservableRoomResponseConverter implements Converter<RoomEntity, ReservationRoomResponse> {

    @Override
    public ReservationRoomResponse convert(RoomEntity source) {

        ReservationRoomResponse reservationRoomResponse = new ReservationRoomResponse();
        reservationRoomResponse.setRoomNumber(source.getRoomNumber());
        reservationRoomResponse.setPrice(Integer.valueOf(source.getPrice()));

        Links links = new Links();
        Self self = new Self();
        self.setRef(ResourceConstants.ROOM_RESERVATION_V1 + "/" + source.getId());
        links.setSelf(self);

        reservationRoomResponse.setLinks(links);

        return reservationRoomResponse;
    }
}
