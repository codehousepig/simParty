package codehouse.simparty.service;

import codehouse.simparty.dto.NoticeDTO;
import codehouse.simparty.entity.Notice;
import codehouse.simparty.repository.NoticeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.aspectj.weaver.ast.Not;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Log4j2
@RequiredArgsConstructor
public class NoticeService implements NoticeServiceImpl {

    private final NoticeRepository noticeRepository;

    @Override
    public Long register(NoticeDTO noticeDTO) {
        Notice notice = dtoToEntity(noticeDTO);

        log.info("==============================");
        System.out.println("NoticeEntity = " + notice);

        noticeRepository.save(notice);
        return notice.getNno();
    }

    @Override
    public NoticeDTO get(Long nno) {
        Optional<Notice> result = noticeRepository.findById(nno);

        if (result.isPresent()) {
            return entityToDTO(result.get());
        }
        return null;
    }

    @Override
    public void modify(NoticeDTO noticeDTO) {
        Long nno = noticeDTO.getNno();
        Optional<Notice> result = noticeRepository.findById(nno);

        if (result.isPresent()) {
            Notice modi = result.get();
            modi.changeTitle(noticeDTO.getTitle());
            modi.changeContents(noticeDTO.getContent());
            noticeRepository.save(modi);
        }
    }

    @Override
    public void remove(Long nno) {
        noticeRepository.deleteById(nno);
    }
}
