package tmall.servlet;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.util.HtmlUtils;

import tmall.bean.Category;
import tmall.bean.User;
import tmall.dao.CategoryDAO;
import tmall.dao.ProductDAO;
import tmall.util.Page;

public class ForeServlet extends BaseForeServlet{
	//调用home方法给首页提供数据
    public String home(HttpServletRequest request, HttpServletResponse response, Page page) {
    	//获取所有17种分类，下面的方法都是在DAO中叫做非CRUD方法，专门用来显示的
        List<Category> cs= new CategoryDAO().list();
        //为每个分类填充产品
        new ProductDAO().fill(cs);
        //为分类填充推荐产品，因为一页肯定显示不下所有产品啊，必须只能显示一部分推荐的
        new ProductDAO().fillByRow(cs);
        //把分类cs设置到请求上
        request.setAttribute("cs", cs);
        //返回首页，在jsp中把上面设置的cs放在页面上
        return "home.jsp";
    }
	public String register(HttpServletRequest request, HttpServletResponse response, Page page) {
		String name = request.getParameter("name");
		String password = request.getParameter("password");
		name = HtmlUtils.htmlEscape(name);
		System.out.println(name);
		boolean exist = userDAO.isExist(name);
		
		if(exist){
			request.setAttribute("msg", "用户名已经被使用,不能使用");
			return "register.jsp";	
		}
		
		User user = new User();
		user.setName(name);
		user.setPassword(password);
		System.out.println(user.getName());
		System.out.println(user.getPassword());
		userDAO.add(user);
		
		return "@registerSuccess.jsp";	
	}
	public String login(HttpServletRequest request, HttpServletResponse response, Page page) {
		String name = request.getParameter("name");
		name = HtmlUtils.htmlEscape(name);
		String password = request.getParameter("password");		
		
		User user = userDAO.get(name,password);
		 
		if(null==user){
			request.setAttribute("msg", "账号密码错误");
			return "login.jsp";	
		}
		request.getSession().setAttribute("user", user);
		return "@forehome";	
	}
}
