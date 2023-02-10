package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealCrud;
import ru.javawebinar.topjava.repository.InMemoryMealCrud;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.slf4j.LoggerFactory.getLogger;

public class MealEditServlet extends HttpServlet {
    private static final String MEALS_LIST = "meals";
    private static final String CREATE_OR_UPDATE = "/editMeal.jsp";
    private static final Logger log = getLogger(MealServlet.class);
    private final MealCrud mealCRUD;
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");


    public MealEditServlet() {
        mealCRUD = InMemoryMealCrud.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String forward = "";
        String action = request.getParameter("action");

        if (action.equalsIgnoreCase("delete")) {
            int mealId = Integer.parseInt(request.getParameter("mealId"));
            mealCRUD.delete(mealId);
            response.sendRedirect(MEALS_LIST);
        } else {
            if (action.equalsIgnoreCase("update")) {
                forward = CREATE_OR_UPDATE;
                int mealId = Integer.parseInt(request.getParameter("mealId"));
                Meal meal = mealCRUD.read(mealId);
                request.setAttribute("meal", meal);
                request.setAttribute("action", "update");
                request.setAttribute("pattern", DATE_TIME_FORMATTER);
            } else if (action.equalsIgnoreCase("create")) {
                forward = CREATE_OR_UPDATE;
                request.setAttribute("meal", new Meal(LocalDateTime.now(), "", 0));
                request.setAttribute("action", "create");
                request.setAttribute("pattern", DATE_TIME_FORMATTER);


            } else {
                forward = MEALS_LIST;
            }

            request.getRequestDispatcher(forward).forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        LocalDateTime dateTime = LocalDateTime.parse(request.getParameter("localDateTime"));
        String description = request.getParameter("description");
        int calories = Integer.parseInt(request.getParameter("calories"));
        MealCrud mealCRUD = InMemoryMealCrud.getInstance();

        int mealId = Integer.parseInt(request.getParameter("mealId"));
        if (mealId == 0) {
            mealCRUD.create(new Meal(dateTime, description, calories));
        } else {
            mealCRUD.update(new Meal(mealId, dateTime, description, calories));
        }
        response.sendRedirect(MEALS_LIST);
    }
}