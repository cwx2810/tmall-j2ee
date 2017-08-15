package tmall.servlet;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tmall.bean.Category;

public class CategoryServlet extends BaseBackServlet{
	public String list(HttpServletRequest request,HttpServletResponse response,Page page){
		//通过BaseServlet中声明DAO从数据库获取数据集合
		List<Category> cs = categoryDAO.list(page.getStart(), page.getCount);
		int total = categoryDAO.getTotal();
		page.setTotal(total);
		
		//将取到的数据集合放在key中，在跳转到jsp后使用
		request.setAttribute("thecs", cs);
		request.setAttribute("page", page);
		
		//跳转到list的jsp，跳转判断已抽象在BaseServlet中
		return "admin/listCategory.jsp";
	}
}
