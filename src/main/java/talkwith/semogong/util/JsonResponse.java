package talkwith.semogong.util;

import lombok.Data;

import java.util.Map;

@Data
public class JsonResponse {

    private String statusCode;
    private Map<String, Object> data;
    private String msg;

}
