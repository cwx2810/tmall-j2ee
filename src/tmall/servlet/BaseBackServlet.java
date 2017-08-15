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
	
	//声明DAO，具体的Servlet方法中会调用此父类的DAO对数据库进行操作
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
	
	//重写service方法，当调用具体Servlet类中的doGet、doPost方法时会到这里调用service
	public void service(HttpServletRequest request,HttpServletResponse response){
		try {
			//获取分页信息
			
			//由访问地址解析而来的方法名传入这里，通过反射调用servlet类中相应的方法
			String method = (String)request.getAttribute("method");
			Method m = this.getClass().getMethod(method, javax.servlet.http.HttpServletRequest.class,
					javax.servlet.http.HttpServletResponse.class,page.class);
			String redirect = m.invoke(this,request,response,page).toString();
			
			//判断具体Servlet中方法返回值，进行服务端、客户端跳转（对跳转进行抽象，在方法中就不用每次麻烦的写），或者输出字符串
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
