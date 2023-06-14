package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {
    public static final int USER_ID = START_SEQ;

    public static List<Meal> getMeals() {
        List<Meal> meals = new ArrayList<>();
        meals.add(new Meal(100003, LocalDateTime.of(2020, 1, 30, 10, 0), "Завтрак", 500));
        meals.add(new Meal(100004, LocalDateTime.of(2020, 1, 30, 13, 0), "Обед", 1000));
        meals.add(new Meal(100005, LocalDateTime.of(2020, 1, 30, 20, 0), "Ужин", 800));
        meals.add(new Meal(100006, LocalDateTime.of(2020, 1, 31, 0, 0), "Еда на граничное значение", 800));
        meals.add(new Meal(100007, LocalDateTime.of(2020, 1, 31, 10, 0), "Завтрак", 800));
        meals.add(new Meal(100008, LocalDateTime.of(2020, 1, 31, 13, 0), "Обед", 800));
        meals.add(new Meal(100009, LocalDateTime.of(2020, 1, 31, 20, 0), "Ужин", 800));
        return meals;
    }

    public static Meal getNew() {
        return new Meal(100000, LocalDateTime.of(2020, 1, 30, 20, 0), "New", 800);
    }

    public static Meal getUpdated() {
        Meal updated = new Meal(getMeals().get(0));
        updated.setDateTime(LocalDateTime.of(2020, 1, 30, 11, 0));
        updated.setDescription("Поздний завтрак");
        updated.setCalories(400);
        return updated;
    }

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).usingRecursiveComparison().ignoringFields("calories").isEqualTo(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).usingRecursiveFieldByFieldElementComparatorIgnoringFields("calories").isEqualTo(expected);
    }
}
