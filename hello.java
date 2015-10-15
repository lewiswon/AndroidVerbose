public class hello{

public static hello getInstance(){
	return new hello();
}
private hello(){
System.out.print("hello world");
}
public static void main(String [] a){
	hello.getInstance();
}
}
