package codehouse.simparty.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString(exclude = "clothes")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bno;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    @ManyToOne(fetch = FetchType.LAZY)
    private Clothes clothes;
}
