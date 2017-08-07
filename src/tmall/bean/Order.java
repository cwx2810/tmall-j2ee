package tmall.bean;

import java.util.Date;
import java.util.List;

public class Order {
	private int id;
	private String orderCode;
	private String address;
	private String post;
	private String receiver;
	private String mobile;
	private String userMessage;
	private Date createDate;
	private Date payDate;
	private Date deliveryDate;
	private Date confirmDate;
	//���������Ӧһ���û�
	private User user;
	//һ��������Ӧ���������
	private List<OrderItem> orderItems;
	//�������������ݿ������
	private float total;
	private int totalNumber;
	//�������ݿ��е�Ӣ��Ϊ������ʾ
	private String status;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getOrderCode() {
		return orderCode;
	}
	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPost() {
		return post;
	}
	public void setPost(String post) {
		this.post = post;
	}
	public String getReceiver() {
		return receiver;
	}
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getUserMessage() {
		return userMessage;
	}
	public void setUserMessage(String userMessage) {
		this.userMessage = userMessage;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Date getPayDate() {
		return payDate;
	}
	public void setPayDate(Date payDate) {
		this.payDate = payDate;
	}
	public Date getDeliveryDate() {
		return deliveryDate;
	}
	public void setDeliveryDate(Date deliveryDate) {
		this.deliveryDate = deliveryDate;
	}
	public Date getConfirmDate() {
		return confirmDate;
	}
	public void setConfirmDate(Date confirmDate) {
		this.confirmDate = confirmDate;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public List<OrderItem> getOrderItems() {
		return orderItems;
	}
	public void setOrderItems(List<OrderItem> orderItems) {
		this.orderItems = orderItems;
	}
	public float getTotal() {
		return total;
	}
	public void setTotal(float total) {
		this.total = total;
	}
	public int getTotalNumber() {
		return totalNumber;
	}
	public void setTotalNumber(int totalNumber) {
		this.totalNumber = totalNumber;
	}
	public String getStatus() {
		String desc = "δ֪";
		switch(status){
//	      case OrderDAO.waitPay:
//		      desc="������";
//		      break;
//		  case OrderDAO.waitDelivery:
//		      desc="������";
//		      break;
//		  case OrderDAO.waitConfirm:
//		      desc="���ջ�";
//		      break;
//		  case OrderDAO.waitReview:
//		      desc="������";
//		      break;
//		  case OrderDAO.finish:
//		      desc="���";
//		      break;
//		  case OrderDAO.delete:
//		      desc="�h��";
//		      break;
//		  default:
//		      desc="δ֪";
		}
		return desc;
	}
	
}