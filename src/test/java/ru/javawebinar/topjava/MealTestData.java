package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.UserTestData.GUEST_ID;

public class MealTestData {
    public static final int USER_MEAL1_ID = GUEST_ID + 1;
    public static final int USER_MEAL2_ID = USER_MEAL1_ID + 1;
    public static final int USER_MEAL3_ID = USER_MEAL2_ID + 1;
    public static final int ADMIN_MEAL1_ID = USER_MEAL3_ID + 1;
    public static final int ADMIN_MEAL2_ID = ADMIN_MEAL1_ID + 1;
    public static final int ADMIN_MEAL3_ID = ADMIN_MEAL2_ID + 1;
    public static final int ADMIN_MEAL4_ID = ADMIN_MEAL3_ID + 1;
    public static final int ADMIN_MEAL5_ID = ADMIN_MEAL4_ID + 1;
    public static final int ADMIN_MEAL6_ID = ADMIN_MEAL5_ID + 1;
    public static final int ADMIN_MEAL7_ID = ADMIN_MEAL6_ID + 1;
    public static final int NOT_FOUND = 10;

    public static final Meal userMeal1 = new Meal(USER_MEAL1_ID, LocalDateTime.of(2020, 1, 30, 10, 0), "Завтрак", 500);
    public static final Meal userMeal2 = new Meal(USER_MEAL2_ID, LocalDateTime.of(2020, 1, 30, 13, 0), "Обед", 1000);
    public static final Meal userMeal3 = new Meal(USER_MEAL3_ID, LocalDateTime.of(2020, 1, 30, 20, 0), "Ужин", 500);
    public static final Meal adminMeal1 = new Meal(ADMIN_MEAL1_ID, LocalDateTime.of(2020, 1, 31, 0, 0), "Еда на граничное значение", 100);
    public static final Meal adminMeal2 = new Meal(ADMIN_MEAL2_ID, LocalDateTime.of(2020, 1, 31, 10, 0), "Завтрак", 1000);
    public static final Meal adminMeal3 = new Meal(ADMIN_MEAL3_ID, LocalDateTime.of(2020, 1, 31, 13, 0), "Обед", 500);
    public static final Meal adminMeal4 = new Meal(ADMIN_MEAL4_ID, LocalDateTime.of(2020, 1, 31, 20, 0), "Ужин", 410);
    public static final Meal adminMeal5 = new Meal(ADMIN_MEAL5_ID, LocalDateTime.of(2020, 2, 1, 10, 30), "Завтрак", 500);
    public static final Meal adminMeal6 = new Meal(ADMIN_MEAL6_ID, LocalDateTime.of(2020, 2, 1, 12, 15), "Обед", 1000);
    public static final Meal adminMeal7 = new Meal(ADMIN_MEAL7_ID, LocalDateTime.of(2020, 2, 1, 21, 10), "Ужин", 500);

    public static Meal getNew() {
        return new Meal(LocalDateTime.of(2020, 1, 30, 22, 30), "New", 800);
    }

    public static Meal getUpdated() {
        Meal updated = new Meal(userMeal1);
        updated.setDateTime(LocalDateTime.of(2020, 1, 30, 11, 0));
        updated.setDescription("Поздний завтрак");
        updated.setCalories(400);
        return updated;
    }

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).usingRecursiveFieldByFieldElementComparator().isEqualTo(expected);
    }
}
