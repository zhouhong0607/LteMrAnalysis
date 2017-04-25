package calcu;

import java.util.Map;

import com.test.data.Measurement;

/**
 * 计算平均值的接口     实现有RsrpCalcutor  ，RsrqCalcutor 等
 * @author Administrator
 *
 */
public interface CalExper
{
	public double[] calExpValue(Map<Integer, Map<String, Measurement>> allData,String id);
}
