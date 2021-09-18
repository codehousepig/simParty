package codehouse.simparty.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AmazonS3ServiceTests {

    @Autowired
    private AmazonS3Service amazonS3Service;

/*    @Test
    public void deleteTest() {
        String fileName = "clothes/4056d017-3b30-4273-8ccc-1a43e8e73777_code.jpg";
        amazonS3Service.delete(fileName);
    }*/
}
