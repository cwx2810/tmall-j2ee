package tmall.servlet;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tmall.bean.Category;

public class CategoryServlet extends BaseBackServlet{
	public String list(HttpServletRequest request,HttpServletResponse response,Page page){
		//ͨ��BaseServlet������DAO�����ݿ��ȡ���ݼ���
		List<Category> cs = categoryDAO.list(page.getStart(), page.getCount);
		int total = categoryDAO.getTotal();
		page.setTotal(total);
		
		//��ȡ�������ݼ��Ϸ���key�У�����ת��jsp��ʹ��
		request.setAttribute("thecs", cs);
		request.setAttribute("page", page);
		
		//��ת��list��jsp����ת�ж��ѳ�����BaseServlet��
		return "admin/listCategory.jsp";
	}
}
