package tmall.util;

public class Page {
	int start;
	int count;
	int total;
	String param;
	public int getStart() {
		return start;
	}
	public void setStart(int start) {
		this.start = start;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public String getParam() {
		return param;
	}
	public void setParam(String param) {
		this.param = param;
	}
	//���췽��
	public Page(int start,int count){
		super();
		this.start = start;
		this.count = count;
	}
	//�ж��Ƿ���ǰһҳ
	public boolean isHasPreviouse(){
		if(start==0)
			return false;
		return true;
	}
	//�ж��Ƿ��к�һҳ
	public boolean isHasNext(){
		if(start==getLast())
			return false;
		return true;
	}
	//��ȡ���һҳ
	public int getLast(){
		int last;
		if(total%count==0)
			last = total-count;
		else
			last = total-total%count;
		last = last<0?0:last;
		return last;
	}
	//��ȡ��ҳ��
	public int getTotalPage(){
		int totalPage;
		if(total%count==0)
			totalPage = total/count;
		else
			totalPage = total/count + 1;
		totalPage = totalPage==0?1:totalPage;
		return totalPage;
	}
}
