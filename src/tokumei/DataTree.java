package tokumei;

import java.util.ArrayList;
import java.util.Comparator;


public class DataTree {
	private SameStageNode ssn[][][]; //同じ階層のノード(属性が三つある場合)
	private int zokuseisu; //属性数
	private int[] ketasu; //各属性の桁数
	private int k; //何匿名か
	private ArrayList<SameStageNode> ssnlist = new ArrayList<SameStageNode>();
	private DataList list = new DataList();

	public DataTree(DataList datalist, int k){
		zokuseisu =datalist.getData(0).getZokuseisu();
		ketasu = new int[zokuseisu];
		for(int i=0; i<zokuseisu; i++){
			ketasu[i] = datalist.getData(0).getZoku(i).getKetaSu();
		}
		this.k = k;

		ssn = new SameStageNode[ketasu[0]+1][ketasu[1]+1][ketasu[2]+1]; //属性が三つの場合

		makeSSN(datalist);
		sortSSN();


		makeNewDataList(datalist.getListSize());
	}

	private void makeSSN(DataList datalist){ //属性が3つある場合
		for(int h=0; h<ketasu[0]+1; h++)
			for(int i=0; i<ketasu[1]+1;i++)
				for(int j=0; j<ketasu[2]+1; j++)
					ssn[h][i][j]= new SameStageNode(h,i,j,ketasu,k);

		for(int g=0; g<datalist.getListSize();g++){
			for(int h=0; h<ketasu[0]+1; h++)
				for(int i=0; i<ketasu[1]+1;i++)
					for(int j=0; j<ketasu[2]+1; j++)
						ssn[h][i][j].addData(datalist.getData(g));
		}
	}

	private void sortSSN(){
		for(int h=0; h<ketasu[0]+1; h++)
			for(int i=0; i<ketasu[1]+1;i++)
				for(int j=0; j<ketasu[2]+1; j++){
					ssn[h][i][j].output();
					ssnlist.add(ssn[h][i][j]);
				}


		ssnlist.sort(Comparator.comparing(SameStageNode::getTokumeido));
	}

	//datalist.sort(Comparator.comparingInt(Data::getAddress)
	//.thenComparingInt(Data::getAge).thenComparing(Data::getIllness));

	private void makeNewDataList(int size){
		DataList tmplist = new DataList();
		for(int i=0; i<ssnlist.size();i++){
			for(int j=0; j<ssnlist.get(i).getNode().size();j++){
				if(ssnlist.get(i).getNode().get(j).tokumeiCheckOutput() &&
						(k<=size-ssnlist.get(i).getNode().get(j).getOutputDataSize()-list.getListSize() ||
								0==size-ssnlist.get(i).getNode().get(j).getOutputDataSize()-list.getListSize())){
					tmplist.addData(ssnlist.get(i).getNode().get(j).getOutputData());
					list.addData(ssnlist.get(i).getNode().get(j).getOutputData());
/*
					for(int l=i;l<ssnlist.size() ;l++){
						for(int m=j;m<ssnlist.get(l).getNode().size();m++){
							if(!(l==i && m==j))
								ssnlist.get(l).getNode().get(m).removeOutputData(ssnlist.get(i).getNode().get(j).getOutputData());
						}
					}
*/
					for(int m=j;m<ssnlist.get(i).getNode().size();m++){
						if(!( m==j))
							ssnlist.get(i).getNode().get(m).removeOutputData(ssnlist.get(i).getNode().get(j).getOutputData());
					}
					for(int l=i+1;l<ssnlist.size() ;l++){
						for(int m=0;m<ssnlist.get(l).getNode().size();m++){
							ssnlist.get(l).getNode().get(m).removeOutputData(ssnlist.get(i).getNode().get(j).getOutputData());
						}
					}


				}
				else{

				}
			}
		}


	}

	public DataList outputDataList(){
		return list;
	}
}
