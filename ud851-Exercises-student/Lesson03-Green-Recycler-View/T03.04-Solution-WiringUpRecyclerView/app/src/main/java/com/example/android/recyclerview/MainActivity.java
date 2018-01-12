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
package com.example.android.recyclerview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {

    // переменная, хранящая в себе кол-во элементов для отображения
    private static final int NUM_LIST_ITEMS = 100;

    //Ссылки на RecyclerView и Adapter для сброса списка в его «симпатичное» состояние при нажатии элемента меню сброса.
    private GreenAdapter mAdapter;
    private RecyclerView mNumbersList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNumbersList = (RecyclerView) findViewById(R.id.rv_numbers);

        /*LinearLayoutManager отвечает за измерение и позиционирование позиций элементов в пределах
         RecyclerView в линейный список. Это означает, что он может производить либо горизонтальный, либо
         вертикальный список в зависимости от того, какой параметр вы передаете LinearLayoutManager
         конструктор. По умолчанию, если вы не указали ориентацию, вы получите вертикальный список.
         В нашем случае нам нужен вертикальный список, поэтому нам не нужно передавать флаг ориентации
         конструктор LinearLayoutManager. Существуют другие LayoutManager для отображения ваших данных в равномерных сетках,
         шахматные решетки и многое другое! Дополнительную информацию см. В документации разработчика.*/
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        // присваиваем RecyclerView LinearLayoutManager
        mNumbersList.setLayoutManager(layoutManager);

        /*Используем этот параметр для повышения производительности, если мы знаем,
        что изменения в содержимом не меняют размер макета дочернего элемента в RecyclerView*/
        mNumbersList.setHasFixedSize(true);

        // Создаем адаптер и передаем в него кол-во отображаемых элементов
        mAdapter = new GreenAdapter(NUM_LIST_ITEMS);

        // Присваиваем нашему RecyclerView адаптер
        mNumbersList.setAdapter(mAdapter);
    }
}
