package talkwith.semogong.util;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

@Data
@AllArgsConstructor
public class ResponseResult {
    private String statusCode;
    private String msg;
    private Map<String, Object> data;

    public static ResponseResult createResult(String statusCode, String msg, Map<String, Object> data) {
        return new ResponseResult(statusCode, msg, data);
    }

}
