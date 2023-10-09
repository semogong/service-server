package talkwith.semogong.util.response;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StatusCode {
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

}
