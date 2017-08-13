package tmall.servlet;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class BaseBackServlet extends HttpServlet{
	//��дservice�����������þ���Servlet���е�doGet��doPost����ʱ�ᵽ�������service
	public void service(HttpServletRequest request,HttpServletResponse response){
		try {
			//��ȡ��ҳ��Ϣ
			
			//�ɷ��ʵ�ַ���������ķ������������ͨ���������servlet������Ӧ�ķ���
			String method = (String)request.getAttribute("method");
			Method m = this.getClass().getMethod(method, javax.servlet.http.HttpServletRequest.class,
					javax.servlet.http.HttpServletResponse.class,page.class);
			String redirect = m.invoke(this,request,response,page).toString();
			
			//���ݷ�������ֵ��������ת����������ַ���
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
}
