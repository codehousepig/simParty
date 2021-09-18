package codehouse.simparty.service;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Service
@Log4j2
@RequiredArgsConstructor
public class AmazonS3Service {

    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    public String bucket;  // S3 버킷 이름

    String dirName = "clothes"; // S3 버킷 밑에 저장될 디텍토리 경로

    public void delete(String fileName) {
        try {
            amazonS3Client.deleteObject(new DeleteObjectRequest(bucket, fileName));
        } catch (AmazonServiceException e) {
            e.printStackTrace();
        } catch (SdkClientException e) {
            e.printStackTrace();
        }
    }

    // MultipartFile -> File 변환
    public String uploadStart(MultipartFile mFile, String uuid) throws IOException {
        File uploadFile = convert(mFile);
        return uploadMiddle(uploadFile, uuid); // 변환 후 호출
    }

    // S3로 파일 업로드 호출과 부가적인 기능
    private String uploadMiddle(File uploadFile, String uuid) {
        // S3에 저장될 경로를 포함한 파일 이름
        String fileName = dirName + "/" + uuid + "_" + uploadFile.getName(); // == dirName + File.separator + ...
        String uploadImageUrl = upload(uploadFile, fileName); // S3로 업로드
        removeNewFile(uploadFile); // 변환 파일 삭제
        return uploadImageUrl;
    }

    // S3로 업로드
    private String upload(File uploadFile, String fileName) {
        amazonS3Client.putObject(new PutObjectRequest(bucket, fileName, uploadFile).withCannedAcl(CannedAccessControlList.PublicRead));
        return amazonS3Client.getUrl(bucket, fileName).toString();
    }

    // 로컬에 저장된 이미지 삭제
    private void removeNewFile(File targetFile) {
        if (targetFile.delete()) {
            log.info("Convert File delete success");
            return;
        }
        log.info("Convert File delete fail");
    }

    // 변환된 파일이 로컬에 남아있음
    public File convert(MultipartFile mFile) throws IOException {
        String fileName = getFilename(mFile);
        File file = new File(fileName);
        file.createNewFile();
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(mFile.getBytes());
        fos.close();
        return file;
    }

    public String getFilename(MultipartFile mFile) {
        String originalName = mFile.getOriginalFilename();
        String fileName = originalName.substring(originalName.lastIndexOf("\\") + 1);
        log.info("=====AmazonS3Service=====getFilename=====");
        log.info("fileName: " + fileName);
        return fileName;
    }
}