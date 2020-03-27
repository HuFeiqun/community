package life.majiang.community.advice;

import life.majiang.community.exception.CustomizeException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * @Created by hfq on 2020/3/27 23:50
 * @used to:
 * @return:
 */
@ControllerAdvice
public class CustomizeExceptionHandler {

    @ExceptionHandler(Exception.class)
    ModelAndView handle(HttpServletRequest request, Throwable e, Model model) {
        //以下代码不工作
//        HttpStatus status = getStatus(request);
//        if(status.is4xxClientError()){
//            model.addAttribute("message","请求资源有误!!!");
//        }
//        else if(status.is5xxServerError()){
//            model.addAttribute("message","服务器出错!!!");
//        }

        if(e instanceof CustomizeException){  //已预判异常
            model.addAttribute("message",e.getMessage());
        }else {
            model.addAttribute("message","服务冒烟了，请稍后再试试!!!");
        }

        return new ModelAndView("error");
    }

//    private HttpStatus getStatus(HttpServletRequest request) {
//        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
//        if (statusCode == null) {
//            return HttpStatus.INTERNAL_SERVER_ERROR;
//        }
//        return HttpStatus.valueOf(statusCode);
//    }
}
