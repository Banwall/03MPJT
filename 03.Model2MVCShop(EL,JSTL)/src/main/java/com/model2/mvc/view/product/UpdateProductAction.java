package com.model2.mvc.view.product;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;

public class UpdateProductAction extends Action {

	
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		System.out.println("UpdateProductAction Start.........");
		int prodNo =  Integer.parseInt(request.getParameter("prodNo"));
		String manuDate = "";
		
		String [] arr = request.getParameter("manuDate").split("-");
		for(int i = 0 ; i< arr.length; i++) {
			System.out.println(arr[i]);
			manuDate += arr[i];
		}
		
		Product productVO = new Product();
		productVO.setProdNo(prodNo);
		productVO.setProdName(request.getParameter("prodName"));
		productVO.setProdDetail(request.getParameter("prodDetail"));
		productVO.setPrice(Integer.parseInt(request.getParameter("price")));
		productVO.setManuDate(manuDate);
		productVO.setFileName(request.getParameter("fileName"));
		
		System.out.println(productVO);
		ProductService service=new ProductServiceImpl();
		service.updateProduct(productVO);
				
		return "forward:/getProduct.do?prod_no="+prodNo;
	}

}
