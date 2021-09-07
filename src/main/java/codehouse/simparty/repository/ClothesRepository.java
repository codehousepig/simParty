package codehouse.simparty.repository;

import codehouse.simparty.entity.Clothes;
import codehouse.simparty.repository.search.SearchClothesRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;

public interface ClothesRepository extends JpaRepository<Clothes, Long>, QuerydslPredicateExecutor<Clothes>, SearchClothesRepository {

    @Query("select c, i " +
            "from Clothes c " +
            "left outer join Image i on i.clothes = c " +
            "group by c")
    Page<Object[]> getListPage(Pageable pageable);

    @Query("select c, i " +
            "from Clothes c " +
            "left outer join Image i on i.clothes = c " +
            "where c.cno = :cno " +
            "group by i")
    List<Object[]> getClothesWithAll(Long cno);


}
