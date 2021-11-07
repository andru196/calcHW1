package com.example.calchw1.domain

import com.fathzer.soft.javaluator.DoubleEvaluator
import java.util.*
import kotlin.math.floor

class CalculationExecutor {
    companion object {
        private const val sights = "/-+*%^"
        private val delimiters = sights.toCharArray().map { it.toString() }.toTypedArray()
        private val evaluator = DoubleEvaluator()
        var precition = 0

        fun getSightsStack(expression: String) : Stack<Int> {
            val result = Stack<Int>()
            expression.forEachIndexed{index: Int, c: Char ->
                if (sights.contains(c))
                    result.add(index)
            }
            return result
        }

        fun computate(expression: String, sightsStack: Stack<Int>): String {
            var prev = 0
            var rez = .0
            if (sightsStack.isNotEmpty()) {
                val nums = expression.split(delimiters = delimiters)
                    .filter { it.isNotEmpty() }
                    .map { it.toDouble() }
                var i = 0
                while (i < nums.size - 1) {
                    if (i == 0)
                        rez = nums[i]
                    rez = when (expression[sightsStack[i]]) {
                        '*' -> rez * nums[i + 1]
                        '-' -> rez - nums[i + 1]
                        '+' -> rez + nums[i + 1]
                        '%' -> rez % nums[i + 1]
                        '^' -> Math.pow(rez, nums[i + 1])
                        else -> rez / nums[i + 1]
                    }
                    i++;
                }
            } else
                rez = expression.toDouble()
            if (rez - floor(rez) == .0)
                return "%.0f".format(rez)
            return ("%." + precition.toString() + "f").format(rez)
        }

        fun computateWithEvaluator(expression: String) : String {
            val expr = expression.trim(chars = sights.toCharArray())
            if (expr.isBlank())
                return ""
            val rez = evaluator.evaluate(expr)
            if (rez - floor(rez) == .0)
                return "%.0f".format(rez)
            return ("%." + precition.toString() + "f").format(rez)
        }
    }
}