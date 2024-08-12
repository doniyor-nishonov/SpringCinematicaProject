package com.pdp.service;

import com.pdp.daos.MovieDAO;
import com.pdp.domains.Category;
import com.pdp.domains.Image;
import com.pdp.domains.Movie;
import com.pdp.domains.MovieCategory;
import com.pdp.dto.MovieDTO;
import com.pdp.dto.MovieSaveDTO;
import com.pdp.utils.ImageUtils;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * @author Doniyor Nishonov
 * @since 04/August/2024  11:58
 **/
@Component
public class MovieService implements BaseService<Movie, Integer> {
    private final MovieDAO dao;
    private final ImageService imageService;
    private final CategoryService categoryService;
    private final MovieCategoryService movieCategoryService;
    private final ImageUtils imageUtils;

    @Autowired
    public MovieService(MovieDAO dao, ImageService imageService, CategoryService categoryService, MovieCategoryService movieCategoryService, ImageUtils imageUtils) {
        this.dao = dao;
        this.imageService = imageService;
        this.categoryService = categoryService;
        this.movieCategoryService = movieCategoryService;
        this.imageUtils = imageUtils;
    }

    @Override
    public Integer save(Movie domain) {
        return dao.save(domain);
    }

    @Override
    public void update(Movie domain) {
        dao.update(domain);
    }

    @Override
    public void delete(Integer uuid) {
        dao.delete(uuid);
    }

    @Override
    public Movie findById(Integer uuid) {
        return dao.findById(uuid);
    }

    @Override
    public List<Movie> findAll() {
        return dao.findAll();
    }

    public List<MovieDTO> findAllMoviesWithImages() {
        return dao.findAllMoviesWithImages();
    }


    public Integer saveWithDTO(MovieSaveDTO dto) {
        String title = dto.getTitle();
        String category = dto.getCategory();
        Integer category_id = categoryService.save(new Category(category));
        String description = dto.getDescription();
        String language = dto.getLanguage();
        String trailerUrl = dto.getTrailerUrl();
//        LocalDate releaseDate = LocalDate.parse(dto.getReleaseDate());
        LocalDate releaseDate = dto.getReleaseDate();
        Integer imageId = imageUtils.saveFile(dto.getFile());
        Movie movie = Movie.builder()
                .title(title)
                .description(description)
                .language(language)
                .releaseDate(releaseDate)
                .trailerUrl(trailerUrl)
                .rating(dto.getRating())
                .imageId(imageId)
                .build();
        Integer movie_id = save(movie);
        movieCategoryService.save(new MovieCategory(movie_id, category_id));
        return movie_id;
    }
    public List<MovieDTO> findAllMoviesByCategory(String selectedCategory) {
        if (Objects.isNull(selectedCategory) || selectedCategory.isEmpty()) return findAllMoviesWithImages();
        return dao.findAllMoviesByCategory(selectedCategory);
    }

    public List<MovieDTO> findAllByName(String name) {
        if (Objects.isNull(name) || name.isEmpty()) return findAllMoviesWithImages();
        return dao.findAllByName(name);
    }
}
