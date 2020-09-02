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

package com.hds.hdswallet.screens.edit_category

import com.hds.hdswallet.base_screen.MvpPresenter
import com.hds.hdswallet.base_screen.MvpRepository
import com.hds.hdswallet.base_screen.MvpView
import com.hds.hdswallet.core.helpers.Tag
import com.hds.hdswallet.core.helpers.TagColor

interface EditCategoryContract {
    interface View: MvpView {
        fun getCategoryId(): String?
        fun init(tag: Tag)
        fun getName(): String
        fun getSelectedCategoryColor(): TagColor
        fun setSaveEnabled(enable: Boolean)
        fun finish()
    }

    interface Presenter: MvpPresenter<View> {
        fun onSavePressed()
        fun onNameChanged(name: String)
        fun onChangeColor(tagColor: TagColor)
    }

    interface Repository: MvpRepository {
        fun saveCategory(tag: Tag)
        fun getCategoryFromId(categoryId: String): Tag?
        fun createNewCategory(): Tag
        fun getAllCategory(): List<Tag>
    }
}