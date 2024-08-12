package com.pdp.controller;

import com.pdp.config.security.SessionUser;
import com.pdp.domains.*;
import com.pdp.dto.*;
import com.pdp.service.*;
import com.pdp.utils.ImageUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * @author Doniyor Nishonov
 * @since 02/August/2024  19:14
 **/
@Controller
@RequestMapping("/home")
public class HomeController {

    private final MovieService movieService;
    private final ImageService imageService;
    private final SessionUser sessionUser;
    private final ShowtimeService showtimeService;
    private final HallSeatService hallSeatService;
    private final TicketService ticketService;
    private final TransactionService transactionService;
    private final ScreenService screenService;
    private final CinemaService cinemaService;
    private final ReviewService reviewService;
    private final CategoryService categoryService;
    private final ImageUtils imageUtils;
    private final UserService userService;

    public HomeController(MovieService movieService, ImageService imageService, SessionUser sessionUser, ShowtimeService showtimeService, HallSeatService hallSeatService, TicketService ticketService, TransactionService transactionService, ScreenService screenService, CinemaService cinemaService, ReviewService reviewService, CategoryService categoryService, ImageUtils imageUtils, UserService userService) {
        this.movieService = movieService;
        this.imageService = imageService;
        this.sessionUser = sessionUser;
        this.showtimeService = showtimeService;
        this.hallSeatService = hallSeatService;
        this.ticketService = ticketService;
        this.transactionService = transactionService;
        this.screenService = screenService;
        this.cinemaService = cinemaService;
        this.reviewService = reviewService;
        this.categoryService = categoryService;
        this.imageUtils = imageUtils;
        this.userService = userService;
    }

    @GetMapping("/")
    public ModelAndView homePage() {
        ModelAndView view = new ModelAndView();
        List<MovieDTO> movies = movieService.findAllMoviesWithImages();
        view.addObject("movies", movies);
        view.setViewName("main/index");
        return view;
    }

