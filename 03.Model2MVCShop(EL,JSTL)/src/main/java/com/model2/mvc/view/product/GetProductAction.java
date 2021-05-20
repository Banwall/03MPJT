package com.model2.mvc.view.product;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;

public class GetProductAction extends Action {

	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		System.out.println("\nGetProductAction Start......");
		String menu = request.getParameter("menu");
		System.out.println("Menu´Â : "+menu);
		
		int prod_no = Integer.parseInt(request.getParameter("prod_no"));
		System.out.println("Search Prod_No : "+prod_no);
		
		ProductService service=new ProductServiceImpl();
		Product product= service.getProduct(prod_no);
		
		request.setAttribute("menu", menu);
		request.setAttribute("product", product);
		System.out.println("GetProductAction End......\n");
		
		return "forward:/product/getProduct.jsp";
	}

}
