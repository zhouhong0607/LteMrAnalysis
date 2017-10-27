package calcu;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Map;

import com.test.bean.Measurement;
import com.test.util.LogUtil;

public class Calculator
{
	private  CalExper calExper;//求均值的  计算器
	private Map<Integer, Map<String, Measurement>> allData;//一天 96个文件的数据
	private double[] result; //计算 均值的   结果
	private String resultStr;
	private String id;
	
	private Calculator()
	{
		// TODO Auto-generated constructor stub
	}

	public  Calculator(CalExper cal,Map<Integer,  Map<String, Measurement>> data,String ide )  //构造函数 ， 制定一种计算器
	{
		// TODO Auto-generated constructor stub
		this.calExper=cal;
		this.allData=data;
		this.id=ide;
		result=new double[96];
	}
	
	//更改计算策略
	public void setCalExper(CalExper calExper)
	{
		this.calExper = calExper;
	}
	
	public double[] getResult()
	{
		return this.result;
	}
	
	public String getResultStr()
	{
		return this.resultStr;
	}
	//计算均值
	public void cal()
	{
		result= calExper.calExpValue(allData,id);
		//滤波  去除0值
		filter();
		//得到 结果 字符串
		StringBuilder sBuilder=new StringBuilder();
		for(int i=0;i<result.length;i++)
			sBuilder.append(String.valueOf(result[i])+  (i!=result.length-1?  "\r\n":""));
		resultStr=sBuilder.toString();
	}
	
	
	private void filter()
	{
		for(int i=0;i<result.length;i++)
		{
			//对0值进行处理， 取前一个 后一个 非零值 进行平均
			if(result[i]==0.0)
			{
				double pre=0.0,next=0.0;
				for(int m=i-1;m>=0;m--)
				{
					if(result[m]!=0.0)
					{
						pre=result[m];
						break;
					}
				}
				for(int n=i+1;n<result.length;n++)
				{
					if(result[n]!=0.0)
					{
						next=result[n];
						break;
					}
				}
				//i若在 头或 尾  则取最近点的值
				if(pre==0.0)
				{
					pre=next;
				}else if (next==0.0)
				{
					next=pre;
				}
				
				
				result[i]=(pre+next)/2.0;
			}
		}
	}
	
	
	
	//将 resultStr的内容写到文件中
	public boolean writeToFile(String filepath,String fileName)
	{
		try
		{
			File file=new File(filepath,fileName);
			if(file.exists())
			{
				file.delete();
				LogUtil.d("文件删除");
			}
			boolean i= file.createNewFile();
			if(i)
			{
				LogUtil.d("文件创建成功");
			BufferedWriter writer=new BufferedWriter(new FileWriter(file));
			writer.write(resultStr);
			writer.flush();
			writer.close();
			}else
			{
				LogUtil.e("文件创建失败");
				return false;
			}
		} catch (Exception e)
		{
			// TODO: handle exception
			LogUtil.e("文件写入异常");
			return false;
		}
		return true;
	}
}
