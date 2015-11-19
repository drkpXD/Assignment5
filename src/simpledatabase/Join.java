package simpledatabase;
import java.util.ArrayList;

public class Join extends Operator{

	private ArrayList<Attribute> newAttributeList;
	private String joinPredicate;
	ArrayList<Tuple> tuples1;
	private boolean join = false;
	private boolean empty = false;
	private int count=0;
	private ArrayList<Attribute> temp = new ArrayList<Attribute>(); 
	//Join Constructor, join fill
	
	
	public Join(Operator leftChild, Operator rightChild, String joinPredicate){
		this.leftChild = leftChild;
		this.rightChild = rightChild;
		this.joinPredicate = joinPredicate;
		newAttributeList = new ArrayList<Attribute>();
		tuples1 = new ArrayList<Tuple>();
		
	}


	/**
     * It is used to return a new tuple which is already joined by the common attribute
     * @return the new joined tuple
     */
	//The record after join with two tables
	@Override
	public Tuple next(){
		
		if(joinPredicate.isEmpty())
			return leftChild.next();
		
		if(!join){
		Tuple leftTuple=leftChild.next();
		Tuple rightTuple=rightChild.next();
		ArrayList<Tuple> leftTupleList= new ArrayList<Tuple>();
		ArrayList<Tuple> rightTupleList= new ArrayList<Tuple>();
		
		if(leftTuple==null){
			if(leftChild.getChild()!=null){
				leftTuple=leftChild.getChild().next();
				while(leftTuple!=null){
					leftTupleList.add(new Tuple(leftTuple.getAttributeList()));
					leftTuple=leftChild.getChild().next();
				}
			}
			leftTuple=leftChild.next();
		}
		else{		
			while (leftTuple!=null){
				leftTupleList.add(leftTuple);
				leftTuple=leftChild.next();
			}
		}
		
		if(rightTuple==null){
			if(rightChild.getChild()!=null){
				rightTuple=rightChild.getChild().next();
				while(rightTuple!=null){
					rightTupleList.add(new Tuple(rightTuple.getAttributeList()));
					rightTuple=rightChild.getChild().next();
				}
			}
			rightTuple=rightChild.next();
		}
		else{	
			while(rightTuple!=null){
				rightTupleList.add(rightTuple);
				rightTuple=rightChild.next();
			}
		}
		if (leftTupleList.size()==0 && rightTupleList.size()==0){
			join=true;
			empty=true;
		}
		else if(leftTupleList.size()==0){
			join=true;
			empty=true;
		}
		else if (rightTupleList.size()==0){
			join=true;
			empty=true;
		}
		else {
			int leftIndex=0;
			int rightIndex=0;
		for(int i=0;i<leftTupleList.size();i++)
			for(int j=0;j<rightTupleList.size();j++)
				if(leftTupleList.get(0).getAttributeName(i).equals(rightTupleList.get(0).getAttributeName(j))){
					leftIndex=i;
					rightIndex=j;
					break;
				}
		
		for(int i=0;i<rightTupleList.size();i++)
			for(int j=0;j<leftTupleList.size();j++)
				if(rightTupleList.get(i).getAttributeValue(rightIndex).equals(leftTupleList.get(j).getAttributeValue(leftIndex))){
					temp.clear();
					temp.addAll(leftTupleList.get(j).getAttributeList());
					temp.addAll(rightTupleList.get(i).getAttributeList());
					temp.remove((leftTupleList.get(j).getAttributeList().size()+rightIndex));
					tuples1.add(new Tuple(new ArrayList<Attribute>(temp)));	
				}
				join=true;	
			}
		}
		
	
		if(empty){
			return null;
		}
		if(count!=tuples1.size()){
			newAttributeList.clear();
			newAttributeList.addAll(tuples1.get(count).getAttributeList());
			count++;
			return tuples1.get(count-1);
		}		
		return null;
	}
	
		
	/**
     * The function is used to get the attribute list of the tuple
     * @return attribute list
     */
	public ArrayList<Attribute> getAttributeList(){
		if(joinPredicate.isEmpty())
			return child.getAttributeList();
		else
		{
			return(newAttributeList);
		}
	}

}