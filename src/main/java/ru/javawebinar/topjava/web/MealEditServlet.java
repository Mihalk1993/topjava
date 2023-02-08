package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealCRUD;
import ru.javawebinar.topjava.model.MealCRUDImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

import static org.slf4j.LoggerFactory.getLogger;

public class MealEditServlet extends HttpServlet {
    private static final String MEALS_LIST = "meals";
    private static final String CREATE_OR_UPDATE = "/editMeal.jsp";
    private static final Logger log = getLogger(MealServlet.class);
    private final MealCRUD mealCRUD;

    public MealEditServlet() {
        mealCRUD = MealCRUDImpl.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String forward = "";
        String action = request.getParameter("action");

        if (action.equalsIgnoreCase("delete")) {
            int mealId = Integer.parseInt(request.getParameter("mealId"));
            mealCRUD.delete(mealId);
            forward = MEALS_LIST;
        } else if (action.equalsIgnoreCase("update")) {
            forward = CREATE_OR_UPDATE;
            int mealId = Integer.parseInt(request.getParameter("mealId"));
            Meal meal = mealCRUD.read(mealId);
            request.setAttribute("meal", meal);
        } else if (action.equalsIgnoreCase("create")) {
            forward = CREATE_OR_UPDATE;
            request.setAttribute("meal", new Meal(LocalDateTime.MIN, "-", 0));
        } else {
            forward = MEALS_LIST;
        }

        request.getRequestDispatcher(forward).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        LocalDateTime dateTime = LocalDateTime.parse(request.getParameter("localDateTime"));
        String description = request.getParameter("description");
        int calories = Integer.parseInt(request.getParameter("calories"));
        MealCRUD mealCRUD = MealCRUDImpl.getInstance();

        int maxCurrentId = 0;
        for (Meal meal : MealCRUDImpl.getInstance().getMeals()) {
            if (meal.getId() > maxCurrentId) maxCurrentId = meal.getId();
        }

        int mealId = Integer.parseInt(request.getParameter("mealId"));
        if (mealId > maxCurrentId) {
            mealCRUD.create(dateTime, description, calories);
        } else {
            mealCRUD.update(mealId, dateTime, description, calories);
        }
        response.sendRedirect(MEALS_LIST);
    }
}