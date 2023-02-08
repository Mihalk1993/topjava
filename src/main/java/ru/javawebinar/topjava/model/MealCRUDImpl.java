package ru.javawebinar.topjava.model;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.concurrent.CopyOnWriteArrayList;

public class MealCRUDImpl implements MealCRUD {
    private static MealCRUDImpl mealCRUDImpl;
    private static final CopyOnWriteArrayList<Meal> meals = new CopyOnWriteArrayList<>(Arrays.asList(
            new Meal(LocalDateTime.of(2023, Month.FEBRUARY, 6, 10, 0), "Завтрак", 500),
            new Meal(LocalDateTime.of(2023, Month.FEBRUARY, 6, 13, 0), "Обед", 1000),
            new Meal(LocalDateTime.of(2023, Month.FEBRUARY, 6, 20, 0), "Ужин", 500),
            new Meal(LocalDateTime.of(2023, Month.FEBRUARY, 7, 0, 0), "Еда на граничное значение", 100),
            new Meal(LocalDateTime.of(2023, Month.FEBRUARY, 7, 10, 0), "Завтрак", 1000),
            new Meal(LocalDateTime.of(2023, Month.FEBRUARY, 7, 13, 0), "Обед", 500),
            new Meal(LocalDateTime.of(2023, Month.FEBRUARY, 7, 20, 0), "Ужин", 410)));

    private MealCRUDImpl() {
    }

    public static MealCRUDImpl getInstance() {
        if (mealCRUDImpl == null) {
            mealCRUDImpl = new MealCRUDImpl();
        }
        return mealCRUDImpl;
    }

    public CopyOnWriteArrayList<Meal> getMeals() {
        return meals;
    }

    @Override
    public void create(LocalDateTime dateTime, String description, int calories) {
        meals.add(new Meal(dateTime, description, calories));
    }

    @Override
    public Meal read(int id) {
        int index = getIndex(id);
        return meals.get(index);
    }

    @Override
    public void update(int id, LocalDateTime dateTime, String description, int calories) {
        int index = getIndex(id);
        meals.set(index, new Meal(id, dateTime, description, calories));
    }

    @Override
    public void delete(int id) {
        int index = getIndex(id);
        meals.remove(index);
    }

    private static int getIndex(int id) {
        int index = 0;
        for (int i = 0; i < meals.size(); i++) {
            Meal meal = meals.get(i);
            if (meal.getId() == id) {
                index = i;
            }
        }
        return index;
    }
}