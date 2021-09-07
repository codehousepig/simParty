package codehouse.simparty.service;

import codehouse.simparty.dto.ClothesDTO;
import codehouse.simparty.dto.PageRequestDTO;
import codehouse.simparty.dto.PageResultDTO;
import codehouse.simparty.entity.Booking;
import codehouse.simparty.entity.Clothes;
import codehouse.simparty.entity.Image;
import codehouse.simparty.entity.QClothes;
import codehouse.simparty.repository.ClothesRepository;
import codehouse.simparty.repository.ImageRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Service
@Log4j2
@RequiredArgsConstructor
public class ClothesServiceImpl implements ClothesService {

    private final ClothesRepository clothesrepository;
    private final ImageRepository imagerepository;

    @Transactional
    @Override
    public Long register(ClothesDTO clothesDTO) {

        Map<String, Object> entityMap = dtoToEntity(clothesDTO);
        Clothes clothes = (Clothes) entityMap.get("clothes");
        List<Image> imageList = (List<Image>) entityMap.get("imageList");

        clothesrepository.save(clothes);
        imageList.forEach(i -> {
            imagerepository.save(i);
        });

        return clothes.getCno();
    }

    @Override
    public ClothesDTO read(Long cno) {
        log.info("=====ClothesServiceImpl=====READ=====");

        List<Object[]> resultI = clothesrepository.getClothesWithImage(cno);
        List<Object[]> resultB = clothesrepository.getClothesWithBooking(cno);
        Clothes clothes = (Clothes) resultI.get(0)[0];

        List<Image> imageList = new ArrayList<>();
        resultI.forEach(arr -> {
            Image image = (Image) arr[1];
            imageList.add(image);
        });

        List<Booking> bookingList = new ArrayList<>();
        resultB.forEach(arr -> {
            Booking booking = (Booking) arr[1];
            System.out.println(booking);
            bookingList.add(booking);
        });

        return entityToDTO(clothes, imageList, bookingList);
    }

    @Transactional
    @Override
    public void modify(ClothesDTO clothesDTO) {
        log.info("=====ClothesServiceImpl=====MODIFY=====");

        Long cno = clothesDTO.getCno();
        List<Object[]> result = clothesrepository.getClothesWithImage(cno);
        result.forEach(arr -> {
            imagerepository.deleteById( ((Image)arr[1]).getInum() );
        });

        Map<String, Object> entityMap = dtoToEntity(clothesDTO);
        Clothes clothes = (Clothes) entityMap.get("clothes");
        List<Image> imageList = (List<Image>) entityMap.get("imageList");

        imageList.forEach(i -> {
            imagerepository.save(i);
        });

    }

    @Override
    public void remove(Long cno) {
        List<Object[]> result = clothesrepository.getClothesWithImage(cno);

        result.forEach(arr -> {
            imagerepository.deleteById( ((Image)arr[1]).getInum() );
        });

        clothesrepository.deleteById(cno);
    }

    @Override
    public PageResultDTO<ClothesDTO, Object[]> getList(PageRequestDTO requestDTO) {
        log.info("=====ClotheServiceImpl=====PageResultDTO=====");
        log.info(requestDTO);

//        Pageable pageable = requestDTO.getPageable(Sort.by("cno").descending()); // 검색 구현 후 주석 처리
//        BooleanBuilder booleanBuilder = getSearch(requestDTO); // 검색 조건 처리 -> 여러 엔티티 타입을 사용하기 때문에 사용 X
//        Page<Object[]> result = clothesrepository.getListPage(pageable); // 검색 구현 후 주석 처리

        Function<Object[], ClothesDTO> fn = (en -> entityToDTO(
                (Clothes) en[0],
                (List<Image>) (Arrays.asList((Image)en[1])),
                (List<Booking>) (Arrays.asList((Booking) en[2])))
        );

        Page<Object[]> result = clothesrepository.searchPage(
                requestDTO.getType(),
                requestDTO.getName(),
                requestDTO.getKeyword(),
                requestDTO.getPageable(Sort.by("cno").descending())
        );

        return new PageResultDTO<>(result, fn);
    }

/*    // Querydsl 처리
    private BooleanBuilder getSearch(PageRequestDTO requestDTO) {
        String type = requestDTO.getType();
        String keyword = requestDTO.getKeyword();

        BooleanBuilder booleanBuilder = new BooleanBuilder();
        QClothes qClothes = QClothes.clothes;
        BooleanExpression expression = qClothes.cno.gt(0L); // cno > 0 조건만 생성
        booleanBuilder.and(expression);

        if (type == null || type.trim().length() == 0) { // 검색 조건이 없는 경우
            return booleanBuilder;
        }

        // 검색 조건을 작성하기
        BooleanBuilder conditionBuilder = new BooleanBuilder();

        if (type.contains("t")) {
            conditionBuilder.or(qClothes.title.contains(keyword));
        }
        if (type.contains("k")) {
            conditionBuilder.or(qClothes.keyword.contains(keyword));
        }

        booleanBuilder.and(conditionBuilder);
        return booleanBuilder;
    }*/
}
