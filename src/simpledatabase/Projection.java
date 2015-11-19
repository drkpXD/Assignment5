package simpledatabase;
import java.util.ArrayList;

public class Projection extends Operator{
	
	ArrayList<Attribute> newAttributeList;
	private String attributePredicate;


	public Projection(Operator child, String attributePredicate){
		
		this.attributePredicate = attributePredicate;
		this.child = child;
		newAttributeList = new ArrayList<Attribute>();
		
	}
	
	
	/**
     * Return the data of the selected attribute as tuple format
     * @return tuple
     */
	@Override
	public Tuple next(){
		Tuple tuple=child.next();
		if (tuple == null){
			return null;
		}
//		System.out.println(tuple);
//		System.out.println("===============");
//		System.out.println(child);
		for(int i =0;i<tuple.getAttributeList().size();i++){
			if(tuple.getAttributeName(i).equals(attributePredicate)){
				newAttributeList.add(tuple.getAttributeList().get(i));
			}
		}
		
		if(newAttributeList.size()>0){
			//System.out.println(newAttributeList.get(newAttributeList.size()-1));
			child.getAttributeList().clear();
			child.getAttributeList().add(newAttributeList.get(newAttributeList.size()-1));
			return new Tuple(child.getAttributeList());
		}
		else{
			return null;
			}
	}
		

	
	/**
     * The function is used to get the attribute list of the tuple
     * @return attribute list
     */
	public ArrayList<Attribute> getAttributeList(){
		return child.getAttributeList();
	}

}