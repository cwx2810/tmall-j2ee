package tmall.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import tmall.bean.User;
import tmall.util.DBUtil;

public class UserDAO {
	//查询数据库得到总数
	public int getTotal() {
		int total = 0;
		try (Connection c = DBUtil.getConnection(); Statement s = c.createStatement();){//初始化连接和保存sql语句的statement
			String sql = "select count(*) from User";//执行查询
			ResultSet rs = s.executeQuery(sql);//执行sql查询，将结果赋给结果集
			while(rs.next()){//遍历结果集，赋给total
				total = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return total;
	}
	//从实体类获得数据对数据库进行增加
	public void add(User bean){
		String sql = "insert into user values(null,?,?)";//初始化sql语句
		try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql);){//初始化连接、保存sql
			ps.setString(1, bean.getName());//获取实体类数据，添加进数据库
			ps.setString(2, bean.getPassword());
			ps.execute();//执行sql插入
			ResultSet rs = ps.getGeneratedKeys();//获得数据库主键，赋给实体类
			if(rs.next()){
				int id = rs.getInt(1);
				bean.setId(id);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	//从实体类获取数据对数据库进行更新
	public void update(User bean){
		String sql = "update user set name=?,password=? where id=?";
		try (Connection c = DBUtil.getConnection();PreparedStatement ps = c.prepareStatement(sql);){
			ps.setString(1, bean.getName());
			ps.setString(2, bean.getPassword());
			ps.setInt(3, bean.getId());//获取实体类主键，赋给数据库
			ps.execute();//执行sql更新
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	//直接删除数据库
	public void delete(int id){
		try (Connection c = DBUtil.getConnection();Statement s = c.createStatement();){
			String sql = "delete from User where id = " + id;
			s.execute(sql);//执行sql删除
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	//从数据库获取数据更新实体类
	public User get(int id) {
		User bean = null;
		try (Connection c = DBUtil.getConnection();Statement s = c.createStatement();){
			String sql = "select * from User where id = " + id;//根据id查询数据
			ResultSet rs = s.executeQuery(sql);//执行查询，返给结果集
			if(rs.next()){//遍历结果集
				bean = new User();//新建实体类对象
				String name = rs.getString("name");//将数据赋给变量
				bean.setName(name);//将变量赋给实体类
				String password = rs.getString("password");
				bean.setPassword(password);
				bean.setId(id);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return bean;
	}
	public List<User> list(){
		return list(0,Short.MAX_VALUE);
	}
	public List<User> list(int start,int count){
		List<User> beans = new ArrayList<User>();
		String sql = "select * from User order by id desc limit ?,?";
		try (Connection c = DBUtil.getConnection();PreparedStatement ps = c.prepareStatement(sql);){
			ps.setInt(1, start);
			ps.setInt(2, count);
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				User bean = new User();
				int id = rs.getInt(1);
				String name = rs.getString("name");
				bean.setName(name);
				String password = rs.getString("password");
				bean.setPassword(password);
				bean.setId(id);
				beans.add(bean);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return beans;
	}
	//判断用户是否存在
	public boolean isExist(String name){
		User user = get(name);
		return user!=null;
	}
	//从数据库根据用户名获取对象赋给实体类
	public User get(String name){
		User bean = null;
		String sql = "select * from User where name = ?";
		try (Connection c = DBUtil.getConnection();PreparedStatement ps = c.prepareStatement(sql);){
			ps.setString(1, name);
			ResultSet rs = ps.executeQuery();
			if(rs.next()){
				bean = new User();
				int id = rs.getInt("id");
				bean.setName(name);
				String password = rs.getString("password");
				bean.setPassword(password);
				bean.setId(id);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return bean;
	}
	//从数据库根据用户名密码获取对象赋给实体类
	public User get(String name,String password){
		User bean = null;
		String sql = "select * from User where name = ?and password = ?";
		try (Connection c = DBUtil.getConnection();PreparedStatement ps = c.prepareStatement(sql);){
			ps.setString(1, name);
			ps.setString(2, password);
			ResultSet rs = ps.executeQuery();
			if(rs.next()){
				bean = new User();
				int id = rs.getInt("id");
				bean.setName(name);
				bean.setPassword(password);
				bean.setId(id);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return bean;
	}
}
