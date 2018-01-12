package com.example.android.pets.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

// Класс PetProvider для безопасной работы с нашей базой данных
public class PetProvider extends ContentProvider {

    // Тег для сообщений журнала
    public static final String LOG_TAG = PetProvider.class.getSimpleName();

    private PetDbHelper mDbHelper;

    // Код сопоставления URI для URI контента для таблицы домашних животных
    private static final int PETS = 100;

    // Код сопоставления URI для URI контента для одного питомца в таблице домашних животных
    private static final int PET_ID = 101;

    // Объект UriMatcher соответствует URI содержимого соответствующему коду.
    // Ввод, переданный в конструктор, представляет собой код, возвращаемый для корневого URI.
    // В качестве входных данных для этого случая обычно используется NO_MATCH.
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    // Статический инициализатор(блок). Это выполняется при первом вызове из этого класса.
    static {
        // Вызовы addURI () идут здесь, для всех шаблонов URI содержимого, которые поставщик
        // должен распознавать. Все пути, добавленные в UriMatcher, имеют соответствующий код для возврата когда совпадение найдено.
        sUriMatcher.addURI(PetContract.CONTENT_AUTHORITY, PetContract.PATH_PETS, PETS);
        sUriMatcher.addURI(PetContract.CONTENT_AUTHORITY, PetContract.PATH_PETS + "/#", PET_ID);
    }

    @Override
    public boolean onCreate() {
        mDbHelper = new PetDbHelper(getContext());
        return true;
    }

