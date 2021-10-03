package codehouse.simparty.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;


@Builder
@AllArgsConstructor
@Data
public class PageRequestDTO {

    private int page;
    private int size;
    private String type;
    private String name;
    private String keyword;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime date;

    public PageRequestDTO(){
        this.page = 1;
        this.size = 20; // (4*5)
    }

    public Pageable getPageable(Sort sort) {
        return PageRequest.of(page - 1, size, sort);
    }
}