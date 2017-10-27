package calcu;

import java.util.Map;

import com.test.bean.Measurement;
import com.test.util.CalUtil;

public class SinrCalcutor implements CalExper
{
	//计算 一天中 96个文件的 SINR的平均值
			@Override
			public double[] calExpValue(Map<Integer, Map<String, Measurement>> allData,String id)
			{
				double[] exp=new double[96];
				
				for(int i=0;i<96;i++)
				{
					String[] valueStr = allData.get(i).get("MR.SinrUL").getObjects().get(id).split(" ");
					double[] valueDou = new double[valueStr.length];
					for (int j = 0; j < valueStr.length; j++)
					{
						valueDou[j] = Double.parseDouble(valueStr[j]);
					}
					exp[i]=CalUtil.calUlSinrExp(valueDou);
				}
				return exp;
			}
}
