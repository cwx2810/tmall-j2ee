package tmall.servlet;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tmall.dao.CategoryDAO;
import tmall.dao.OrderDAO;
import tmall.dao.OrderItemDAO;
import tmall.dao.ProductDAO;
import tmall.dao.ProductImageDAO;
import tmall.dao.PropertyDAO;
import tmall.dao.PropertyValueDAO;
import tmall.dao.ReviewDAO;
import tmall.dao.UserDAO;

public abstract class BaseBackServlet extends HttpServlet{
	
	//����DAO�������Servlet�����л���ô˸����DAO�����ݿ���в���
	public abstract String add(HttpServletRequest request,HttpServletResponse response,Page page);
	public abstract String delete(HttpServletRequest request,HttpServletResponse response,Page page);
	public abstract String edit(HttpServletRequest request,HttpServletResponse response,Page page);
	public abstract String update(HttpServletRequest request,HttpServletResponse response,Page page);
	public abstract String list(HttpServletRequest request,HttpServletResponse response,Page page);
	protected CategoryDAO categoryDAO = new CategoryDAO();
	protected OrderDAO orderDAO = new OrderDAO();
	protected OrderItemDAO orderItemDAO = new OrderItemDAO();
	protected ProductDAO productDAO = new ProductDAO();
	protected ProductImageDAO prodectImageDAO = new ProductImageDAO();
	protected PropertyDAO propertyDAO = new PropertyDAO();
	protected PropertyValueDAO propertyValueDAO = new PropertyValueDAO();
	protected ReviewDAO reviewDAO = new ReviewDAO();
	protected UserDAO userDAO = new UserDAO();
	
	//��дservice�����������þ���Servlet���е�doGet��doPost����ʱ�ᵽ�������service
	public void service(HttpServletRequest request,HttpServletResponse response){
		try {
			//��ȡ��ҳ��Ϣ
			
			//�ɷ��ʵ�ַ���������ķ������������ͨ���������servlet������Ӧ�ķ���
			String method = (String)request.getAttribute("method");
			Method m = this.getClass().getMethod(method, javax.servlet.http.HttpServletRequest.class,
					javax.servlet.http.HttpServletResponse.class,page.class);
			String redirect = m.invoke(this,request,response,page).toString();
			
			//�жϾ���Servlet�з�������ֵ�����з���ˡ��ͻ�����ת������ת���г����ڷ����оͲ���ÿ���鷳��д������������ַ���
			if(redirect.startsWith("@"))
				response.sendRedirect(redirect.substring(1));
			else if(redirect.startsWith("%"))
				response.getWriter().print(redirect.substring(1));
			else
				request.getRequestDispatcher(redirect).forward(request, response);
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
}
