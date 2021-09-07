package codehouse.simparty.repository.search;

import codehouse.simparty.entity.Clothes;
import codehouse.simparty.entity.QClothes;
import codehouse.simparty.entity.QImage;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.JPQLQuery;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;
import java.util.stream.Collectors;

@Log4j2
public class SearchClothesRepositoryImpl extends QuerydslRepositorySupport implements SearchClothesRepository{

    public SearchClothesRepositoryImpl() {
        super(Clothes.class);
    }

    @Override
    public Page<Object[]> searchPage(String type, String name, String keyword, Pageable pageable) {
        log.info("=====SearchClothesRepositoryImpl=====searchPage=====");

        QClothes clothes = QClothes.clothes;
        QImage image = QImage.image;

        JPQLQuery<Clothes> jpqlQuery = from(clothes);
        jpqlQuery.leftJoin(image).on(image.clothes.eq(clothes));

        JPQLQuery<Tuple> tuple = jpqlQuery.select(clothes, image);

        BooleanBuilder booleanBuilder = new BooleanBuilder();
        BooleanExpression expression = clothes.cno.gt(0L);

        booleanBuilder.and(expression);

        if (type != null) {
            String[] typeArr = type.split("");
            // 검색 조건을 작성하기
            BooleanBuilder conditionBuilder = new BooleanBuilder();
            for (String t : typeArr) {
                switch (t) {
                    case "n":
                        conditionBuilder.or(clothes.title.contains(name));
                        break;
                    case "k":
                        conditionBuilder.or(clothes.keyword.contains(keyword));
                        break;
                    case "kt":
                        conditionBuilder.or(clothes.keyword.contains(keyword));
                        conditionBuilder.or(clothes.title.contains(name));
                        break;
                }
            }
            booleanBuilder.and(conditionBuilder);
        }
        tuple.where(booleanBuilder);

        // order by
        Sort sort = pageable.getSort();

        // tuple.orderBy(clothes.cno.desc()); // 를 직접 코드로 처리하면
        sort.stream().forEach(order -> {
            Order direction = order.isAscending() ? Order.ASC : Order.DESC;
            String prop = order.getProperty();

            PathBuilder orderByExpression = new PathBuilder(Clothes.class, "clothes");
            tuple.orderBy(new OrderSpecifier(direction, orderByExpression.get(prop)));
        });

        tuple.groupBy(clothes);

        // page 처리
        tuple.offset(pageable.getOffset());
        tuple.limit(pageable.getPageSize());

        List<Tuple> result = tuple.fetch();

        log.info(result);

        long count = tuple.fetchCount();

        return new PageImpl<Object[]>(
                result.stream().map(t -> t.toArray()).collect(Collectors.toList()),
                pageable,
                count
        );
    }

    @Override
    public Clothes searchTest() {
/*        log.info("=====SearchClothesRepositoryImpl=====searchTest=====");

        QClothes clothes = QClothes.clothes;
        QImage image = QImage.image;

        JPQLQuery<Clothes> jpqlQuery = from(clothes);
        jpqlQuery.leftJoin(image).on(image.clothes.eq(clothes));

//        jpqlQuery.select(clothes, image).groupBy(clothes);
        JPQLQuery<Tuple> tuple = jpqlQuery.select(clothes, image);
        tuple.groupBy(clothes);

        System.out.println("------------------------------");
        System.out.println(jpqlQuery);
        System.out.println("------------------------------");

        List<Clothes> result = jpqlQuery.fetch();*/

        return null;
    }

}
