package com.lms.api.mobile.controller;

import com.lms.api.common.dto.LoginInfo;
import com.lms.api.mobile.controller.dto.CreateReservationCgtRequest;
import com.lms.api.mobile.controller.dto.CreateReservationsRequest;
import com.lms.api.mobile.controller.dto.CreateReservationsResponse;
import com.lms.api.mobile.controller.dto.DeleteReservationCgtRequest;
import com.lms.api.mobile.controller.dto.ListCgtRequest;
import com.lms.api.mobile.controller.dto.ListRemainSchedulesRequest;
import com.lms.api.mobile.controller.dto.ListRemainSchedulesResponse;
import com.lms.api.mobile.controller.dto.ListSchedulesRequest;
import com.lms.api.mobile.controller.dto.ListSchedulesResponse;
import com.lms.api.mobile.controller.dto.ListTeachersRequest;
import com.lms.api.mobile.controller.dto.ListTeachersResponse;
import com.lms.api.mobile.controller.dto.ListTimesRequest;
import com.lms.api.mobile.controller.dto.ListTimesResponse;
import com.lms.api.mobile.controller.dto.UpdateCancelReservationsRequest;
import com.lms.api.mobile.controller.dto.UpdateCancelReservationsResponse;
import com.lms.api.mobile.service.ReservationService;
import com.lms.api.mobile.service.dto.Cgt;
import com.lms.api.mobile.service.dto.CreateReservationCgt;
import com.lms.api.mobile.service.dto.CreateReservationCgtResponse;
import com.lms.api.mobile.service.dto.CreateReservations;
import com.lms.api.mobile.service.dto.Reservation;
import com.lms.api.mobile.service.dto.Schedule;
import com.lms.api.mobile.service.dto.Teacher;
import com.lms.api.mobile.service.dto.UpdateCancelReservations;
import jakarta.validation.Valid;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mobile/v1/reservations")
@RequiredArgsConstructor
@Slf4j
public class ReservationController {
    private final ReservationService reservationService;
    private final ReservationControllerMapper reservationControllerMapper;

    @PutMapping("/cancel")
    public UpdateCancelReservationsResponse deleteReservations(LoginInfo loginInfo,  @RequestBody UpdateCancelReservationsRequest request) {
        UpdateCancelReservations updateCancelReservations = reservationControllerMapper.toUpdateCancelReservations(request);

        List<Reservation> reservations = reservationService.updateCancelReservations(updateCancelReservations, loginInfo.getId());

        return UpdateCancelReservationsResponse.builder()
            .cancelReservations(reservationControllerMapper.toUpdateCancelReservationsResponseReservations(reservations))
            .build();
    }

    @GetMapping("/times")
    public ListTimesResponse listTimes(@Valid ListTimesRequest request) {
        List<LocalTime> times = reservationService.listTimes(request.getDate(), request.getTeacherId());

        return ListTimesResponse.builder()
            .times(times)
            .build();
    }

    @GetMapping("/teachers")
    public ListTeachersResponse listTeachers(@Valid ListTeachersRequest request) {
        List<Teacher> teachers = reservationService.listTeachers(request.getDate(), request.getTime());

        return ListTeachersResponse.builder()
            .teachers(reservationControllerMapper.toListTeachersResponseTeachers(teachers))
            .build();
    }

    @GetMapping("/schedules/remain")
    public ListRemainSchedulesResponse listRemainSchedules(@Valid ListRemainSchedulesRequest request) {
        List<Schedule> schedules = reservationService.listRemainSchedules(request.getDate(), request.getTime());

        return ListRemainSchedulesResponse.builder()
            .schedules(reservationControllerMapper.toListSchedulesResponseSchedules(schedules))
            .build();
    }

    @PostMapping
    public CreateReservationsResponse createReservations(LoginInfo loginInfo, @RequestBody @Valid CreateReservationsRequest request) {
        CreateReservations createReservations = reservationControllerMapper.toCreateReservations(request, loginInfo.getId());

        List<Schedule> schedules = reservationService.createReservations(createReservations);

        return CreateReservationsResponse.builder()
            .date(request.getDate())
            .schedules(reservationControllerMapper.toCreateReservationsResponseSchedules(schedules))
            .build();
    }

    @GetMapping("/schedules")
    public ListSchedulesResponse listSchedules(LoginInfo loginInfo, @Valid ListSchedulesRequest request) {
        LocalDate startDate = request.getYearMonth().atDay(1);
        LocalDate endDate = request.getYearMonth().atEndOfMonth();

        List<Reservation> reservations = reservationService.listReservations(loginInfo.getId(), startDate, endDate);

        return ListSchedulesResponse.builder()
            .schedules(Stream.iterate(startDate, date -> !endDate.isBefore(date), date -> date.plusDays(1))
                .map(date -> ListSchedulesResponse.Schedule.builder()
                    .date(date)
                    .reservations(reservations.stream()
                        .filter(reservation -> reservation.getDate().equals(date))
                        .map(reservationControllerMapper::toListSchedulesResponseReservation)
                        .toList())
                    .build())
                .toList())
            .build();
    }

    /** CGT 목록 */
    @GetMapping("/cgt")
    public List<Cgt> listCgt(  LoginInfo loginInfo, @Valid ListCgtRequest request){
        return reservationService.listCgt(loginInfo.getId(), request);
    }

    /** CGT 예약*/
    @PostMapping("/cgt")
    public CreateReservationCgtResponse createReservationCgt(LoginInfo loginInfo, @Valid @RequestBody  CreateReservationCgtRequest request){
        CreateReservationCgt createCgt = reservationControllerMapper.toCreateReservationCgt(loginInfo.getId(), request);
        return reservationService.createReservationCgt(createCgt);
    }

    /** CGT 예약취소*/
    @DeleteMapping("/cgt")
    public void deleteReservationCgt(LoginInfo loginInfo, @Valid @RequestBody DeleteReservationCgtRequest request){
        reservationService.deleteReservationCgt(loginInfo.getId(), request);
    }
}
