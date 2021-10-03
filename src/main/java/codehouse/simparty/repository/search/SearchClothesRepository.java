package codehouse.simparty.repository.search;

import codehouse.simparty.entity.Clothes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;

public interface SearchClothesRepository {

    Page<Object[]> searchPage(String type, String name, String keyword, LocalDateTime date, Pageable pageable);

}
