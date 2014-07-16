package productdb;
import java.io.Serializable;

public class Product implements Serializable {
	private Integer id;
	private String name;
	private double price;
	private DeptCode dept;

	public Product(String name, double price, DeptCode code) {
		this(null, name, price, code);
	}

	public Product(Integer id, String name, double price, DeptCode code) {
		this.id = id;
		this.name = name;
		this.price = price;
		this.dept = code;
	}
	
	public String getName() {
		return name;
	}

	public double getPrice() {
		return price;
	}
	
	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}

	public DeptCode getDept() {
		return dept;
	}

	public void setDept(DeptCode dept) {
		this.dept = dept;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	@Override
	public String toString() {
		String info = String.format("productId:%d,name:%s,dept:%s,price:%.2f",
				id, name, dept, price);
		return info;
	}

}