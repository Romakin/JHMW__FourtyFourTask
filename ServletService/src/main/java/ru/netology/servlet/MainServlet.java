package ru.netology.servlet;

import ru.netology.controller.PostController;
import ru.netology.repository.PostRepository;
import ru.netology.service.PostService;

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
    final PostRepository repository = new PostRepository();
    final PostService service = new PostService(repository);
    controller = new PostController(service);
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

