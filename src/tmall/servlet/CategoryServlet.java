package tmall.servlet;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tmall.bean.Category;
import tmall.util.ImageUtil;
import tmall.util.Page;

public class CategoryServlet extends BaseBackServlet{
	//���
	public String add(HttpServletRequest request,HttpServletResponse response,Page page){
		//��ȡ����������Ķ����Ʋ�����ͼƬ�ȣ�������BaseServlet�е�Upload��������󣬸���������
		Map<String,String> params = new HashMap<>();
		InputStream is = super.parseUpload(request, params);
		//��ȡ�����name��Ϣ������name������DAO������ݿ�
		String name = params.get("name");
		Category c = new Category();
		c.setName(name);
		categoryDAO.add(c);
		//��λ���������Ŀ¼
		File imageFolder = new File(request.getSession().getServletContext().getRealPath("img/category"));
		//�ϴ����������ʽ��id.jpg����
		File file = new File(imageFolder,c.getId()+".jpg");
		//��ʽ�ϴ�
		try {
			//�����������Ϊ�����ǺϷ��ģ�����Ը��ƽ�������
			if(is!=null && is.available()!=0){
				try (FileOutputStream fos = new FileOutputStream(file)){
					//��ʼ�������ƿռ�
					byte b[] = new byte[1024*1024];
					int length = 0;
					//����������ȡ�����ֽڣ�ֱ���������ȱ�-1��������Ϊֹ
					while((length = is.read(b)) != -1){
						//д�������������Ĳ��������ݡ���ʼ������
						fos.write(b, 0, length);
					}
					//ˢ��ִ��
					fos.flush();
					//ȷ���ļ���jpg��ʽ
					BufferedImage img = ImageUtil.change2jpg(file);
					//ִ��дͼƬ������������󣬸�ʽ�����ƣ����������
					ImageIO.write(img, "jpg", file);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		//@��ͷΪ�ͻ�����ת
		return "@admin_category_list";
	}
	
	//ɾ��
	public String delete(HttpServletRequest request,HttpServletResponse response,Page page){
		//��ȡ��վ����Ҫɾ����Ŀ��ID
		int id = Integer.parseInt(request.getParameter("id"));
		//ͨ��DAOɾ��
		categoryDAO.delete(id);
		//�ͻ�����ת
		return "@admin_category_list";
	}
	
	//�༭
	public String edit(HttpServletRequest request,HttpServletResponse response,Page page){
		//��ȡ��վ����Ҫ�༭��id
		int id = Integer.parseInt(request.getParameter("id"));
		//��idͨ��DAO��ȡʵ�����еĶ���
		Category c = categoryDAO.get(id);
		//�ѻ�ȡ���Ķ������������
		request.setAttribute("c", c);
		//�������ת���༭ҳ�棬��jspҳ��ѻ�ȡ���Ķ�����ڴ��༭ҳ���ϣ�����ͨ��update��������
		return "admin/editCategory.jsp";
	}
	
	
	//��ѯ
	public String list(HttpServletRequest request,HttpServletResponse response,Page page){
		//ͨ��BaseServlet������DAO�����ݿ��ȡ���ݼ���
		List<Category> cs = categoryDAO.list(page.getStart(), page.getCount());
		//ͨ�������ж�һ���ж���ҳ�棬���һҳ�Ƕ���
		int total = categoryDAO.getTotal();
		page.setTotal(total);
		
		//��ȡ�������ݼ��Ϸ���key�У�����ת��jsp��ʹ��
		request.setAttribute("thecs", cs);
		request.setAttribute("page", page);
		
		//��ת��list��jsp����ת�ж��ѳ�����BaseServlet��
		return "admin/listCategory.jsp";
	}
}
