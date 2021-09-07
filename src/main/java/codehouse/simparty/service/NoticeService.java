package codehouse.simparty.service;

import codehouse.simparty.dto.NoticeDTO;
import codehouse.simparty.dto.PageRequestDTO;
import codehouse.simparty.dto.PageResultDTO;
import codehouse.simparty.entity.Notice;

public interface NoticeService {

    Long register(NoticeDTO noticeDTO); // C

    NoticeDTO read(Long nno); // R

    void modify(NoticeDTO noticeDTO); // U

    void remove(Long nno); // D

    PageResultDTO<NoticeDTO, Notice> getList(PageRequestDTO requestDTO);

    default Notice dtoToEntity(NoticeDTO noticeDTO) {
        Notice notice = Notice.builder()
                .nno(noticeDTO.getNno())
                .title(noticeDTO.getTitle())
                .content(noticeDTO.getContent())
                .writer(noticeDTO.getWriter())
                .build();

        return notice;
    }

    default NoticeDTO entityToDTO(Notice notice) {
        NoticeDTO noticeDTO = NoticeDTO.builder()
                .nno(notice.getNno())
                .title(notice.getTitle())
                .content(notice.getContent())
                .writer(notice.getWriter())
                .regDate(notice.getRegDate())
                .modDate(notice.getModDate())
                .build();

        return noticeDTO;
    }
}
