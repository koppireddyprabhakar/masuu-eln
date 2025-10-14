package com.ectd.global.eln.security;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.ectd.global.eln.dao.LoginDao;
import com.ectd.global.eln.dto.LoginDto;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private LoginDao loginDao;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws IOException, ServletException {
		String path = request.getServletPath();
		if ("/login/logout".equals(path)) {
			final String headerVal = request.getHeader(jwtUtil.getHeader());
			if (headerVal != null && headerVal.startsWith(jwtUtil.getTokenPrefix())) {
				try {
					String token = jwtUtil.stripTokenPrefix(headerVal);
					String userId = jwtUtil.extractUserId(token);
					LoginDto loginDto = loginDao.getUserDetailsById(Integer.parseInt(userId));
					if (loginDto != null) {
						UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(loginDto,
								null, new ArrayList<>());
						SecurityContextHolder.getContext().setAuthentication(auth);
					}
				} catch (Exception e) {
					logger.info("Logout token invalid/expired, proceeding without auth for audit.");
				}
			}

			filterChain.doFilter(request, response);
			return;
		}
		final String headerVal = request.getHeader(jwtUtil.getHeader());

		if (headerVal != null && headerVal.startsWith(jwtUtil.getTokenPrefix())) {
			final String token = jwtUtil.stripTokenPrefix(headerVal);
			try {
				if (jwtUtil.isAccessToken(token) && SecurityContextHolder.getContext().getAuthentication() == null) {
					String userId = jwtUtil.extractUserId(token);
					LoginDto loginDto = loginDao.getUserDetailsById(Integer.parseInt(userId));
					if (loginDto != null && jwtUtil.validateAccessToken(token, userId)) {
						UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(loginDto,
								null, null);
						auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
						SecurityContextHolder.getContext().setAuthentication(auth);
					}
				}
			} catch (Exception e) {
				logger.warn("JWT invalid or not an access token: " + e.getMessage());
				response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				response.getWriter().write("Invalid or expired JWT");
				return;
			}
		}
		filterChain.doFilter(request, response);
	}
}
