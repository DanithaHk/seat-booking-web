package com.example.backend.service.serviceImpl;

import com.example.backend.dto.BookingDTO;
import com.example.backend.dto.BookingRequestDTO;
import com.example.backend.entity.*;
import com.example.backend.repo.*;
import com.example.backend.service.BookingService;
import com.example.backend.util.VarList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
@Service
public class BookingServiceImpl implements BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private SeatRepository seatRepository;

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private BookingSeatRepository bookingSeatRepository;

    /**
     * Create a new booking
     */
    @Override
    @Transactional
    public int createBooking(BookingRequestDTO dto) {

        Schedule schedule = scheduleRepository.findById(dto.getScheduleId().intValue())
                .orElseThrow(() -> new RuntimeException("Schedule not found"));
        Bus bus = schedule.getBus(); 

        LocalDateTime bookingDateTime = LocalDateTime.of(dto.getDate(), dto.getTime());

        List<Seat> seats = seatRepository.findByBusIdAndScheduleId(bus.getId(), schedule.getId());
        System.out.println(dto.getContactNumber());
        System.out.println(dto.getSeatIds());
        System.out.println(seats.size());
        if (seats.isEmpty()) {
            seats = new ArrayList<>();
            for (int i = 1; i <= bus.getTotalSeats(); i++) {
                Seat seat = new Seat();
                seat.setBus(bus);
                seat.setSchedule(schedule);
                seat.setSeatNumber(String.valueOf(i));
                seat.setSeatStatus(SeatStatus.AVAILABLE);
                seatRepository.save(seat);
                seats.add(seat);
            }
        }

        for (Integer seatId : dto.getSeatIds()) {
            Seat seat = seats.stream()
                    .filter(s -> s.getId().equals(seatId))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Seat not found: " + seatId));
            System.out.println(seat.getSeatStatus());
            if (seat.getSeatStatus() != SeatStatus.AVAILABLE)
                throw new RuntimeException("Seat already booked: " + seat.getSeatNumber());
        }

        Booking booking = new Booking();
        booking.setSchedule(schedule);
        booking.setBookingDate(bookingDateTime);
        booking.setUserName(dto.getUserName());
        booking.setContactNumber(dto.getContactNumber());
        booking.setEmail(dto.getEmail());
        booking.setTotalPrice(dto.getTotalPrice());
        bookingRepository.save(booking);

        for (Integer seatId : dto.getSeatIds()) {
            Seat seat = seats.stream()
                    .filter(s -> s.getId().equals(seatId))
                    .findFirst().get();

            seat.setSeatStatus(SeatStatus.BOOKED);
            seat.setBookingDate(bookingDateTime);
            seatRepository.save(seat);

            BookingSeat bs = new BookingSeat();
            bs.setBooking(booking);
            bs.setSeat(seat);
            System.out.println(bs);
            bookingSeatRepository.save(bs);
        }

        return VarList.Created;
    }

    @Override
    public List<BookingDTO> getAllBookings() {
        return bookingRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<BookingDTO> getBookingsByEmail(String email) {
        return bookingRepository.findByEmail(email)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<BookingDTO> getBookingsBySchedule(Integer scheduleId) {
        return bookingRepository.findByScheduleId(scheduleId)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public int cancelBooking(Integer bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        List<BookingSeat> bookedSeats = bookingSeatRepository.findByBookingId(bookingId);
        for (BookingSeat bs : bookedSeats) {
            Seat seat = bs.getSeat();
            seat.setSeatStatus(SeatStatus.AVAILABLE);
            seat.setBookingDate(null);
            seatRepository.save(seat);
            bookingSeatRepository.delete(bs);
        }

        bookingRepository.delete(booking);
        return VarList.OK;
    }

    private BookingDTO convertToDTO(Booking booking) {
        BookingDTO dto = new BookingDTO();
        dto.setId(booking.getId());
        dto.setUserName(booking.getUserName());
        dto.setContactNumber(booking.getContactNumber());
        dto.setEmail(booking.getEmail());
        dto.setBookingDate(booking.getBookingDate());
        dto.setScheduleId(booking.getSchedule().getId());
        dto.setTotalPrice(booking.getTotalPrice());

        List<Integer> seatIds = bookingSeatRepository.findByBookingId(booking.getId())
                .stream()
                .map(bs -> bs.getSeat().getId())
                .collect(Collectors.toList());
        dto.setSeatIds(seatIds);

        return dto;
    }
    @Override
    public BookingDTO getBookingById(Integer bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));
        return convertToDTO(booking);
    }

}
