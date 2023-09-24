package talkwith.semogong.util;

import lombok.Data;

import java.util.Map;

@Data
public class JsonResponse {

    public static final String SUCCESS = "0000";

    public static final String DUPLICATE_EMAIL = "0001";
    public static final String SHAPE_ERROR_EMAIL = "0002";
    public static final String LENGTH_ERROR_PASSWORD = "0003";

    public static final String SHAPE_ERROR_PASSWORD = "0004";
    public static final String DUPLICATE_NAME = "0005";
    public static final String LENGTH_ERROR_NAME = "0006";

    public static final String NOT_GENERATED_CODE = "0007";
    public static final String NOT_MATCHED_CODE = "0008";

    public static final String GENERAL_FAIL = "9999";


    private String statusCode;
    private String msg;
    private Map<String, Object> data;

    public JsonResponse(ResponseResult result) {
        this.statusCode = result.getStatusCode();
        this.msg = result.getMsg();
        this.data = result.getData();
    }

}
