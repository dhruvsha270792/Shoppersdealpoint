package com.nexusdevs.shoppersdeal.server.configuration;

import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

public class WebAppInitializer implements WebApplicationInitializer {
	
	private String TMP_Folder = "";
	private int MAX_UPLOAD_SIZE = 5 * 1024 * 1024;

	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		servletContext.getServletRegistration("default").addMapping("*.json","*.js","*.html","*.css","*.jpg","*.png","*.gif","*.ico","*.eot","*.svg","*.ttf","*.woff","*.otf","*.woff2");
		servletContext.getServletRegistration("jsp").addMapping("*.jsp");
		
		//Create Spring root application context
		AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
		context.register(ApplicationConfiguration.class);
		
		//Add this root context to servlet context as listener
		servletContext.addListener(new ContextLoaderListener(context));
		
		//Add this root context to servlet context as servlet
		ServletRegistration.Dynamic registration = servletContext.addServlet("DispatcherServlet", new DispatcherServlet(context));
		
		//Handling file uploading
		MultipartConfigElement multipartConfigElement = new MultipartConfigElement(TMP_Folder, MAX_UPLOAD_SIZE, MAX_UPLOAD_SIZE*2, MAX_UPLOAD_SIZE/2);
		registration.setMultipartConfig(multipartConfigElement);
		
		//Map DispatcherServlet
		registration.setLoadOnStartup(1);
		registration.addMapping("/");
	}

}


//Create spring servlet context
//AnnotationConfigWebApplicationContext dispatcherContext = new AnnotationConfigWebApplicationContext();
//dispatcherContext.register(annotatedClasses);