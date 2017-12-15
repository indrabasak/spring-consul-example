package com.basaki.controller;

import com.basaki.error.ErrorInfo;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.ServletRequestAttributes;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@ApiIgnore
public class CustomErrorController implements ErrorController {

    private static final String PATH = "/error";

    @Value("${debug:true}")
    private boolean debug;

    @Autowired
    private ErrorAttributes errorAttributes;

    @RequestMapping(value = PATH)
    ErrorInfo error(HttpServletRequest request, HttpServletResponse response) {
        ErrorInfo info = new ErrorInfo();
        info.setCode(response.getStatus());
        Map<String, Object> attributes = getErrorAttributes(request, debug);
        info.setMessage((String) attributes.get("message"));
        System.out.println((String) attributes.get("error"));
        return info;
    }

    @Override
    public String getErrorPath() {
        return PATH;
    }

    private Map<String, Object> getErrorAttributes(HttpServletRequest request,
            boolean includeStackTrace) {
        RequestAttributes requestAttributes =
                new ServletRequestAttributes(request);
        return errorAttributes.getErrorAttributes(requestAttributes,
                includeStackTrace);
    }

    /*
    public class ErrorJson {

    public Integer status;
    public String error;
    public String message;
    public String timeStamp;
    public String trace;

    public ErrorJson(int status, Map<String, Object> errorAttributes) {
        this.status = status;
        this.error = (String) errorAttributes.get("error");
        this.message = (String) errorAttributes.get("message");
        this.timeStamp = errorAttributes.get("timestamp").toString();
        this.trace = (String) errorAttributes.get("trace");
    }

}
     */

}
