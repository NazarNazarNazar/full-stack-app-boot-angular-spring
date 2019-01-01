package com.example.learning.rest;

import com.example.learning.converter.RoomEntityToReservableRoomResponseConverter;
import com.example.learning.entity.ReservationEntity;
import com.example.learning.entity.RoomEntity;
import com.example.learning.model.request.ReservationRequest;
import com.example.learning.model.response.ReservationResponse;
import com.example.learning.model.response.ReservationRoomResponse;
import com.example.learning.repository.PageableRoomRepository;
import com.example.learning.repository.ReservationRepository;
import com.example.learning.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(ResourceConstants.ROOM_RESERVATION_V1)
@CrossOrigin(origins = "http://localhost:4200")
public class ReservationResource {

    @Autowired
    PageableRoomRepository pageableRoomRepository;

    @Autowired
    RoomRepository roomRepository;

    @Autowired
    ReservationRepository reservationRepository;

    @Autowired
    ConversionService conversionService;

    @RequestMapping(path = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ReservationRoomResponse> getAvailableRooms(
            @RequestParam(value = "checkin")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                    LocalDate checkin,
            @RequestParam(value = "checkin")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                    LocalDate checkout, Pageable pageable) {

        Page<RoomEntity> roomEntityList = pageableRoomRepository.findAll(pageable);

        return roomEntityList.stream()
                .map(roomEntity -> new RoomEntityToReservableRoomResponseConverter()
                        .convert(roomEntity))
                .collect(Collectors.toList());
    }

    @GetMapping(path = "/{roomId}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<RoomEntity> getRoomById(@PathVariable Long roomId) {

        return new ResponseEntity<>(roomRepository.findRoomEntityById(roomId), HttpStatus.OK);
    }

    @PostMapping(path = "",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<ReservationResponse> createReservation(
            @RequestBody
                    ReservationRequest reservationRequest) {

        ReservationEntity reservationEntity = conversionService.convert(reservationRequest, ReservationEntity.class);
        reservationRepository.save(reservationEntity);

        RoomEntity roomEntityById =
                roomRepository.findRoomEntityById(reservationRequest.getRoomId());
        roomEntityById.addReservationEntity(reservationEntity);
        roomRepository.save(roomEntityById);
        reservationEntity.setRoomEntity(roomEntityById);

        ReservationResponse reservationResponse =
                conversionService.convert(reservationEntity, ReservationResponse.class);

        return new ResponseEntity<>(reservationResponse, HttpStatus.CREATED);
    }

    @PutMapping(path = "",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<ReservationRoomResponse> updateReservation(
            @RequestBody
                    ReservationRequest reservationRequest) {

        return new ResponseEntity<>(new ReservationRoomResponse(), HttpStatus.OK);
    }

    @DeleteMapping(path = "/{reservationId}")
    public ResponseEntity<Void> deleteReservation(
            @PathVariable
                    long reservationId) {

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
