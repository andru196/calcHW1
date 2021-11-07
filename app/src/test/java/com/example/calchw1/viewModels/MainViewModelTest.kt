package com.example.calchw1.viewModels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.calchw1.domain.SettingsDao
import org.junit.Assert
import org.junit.Rule
import org.junit.Test

class MainViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Test
    fun plusTest() {
        val vm = MainViewModel(SettingDaoFake())
        vm.onNumberClick(2)
        vm.onSightClick('+')
        vm.onNumberClick(2)

        Assert.assertEquals("4", vm.getResult())
    }


    @Test
    fun divideTest() {
        val vm = MainViewModel(SettingDaoFake())
        vm.onNumberClick(1)
        vm.onNumberClick(0)
        vm.onSightClick('/')
        vm.onNumberClick(2)

        Assert.assertEquals("5", vm.getResult())
    }
}

class SettingDaoFake: SettingsDao {
    private var resultPanelType: ResultPanelType = ResultPanelType.LEFT
    override suspend fun getResultPanelType(): ResultPanelType {
        return resultPanelType
    }

    override suspend fun setResultPanelType(resultPanelType: ResultPanelType) {
        this.resultPanelType = resultPanelType
    }
}