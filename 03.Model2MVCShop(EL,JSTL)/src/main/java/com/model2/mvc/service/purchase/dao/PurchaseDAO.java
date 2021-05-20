package com.model2.mvc.service.purchase.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.model2.mvc.common.Search;
import com.model2.mvc.common.util.DBUtil;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.domain.User;

public class PurchaseDAO {

	public PurchaseDAO() {
	}

	public Purchase findPurchase(int tranNO) throws SQLException {

		Connection con = DBUtil.getConnection();

		PreparedStatement pStmt = con.prepareStatement("SELECT * FROM transaction WHERE tran_no in (?)");
		pStmt.setInt(1, tranNO);
		ResultSet rs = pStmt.executeQuery();

		rs.next();

		Purchase purchase = new Purchase();
		Product product = new Product();
		product.setProdNo(rs.getInt("prod_no"));
		User user = new User();
		user.setUserId(rs.getString("buyer_id"));

		purchase.setTranNo(rs.getInt(1));
		purchase.setPurchaseProd(product);
		purchase.setBuyer(user);
		purchase.setPaymentOption(rs.getString(4).trim());
		purchase.setReceiverName(rs.getString(5));
		purchase.setReceiverPhone(rs.getString(6));
		purchase.setDivyAddr(rs.getString(7));
		purchase.setDivyRequest(rs.getString(8));
		purchase.setTranCode(rs.getString(9));
		purchase.setOrderDate(rs.getDate(10));
		purchase.setDivyDate(rs.getString("dlvy_date"));

		con.close();
		pStmt.close();
		rs.close();

		System.out.println("findPurchase END........");
		return purchase;
	}
	
	public Purchase findPurchase2(int prodNo) throws SQLException {
		
		Purchase purchase = new Purchase();
		
		Connection con = DBUtil.getConnection();

		PreparedStatement pStmt = con.prepareStatement("SELECT tran_status_code, tran_no FROM transaction WHERE prod_no in (?)");
		pStmt.setInt(1, prodNo);
		ResultSet rs = pStmt.executeQuery();
		
		if(rs.next()) {
		
			purchase.setTranCode(rs.getString(1).trim());
			purchase.setTranNo(rs.getInt(2));
		}
		
		con.close();
		pStmt.close();
		rs.close();

		return purchase;
	}

	public Map<String, Object> getPurchaseList(Search search, String buyer_id) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();
		
		User user = new User();
		user.setUserId(buyer_id);

		Connection con = DBUtil.getConnection();

		String sql = "SELECT * FROM  transaction WHERE buyer_id in ('"+buyer_id+"')";

		int totalCount = this.getTotalCount(sql);
		System.out.println("UserDAO :: totalCount :: " + totalCount);
		System.out.println(search);
		sql = makeCurrentPageSql(sql,search);
		
		PreparedStatement pStmt = con.prepareStatement(sql);
		
		ResultSet rs = pStmt.executeQuery();


		List<Purchase> list = new ArrayList<Purchase>();
			
		while(rs.next()) {
			
			Purchase vo = new Purchase();

			vo.setBuyer(user);			
			vo.setTranNo(rs.getInt(1));
			vo.setReceiverName(rs.getString(5));
			vo.setReceiverPhone(rs.getString(6));
			vo.setTranCode(rs.getString(9).trim());
				
			list.add(vo);
		}
		map.put("totalCount", new Integer(totalCount));
		
		System.out.println("list.size() : " + list.size());
		map.put("list", list);
		System.out.println("map().size() : " + map.size());

		con.close();
		pStmt.close();
		rs.close();

		System.out.println("PurchaseDAO.getPurchaseList END........\n");
		return map;
	}

	public HashMap<String, Object> getSaleList(Search search) {
		return null;
	}

	public void insertPurchase(Purchase purchase) throws Exception {

		Connection con = DBUtil.getConnection();

		String sql = "INSERT\n" + "INTO transaction\n"
				+ "values (seq_transaction_tran_no.nextval, ?, ?, ?, ?, ?, ?, ?, ?, sysdate, ?)";
		PreparedStatement pStmt = con.prepareStatement(sql);
		pStmt.setInt(1, purchase.getPurchaseProd().getProdNo());
		pStmt.setString(2, purchase.getBuyer().getUserId());
		pStmt.setString(3, purchase.getPaymentOption());
		pStmt.setString(4, purchase.getReceiverName());
		pStmt.setString(5, purchase.getReceiverPhone());
		pStmt.setString(6, purchase.getDivyAddr());
		pStmt.setString(7, purchase.getDivyRequest());
		pStmt.setString(8, purchase.getTranCode());
		pStmt.setString(9, purchase.getDivyDate());
		pStmt.executeUpdate();
		System.out.println("입력된 구매 정보 : " + purchase);
		con.close();
		System.out.println("insertPurchase END...........\n");
	}

	public void updatePurchase(Purchase purchase) throws SQLException {

		Connection con = DBUtil.getConnection();

		String sql = "UPDATE transaction SET payment_option=? , receiver_name=? , receiver_phone=? , demailaddr=? , dlvy_request=? , dlvy_date=? WHERE tran_no in (?)";

		PreparedStatement pStmt = con.prepareStatement(sql);
		pStmt.setString(1, purchase.getPaymentOption());
		pStmt.setString(2, purchase.getReceiverName());
		pStmt.setString(3, purchase.getReceiverPhone());
		pStmt.setString(4, purchase.getDivyAddr());
		pStmt.setString(5, purchase.getDivyRequest());
		pStmt.setString(6, purchase.getDivyDate());
		pStmt.setInt(7, purchase.getTranNo());
		pStmt.executeUpdate();

		con.close();
		pStmt.close();

	}

	public void updateTranCode(Purchase purchaseVO) throws SQLException {

		Connection con = DBUtil.getConnection();
		
		String sql = "UPDATE transaction SET tran_status_code= ? WHERE tran_no in (?)";
		
		PreparedStatement pStmt = con.prepareStatement(sql);
		pStmt.setString(1, purchaseVO.getTranCode());
		pStmt.setInt(2, purchaseVO.getTranNo());
		pStmt.executeUpdate();
		
		con.close();
		pStmt.close();
		System.out.println("PurchaseDao UpdateTranCode End......");
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