package tmall.servlet;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tmall.bean.Category;
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
}
