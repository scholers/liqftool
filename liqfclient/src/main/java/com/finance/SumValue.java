package com.finance;

import java.util.ArrayList;
import java.util.List;

import com.finance.enums.FinanceTypeEnum;
import com.finance.enums.SubTypeEnum;
import com.finance.util.MoneyUtil;
import com.finance.vo.FinanceVo;
import com.net.pic.ui.HttpClientUrl;

public class SumValue {

	private static List<FinanceVo> voList = new ArrayList<FinanceVo>();

	static {

		FinanceVo vo = new FinanceVo(FinanceTypeEnum.IN, SubTypeEnum.IN, "新方程域秀智享5号尊享B", 1010000.0, "http://simu.howbuy.com/yuxiuziben/S33873/");
		
		vo.setNum(948766.6);
		voList.add(vo);

	}

	private double getCurValue(String url) {
		HttpClientUrl clientUrl = new HttpClientUrl(url, "");
		String value = clientUrl.parseHtml();
		if (value != null && !value.equals("")) {
			return Double.parseDouble(value);
		} else {
			return 0.00;
		}

	}

	private  void sumAll() {
		// 总市值
		double allValue = new Double(0.0);
		// 总盈利
		double getValue = new Double(0.0);
		for (FinanceVo finVo : voList) {
			// 获取当前的净值
			finVo.setCurValue(getCurValue(finVo.getUrl()));
			// 获取当前的市值
			double curMoney = MoneyUtil.multiply(finVo.getCurValue(),
					finVo.getNum());
			if (finVo.getFinanceTypeEnum() == FinanceTypeEnum.OUT) {
				curMoney = MoneyUtil.multiply(curMoney, 6.1959d);
			}
			// 获取盈利
			double getMoney = MoneyUtil.subtract(curMoney, finVo.getPrint());
			// 获取总市值
			allValue = MoneyUtil.add(curMoney, allValue);
			System.out.println("curMoney==" + curMoney);
			// 获取总盈利
			getValue = MoneyUtil.add(getMoney, getValue);
			System.out.println("getMoney==" + getMoney);
		}

		System.out.println("总市值==" + MoneyUtil.getRealMoney(allValue));
		System.out.println("总盈利==" + MoneyUtil.getRealMoney(getValue));

		System.out.println("总盈利2=="
				+ MoneyUtil.getRealMoney(MoneyUtil.add(getValue, 60000)));
	}

	/**
	 * 
	 * @author jill
	 * 
	 */
	public static void main(String[] args) {
		SumValue sumValue = new SumValue();
		sumValue.sumAll();
	}

}
