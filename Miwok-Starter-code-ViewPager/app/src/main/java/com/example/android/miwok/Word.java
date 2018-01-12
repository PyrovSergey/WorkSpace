package com.example.android.miwok;


// вспомогательный класс "Слово"
public class Word {

    // Перевод по умолчанию для слова
    private String mDefaultTranslation;
    // Miwok перевод слова
    private String mMiwokTranslation;
    // Переменная для image
    private int mImageResourceId = NO_IMAGE_PROVIDED;

    private int mAudioResourceId;

    private static int NO_IMAGE_PROVIDED = -1;

    // Конструктор класса
    public Word(String defaultTranslation, String miwokTranslation, int mImageResourceId, int mAudioResourceId) {
        this.mDefaultTranslation = defaultTranslation;
        this.mMiwokTranslation = miwokTranslation;
        this.mImageResourceId = mImageResourceId;
        this.mAudioResourceId = mAudioResourceId;
    }

    // Конструктор класса 2
    public Word(String defaultTranslation, String miwokTranslation, int mAudioResourceId) {
        this.mDefaultTranslation = defaultTranslation;
        this.mMiwokTranslation = miwokTranslation;
        this.mAudioResourceId = mAudioResourceId;
    }

    // метод для перевода по умолчанию
    public String getDefaultTranslation() {
        return mDefaultTranslation;
    }

    // метод для перевода на Miwok.
    public String getMiwokTranslation() {
        return mMiwokTranslation;
    }

    // метод для получения image
    public int getmImageResourceId() {
        return mImageResourceId;
    }

    public int getmAudioResourceId() {
        return mAudioResourceId;
    }

    public boolean hasImage() {
        return mImageResourceId != NO_IMAGE_PROVIDED;
    }
}
