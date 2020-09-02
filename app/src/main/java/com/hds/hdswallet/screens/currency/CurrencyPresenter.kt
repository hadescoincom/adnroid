/*
 * // Copyright 2018 Hds Development
 * //
 * // Licensed under the Apache License, Version 2.0 (the "License");
 * // you may not use this file except in compliance with the License.
 * // You may obtain a copy of the License at
 * //
 * //    http://www.apache.org/licenses/LICENSE-2.0
 * //
 * // Unless required by applicable law or agreed to in writing, software
 * // distributed under the License is distributed on an "AS IS" BASIS,
 * // WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * // See the License for the specific language governing permissions and
 * // limitations under the License.
 */

package com.hds.hdswallet.screens.currency

import com.hds.hdswallet.base_screen.BasePresenter
import com.hds.hdswallet.core.entities.Currency
import com.hds.hdswallet.core.entities.ExchangeRate
import com.hds.hdswallet.core.entities.dto.ExchangeRateDTO
import com.hds.hdswallet.core.helpers.LocaleHelper

class CurrencyPresenter(view: CurrencyContract.View?, repository: CurrencyContract.Repository)
    : BasePresenter<CurrencyContract.View, CurrencyContract.Repository>(view, repository), CurrencyContract.Presenter {

    override fun onViewCreated() {
        super.onViewCreated()

        val usdRate = ExchangeRate(ExchangeRateDTO(-1,0,0,0));
        usdRate.currency = Currency.Usd

        val btcRate = ExchangeRate(ExchangeRateDTO(-1,0,0,0));
        btcRate.currency = Currency.Bitcoin

        val offRate = ExchangeRate(ExchangeRateDTO(-1,0,0,0));
        offRate.currency = Currency.Off

        var currencies = mutableListOf<ExchangeRate>() //repository.getCurrencies().toMutableList()
        currencies.add(offRate)
        currencies.add(usdRate)
        currencies.add(btcRate)

        view?.init(currencies, repository.getCurrentCurrency())
    }

    override fun onSelectCurrency(currency: Currency) {
        if (currency != repository.getCurrentCurrency()) {
            repository.setCurrency(currency)
            view?.changeCurrency(currency)
        }
    }
}