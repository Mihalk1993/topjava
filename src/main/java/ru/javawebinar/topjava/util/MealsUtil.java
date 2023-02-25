package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealTo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class MealsUtil {
    public static final int DEFAULT_CALORIES_PER_DAY = 2000;

    public static final List<Meal> meals = Arrays.asList(
            new Meal(null, 2, LocalDateTime.of(2023, Month.FEBRUARY, 25, 10, 0), "Завтрак", 500),
            new Meal(null, 2, LocalDateTime.of(2023, Month.FEBRUARY, 25, 13, 0), "Обед", 1000),
            new Meal(null, 2, LocalDateTime.of(2023, Month.FEBRUARY, 25, 20, 0), "Ужин", 500),
            new Meal(null, 1, LocalDateTime.of(2023, Month.FEBRUARY, 26, 0, 0), "Еда на граничное значение", 100),
            new Meal(null, 1, LocalDateTime.of(2023, Month.FEBRUARY, 26, 10, 0), "Завтрак", 1000),
            new Meal(null, 1, LocalDateTime.of(2023, Month.FEBRUARY, 26, 13, 0), "Обед", 500),
            new Meal(null, 1, LocalDateTime.of(2023, Month.FEBRUARY, 26, 20, 0), "Ужин", 410)
    );

    public static List<MealTo> getTos(Collection<Meal> meals, int caloriesPerDay) {
        return filterByPredicate(meals, caloriesPerDay, meal -> true);
    }

    public static List<MealTo> getTos(Collection<Meal> meals, int caloriesPerDay, LocalTime startTime, LocalTime endTime) {
        return filterByPredicate(meals, caloriesPerDay, meal -> DateTimeUtil.isBetweenHalfOpen(meal.getTime(), startTime, endTime));
    }

    private static List<MealTo> filterByPredicate(Collection<Meal> meals, int caloriesPerDay, Predicate<Meal> timeFilter) {
        Map<LocalDate, Integer> caloriesSumByDate = meals.stream()
                .collect(
                        Collectors.groupingBy(Meal::getDate, Collectors.summingInt(Meal::getCalories))
                );

        return meals.stream()
                .filter(timeFilter)
                .map(meal -> createTo(meal, caloriesSumByDate.get(meal.getDate()) > caloriesPerDay))
                .collect(Collectors.toList());
    }

    private static MealTo createTo(Meal meal, boolean excess) {
        return new MealTo(meal.getId(), meal.getDateTime(), meal.getDescription(), meal.getCalories(), excess);
    }
}
