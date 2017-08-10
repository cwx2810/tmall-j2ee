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
	//��ѯ���ݿ�õ�����
	public int getTotal() {
		int total = 0;
		try (Connection c = DBUtil.getConnection(); Statement s = c.createStatement();){//��ʼ�����Ӻͱ���sql����statement
			String sql = "select count(*) from User";//ִ�в�ѯ
			ResultSet rs = s.executeQuery(sql);//ִ��sql��ѯ����������������
			while(rs.next()){//���������������total
				total = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return total;
	}
	//��ʵ���������ݶ����ݿ��������
	public void add(User bean){
		String sql = "insert into user values(null,?,?)";//��ʼ��sql���
		try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql);){//��ʼ�����ӡ�����sql
			ps.setString(1, bean.getName());//��ȡʵ�������ݣ���ӽ����ݿ�
			ps.setString(2, bean.getPassword());
			ps.execute();//ִ��sql����
			ResultSet rs = ps.getGeneratedKeys();//������ݿ�����������ʵ����
			if(rs.next()){
				int id = rs.getInt(1);
				bean.setId(id);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	//��ʵ�����ȡ���ݶ����ݿ���и���
	public void update(User bean){
		String sql = "update user set name=?,password=? where id=?";
		try (Connection c = DBUtil.getConnection();PreparedStatement ps = c.prepareStatement(sql);){
			ps.setString(1, bean.getName());
			ps.setString(2, bean.getPassword());
			ps.setInt(3, bean.getId());//��ȡʵ�����������������ݿ�
			ps.execute();//ִ��sql����
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	//ֱ��ɾ�����ݿ�
	public void delete(int id){
		try (Connection c = DBUtil.getConnection();Statement s = c.createStatement();){
			String sql = "delete from User where id = " + id;
			s.execute(sql);//ִ��sqlɾ��
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	//�����ݿ��ȡ���ݸ���ʵ����
	public User get(int id) {
		User bean = null;
		try (Connection c = DBUtil.getConnection();Statement s = c.createStatement();){
			String sql = "select * from User where id = " + id;//����id��ѯ����
			ResultSet rs = s.executeQuery(sql);//ִ�в�ѯ�����������
			if(rs.next()){//���������
				bean = new User();//�½�ʵ�������
				String name = rs.getString("name");//�����ݸ�������
				bean.setName(name);//����������ʵ����
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
	//�ж��û��Ƿ����
	public boolean isExist(String name){
		User user = get(name);
		return user!=null;
	}
	//�����ݿ�����û�����ȡ���󸳸�ʵ����
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
	//�����ݿ�����û��������ȡ���󸳸�ʵ����
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
