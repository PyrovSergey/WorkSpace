package com.example.android.miwok;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

// Класс адаптер
public class WordAdapter extends ArrayAdapter<Word> {

    private int mColorResourceId;
    // Конструктор класса
    public WordAdapter(Activity context, ArrayList<Word> words, int mColorResourceId) {
        /* Здесь мы инициализируем внутреннее хранилище WordAdapter для контекста и списка.
        второй аргумент используется, когда WordAdapter заполняет один TextView.
        Поскольку это настраиваемый адаптер для двух текстовых элементов, адаптер не является
        будет использовать этот второй аргумент, поэтому он может быть любым значением. Здесь мы использовали 0.*/
        super(context, 0, words);
        this.mColorResourceId = mColorResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Проверьте, используется ли существующее представление повторно, иначе раздуйте представление
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }

        // Получите объект {@link Word}, расположенный в этой позиции в списке
        Word currentWord = getItem(position);

        // Найдите TextView в макете miwok_text_view.xml с идентификатором version_name
        TextView miwokTextView = (TextView) listItemView.findViewById(R.id.miwok_text_view);
        /* Получить имя версии из текущего объекта WordAdapter и
          установите этот текст на имя TextView */
        miwokTextView.setText(currentWord.getMiwokTranslation());

        // Найти TextView в макете default_text_view.xml с идентификатором version_number
        TextView defaultTextView = (TextView) listItemView.findViewById(R.id.default_text_view);
        /* Получить номер версии из текущего объекта WordAdapter и
         установите этот текст на число TextView */
        defaultTextView.setText(currentWord.getDefaultTranslation());

        ImageView iconView = (ImageView) listItemView.findViewById(R.id.imageView);
        if (currentWord.hasImage()) {
            iconView.setImageResource(currentWord.getmImageResourceId());
            iconView.setVisibility(View.VISIBLE);
        } else {
            iconView.setVisibility(View.GONE);
        }

        View textContainer = listItemView.findViewById(R.id.text_container);
        int color = ContextCompat.getColor(getContext(), mColorResourceId);
        textContainer.setBackgroundColor(color);
        /* Вернуть весь макет элемента списка (содержащий 2 TextViews)
          так что его можно показать в ListView */
        return listItemView;
    }
}
