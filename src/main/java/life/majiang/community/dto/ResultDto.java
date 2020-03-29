package life.majiang.community.dto;

import life.majiang.community.exception.CustomizeErrorCode;
import life.majiang.community.exception.CustomizeException;
import lombok.Data;

/**
 * @Created by hfq on 2020/3/29 14:52
 * @used to:
 * @return:
 */
@Data
public class ResultDto {
    private int code;
    private String message;

    public static ResultDto errorOf(Integer code,String message){
        ResultDto resultDto = new ResultDto();
        resultDto.code=code;
        resultDto.message=message;
        return resultDto;
    }

    public static ResultDto errorOf(CustomizeErrorCode customizeErrorCode) {
        return errorOf(customizeErrorCode.getCode(),customizeErrorCode.getMessage());
    }

    public static ResultDto okOf(){
        ResultDto resultDto = new ResultDto();
        resultDto.code=200;
        resultDto.message="回复请求成功";
        return resultDto;
    }

    public static ResultDto errorOf(CustomizeException e) {
        return errorOf(e.getCode(),e.getMessage());
    }
}
