package com.ra.service.booking;

import com.ra.exception.CustomException;
import com.ra.model.dto.request.BookingRequestDTO;
import com.ra.model.dto.response.BookingResponseDTO;
import com.ra.model.entity.Booking;
import com.ra.model.entity.Product;
import com.ra.model.entity.Room;
import com.ra.model.entity.User;
import com.ra.repository.IBookingRepository;
import com.ra.service.email.IEmailService;
import com.ra.service.product.IProductService;
import com.ra.service.room.IRoomService;
import com.ra.service.user.IUserService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

@Service
public class BookingServiceIMPL implements IBookingService {
    @Autowired
    private IBookingRepository bookingRepository;
    @Autowired
    private IUserService userService;
    @Autowired
    private IRoomService roomService;
    @Autowired
    private IProductService productService;
    @Autowired
    private IEmailService emailService;
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Page<BookingResponseDTO> findAll(Pageable pageable) {
        Page<Booking> bookings = bookingRepository.findAll(pageable);
        return bookings.map(BookingResponseDTO::new);
    }

    @Override
    public Page<BookingResponseDTO> searchByUserId(Pageable pageable, Long userId) throws CustomException {
        User user = userService.findUserById(userId);
        Page<Booking> bookings = bookingRepository.findAllByUser(pageable, user);
        return bookings.map(BookingResponseDTO::new);
    }

    @Override
    public Page<BookingResponseDTO> searchByStatus(Pageable pageable, Integer status) {
        Page<Booking> bookings = bookingRepository.findAllByStatus(pageable, status);
        return bookings.map(BookingResponseDTO::new);
    }

    @Override
    public BookingResponseDTO createBooking(BookingRequestDTO bookingRequestDTO, User userLogin) throws CustomException {
        Room room = roomService.findById(bookingRequestDTO.getRoomId());
        if (!room.getStatus()) {
            throw new CustomException("Phòng này không còn trống, vui lòng chọn phòng khác !");
        }
        Set<Product> products = new HashSet<>();
        for (Long id : bookingRequestDTO.getProducts()) {
            Product product = productService.findById(id);
            products.add(product);
        }
        Booking booking = Booking.builder().
                room(room).
                user(userLogin).
                checkinDate(bookingRequestDTO.getCheckinDate()).
                days(bookingRequestDTO.getDays()).
                note(bookingRequestDTO.getNote()).
                products(products).
                status(bookingRequestDTO.getStatus()).
                build();
        bookingRepository.save(booking);
        BookingResponseDTO bookingResponseDTO = new BookingResponseDTO(booking);
        emailService.sendEmail(userLogin.getEmail(), bookingResponseDTO);
        return bookingResponseDTO;
    }

    @Override
    public BookingResponseDTO changeStatusBooking(Long id, Integer status) throws CustomException {
        Booking booking = bookingRepository.findById(id).orElseThrow(() -> new CustomException("Booking không tồn tại !"));
        if (booking.getStatus() == 0) {
            booking.setStatus(status);
            if (status == 1) {
                roomService.changeStatus(booking.getRoom().getId());
            }
            bookingRepository.save(booking);
        } else {
            throw new CustomException("Booking không thể thay đổi trạng thái !");
        }
        return new BookingResponseDTO(booking);
    }

    @Override
    public BookingResponseDTO changeStatusBookingByUser(Long userId, Long bookingId) throws CustomException {
        Booking booking = bookingRepository.findById(bookingId).orElseThrow(() -> new CustomException("Booking không tồn tại !"));
        if (!booking.getUser().getId().equals(userId)) {
            throw new CustomException("Booking không tồn tại !");
        } else {
            if (booking.getStatus() == 0) {
                booking.setStatus(2);
                bookingRepository.save(booking);
            } else {
                throw new CustomException("Booking không thể thay đổi trạng thái !");
            }
        }
        return new BookingResponseDTO(booking);
    }

    @Override
    public Integer countBookingsAccepted(Integer year) {
        // Lấy ngày đầu tiên của năm
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, Calendar.JANUARY);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        Date startDate = new Date(calendar.getTimeInMillis());

        // Lấy ngày cuối cùng của năm
        calendar.set(Calendar.MONTH, Calendar.DECEMBER);
        calendar.set(Calendar.DAY_OF_MONTH, 31);
        Date endDate = new Date(calendar.getTimeInMillis());

        return bookingRepository.countAllByStatusAndCheckinDateBetween(1, startDate, endDate);
    }
}
