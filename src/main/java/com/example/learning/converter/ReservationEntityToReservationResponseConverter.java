package com.example.learning.converter;

import com.example.learning.entity.ReservationEntity;
import com.example.learning.model.response.ReservationResponse;
import org.springframework.core.convert.converter.Converter;

public class ReservationEntityToReservationResponseConverter implements Converter<ReservationEntity, ReservationResponse> {
    @Override
    public ReservationResponse convert(ReservationEntity source) {

        ReservationResponse reservationResponse = new ReservationResponse();
        reservationResponse.setCheckin(source.getCheckin());
        reservationResponse.setCheckout(source.getCheckout());

        if (source.getRoomEntity() != null)
            reservationResponse.setId(source.getRoomEntity().getId());

        return reservationResponse;
    }
}
