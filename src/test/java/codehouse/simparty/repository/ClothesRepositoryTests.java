package codehouse.simparty.repository;

import codehouse.simparty.dto.ClothesDTO;
import codehouse.simparty.repository.search.SearchClothesRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;
import codehouse.simparty.entity.Clothes;
import codehouse.simparty.entity.Image;

import java.util.UUID;
import java.util.stream.IntStream;

@SpringBootTest
public class ClothesRepositoryTests {

    @Autowired
    private ClothesRepository clothesrepository;
    @Autowired
    private ImageRepository imagerepository;

/*    @Test
    public void searchTest2() {

        Pageable pageable = PageRequest.of(0, 10, Sort.by("cno").descending());
        Page<Object[]> result = clothesrepository.searchPage("k", "ch", pageable);
    }*/

/*    @Test
    public void searchTest() {
        clothesrepository.searchTest();
    }*/

/*    @Commit
    @Transactional
    @Test
    public void insertClothesTest() {

        IntStream.rangeClosed(1, 30).forEach(i -> {
            Clothes clothes = Clothes.builder()
                    .title("의상..." + i)
                    .build();

            System.out.println("=============");

            clothesrepository.save(clothes);

            int count = (int)(Math.random() * 5 ) + 1; // 1, 2, 3, 4
            for (int j = 0; j < count; j++) {
                Image image = Image.builder()
                        .uuid(UUID.randomUUID().toString())
                        .clothes(clothes)
                        .fileName("test" + j + ".jpg")
                        .build();

                imagerepository.save(image);
            }
        });
    }*/
}
