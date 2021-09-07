package codehouse.simparty.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClothesDTO {

    private Long cno;

    private String title;

    private String keyword;

    @Builder.Default
    private List<ImageDTO> imageDTOList = new ArrayList<>();

    private LocalDateTime regDate, modDate;
}
