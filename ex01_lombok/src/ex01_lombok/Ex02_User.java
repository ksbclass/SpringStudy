package ex01_lombok;

public class Ex02_User {
	private String id;
	private String pw;
	
	public Ex02_User(Builder builder) {
		this.id = builder.id;
		this.pw = builder.pw;
	}
	
	public static Builder builder() {
		return new Builder();
	}
	
	public String toString() {
		return "User [id="+id +",pw="+pw+"]"; 
	}
	
	//내부 클래스
	public static class Builder {
		private String id;
		private String pw;
		public Builder id(String id) {
			this.id = id;
			return this;
		}
		public Builder pw(String pw) {
			this.pw = pw;
			return this;
		}
		public Ex02_User build() {
			return new Ex02_User(this);
		}
		
	}
}
