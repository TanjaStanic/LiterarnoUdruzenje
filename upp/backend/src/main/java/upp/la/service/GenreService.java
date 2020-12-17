package upp.la.service;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import upp.la.dto.GenreDto;
import upp.la.model.Genre;
import upp.la.repository.GenreRepository;

import java.util.List;

@Service
public class GenreService {

    @Autowired
    GenreRepository genreRepository;

    public List<Genre> findAll() {
        return genreRepository.findAll();
    }

    public Genre findOneById(Long id) {
        return genreRepository.findGenreById(id);
    }

    public Genre convertFromDTO(GenreDto genreDTO){
        Genre genre = new Genre();
        if(genre.getId()!=null) {
            genre.setId(genreDTO.getId());
        }
        genre.setName(genreDTO.getName());
        return genre;
    }
    public GenreDto convertToDTO(Genre genre){
        GenreDto genreDTO = new GenreDto();
        genreDTO.setId(genre.getId());
        genreDTO.setName(genre.getName());
        return genreDTO;
    }

}
