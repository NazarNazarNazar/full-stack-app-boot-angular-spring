package com.example.learning.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Room")
public class RoomEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    private Integer roomNumber;

    @NotNull
    private String price;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    private List<ReservationEntity> reservationEntities;

    public RoomEntity() {
    }

    public RoomEntity(Long id, Integer roomNumber, String price) {
        this.id = id;
        this.roomNumber = roomNumber;
        this.price = price;
    }

    public RoomEntity(Integer roomNumber, String price, List<ReservationEntity> reservationEntities) {
        this.roomNumber = roomNumber;
        this.price = price;
        this.reservationEntities = reservationEntities;
    }

    public RoomEntity(Integer roomNumber, String price) {
        super();
        this.roomNumber = roomNumber;
        this.price = price;
    }



    public List<ReservationEntity> getReservationEntities() {
        return reservationEntities;
    }

    public void setReservationEntities(List<ReservationEntity> reservationEntities) {
        this.reservationEntities = reservationEntities;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(Integer roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void addReservationEntity(ReservationEntity reservationEntity) {
        if (null == reservationEntities) {
            reservationEntities = new ArrayList<>();
        }

        reservationEntities.add(reservationEntity);
    }
}
