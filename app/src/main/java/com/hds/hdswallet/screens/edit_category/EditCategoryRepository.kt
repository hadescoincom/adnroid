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

import com.hds.hdswallet.base_screen.BaseRepository
import com.hds.hdswallet.core.helpers.Tag
import com.hds.hdswallet.core.helpers.TagHelper

class EditCategoryRepository: BaseRepository(), EditCategoryContract.Repository {
    override fun saveCategory(tag: Tag) {
        TagHelper.saveTag(tag)
    }

    override fun getCategoryFromId(categoryId: String): Tag? {
        return TagHelper.getTag(categoryId)
    }

    override fun createNewCategory(): Tag {
        return Tag.new()
    }

    override fun getAllCategory(): List<Tag> {
        return TagHelper.getAllTags()
    }
}