    @GetMapping("/movies")
    public ModelAndView moviesPage(@RequestParam(value = "category", required = false) String selectedCategory) {
        ModelAndView view = new ModelAndView();
        System.out.println("selectedCategory = " + selectedCategory);
        List<MovieDTO> movies = movieService.findAllMoviesByCategory(selectedCategory);
        Set<String> categories = categoryService.findAllValues();
        view.addObject("movies", movies);
        view.addObject("categories", categories);
        view.addObject("selectedCategory", selectedCategory);
        view.setViewName("main/movies");
        return view;
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/movie_details")
    public ModelAndView movieDetailsPage(@RequestParam(value = "movie_id", required = false) Integer movie_id) {
        ModelAndView modelAndView = new ModelAndView();
        Movie movie = movieService.findById(movie_id);
        Image image = imageService.findById(movie.getImageId());
        MovieDTO movieDto = getMovieDTO(movie, image);
        List<ShowtimeDTO> showtimes = showtimeService.findByMovieId(movie_id);
        List<ReviewDTO> reviews = reviewService.findAllByMovieId(movie_id);
        System.out.println("movieDto = " + movieDto);
        modelAndView.addObject("movie", movieDto);
        modelAndView.addObject("showtimes", showtimes);
        modelAndView.addObject("reviews", reviews);
        modelAndView.addObject("extension",getImageExtension(sessionUser.getUser()));
        modelAndView.addObject("user_id", sessionUser.getId());
        modelAndView.setViewName("main/movie_details");
        return modelAndView;
    }

    private static MovieDTO getMovieDTO(Movie movie, Image image) {
        return MovieDTO.builder()
                .movie_id(movie.getId())
                .title(movie.getTitle())
                .description(movie.getDescription())
                .language(movie.getLanguage())
                .trailerUrl(movie.getTrailerUrl())
                .rating(movie.getRating())
                .releaseDate(movie.getReleaseDate())
                .originalName(image.getName())
                .extension(image.getExtension())
                .build();
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/profile")
    public ModelAndView profile() {
        ModelAndView view = new ModelAndView();
        view.setViewName("main/profile");
        AuthUser user = sessionUser.getUser();
        String extension = getImageExtension(user);
        view.addObject("user", user);
        view.addObject("extension", extension);
        return view;
    }

    private String getImageExtension(AuthUser user) {
        Image image = imageService.findById(user.getImageId());
        String extension = "/static/img/bbb.jpg";
        if (Objects.nonNull(image)) {
            extension = image.getExtension();
        }
        return extension;
    }

    @GetMapping("/buy-tickets")
    public String showSeatingChart(@RequestParam("showtimeId") Integer showtimeId, Model model) {
        List<Integer> seats = hallSeatService.findSeatNumberByShowtimeId(showtimeId);
        Showtime showtime = showtimeService.findById(showtimeId);
        Screen screen = screenService.findById(showtime.getScreenId());
        Integer totalSeats = screen.getSeatingCapacity();
        model.addAttribute("soldSeats", seats);
        model.addAttribute("showtimeId", showtimeId);
        model.addAttribute("totalSeats", totalSeats);
        return "main/ticket_purchase";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/buy-tickets")
    public String buyTickets(@RequestParam("selectedSeats") String selectedSeats, @RequestParam("showtimeId") Integer showtimeId, Model model) {
        String[] seats = selectedSeats.split(",");
        Showtime showtime = showtimeService.findById(showtimeId);
        Double price = showtime.getPrice();
        model.addAttribute("totalAmount", price * seats.length);
        model.addAttribute("selectedSeats", selectedSeats);
        model.addAttribute("showtimeId", showtimeId);
        for (String seat : seats) {
            hallSeatService.save(HallSeat.builder().showtimeId(showtimeId).seatNumber(Integer.parseInt(seat)).build());
        }
        return "main/payment";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/confirm_payment")
    public String confirmPayment(@ModelAttribute PaymentDTO dto, Model model) {
        Ticket ticket = Ticket.builder()
                .seatNumber(dto.getSelectedSeats())
                .price(dto.getTotalAmount())
                .showtimeId(dto.getShowtimeId())
                .userId(sessionUser.getId())
                .build();
        Integer ticketId = ticketService.save(ticket);
        Transaction transaction = Transaction.builder()
                .userId(sessionUser.getId())
                .totalAmount(dto.getTotalAmount())
                .paymentMethod("VISA CARD")
                .ticketId(ticketId)
                .cardHolderName(dto.getCardHolderName())
                .cardNumber(dto.getCardNumber())
                .expiryDate(dto.getExpiryDate())
                .cvv(dto.getCvv())
                .build();
        transactionService.save(transaction);
        Showtime showtime = showtimeService.findById(dto.getShowtimeId());
        Movie movie = movieService.findById(showtime.getMoveId());
        Integer cinemaID = screenService.findById(showtime.getScreenId()).getCinemaID();
        Cinema cinema = cinemaService.findById(cinemaID);
        model.addAttribute("fullname", sessionUser.getUser().getName());
        model.addAttribute("movieName", movie.getTitle());
        model.addAttribute("cinemaName", cinema.getName());
        model.addAttribute("seats", dto.getSelectedSeats());
        model.addAttribute("currentTime", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        model.addAttribute("price", dto.getTotalAmount());
        return "main/ticketConfirmation";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/transactions")
    public String paymentResp(Model model) {
        List<TransactionDTO> dtoList = transactionService.findAllByUserId(sessionUser.getId());
        System.out.println("dtoList = " + dtoList);
        model.addAttribute("transactions", dtoList);
        return "main/transactions";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/write_review")
    public String writeReview(@ModelAttribute ReviewDTO dto) {
        Review review = Review.builder()
                .moveId(dto.getMovieId())
                .userId(sessionUser.getId())
                .comment(dto.getComment())
                .rating(Double.valueOf(dto.getRating()))
                .build();
        reviewService.save(review);
        return "redirect:/home/movie_details?movie_id=" + dto.getMovieId();
    }

    @PostMapping("/delete_review")
    public String deleteReview(
            @RequestParam("movieId") Integer movieId,
            @RequestParam("reviewId") Integer reviewId
    ) {
        reviewService.delete(reviewId);
        return "redirect:/home/movie_details?movie_id=" + movieId;
    }

    @GetMapping("/search_movie")
    public String searchMovie(@RequestParam("name") String name, Model model) {
        List<MovieDTO> movies = movieService.findAllByName(name);
        Set<String> categories = categoryService.findAllValues();
        model.addAttribute("movies", movies);
        model.addAttribute("categories", categories);
        return "main/movies";
    }

    @PostMapping("/profilePhotoUpdate")
    public String profilePhoto(@RequestParam("file") MultipartFile file) {
        Integer imageId = imageUtils.saveFile(file);
        AuthUser user = sessionUser.getUser();
        user.setImageId(imageId);
        userService.update(user);
        return "redirect:/home/profile";
    }
}
