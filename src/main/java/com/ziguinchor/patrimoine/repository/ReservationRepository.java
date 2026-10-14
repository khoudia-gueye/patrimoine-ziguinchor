package com.ziguinchor.patrimoine.repository;

import com.ziguinchor.patrimoine.model.Reservation;
import com.ziguinchor.patrimoine.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByTouristeOrderByDateReservationDesc(User touriste);
}
