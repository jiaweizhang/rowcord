package rowcord;

/**
 * Created by jiaweizhang on 1/20/2016.
 */

/*
@EnableWebMvc
@ControllerAdvice
public class ServiceExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Throwable.class)
    @ResponseBody
    ResponseEntity<Object> handleControllerException(HttpServletRequest req, Throwable ex) {
        return new ResponseEntity<Object>(ex.getStackTrace(), HttpStatus.NOT_FOUND);
        /*ErrorResponse errorResponse = new ErrorResponse(ex);
        if(ex instanceof ServiceException) {
            errorResponse.setDetails(((ServiceException)ex).getDetails());
        }
        if(ex instanceof ServiceHttpException) {
            return new ResponseEntity<Object>(errorResponse,((ServiceHttpException)ex).getStatus());
        } else {
            return new ResponseEntity<Object>(errorResponse,HttpStatus.INTERNAL_SERVER_ERROR);
        }*/
    /*}

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        Map<String,String> responseBody = new HashMap<String, String>();
        responseBody.put("path",request.getContextPath());
        responseBody.put("message","The URL you have reached is not in service at this time (404).");
        return new ResponseEntity<Object>(responseBody,HttpStatus.NOT_FOUND);
    }
}*/