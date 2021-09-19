package codehouse.simparty.controller;

import codehouse.simparty.dto.ImageDTO;
import codehouse.simparty.service.AmazonS3Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import net.coobird.thumbnailator.Thumbnailator;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@Log4j2
@RequiredArgsConstructor
public class FileController {

    @Value("C:/simparty_imagesTemp")
    private String uploadTemp; // 디렉토리 주소
    @Value("https://simparty.s3.ap-northeast-2.amazonaws.com/clothes")
    private String uploadSimClothes; // 디렉토리 주소

    private final AmazonS3Service amazonS3Service;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/uploadAjax")
    public ResponseEntity<List<ImageDTO>> uploadFile(MultipartFile[] uploadFiles) throws IOException {
        log.info("=====FileController=====uploadFile=====");

        List<ImageDTO> resultDTOList = new ArrayList<>();

        for (MultipartFile uploadFile : uploadFiles) {

            // 이미지 파일만 업로드 가능
            if (uploadFile.getContentType().startsWith("image") == false) {
                log.warn("this file is not image type");

                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
            // UUID
            String uuid = UUID.randomUUID().toString();
            String fileName = amazonS3Service.getFilename(uploadFile);

            // 원본 S3 업로드 시작
            String clothesDirPath = amazonS3Service.uploadStart(uploadFile, uuid);
            log.info("ClothesDirPath: " + clothesDirPath);

            // 저장할 파일 이름 중간에 "_" 를 이용해서 구분
            String saveName = uploadTemp + File.separator + fileName;
            Path savePath = Paths.get(saveName);
            try {
                //원본 파일 임시 저장
                uploadFile.transferTo(savePath);

                // 섬네일 생성 추가
                String thumbnailSaveName = uploadTemp + File.separator + fileName;
                System.out.println(thumbnailSaveName);

                // 섬네일 파일 이름은 중간에 s_로 시작하도록
                File thumbnailFile = new File(thumbnailSaveName);
                System.out.println(thumbnailFile);

                // 섬네일 생성
                Thumbnailator.createThumbnail(savePath.toFile(), thumbnailFile,270,337 );
                System.out.println("ThumbnailFile Size: " + thumbnailFile.length());

                // 섬네일 로컬 파일 삭제
                String thumbnailDirPath = amazonS3Service.uploadThumbnail(thumbnailFile, uuid);
                log.info("ThumbnailDirPath: " + thumbnailDirPath);

                resultDTOList.add(new ImageDTO(fileName,uuid,uploadSimClothes));
            } catch (IOException e) {
                e.printStackTrace();
            }

        } // end for
        return new ResponseEntity<>(resultDTOList, HttpStatus.OK);
    }

    @GetMapping("/display")
    public ResponseEntity<byte[]> getFile(String fileName, String size) {
        log.info("=====FileController=====getFile=====");

        ResponseEntity<byte[]> result = null;

        try {
            String srcFileName =  URLDecoder.decode(fileName,"UTF-8");

            log.info("fileName: " + srcFileName);

            File file = new File(uploadTemp + File.separator + srcFileName);

            System.out.println("size: " + size);
            // 원본 이미지 조회 추가
            if (size != null && size.equals("1")) {
                file = new File(file.getParent(), file.getName().substring(2));
            }

            log.info("file: " + file);

            HttpHeaders header = new HttpHeaders();

            //MIME타입 처리
            header.add("Content-Type", Files.probeContentType(file.toPath()));
            //파일 데이터 처리
            result = new ResponseEntity<>(FileCopyUtils.copyToByteArray(file), header, HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/removeFile")
    public ResponseEntity<Boolean> removeFile(String fileName){
        try {
            amazonS3Service.delete(fileName);

            // 섬네일 삭제 부분 넣기

            return new ResponseEntity<>(true, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
