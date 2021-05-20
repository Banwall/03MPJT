package com.model2.mvc.view.product;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;

public class UpdateProductViewAction extends Action {


	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("UpdateProductViewAction Start.........");
		
		int prod_no = Integer.parseInt(request.getParameter("prod_no"));
		
		ProductService service=new ProductServiceImpl();
		Product productVO =service.getProduct(prod_no);
		
		request.setAttribute("vo", productVO);
		System.out.println("UpdateProductViewAction END.........");
		return "forward:/product/updateProductView.jsp";
	}

}
