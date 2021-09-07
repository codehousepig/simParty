package codehouse.simparty.repository.search;

import codehouse.simparty.entity.Clothes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SearchClothesRepository {

    Clothes searchTest();

    Page<Object[]> searchPage(String type, String name, String keyword, Pageable pageable);

}
