public class World{
public static World getInstance(){

return new World();
}
private World(){
	System.out.println("The world is Korea");
}
public static void main(String [] a){

	World.getInstance();
}

}
