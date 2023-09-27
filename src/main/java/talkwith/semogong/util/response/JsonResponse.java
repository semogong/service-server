package talkwith.semogong.util.response;

import lombok.Data;

import java.util.Map;

@Data
public class JsonResponse {

    private String statusCode;
    private String msg;
    private Map<String, Object> data;

    public JsonResponse(ResponseResult result) {
        this.statusCode = result.getStatusCode();
        this.msg = result.getMsg();
        this.data = result.getData();
    }

}
