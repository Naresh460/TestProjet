package com;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class Retryanalyzer implements IRetryAnalyzer {

	int counter = 0;
	int retryLimit = 5;

	@Override
	public boolean retry(ITestResult result) {
		if (counter < retryLimit) {
			counter++;
			return true;
		}
		return false;
	}

}
