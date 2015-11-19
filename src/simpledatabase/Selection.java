package simpledatabase;
import java.util.ArrayList;

public class Selection extends Operator{
	
	ArrayList<Attribute> attributeList;
	String whereTablePredicate;
	String whereAttributePredicate;
	String whereValuePredicate;

	
	public Selection(Operator child, String whereTablePredicate, String whereAttributePredicate, String whereValuePredicate) {
		this.child = child;
		this.whereTablePredicate = whereTablePredicate;
		this.whereAttributePredicate = whereAttributePredicate;
		this.whereValuePredicate = whereValuePredicate;
		attributeList = new ArrayList<Attribute>();

	}
	
	
	/**
     * Get the tuple which match to the where condition
     * @return the tuple
     */
	@Override
	public Tuple next(){	
		Operator searchForTable=child;
		while (searchForTable.getChild()!=null){
			searchForTable=searchForTable.getChild();
		}
		if (!searchForTable.from.equals(whereTablePredicate)){
			return null;
		}
		Tuple tuple=child.next();
		//System.out.println(tuple);
		while (tuple!=null){
			attributeList = child.getAttributeList();
			for (int i=0;i<attributeList.size();i++){
				if(tuple.getAttributeName(i).equals(whereAttributePredicate)){
					if(tuple.getAttributeValue(i).equals(whereValuePredicate)){				
						return tuple;
					}
				}
			}
//			System.out.println(tuple);
//			System.out.println("EEE");
			tuple=child.next();
//			System.out.println(tuple);
		}		
		return null;

	}
	
	/**
     * The function is used to get the attribute list of the tuple
     * @return the attribute list
     */
	public ArrayList<Attribute> getAttributeList(){
		
		return(child.getAttributeList());
	}

	
}