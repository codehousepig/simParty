package codehouse.simparty.service;

import codehouse.simparty.dto.NoticeDTO;
import codehouse.simparty.dto.PageRequestDTO;
import codehouse.simparty.dto.PageResultDTO;
import codehouse.simparty.entity.Notice;
import codehouse.simparty.repository.NoticeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.aspectj.weaver.ast.Not;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.function.Function;

@Service
@Log4j2
@RequiredArgsConstructor
public class NoticeServiceImpl implements NoticeService {

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
    public NoticeDTO read(Long nno) {
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

    @Override
    public PageResultDTO<NoticeDTO, Notice> getList(PageRequestDTO requestDTO) {
        Pageable pageable = requestDTO.getPageable(Sort.by("nno").descending());
        Page<Notice> result = noticeRepository.findAll(pageable);
        Function<Notice, NoticeDTO> fn = (en -> entityToDTO(en));

        return new PageResultDTO<>(result, fn);
    }
}
