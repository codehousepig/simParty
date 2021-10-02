package codehouse.simparty.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookingDTO {

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime startDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime endDate;

    public boolean today() {
        LocalDateTime today = LocalDateTime.now();
        int a = today.getDayOfMonth();
        int b = startDate.getDayOfMonth();
        return a == b;
    }

    public boolean day1() {
        LocalDateTime today = LocalDateTime.now();
        int a = today.getDayOfMonth() + 1;
        int b = startDate.getDayOfMonth();
        return a == b;
    }

    public boolean day2() {
        LocalDateTime today = LocalDateTime.now();
        int a = today.getDayOfMonth() + 2;
        int b = startDate.getDayOfMonth();
        return a == b;
    }

    public boolean searchday() {

        return true;
    }

    public int daystart() {
        LocalTime start = LocalTime.of(11, 00);
        return (int) ChronoUnit.MINUTES.between(start, startDate);
    }
    public int dayend() {
        LocalTime start = LocalTime.of(11, 00);
        int st = startDate.getDayOfMonth();
        System.out.println("st = " + st);
        int ed = endDate.getDayOfMonth();
        System.out.println("ed = " + ed);

        int num = ed - st;

        System.out.println("aaaaaaaaaaaaaaaaa: " + ChronoUnit.DAYS.between(startDate, endDate));
        System.out.println("daystart(): " + daystart());
        int plus = 660 - daystart();
        System.out.println("plus: " + plus);

        if (num == 1) {
            return (int) ChronoUnit.MINUTES.between(start, endDate) ;
        } else {
            return (int) ChronoUnit.MINUTES.between(start, endDate);
        }
    }

}
