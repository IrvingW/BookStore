package util;

import java.util.Map;

import org.springframework.web.util.HtmlUtils;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

/*Intercept XSS attack*/
public class XssInterceptor extends AbstractInterceptor{

    @Override
    public String intercept(ActionInvocation invocation) throws Exception {
        // TODO Auto-generated method stub
        ActionContext actionContext = invocation.getInvocationContext();
        Map<String, Object> map = actionContext.getParameters();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            String value =  ((String[])(entry.getValue()))[0];
            entry.setValue(HtmlUtils.htmlEscape(value));//将提交上来的字符串进行转码
            //System.out.println((entry.getValue()));
        }
        return invocation.invoke();
    }
}