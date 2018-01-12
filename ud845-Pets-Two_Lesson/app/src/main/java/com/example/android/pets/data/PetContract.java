/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.pets.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;


// Приложение API для домашних животных.
public final class PetContract {

    public static final String CONTENT_AUTHORITY = "com.example.android.pets";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_PETS = "pets";

    // Чтобы кто-то случайно не создавал экземпляр класса контракта, дайте ему пустой конструктор.
    private PetContract() {
    }

    // Внутренний класс, который определяет постоянные значения для таблицы базы данных домашних животных.
    // Каждая запись в таблице представляет собой одно домашнее животное.
    public static final class PetEntry implements BaseColumns {

        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_PETS);

        // Тип MIME {@link #CONTENT_URI} для списка домашних животных.
        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_PETS;

        // Тип MIME {@link #CONTENT_URI} для одного питомца.
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_PETS;

        // Название таблицы базы данных для домашних животных
        public final static String TABLE_NAME = "pets";

        // Уникальный идентификационный номер для домашнего животного (только для использования в таблице базы данных).
        // Тип: INTEGER
        public final static String _ID = BaseColumns._ID;

        // Имя домашнего животного.
        // Тип: TEXT
        public final static String COLUMN_PET_NAME = "name";

        // Breed of the pet.
        // Тип: TEXT
        public final static String COLUMN_PET_BREED = "breed";

        // Пол домашнего животного.
        // Единственными возможными значениями являются {@link #GENDER_UNKNOWN}, {@link #GENDER_MALE}, или {@link #GENDER_FEMALE}.
        // Тип: INTEGER
        public final static String COLUMN_PET_GENDER = "gender";

        // Вес животного.
        // Тип: INTEGER
        public final static String COLUMN_PET_WEIGHT = "weight";

        // Возможные значения для пола домашнего животного.
        public static final int GENDER_UNKNOWN = 0;
        public static final int GENDER_MALE = 1;
        public static final int GENDER_FEMALE = 2;
    }
}

