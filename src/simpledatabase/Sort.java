package simpledatabase;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Sort extends Operator{
	
	private ArrayList<Attribute> newAttributeList;
	private String orderPredicate;
	ArrayList<Tuple> tuplesResult;
	private boolean sorted = false;
	int count=0;

	
	public Sort(Operator child, String orderPredicate){
		this.child = child;
		this.orderPredicate = orderPredicate;
		newAttributeList = new ArrayList<Attribute>();
		tuplesResult = new ArrayList<Tuple>();
		
	}
	
	
	/**
     * The function is used to return the sorted tuple
     * @return tuple
     */
	@Override
	public Tuple next(){
		Tuple tuple=child.next();
		if(!sorted){
			while(true){
				if(tuple==null){
					break;
				}
				tuplesResult.add(tuple);
				tuple=child.next();
			}
			for(Attribute x:tuplesResult.get(0).getAttributeList()){
				if(x.getAttributeName().equals(orderPredicate) ){
					break;
				}
				count += 1;
			}
			
			Collections.sort(tuplesResult, new Comparator<Tuple>(){
				public int compare(Tuple tuple, Tuple tuple2) {
					return (String.valueOf(tuple.getAttributeValue(count))).compareTo(String.valueOf(tuple2.getAttributeValue(count)));
				}
			} );
			sorted = true;
		}
		
		if(!tuplesResult.isEmpty()){
			return tuplesResult.remove(0);
		}
		return null;
	}
	
	/**
     * The function is used to get the attribute list of the tuple
     * @return attribute list
     */
	public ArrayList<Attribute> getAttributeList(){
		return child.getAttributeList();
	}

}