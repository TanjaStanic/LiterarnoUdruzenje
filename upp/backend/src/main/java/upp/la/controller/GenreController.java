package upp.la.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import upp.la.model.Genre;
import upp.la.service.GenreService;

import java.util.List;

@RestController
@RequestMapping("/genre")
public class GenreController {

    @Autowired
    GenreService genreService;

    @GetMapping(value = "/all")
    public ResponseEntity<List<Genre>> allGenres() {
        return new ResponseEntity<>(genreService.findAll(), HttpStatus.OK);
    }
}
