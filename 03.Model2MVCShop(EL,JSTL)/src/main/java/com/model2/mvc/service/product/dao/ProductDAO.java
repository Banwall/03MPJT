package com.model2.mvc.service.product.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServlet;

import com.model2.mvc.common.Search;
import com.model2.mvc.common.util.DBUtil;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;

public class ProductDAO extends HttpServlet {
       
    public ProductDAO() {
    }
    
    public Product findProduct(int no) throws SQLException {
    	
    	Product product = new Product();
    	Connection con = DBUtil.getConnection();
    	
    	PreparedStatement pStmt = con.prepareStatement("SELECT * FROM product WHERE prod_no in ( ? )");
    	
    	pStmt.setInt(1, no);
    	ResultSet rs = pStmt.executeQuery();
    	
    	if(rs.next()) {
    		product.setProdNo(rs.getInt(1));
    		product.setProdName(rs.getString(2));
    		product.setProdDetail(rs.getString(3));
    		product.setManuDate(rs.getString(4));
    		product.setPrice(rs.getInt(5));
    		product.setFileName(rs.getString(6));
    		product.setRegDate(rs.getDate(7));
    	 }
    	
    	con.close();

		return product;
    }
    
    public Map<String , Object> getProductList(Search search) throws Exception {
    	
    	PurchaseService service = new PurchaseServiceImpl();
    	
    	Map<String,Object> map = new HashMap<String,Object>();
		Connection con = DBUtil.getConnection();
		
		String sql = "SELECT * FROM product ";
		if (search.getSearchCondition() != null) {
			if (search.getSearchCondition().equals("0")) {
				sql += " where prod_no LIKE '%"+ search.getSearchKeyword()+"%'";
			} else if (search.getSearchCondition().equals("1")) {
				sql += " where prod_name LIKE '%"+ search.getSearchKeyword()+"%'";
			} else if (search.getSearchCondition().equals("2")) {
				sql += " where price LIKE '%"+ search.getSearchKeyword()	+"%'";
			}
		}
		sql += " order by PROD_NO";

		int totalCount = this.getTotalCount(sql);
		System.out.println("ProductDAO :: totalCount :: " + totalCount);
		
		sql = makeCurrentPageSql(sql,search);
		PreparedStatement pStmt = con.prepareStatement(sql);
		ResultSet rs = pStmt.executeQuery();

		System.out.println(search);

		List<Purchase> list = new ArrayList<Purchase>();

		while(rs.next()) {
			
				Product vo = new Product();
				Purchase purchase = service.getPurchase2(rs.getInt(1));

				vo.setProdNo(rs.getInt(1));
				vo.setProdName(rs.getString(2));
				vo.setProdDetail(rs.getString(3));
				vo.setManuDate(rs.getString(4));
				vo.setPrice(rs.getInt(5));
				vo.setFileName(rs.getString(6));
				vo.setRegDate(rs.getDate(7));
				System.out.println("Product :: "+vo);
				System.out.println("Purchase :: "+purchase);
				purchase.setPurchaseProd(vo);
				
				list.add(purchase);
		}
		map.put("totalCount", new Integer(totalCount));
		
		System.out.println("list.size() : "+ list.size());
		map.put("list", list);
		System.out.println("map().size() : "+ map.size());

		rs.close();
		pStmt.close();
		con.close();

		return map;
    }
    
    public void insertProduct(Product product) throws Exception {
    	
    	System.out.println("\ninsertProduct Start..........");
    	Connection con = DBUtil.getConnection();
    	
    	String sql = "INSERT INTO product values (seq_product_prod_no.nextval, ?, ?, ?, ?, ?, sysdate) ";
    			
    	PreparedStatement pStmt = con.prepareStatement(sql);
    	pStmt.setString(1, product.getProdName());
    	pStmt.setString(2, product.getProdDetail());
    	pStmt.setString(3, product.getManuDate());
    	pStmt.setInt(4, product.getPrice());
    	pStmt.setString(5, product.getFileName());
    	pStmt.executeUpdate();
    	
    	con.close();
    	System.out.println("ProductDAO.InsertProduct END.....\n");
    }
    
    public void updateProduct(Product product) throws SQLException {
    	Connection con = DBUtil.getConnection();
    	
    	String sql = "UPDATE product SET prod_name = ? , prod_detail = ? , price = ? ,manufacture_day= ?, image_file = ? , reg_date = sysdate WHERE prod_no in (?)";
    	
    	PreparedStatement pStmt = con.prepareStatement(sql);
    	pStmt.setString(1, product.getProdName());
    	pStmt.setString(2, product.getProdDetail());
    	pStmt.setInt(3, product.getPrice());
    	pStmt.setString(4, product.getManuDate());
    	pStmt.setString(5, product.getFileName());
    	pStmt.setInt(6, product.getProdNo());
    	pStmt.executeUpdate();
    	
    	con.close();
    	System.out.println("ProductDAO.updateProduct END..........\n"); 
    }
    
    private int getTotalCount(String sql) throws Exception {
		sql = "SELECT COUNT(*)"+
				"FROM ("+sql+") countTable";
		
		Connection con = DBUtil.getConnection();
		PreparedStatement pStmt = con.prepareStatement(sql);
		ResultSet rs = pStmt.executeQuery();
		
		int totalCount = 0;
		if(rs.next()) {
			totalCount = rs.getInt(1);
		}
		
		rs.close();
		pStmt.close();
		con.close();
		
		return totalCount;
	}
	
	// 게시판 currentPage Row 만  return 
		private String makeCurrentPageSql(String sql , Search search){
			sql = 	"SELECT * "+ 
						"FROM (		SELECT inner_table. * ,  ROWNUM AS row_seq " +
										" 	FROM (	"+sql+" ) inner_table "+
										"	WHERE ROWNUM <="+search.getCurrentPage()*search.getPageSize()+" ) " +
						"WHERE row_seq BETWEEN "+((search.getCurrentPage()-1)*search.getPageSize()+1) +" AND "+search.getCurrentPage()*search.getPageSize();
			System.out.println("UserList Search 확인 : "+search);
			System.out.println("UserList Search CurrentPage 확인 : "+search.getCurrentPage());
			System.out.println("UserList Search PageSize 확인 : "+search.getPageSize());
			System.out.println("UserList Search 합 확인 : "+search.getCurrentPage()*search.getPageSize());
			
			System.out.println("UserDAO :: make SQL :: "+ sql);	
			
			return sql;
		}
}
