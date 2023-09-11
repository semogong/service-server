package talkwith.semogong.util;

import lombok.Data;

import java.util.Map;

@Data
public class JsonResponse {

    public static final String SUCCESS = "0000";
    public static final String ERROR = "9999";

    private String statusCode;
    private Map<String, Object> data;
    private String msg;

}
