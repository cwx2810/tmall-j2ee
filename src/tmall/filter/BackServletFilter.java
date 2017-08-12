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
		//ǿ�ƽ�Servletת����httpServlet��httpServlet��Servlet���Ӽ�
		HttpServletRequest request = (HttpServletRequest)req;
		HttpServletResponse response = (HttpServletResponse)res;
		//��ȡ��������·��
		String uri = request.getRequestURI();
		//��ȡ����·��ǰ������tmall/�������tomcat��xml�����õģ��ǵ���
		String contextPath = request.getServletContext().getContextPath();
		//�г�����
		uri = StringUtils.remove(uri, contextPath);
		//��ʣ�µĵ�ַ����admin��ͷ�ľͶ���
		if(uri.startsWith("/admin_")){
			//�÷���·��ƴ�ӳ�Servletpath
			String servletPath = StringUtils.substringBetween(uri, "_", "_") + "Servlet";
			//ͬ���ǻ��ڵ�ַ��ȡҪ���ʵķ�����
			String method = StringUtils.substringAfterLast(uri, "_");
			//���÷�������Servletpath
			request.setAttribute("method", method);
			//����������ת��Servlet
			req.getRequestDispatcher("/" + servletPath).forward(request, response);
			return;
		}
		//���й���
		chain.doFilter(request, response);
	}
	public void init(FilterConfig arg0) throws ServletException{
		
	}
}
