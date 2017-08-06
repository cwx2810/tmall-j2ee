package tmall.bean;

import java.util.List;

public class Category {
	private int id;
	private String name;
	//一个分类对应多个产品
	List<Product> products;
	//一个产品对应多个产品集合
	List<List<Product>> productByRow;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	/*重写toString方法，当打印Category对象时会打印name，测试用*/
	@Override
	public String toString() {
		return "Category [name=" + name + "]";
	}
	public List<Product> getProducts() {
		return products;
	}
	public void setProducts(List<Product> products) {
		this.products = products;
	}
	public List<List<Product>> getProductByRow() {
		return productByRow;
	}
	public void setProductByRow(List<List<Product>> productByRow) {
		this.productByRow = productByRow;
	}
	
}
