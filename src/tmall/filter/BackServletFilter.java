package tmall.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

public class BackServletFilter implements Filter{
	public void destroy(){
		
	}
	@Override
	public void doFilter(ServletRequest req,ServletResponse res,FilterChain chain) throws IOException,ServletException{
		//强制将Servlet转换成httpServlet，httpServlet是Servlet的子集
		HttpServletRequest request = (HttpServletRequest)req;
		HttpServletResponse response = (HttpServletResponse)res;
		//获取完整访问路径
		String uri = request.getRequestURI();
		//获取访问路径前的冗余tmall/，这个在tomcat的xml里设置的，记得吗？
		String contextPath = request.getServletContext().getContextPath();
		//切除冗余
		uri = StringUtils.remove(uri, contextPath);
		//若剩下的地址是以admin开头的就对了
		if(uri.startsWith("/admin_")){
			//用访问路径拼接成Servletpath
			String servletPath = StringUtils.substringBetween(uri, "_", "_") + "Servlet";
			//同样是基于地址获取要访问的方法名
			String method = StringUtils.substringAfterLast(uri, "_");
			//设置方法名给Servletpath
			request.setAttribute("method", method);
			//发送请求，跳转到Servlet
			req.getRequestDispatcher("/" + servletPath).forward(request, response);
			return;
		}
		//进行过滤
		chain.doFilter(request, response);
	}
	public void init(FilterConfig arg0) throws ServletException{
		
	}
}
