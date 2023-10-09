package talkwith.semogong.util.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

@Data
@AllArgsConstructor
public class ServiceApiResponse {
    private String statusCode;
    private String msg;
    private Map<String, Object> data;

    public ServiceApiResponse() {
    }


    public static ServiceApiResponse create(String statusCode, String msg, Map<String, Object> data) {
        return new ServiceApiResponse(statusCode, msg, data);
    }

    public static ServiceApiResponse create(String statusCode, String msg) {
        return new ServiceApiResponse(statusCode, msg, Map.of("data",""));
    }



}
