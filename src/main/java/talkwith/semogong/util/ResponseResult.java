package talkwith.semogong.util;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Data
@Getter @Setter
public class ResponseResult {
    private String statusCode;
    private String msg;
    private Map<String, Object> data;

    public ResponseResult(String statusCode, String msg, Map<String, Object> data){
        this.statusCode = statusCode;
        this.msg = msg;
        this.data = data;
    }

}
