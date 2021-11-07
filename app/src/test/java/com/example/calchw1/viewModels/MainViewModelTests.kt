package com.example.calchw1.viewModels

import com.example.calchw1.domain.CalculationExecutor
import com.fathzer.soft.javaluator.DoubleEvaluator
import org.junit.Assert
import org.junit.Test

class MainViewModelTests {


    @Test
    fun plusTest() {
        val result = "4"
        val expr = "2+2"
        Assert.assertEquals(result, CalculationExecutor.computate(expr,
                                        CalculationExecutor.getSightsStack(expr)))
    }

    @Test
    fun minusTest() {
        val result = "2"
        val expr = "4-2"
        Assert.assertEquals(result, CalculationExecutor.computate(expr,
            CalculationExecutor.getSightsStack(expr)))
    }


    @Test
    fun powTest() {
        val result = "16"
        val expr = "4^2"
        Assert.assertEquals(result, CalculationExecutor.computate(expr,
            CalculationExecutor.getSightsStack(expr)))
    }

    @Test
    fun apiTest() {
        val result = "16.0"
        val expr = "4^2"
        val evaluator = DoubleEvaluator().evaluate(expr)
        Assert.assertEquals(result, evaluator.toString())
    }


    @Test
    fun inputTest() {
        calculationTest("", "")
        calculationTest("2", "2")
        calculationTest("2+", "2")
        calculationTest("2+2", "4")
    }

    private fun calculationTest (expression: String, result: String) {
        Assert.assertEquals(result, CalculationExecutor.computateWithEvaluator(expression))
    }
}