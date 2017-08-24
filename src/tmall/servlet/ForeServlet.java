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
	//����home��������ҳ�ṩ����
    public String home(HttpServletRequest request, HttpServletResponse response, Page page) {
    	//��ȡ����17�ַ��࣬����ķ���������DAO�н�����CRUD������ר��������ʾ��
        List<Category> cs= new CategoryDAO().list();
        //Ϊÿ����������Ʒ
        new ProductDAO().fill(cs);
        //Ϊ��������Ƽ���Ʒ����Ϊһҳ�϶���ʾ�������в�Ʒ��������ֻ����ʾһ�����Ƽ���
        new ProductDAO().fillByRow(cs);
        //�ѷ���cs���õ�������
        request.setAttribute("cs", cs);
        //������ҳ����jsp�а��������õ�cs����ҳ����
        return "home.jsp";
    }
	public String register(HttpServletRequest request, HttpServletResponse response, Page page) {
		String name = request.getParameter("name");
		String password = request.getParameter("password");
		name = HtmlUtils.htmlEscape(name);
		System.out.println(name);
		boolean exist = userDAO.isExist(name);
		
		if(exist){
			request.setAttribute("msg", "�û����Ѿ���ʹ��,����ʹ��");
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
			request.setAttribute("msg", "�˺��������");
			return "login.jsp";	
		}
		request.getSession().setAttribute("user", user);
		return "@forehome";	
	}
}