    // метод запроса на получения либо всей таблицы из БД, либо конкретно по ID
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        // Получаем нашу базу данных
        SQLiteDatabase database = mDbHelper.getReadableDatabase();
        // Создаем объект типа Cursor, куда передадим результат
        Cursor cursor = null;
        // Получем число, которое пришло в uri (100 или 101)
        int match = sUriMatcher.match(uri);
        switch (match) {
            case PETS: // если число 100 и нам нужна вся таблица pets
                cursor = database.query(PetContract.PetEntry.TABLE_NAME, projection, selection, null, null, null, sortOrder);
                break;
            case PET_ID: // если число 101 и нам нужна только конкретная строка по ID
                selection = PetContract.PetEntry._ID + "=?"; // выбираем столбец _ID
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))}; // выбираем строку исходя из _ID (ее номер запишется в "=?" в selection
                // записываем в Cursor ту часть из БД, которая нас интересовала исходя из ID
                cursor = database.query(PetContract.PetEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            default:
                // иначе кидаем исключение, что запрос с таким URI не обработан
                throw new IllegalArgumentException("Cannot query unknown URI " + uri);
        }
        // если данные изменились, то эта строка сработает и уведомит об этом
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        // возвращаем результат
        return cursor;
    }

    @Override
    public String getType(Uri uri) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case PETS:
                return PetContract.PetEntry.CONTENT_LIST_TYPE;
            case PET_ID:
                return PetContract.PetEntry.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalStateException("Unknown URI " + uri + " with match " + match);
        }
    }

    // Метод для добавления петомца в в базу данных
    public Uri insert(Uri uri, ContentValues contentValues) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case PETS:
                return insertPet(uri, contentValues);
            default:
                throw new IllegalArgumentException("Insertion is not supported for " + uri);
        }
    }

    // Вставьте домашнее животное в базу данных с указанными значениями содержимого. Вернуть новый URI содержимого для этой конкретной строки в базе данных.
    private Uri insertPet(Uri uri, ContentValues values) {
        // Проверяем, что имя не равно нулю.
        String name = values.getAsString(PetContract.PetEntry.COLUMN_PET_NAME);
        if (name == null || name.equals(" ")) {
            throw new IllegalArgumentException("Pet requires a name");
        }

        int weight = values.getAsInteger(PetContract.PetEntry.COLUMN_PET_WEIGHT);
        if (weight <= 0) {
            throw new IllegalArgumentException("Pet wrong age");
        }
        // Получить базу данных для записи
        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        // Вставьте новое домашнее животное с заданными значениями
        long id = database.insert(PetContract.PetEntry.TABLE_NAME, null, values);

        // Если идентификатор равен -1, то вставка не удалась. Запишите ошибку и верните нуль.
        if (id == -1) {
            Log.e(LOG_TAG, "Failed to insert row for " + uri);
            return null;
        }
        // если были изменения то сработает вот эта строка
        getContext().getContentResolver().notifyChange(uri, null);

        // Как только мы узнаем ID новой строки в таблице,
        // возвращаем новый URI с добавленным до конца идентификатором
        return ContentUris.withAppendedId(uri, id);
    }

    // метод по удалению либо всей таблицы, либо по отдельным ID
    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Отслеживаем количество удаленных строк
        int rowsDeleted;

        // Получить базу данных для записи
        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        final int match = sUriMatcher.match(uri);
        switch (match) {
            case PETS:
                // Удалите все строки, соответствующие аргументам выбора и выборки
                rowsDeleted = database.delete(PetContract.PetEntry.TABLE_NAME, selection, selectionArgs);
                // Если было удалено 1 или несколько строк, уведомите всех слушателей о том, что данные в данном URI изменились
                if (rowsDeleted != 0) {
                    getContext().getContentResolver().notifyChange(uri, null);
                }
                return rowsDeleted;

            case PET_ID:
                // Удаление одной строки, заданным ID в URI
                selection = PetContract.PetEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                // Удалите одну строку, указанную идентификатором в URI
                rowsDeleted = database.delete(PetContract.PetEntry.TABLE_NAME, selection, selectionArgs);
                // Если было удалено 1 или несколько строк, уведомите всех слушателей о том, что данные в данном URI изменились
                if (rowsDeleted != 0) {
                    getContext().getContentResolver().notifyChange(uri, null);
                }
                return rowsDeleted;
            default:
                throw new IllegalArgumentException("Deletion is not supported for " + uri);
        }
    }

    // Метод для обновления информации в БД
    @Override
    public int update(Uri uri, ContentValues contentValues, String selection, String[] selectionArgs) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case PETS:
                return updatePet(uri, contentValues, selection, selectionArgs);
            case PET_ID:
                // Для кода PET_ID извлеките идентификатор из URI,
                // поэтому мы знаем, какую строку обновить. Выбор будет "_id =?" и выбор
                // Аргумента будут массивом String, содержащим фактический идентификатор.
                selection = PetContract.PetEntry._ID + "=?"; // тут мы определяем, что ищем по ID
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))}; // а тут вместо "=?" будут подставляться те № ID строк, которые обновляем
                // и возвращаем кол-во обновленных строк вызывая сам метод обновления updatePet с основной логикой
                return updatePet(uri, contentValues, selection, selectionArgs);
            default:
                // иначе кидаем исключение
                throw new IllegalArgumentException("Update is not supported for " + uri);
        }
    }

    /**
     * Обновление домашних животных в базе данных с заданными значениями содержимого. Применить изменения к строкам
     * указаным в аргументах выбора и выбора args (которые могут быть 0 или 1 или более домашних животных).
     * Вернуть количество строк, которые были успешно обновлены.
     */
    private int updatePet(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        // If the {@link PetEntry#COLUMN_PET_NAME} key is present, check that the name value is not null.
        if (values.containsKey(PetContract.PetEntry.COLUMN_PET_NAME)) {
            String name = values.getAsString(PetContract.PetEntry.COLUMN_PET_NAME);
            if (name == null) {
                throw new IllegalArgumentException("Pet requires a name");
            }
        }

        // If the {@link PetEntry#COLUMN_PET_GENDER} key is present, check that the gender value is valid.
        if (values.containsKey(PetContract.PetEntry.COLUMN_PET_GENDER)) {
            Integer gender = values.getAsInteger(PetContract.PetEntry.COLUMN_PET_GENDER);
            if (gender == null) {
                throw new IllegalArgumentException("Pet requires valid gender");
            }
        }

        // If the {@link PetEntry#COLUMN_PET_WEIGHT} key is present, check that the weight value is valid.
        if (values.containsKey(PetContract.PetEntry.COLUMN_PET_WEIGHT)) {
            // Check that the weight is greater than or equal to 0 kg
            Integer weight = values.getAsInteger(PetContract.PetEntry.COLUMN_PET_WEIGHT);
            if (weight != null && weight < 0) {
                throw new IllegalArgumentException("Pet requires valid weight");
            }
        }

        // Если для обновления нет значений, не пытайтесь обновить базу данных
        if (values.size() == 0) {
            return 0;
        }

        // В противном случае получить базу данных, пригодную для записи, для обновления данных
        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        // Выполните обновление базы данных и получите количество затронутых строк.
        int rowsUpdated = database.update(PetContract.PetEntry.TABLE_NAME, values, selection, selectionArgs);

        // Если было обновлено 1 или более строк, сообщаем всем слушателям, что данные в данном URI изменились
        if (rowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        // Возвращает количество строк базы данных, на которые влияет инструкция update и собственно обновляет БД
        return rowsUpdated;
    }
}
