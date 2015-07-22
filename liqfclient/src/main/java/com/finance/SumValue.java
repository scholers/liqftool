package com.finance;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.finance.util.MoneyUtil;
import com.finance.vo.FinanceVo;

public class SumValue {

	private static List<FinanceVo> voList = new ArrayList<FinanceVo>();

	static {
		FinanceVo vo = new FinanceVo("境外", "公募基金", "贝莱德欧洲特别时机基金 (美元) A2对冲股份",
				105000.0);
		vo.setCurValue(14.56);
		vo.setNum(1188.66);

		voList.add(vo);
		vo = new FinanceVo("境内", "公募基金", "宝盈核心优势混合A", 11000.0);
		vo.setCurValue(1.4160);
		vo.setNum(5599.59);
		voList.add(vo);
		
		vo = new FinanceVo("境内", "私募基金", "新方程域秀智享5号尊享B", 1010000.0);
		vo.setCurValue(0.818);
		vo.setNum(948766.6);
		voList.add(vo);
		
		vo = new FinanceVo("境内", "公募基金", "嘉实全球互联网", 100000.0);
		vo.setCurValue(0.9380);
		vo.setNum(75248.72);
		voList.add(vo);
		
		vo = new FinanceVo("境内", "公募基金", "汇添富社会责任股票", 50000.0);
		vo.setCurValue(2.022);
		vo.setNum(15685.1);
		voList.add(vo);
		
		vo = new FinanceVo("境内", "公募基金", "长信内需成长股票", 60000.0);
		vo.setCurValue(1.657);
		vo.setNum(26744.82);
		voList.add(vo);
		
		vo = new FinanceVo("境内", "公募基金", "长信量化先锋股票", 75267.71);
		vo.setCurValue(1.902);
		vo.setNum(37193.12);
		voList.add(vo);

	}



	private static void sumAll() {
		//总市值
		double allValue = new Double(0.0);
		//总盈利
		double getValue = new Double(0.0);
		for (FinanceVo finVo : voList) {
			//获取当前的市值
			double curMoney = MoneyUtil.multiply(finVo.getCurValue(),
					finVo.getNum());
			if(("境外").equals(finVo.getType())) {
				curMoney = MoneyUtil.multiply(curMoney ,6.1959d);
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
		
		System.out.println("总盈利2==" + MoneyUtil.getRealMoney(MoneyUtil.add(getValue, 60000)));
	}

	/**
	 * 
	 * @author jill
	 * 
	 */
	public static void main(String[] args) {
		sumAll();
	}

}
