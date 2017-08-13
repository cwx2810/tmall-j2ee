package tmall.servlet;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class BaseBackServlet extends HttpServlet{
	//重写service方法，当调用具体Servlet类中的doGet、doPost方法时会到这里调用service
	public void service(HttpServletRequest request,HttpServletResponse response){
		try {
			//获取分页信息
			
			//由访问地址解析而来的方法名传入这里，通过反射调用servlet类中相应的方法
			String method = (String)request.getAttribute("method");
			Method m = this.getClass().getMethod(method, javax.servlet.http.HttpServletRequest.class,
					javax.servlet.http.HttpServletResponse.class,page.class);
			String redirect = m.invoke(this,request,response,page).toString();
			
			//根据方法返回值，进行跳转，或者输出字符串
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
}
