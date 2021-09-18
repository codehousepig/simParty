package codehouse.simparty.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ImageDTO {

    private String fileName;
    private String uuid;
    private String folderPath;

    public String getImageURL() {
/*        try {
            return URLEncoder.encode(folderPath + "/" + uuid + "_" + fileName, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";*/
        String url = folderPath + "/" + uuid + "_" + fileName;
        return url;
    }

    public String getKey() {
        String str = folderPath;
        String[] array = str.split("/");
        String dir = array[array.length-1];
        String key = dir + "/" + uuid + "_" + fileName;
        return key;
    }

    public String getThumbnailURL() {
        try {
            return URLEncoder.encode(folderPath + "/s_" + uuid + "_" + fileName, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }
}
