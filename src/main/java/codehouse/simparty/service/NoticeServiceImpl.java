package codehouse.simparty.service;

import codehouse.simparty.dto.NoticeDTO;
import codehouse.simparty.entity.Notice;

public interface NoticeServiceImpl {

    Long register(NoticeDTO noticeDTO); // C

    NoticeDTO get(Long nno); // R

    void modify(NoticeDTO noticeDTO); // U

    void remove(Long nno); // D

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
