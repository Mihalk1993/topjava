package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

import static ru.javawebinar.topjava.util.TimeUtil.isBetweenHalfOpen;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> meals = Arrays.asList(
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
        );

        LocalTime startTime = LocalTime.of(0, 0);
        LocalTime endTime = LocalTime.of(23, 0);

        System.out.println("    filteredByCycles:");
        List<UserMealWithExcess> mealsTo = filteredByCycles(meals, startTime, endTime, 2000);
        mealsTo.forEach(System.out::println);

        System.out.println("    filteredByStreams:");
        System.out.println(filteredByStreams(meals, startTime, endTime, 2000));

        System.out.println("    filteredByCycles2:");
        List<UserMealWithExcess> mealsTo2 = filteredByCycles2(meals, startTime, endTime, 2000);
        mealsTo2.forEach(System.out::println);

//        System.out.println("    filteredByStreams2:");
//        System.out.println(filteredByStreams2(meals, startTime, endTime, 2000));
    }

    public static List<UserMealWithExcess> filteredByCycles(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> caloriesPerDayMap = new HashMap<>();
        for (UserMeal meal : meals) {
            caloriesPerDayMap.merge(meal.getDateTime().toLocalDate(), meal.getCalories(), Integer::sum);
        }

        List<UserMealWithExcess> filteredList = new ArrayList<>();
        for (UserMeal meal : meals) {
            LocalDateTime mealDateTime = meal.getDateTime();
            if (isBetweenHalfOpen(mealDateTime.toLocalTime(), startTime, endTime)) {
                UserMealWithExcess mealWithExcess = new UserMealWithExcess(mealDateTime, meal.getDescription(), meal.getCalories(),
                        caloriesPerDayMap.get(mealDateTime.toLocalDate()) > caloriesPerDay);
                filteredList.add(mealWithExcess);
            }
        }
        return filteredList;
    }

    public static List<UserMealWithExcess> filteredByStreams(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> caloriesPerDayMap = new HashMap<>();
        meals.forEach(meal -> caloriesPerDayMap.merge(meal.getDateTime().toLocalDate(), meal.getCalories(), Integer::sum));

        return meals.stream()
                .filter(meal -> isBetweenHalfOpen(meal.getDateTime().toLocalTime(), startTime, endTime))
                .map(meal -> new UserMealWithExcess(meal.getDateTime(), meal.getDescription(), meal.getCalories(),
                        caloriesPerDayMap.get(meal.getDateTime().toLocalDate()) > caloriesPerDay))
                .collect(Collectors.toList());
    }

    public static List<UserMealWithExcess> filteredByCycles2(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        class Excess {
            private final int calories;
            private final AtomicBoolean excess;

            public Excess(int calories, AtomicBoolean excess) {
                this.calories = calories;
                this.excess = excess;
                if (calories > caloriesPerDay) excess.set(true);
            }

            public AtomicBoolean getExcess() {
                return excess;
            }

            public int getCalories() {
                return calories;
            }
        }

        Map<LocalDate, Excess> caloriesPerDayMap = new HashMap<>();
        List<UserMealWithExcess> filteredList = new ArrayList<>();
        for (UserMeal meal : meals) {
            caloriesPerDayMap.merge(meal.getDateTime().toLocalDate(),
                    new Excess(meal.getCalories(), new AtomicBoolean()),
                    (oldExcess, excess) -> new Excess(oldExcess.getCalories() + meal.getCalories(), oldExcess.getExcess()));

            if (isBetweenHalfOpen(meal.getDateTime().toLocalTime(), startTime, endTime)) {
                filteredList.add(new UserMealWithExcess(meal.getDateTime(), meal.getDescription(), meal.getCalories(),
                        caloriesPerDayMap.get(meal.getDateTime().toLocalDate()).getExcess()));
            }
        }
        return filteredList;
    }

    public static List<UserMealWithExcess> filteredByStreams2(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        // TODO implement by streams in 1 pass through the original list of meals.stream()
        return null;
    }
}
