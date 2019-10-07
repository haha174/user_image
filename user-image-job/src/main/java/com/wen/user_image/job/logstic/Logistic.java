package com.wen.user_image.job.logstic;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Logistic {




 
	/**
	 * @param inX
	 * @param weights
	 * @return
	 */
	public static String classifyVector(ArrayList<String> inX, ArrayList<Double> weights) {
		ArrayList<Double> sum = new ArrayList<>();
		sum.add(0.0);
		for (int i = 0; i < inX.size(); i++) {
			sum.set(0, sum.get(0) + Double.parseDouble(inX.get(i)) * weights.get(i));
		}
		if (sigmoid(sum).get(0) > 0.5)
			return "1";
		else
			return "0";
 
	}

 
	/**
	 * 自然数底数e的参数次方。  Math.exp
	 * @param inX
	 * @return
	 * @Description: [sigmod函数]
	 */
	public static ArrayList<Double> sigmoid(ArrayList<Double> inX) {
		ArrayList<Double> inXExp = new ArrayList<Double>();
		for (int i = 0; i < inX.size(); i++) {
			inXExp.add(1.0 / (1 + Math.exp(-inX.get(i))));
		}
		return inXExp;
	}
 
	/**
	 * @param dataSet
	 * @param classLabels
	 * @param numberIter
	 * @return
	 */
	public static ArrayList<Double> gradAscent1(Matrix dataSet, ArrayList<String> classLabels, int numberIter) {
		int m = dataSet.data.size();
		int n = dataSet.data.get(0).size();
		double alpha = 0.0;
		int randIndex = 0;
		ArrayList<Double> weights = new ArrayList<>();
		ArrayList<Double> h;
		ArrayList<Integer> dataIndex = new ArrayList<>();
		ArrayList<Double> dataMatrixMulWeights = new ArrayList<>();
		/**
		 * 为什么要添加初始值
		 * 方便后面可以直接进行乘法运算
		 */
		for (int i = 0; i < n; i++) {
			weights.add(1.0);
		}
		dataMatrixMulWeights.add(0.0);
		double error = 0.0;
		for (int j = 0; j < numberIter; j++) {
			// 产生0到99的数组
			for (int p = 0; p < m; p++) {
				dataIndex.add(p);
			}
			// 进行每一次的训练
			/**
			 * 随机梯度下降
			 */
			for (int i = 0; i < m; i++) {
				// 为什么要取这个数字 为什么是这样取得
				alpha = 4 / (1.0 + i + j) + 0.0001;
				// 随机取得一个随机数
				randIndex = (int) (Math.random() * dataIndex.size());
				dataIndex.remove(randIndex);
				double temp = 0.0;
				/**
				 * 为什么要将temp加起来
				 * 一个列表是一组值 需要 求和然后 梯度下降 得到的值于 label 值进行比较
				 */
				for (int k = 0; k < n; k++) {
					temp = temp + Double.parseDouble(dataSet.data.get(randIndex).get(k)) * weights.get(k);
				}
				dataMatrixMulWeights.set(0, temp);
				/**
				 * 逻辑回归 返回的是一个值
				 */
				h = sigmoid(dataMatrixMulWeights);
				/**
				 * 求差值label  和 梯度下降 结果 求 差值
				 */
				error = Double.parseDouble(classLabels.get(randIndex)) - h.get(0);
				double tempWeight = 0.0;
				for (int p = 0; p < n; p++) {
					tempWeight = alpha * Double.parseDouble(dataSet.data.get(randIndex).get(p)) * error;
					weights.set(p, weights.get(p) + tempWeight);
				}
			}
 
		}
		return weights;
	}

	public Logistic() {
		super();
	}
}