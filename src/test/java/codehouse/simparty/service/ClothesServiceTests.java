package codehouse.simparty.service;

import codehouse.simparty.dto.ClothesDTO;
import codehouse.simparty.dto.PageRequestDTO;
import codehouse.simparty.dto.PageResultDTO;
import codehouse.simparty.entity.Clothes;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ClothesServiceTests {

    @Autowired
    private ClothesService clothesservice;

    @Test
    public void SearchTest() {

        PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
                .page(1)
                .size(10)
                .type("k")
                .keyword("a")
                .build();

        PageResultDTO<ClothesDTO, Object[]> resultDTO = clothesservice.getList(pageRequestDTO);

        System.out.println("==============================");
        System.out.println(resultDTO.isPrev());
        System.out.println(resultDTO.isNext());
        System.out.println(resultDTO.getTotalPage());

        System.out.println("==============================");
        for (ClothesDTO clothesDTO : resultDTO.getDtoList()) {
            System.out.println(clothesDTO);
        }

        System.out.println("==============================");
        resultDTO.getPageList().forEach(i -> System.out.println(i));
    }
}
