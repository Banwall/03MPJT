package com.model2.mvc.view.product;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;

public class AddProductAction extends Action {

	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		System.out.println("\nAddProductAction Start.......");
		Product productVO = new Product();
		String manuDate = "";
		
		//manuDate Split
		String [] arr = request.getParameter("manuDate").split("-");
		for(int i = 0 ; i< arr.length; i++) {
			System.out.println(arr[i]);
			manuDate += arr[i];
		}
		
		productVO.setProdName(request.getParameter("prodName"));
		productVO.setProdDetail(request.getParameter("prodDetail"));
		productVO.setManuDate(manuDate);
		productVO.setPrice(Integer.parseInt(request.getParameter("price")));
		productVO.setFileName(request.getParameter("fileName"));
		
		ProductService service = new ProductServiceImpl();
		service.addProduct(productVO);
		
		request.setAttribute("productVO", productVO);
		
		System.out.println("AddProductAction END.......\n");
		return "forward:/product/addProduct.jsp";	
	}
}
