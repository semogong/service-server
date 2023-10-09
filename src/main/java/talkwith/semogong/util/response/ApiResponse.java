package talkwith.semogong.util.response;

import lombok.Data;

import java.util.Map;

@Data
public class ApiResponse {

    private String statusCode;
    private String msg;
    private Map<String, Object> data;

    public ApiResponse(String statusCode, String msg) {
        this.statusCode = statusCode;
        this.msg = msg;
        this.data = Map.of("data", "");
    }

    public ApiResponse(ServiceApiResponse result) {
        this.statusCode = result.getStatusCode();
        this.msg = result.getMsg();
        this.data = result.getData();
    }

}
