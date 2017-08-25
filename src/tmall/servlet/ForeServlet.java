package tmall.servlet;

import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.util.HtmlUtils;

import tmall.bean.Category;
import tmall.bean.Product;
import tmall.bean.ProductImage;
import tmall.bean.PropertyValue;
import tmall.bean.Review;
import tmall.bean.User;
import tmall.comparator.ProductAllComparator;
import tmall.comparator.ProductDateComparator;
import tmall.comparator.ProductPriceComparator;
import tmall.comparator.ProductReviewComparator;
import tmall.comparator.ProductSaleCountComparator;
import tmall.dao.CategoryDAO;
import tmall.dao.ProductDAO;
import tmall.dao.ProductImageDAO;
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
    //注册
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
	//登陆
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
	//跳转到产品详情页
	public String product(HttpServletRequest request, HttpServletResponse response, Page page) {
		int pid = Integer.parseInt(request.getParameter("pid"));
		Product p = productDAO.get(pid);
		
		List<ProductImage> productSingleImages = productImageDAO.list(p, ProductImageDAO.type_single);
		List<ProductImage> productDetailImages = productImageDAO.list(p, ProductImageDAO.type_detail);
		p.setProductSingleImages(productSingleImages);
		p.setProductDetailImages(productDetailImages);
		
		List<PropertyValue> pvs = propertyValueDAO.list(p.getId());		
	
		List<Review> reviews = reviewDAO.list(p.getId());
		
		productDAO.setSaleAndReviewNumber(p);

		request.setAttribute("reviews", reviews);

		request.setAttribute("p", p);
		request.setAttribute("pvs", pvs);
		return "product.jsp";		
	}
	//退出
	public String logout(HttpServletRequest request, HttpServletResponse response, Page page) {
		request.getSession().removeAttribute("user");
		return "@forehome";	
	}
	//点击购物时检查是否登陆
	public String checkLogin(HttpServletRequest request, HttpServletResponse response, Page page) {
		User user =(User) request.getSession().getAttribute("user");
		if(null!=user)
			return "%success";
		return "%fail";
	}
	//如果未登陆，模态登陆，弹一个框出来让登陆
	public String loginAjax(HttpServletRequest request, HttpServletResponse response, Page page) {
		String name = request.getParameter("name");
		String password = request.getParameter("password");		
		User user = userDAO.get(name,password);
		
		if(null==user){
			return "%fail";	
		}
		request.getSession().setAttribute("user", user);
		return "%success";	
	}
	//排序，按照人气，销量等等
	public String category(HttpServletRequest request, HttpServletResponse response, Page page) {
		int cid = Integer.parseInt(request.getParameter("cid"));
		
		Category c = new CategoryDAO().get(cid);
		new ProductDAO().fill(c);
		new ProductDAO().setSaleAndReviewNumber(c.getProducts());		
		
		String sort = request.getParameter("sort");
		if(null!=sort){
		switch(sort){
			case "review":
				Collections.sort(c.getProducts(),new ProductReviewComparator());
				break;
			case "date" :
				Collections.sort(c.getProducts(),new ProductDateComparator());
				break;
				
			case "saleCount" :
				Collections.sort(c.getProducts(),new ProductSaleCountComparator());
				break;
				
			case "price":
				Collections.sort(c.getProducts(),new ProductPriceComparator());
				break;
				
			case "all":
				Collections.sort(c.getProducts(),new ProductAllComparator());
				break;
			}
		}
		
		request.setAttribute("c", c);
		return "category.jsp";		
	}
	//搜索
	public String search(HttpServletRequest request, HttpServletResponse response, Page page){
		String keyword = request.getParameter("keyword");
		List<Product> ps= new ProductDAO().search(keyword,0,20);
		productDAO.setSaleAndReviewNumber(ps);
		request.setAttribute("ps",ps);
		return "searchResult.jsp";
	}
}
