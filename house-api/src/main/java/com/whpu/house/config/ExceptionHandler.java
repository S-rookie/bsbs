package com.whpu.house.config;

import com.whpu.house.common.Response;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;


//@RestController
public class ExceptionHandler implements ErrorController {
    private static final String ERROR_PATH = "error";

    @RequestMapping(ERROR_PATH)
    public Response errorPage(){
        return Response.Fail();
    }

    @Override
    public String getErrorPath() {
        return ERROR_PATH;
    }
}
