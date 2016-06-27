package tokumei;

import java.util.ArrayList;

public class SameStageNode { //3属性の場合のクラス
	private int zokuseisu;
	private int k; //何匿名か
	private double tokumeido;
	private int[] zokutokumeido;
	private int[] ketasu;
	private ArrayList<Node> node = new ArrayList<Node>();

	public SameStageNode(){}
	public SameStageNode(int a, int b, int c,int[] d, int e){
		zokuseisu=3;
		ketasu = d;
		k=e;
		tokumeido =((double)a)/ketasu[0];
		tokumeido+=((double)b)/ketasu[1];
		tokumeido+=((double)c)/ketasu[2];
		tokumeido/=3;
		zokutokumeido = new int[zokuseisu];
		zokutokumeido[0] =a;
		zokutokumeido[1] =b;
		zokutokumeido[2] =c;
	}

	public void addData(Data d){
		if(node.size()==0){
			node.add(new Node(zokuseisu,zokutokumeido,ketasu,k));
			node.get(node.size()-1).addData(d);
		}
		else{
			boolean hantei= false;

			for(int i=0; i<node.size();i++)
				hantei = hantei || node.get(i).addData(d);

			if(!hantei){
				node.add(new Node(zokuseisu,zokutokumeido,ketasu,k));
				node.get(node.size()-1).addData(d);
			}
		}
	}

	public void removeData(Data d){
		for(int i=0; i<node.size();i++)
			node.get(i).removeData(d);
	}
	public void removeData(ArrayList<Data> d){
		for(int i=0; i<node.size();i++)
			node.get(i).removeData(d);
	}

	public void removeOutputData(Data d){
		for(int i=0; i<node.size();i++)
			node.get(i).removeOutputData(d);
	}
	public void removeOutputData(ArrayList<Data> d){
		for(int i=0; i<node.size();i++)
			node.get(i).removeOutputData(d);
	}

	public double getTokumeido(){
		return tokumeido;
	}

	public void output(){
		for(int i=0; i<node.size();i++)
			node.get(i).output();
	}
	public ArrayList<Node> getNode(){
		return node;
	}

	public String toString(){
		String str="";

		for(int i=0; i<node.size(); i++)
			str+=node.get(i);

		str+="\n\n";

		return str;
	}
}
