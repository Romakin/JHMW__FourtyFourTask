package ru.netology.servlet;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.netology.controller.PostController;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "PostsAPI", urlPatterns = {"/api/posts", "/api/posts/*"})
public class MainServlet extends HttpServlet {
  private PostController controller;

  @Override
  public void init() {
    final ApplicationContext context = new AnnotationConfigApplicationContext("ru.netology");
    controller = (PostController) context.getBean("postController");
  }

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) {
    try {
      long id = getId(request.getRequestURI());
      if (id > 0)
        controller.getById(id, response);
      else
        controller.all(response);
    } catch (IOException e) {
      e.printStackTrace();
      response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    }
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) {
    try {
      controller.save(request.getReader(), response);
    } catch (IOException e) {
      e.printStackTrace();
      response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    }
  }

  @Override
  protected void doDelete(HttpServletRequest request, HttpServletResponse response) {
    try {
      long id = getId(request.getRequestURI());
      if (id > 0)
          controller.removeById(id, response);
      else
        response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
    } catch (IOException e) {
      e.printStackTrace();
      response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    }
  }

  private long getId(String path) {
    return (path.matches(".+/\\d+$")) ?
            Long.parseLong(path.substring(path.lastIndexOf("/"))) : -1;
  }

}

