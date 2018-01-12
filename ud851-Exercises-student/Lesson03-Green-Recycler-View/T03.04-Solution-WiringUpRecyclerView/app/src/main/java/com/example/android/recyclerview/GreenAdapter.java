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

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

// Класс адаптер, который содержит внутренний класс ViewHolder и
public class GreenAdapter extends RecyclerView.Adapter<GreenAdapter.NumberViewHolder> {

    // Количество элементов для отображения
    private int mNumberItems;

    // Указываем в конструкторе кол-во элементов для отображения
    public GreenAdapter(int numberOfItems) {
        // и вытаскиваем его в глобальную переменную
        mNumberItems = numberOfItems;
    }

    // Этот метод вызывается для создания каждого ViewHolder с RecyclerView
    @Override
    public NumberViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Получаем контекст, в котором выполняется представление
        Context context = viewGroup.getContext();
        // получем id макета для создания list_item (строчки)
        int layoutIdForListItem = R.layout.number_list_item;
        // создаем inflater
        LayoutInflater inflater = LayoutInflater.from(context);
        // "флаг" должен немедленно прилегать к родителям - ставим false
        boolean shouldAttachToParentImmediately = false;

        // создаем представление, "раздувая" его и передавая id макета для создания list_item, viewGroup и наш "флаг"
        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        // создаем объект NumberViewHolder и передаем в него наше представление
        NumberViewHolder viewHolder = new NumberViewHolder(view);

        return viewHolder;
    }

    /* OnBindViewHolder вызывается RecyclerView для отображения данных в указанной позиции.
    * В этом методе мы обновляем содержимое ViewHolder, чтобы отображать правильные индексы
    * в списке для этой конкретной позиции, используя аргумент «позиция», который удобно передавать нам.
    *
    * @param holder   ViewHolder, который должен быть обновлен для представления содержимого элемента в заданной позиции в наборе данных.
    * @param position Позиция элемента в наборе данных адаптера.*/
    @Override
    public void onBindViewHolder(NumberViewHolder holder, int position) {
        holder.bind(position);
    }

    /*Этот метод просто возвращает количество отображаемых элементов.
    Он используется за кулисами, чтобы помочь разместить наши представления и анимации.
    @return Количество пунктов, доступных в нашем "прогнозе"*/
    @Override
    public int getItemCount() {
        return mNumberItems;
    }

    // Класс, объект которого Кэширует все элементы списка
    class NumberViewHolder extends RecyclerView.ViewHolder {

        // Отобразит позицию в списке, то есть 0 через getItemCount () - 1
        TextView listItemNumberView;

        /* Конструктор для нашего ViewHolder. Внутри этого конструктора мы получаем ссылку
        * на наш TextViews и устанавливаем onClickListener для прослушивания кликов.
        * Они будут обработаны в методе onClick ниже.
        * @param itemView The View that you inflated in
        *                 {@link GreenAdapter#onCreateViewHolder(ViewGroup, int)}*/
        public NumberViewHolder(View itemView) {
            super(itemView);

            listItemNumberView = (TextView) itemView.findViewById(R.id.tv_item_number);
        }

        /* Метод, который мы писали для удобства. Этот метод принимает целое число
        в качестве ввода и использует это целое число для отображения соответствующего текста в элементе списка.
          @param listIndex Позиция позиции в списке
         */
        void bind(int listIndex) {
            listItemNumberView.setText(String.valueOf(listIndex));
        }
    }
}
