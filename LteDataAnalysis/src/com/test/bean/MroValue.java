package com.test.bean;

public class MroValue
{
	//27个参数
		private String lteScRSRP;
		private String LteScRSRQ;
		private String lteScTadv ;
		private String  lteSceNBRxTxTimeDiff;
		private String  lteScPHR;
		private String  lteScAOA;
		private String lteScSinrUL;
		private String lteScEarfcn;
		private String lteScPci;
		private String lteNcRSRP;
		private String lteNcRSRQ;
		private String lteNcEarfcn;
		private String lteNcPci;
		private String tdsPccpchRSCP;
		private String tdsNcellUarfcn;
		private String tdsCellParameterId;
		private String gsmNcellBcch;
		private String gsmNcellCarrierRSSI;
		private String gsmNcellNcc;
		private String gsmNcellBcc;
		private String lteScPUSCHPRBNum;
		private String lteScPDSCHPRBNum;
		private String lteScBSR;
		private String lteScRI1;
		private String lteScRI2;
		private String lteScRI4;
		private String lteScRI8;
		
		public MroValue(String s)
		{
			// TODO Auto-generated constructor stub
			String[] strings=s.split(" ");
			
			this.lteScRSRP=strings[0];
			this.LteScRSRQ=strings[1];
			this.lteScTadv =strings[2];
			this. lteSceNBRxTxTimeDiff=strings[3];
			this. lteScPHR=strings[4];
			this. lteScAOA=strings[5];
			this.lteScSinrUL=strings[6];
			this.lteScEarfcn=strings[7];
			this.lteScPci=strings[8];
			this.lteNcRSRP=strings[9];
			this.lteNcRSRQ=strings[10];
			this.lteNcEarfcn=strings[11];
			this.lteNcPci=strings[12];
			this.tdsPccpchRSCP=strings[13];
			this.tdsNcellUarfcn=strings[14];
			this.tdsCellParameterId=strings[15];
			this.gsmNcellBcch=strings[16];
			this.gsmNcellCarrierRSSI=strings[17];
			this.gsmNcellNcc=strings[18];
			this.gsmNcellBcc=strings[19];
			this.lteScPUSCHPRBNum=strings[20];
			this.lteScPDSCHPRBNum=strings[21];
			this.lteScBSR=strings[22];
			this.lteScRI1=strings[23];
			this.lteScRI2=strings[24];
			this.lteScRI4=strings[25];
			this.lteScRI8=strings[26];
			
		}
		
		
}
