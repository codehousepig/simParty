package codehouse.simparty.controller;

import codehouse.simparty.dto.NoticeDTO;
import codehouse.simparty.service.NoticeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Log4j2
@RequestMapping("/simparty/notice/")
@RequiredArgsConstructor
public class NoticeController {

    private final NoticeService noticeservice; // final

    @PostMapping(value = "")
    public ResponseEntity<Long> register(@RequestBody NoticeDTO noticeDTO) {
        log.info("=============REGISTER=============");
        System.out.println("NoticeDTO = " + noticeDTO);

        Long nno = noticeservice.register(noticeDTO);

        return new ResponseEntity<>(nno, HttpStatus.OK);
    }

    @GetMapping(value = "/{nno}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<NoticeDTO> read(@PathVariable("nno") Long nno) {
        log.info("=============READ=============");
        System.out.println("nno = " + nno);

        return new ResponseEntity<>(noticeservice.get(nno), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{nno}", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> remove(@PathVariable("nno") Long nno) {
        log.info("=============REMOVE=============");
        System.out.println("nno = " + nno);

        noticeservice.remove(nno);
        return new ResponseEntity<>("REMOVE", HttpStatus.OK);
    }

    @PutMapping(value = "/{nno}", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> modify(@RequestBody NoticeDTO noticeDTO) {
        log.info("=============MODIFY=============");
        System.out.println("NoticeDTO = " + noticeDTO);

        noticeservice.modify(noticeDTO);
        return new ResponseEntity<>("MODIFY", HttpStatus.OK);
    }
}
