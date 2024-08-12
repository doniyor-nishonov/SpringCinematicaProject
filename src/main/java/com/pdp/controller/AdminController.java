package com.pdp.controller;

import com.pdp.domains.AuthUser;
import com.pdp.dto.EventDTO;
import com.pdp.dto.MovieDTO;
import com.pdp.dto.MovieSaveDTO;
import com.pdp.dto.ShowtimeDTO;
import com.pdp.service.MovieService;
import com.pdp.service.ScreenService;
import com.pdp.service.ShowtimeService;
import com.pdp.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * @author Doniyor Nishonov
 * @since 06/August/2024  17:14
 **/
@Controller
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {
    private final MovieService movieService;
    private final UserService userService;
    private final ScreenService screenService;
    private final ShowtimeService showtimeService;

    public AdminController(MovieService movieService, UserService userService, ScreenService screenService, ShowtimeService showtimeService) {
        this.movieService = movieService;
        this.userService = userService;
        this.screenService = screenService;
        this.showtimeService = showtimeService;
    }

    @GetMapping("/dashboard")
    public String adminPage() {
        return "admin/adminPage";
    }

    @GetMapping("/addMovie")
    public String addMoviePage(Model model) {
        model.addAttribute("movieSaveDTO", new MovieSaveDTO());
        System.out.println("GET");
        return "/admin/createMovie";
    }

    @PostMapping("/addMovie")
    public String addMoviePost(@ModelAttribute MovieSaveDTO dto) {
        System.out.println("POST");
        movieService.saveWithDTO(dto);
        return "redirect:/admin/dashboard";
    }


    @GetMapping("/manageUsers")
    public ModelAndView manageUsersPage() {
        ModelAndView view = new ModelAndView();
        view.setViewName("admin/manageUsers");
        List<AuthUser> users = userService.findAllWithoutAdmin();
        view.addObject("users", users);
        return view;
    }

    @GetMapping("/createSession")
    public String createSessionPage(@RequestParam("movie_id") Integer movie_id, Model model) {
        model.addAttribute("movie_id", movie_id);
        model.addAttribute("hallImages", screenService.findAllWithImages());
        return "admin/createSession";
    }

    @PostMapping("/createSession")
    public String createSessionPost(@ModelAttribute ShowtimeDTO dto) {
        showtimeService.saveDTO(dto);
        return "redirect:/admin/dashboard";
    }

    @GetMapping("/showMovies")
    public ModelAndView showMoviesPage() {
        ModelAndView view = new ModelAndView();
        List<MovieDTO> movies = movieService.findAllMoviesWithImages();
        view.addObject("movies", movies);
        view.setViewName("admin/showMovies");
        return view;
    }

    @GetMapping("/createEvent")
    public String createEventPage() {
        return "admin/createEvent";
    }

    @PostMapping("/createEvent")
    public String createEventPost(@ModelAttribute EventDTO dto) {
        screenService.saveWithDTO(dto);
        return "admin/createEvent";
    }

    @GetMapping("/deleteUser")
    public String deleteUser(@RequestParam("userId") Integer userId) {
        userService.delete(userId);
        return "redirect:/admin/manageUsers";
    }
    @GetMapping("/activeUser")
    public String activeUser(@RequestParam("userId") Integer userId) {
        AuthUser user = userService.findById(userId);
        user.setStatus("ACTIVE");
        userService.update(user);
        return "redirect:/admin/manageUsers";
    }
    @GetMapping("/blockUser")
    public String blockUser(@RequestParam("userId") Integer userId) {
        AuthUser user = userService.findById(userId);
        user.setStatus("BLOCK");
        userService.update(user);
        return "redirect:/admin/manageUsers";
    }
}
