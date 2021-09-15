package codehouse.simparty.service;

import codehouse.simparty.dto.BookingDTO;
import codehouse.simparty.dto.ClothesDTO;
import codehouse.simparty.dto.PageRequestDTO;
import codehouse.simparty.dto.PageResultDTO;
import codehouse.simparty.entity.Booking;
import codehouse.simparty.entity.Clothes;
import codehouse.simparty.repository.BookingRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

@SpringBootTest
public class ClothesServiceTests {

    @Autowired
    private ClothesService clothesservice;
    @Autowired
    private BookingRepository bookingrepository;

/*    @Transactional
    @Test
    public void dateModifyTest() {
        String end = "2021-09-20 20:20";
        Booking booking = bookingrepository.getById(9L);
        System.out.println("BEFORE 9L end_date: 2021-09-15T15:07:55");
        booking.changeEndDate(end);
        System.out.println(booking.getEndDate());
    }*/

/*    @Test
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
    }*/
}
