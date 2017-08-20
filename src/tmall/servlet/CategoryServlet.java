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
	//添加
	public String add(HttpServletRequest request,HttpServletResponse response,Page page){
		//获取浏览器传来的参数（图片等），经过BaseServlet中的Upload方法处理后，赋给输入流
		Map<String,String> params = new HashMap<>();
		InputStream is = super.parseUpload(request, params);
		//获取传入的name信息，根据name，借助DAO添加数据库
		String name = params.get("name");
		Category c = new Category();
		c.setName(name);
		categoryDAO.add(c);
		//定位服务器存放目录
		File imageFolder = new File(request.getSession().getServletContext().getRealPath("img/category"));
		//上传后的命名方式以id.jpg命名
		File file = new File(imageFolder,c.getId()+".jpg");
		//正式上传
		try {
			//如果输入流不为空且是合法的，则可以复制进服务器
			if(is!=null && is.available()!=0){
				try (FileOutputStream fos = new FileOutputStream(file)){
					//初始化二进制空间
					byte b[] = new byte[1024*1024];
					int length = 0;
					//从输入流读取所有字节，直到读到长度变-1读不出来为止
					while((length = is.read(b)) != -1){
						//写进输出流，传入的参数是数据、开始、长度
						fos.write(b, 0, length);
					}
					//刷新执行
					fos.flush();
					//确保文件是jpg格式
					BufferedImage img = ImageUtil.change2jpg(file);
					//执行写图片操作，传入对象，格式化名称，输出流对象
					ImageIO.write(img, "jpg", file);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		//@开头为客户端跳转
		return "@admin_category_list";
	}
	
	//删除
	public String delete(HttpServletRequest request,HttpServletResponse response,Page page){
		//获取网站请求要删除条目的ID
		int id = Integer.parseInt(request.getParameter("id"));
		//通过DAO删除
		categoryDAO.delete(id);
		//客户端跳转
		return "@admin_category_list";
	}
	
	//编辑
	public String edit(HttpServletRequest request,HttpServletResponse response,Page page){
		//获取网站请求要编辑的id
		int id = Integer.parseInt(request.getParameter("id"));
		//用id通过DAO获取实体类中的对象
		Category c = categoryDAO.get(id);
		//把获取到的对象放在请求中
		request.setAttribute("c", c);
		//服务端跳转到编辑页面，在jsp页面把获取到的对象放在待编辑页面上，下面通过update更新他们
		return "admin/editCategory.jsp";
	}
	
	//更新，和添加大同小异
	public String update(HttpServletRequest request,HttpServletResponse response,Page page){
		//建立哈希表，获取浏览器传来的参数
		Map<String,String> params = new HashMap();
		//用父类的update方法过滤一下，若是文件，可以直接获取，是二进制获取的方法不同，获取后赋给输入流等待处理
		InputStream is = super.parseUpload(request, params);
		//根据name和id，通过DAO更新数据库
		String name = params.get("name");
		int id = Integer.parseInt(params.get("id"));
		Category c = new Category();
		c.setId(id);
		c.setName(name);
		categoryDAO.update(c);
		//定位服务器图片存放目录
		File imageFolder = new File(request.getSession().getServletContext().getRealPath("img/category"));
		//上传后的图片以id.jpg方式命名
		File file = new File(imageFolder,c.getId()+".jpg");
		//确保图片目录是创建了的，否则会无法创建图片文件
		file.getParentFile().mkdirs();
		//正式上传
		try {
			//corner case 输入流为空或者可取字节为0，则不能进行上传
			if(is!=null && is.available()!=0){
				//可以上传，创建输入流准备复制
				try (FileOutputStream fos = new FileOutputStream(file)){
					//初始化二进制空进
					byte b[]  = new byte[1024*1024];
					int length = 0;
					//从输入流遍历读取所有字节写进输出流，直到长度变为-1读不出来为止
					while((length = is.read(b))!=-1){
						//写进输入流，开始为0，长度为length
						fos.write(b, 0, length);
					}
					//刷新执行
					fos.flush();
					//确保文件是jpg格式
					BufferedImage img = ImageUtil.change2jpg(file);
					//执行写图片操作，传入对象，格式化名称，输出流对象
					ImageIO.write(img, "jpg", file);
				} catch (Exception e) {}
			}
		} catch (IOException e) {}
		//客户端跳转
		return "@admin_category_list";
	}
	
	
	//查询
	public String list(HttpServletRequest request,HttpServletResponse response,Page page){
		//通过BaseServlet中声明DAO从数据库获取数据集合
		List<Category> cs = categoryDAO.list(page.getStart(), page.getCount());
		//通过总数判断一共有多少页面，最后一页是多少
		int total = categoryDAO.getTotal();
		page.setTotal(total);
		
		//将取到的数据集合放在key中，在跳转到jsp后使用
		request.setAttribute("thecs", cs);
		request.setAttribute("page", page);
		
		//跳转到list的jsp，跳转判断已抽象在BaseServlet中
		return "admin/listCategory.jsp";
	}
}
