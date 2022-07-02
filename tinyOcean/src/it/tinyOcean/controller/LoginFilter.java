package it.tinyOcean.controller;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.*;

/**
 * Servlet Filter implementation class LoginFilter
 */
@WebFilter( urlPatterns={ "/orderHistory.jsp"},
		servletNames={"/UserPage","/OrdineStoria"})
public class LoginFilter implements Filter {
	@Override
	public void init(FilterConfig config) throws ServletException {
		// If you have any <init-param> in web.xml, then you could get them
		// here by config.getInitParameter("name") and assign it as field.
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		HttpSession session = request.getSession(false);
		

		if (session == null || session.getAttribute("currentSessionUser") == null) { // change "user" for the session attribute you
																		// have defined

			response.sendRedirect(request.getContextPath() + "/loginPage.jsp?"); // No logged-in user found, so redirect
																				// to login page.
		} else {
			chain.doFilter(req, res); // Logged-in user found, so just continue request.
		}
	}

}