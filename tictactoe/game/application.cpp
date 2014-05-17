/*
  Copyright (C) 2014 Emerson Max de Medeiros Silva

  This file is part of tictactoe.

  tictactoe is free software: you can redistribute it and/or modify
  it under the terms of the GNU General Public License as published by
  the Free Software Foundation, either version 3 of the License, or
  (at your option) any later version.

  tictactoe is distributed in the hope that it will be useful,
  but WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
  GNU General Public License for more details.

  You should have received a copy of the GNU General Public License
  along with tictactoe.  If not, see <http://www.gnu.org/licenses/>.
*/

#include <tictactoe/game/application.hpp>

#include <cstdlib>

#define GLFW_INCLUDE_GLU
#include <GLFW/glfw3.h>

namespace tictactoe {

Application::Application() : error_code_(EXIT_SUCCESS), running_(true) {}

int Application::Run(int argc, char* argv[]) {
    Initialize(argc, argv);

    while(running_) {
        Events();
        Logic();
        Render();
    }

    Finalize();

    return error_code_;
}

void Application::Initialize(int argc, char* argv[]) {
    if (glfwInit()) {
        if (!CreateWindow(512, 512)) {
            Exit(EXIT_FAILURE);
        }
    } else {
        Exit(EXIT_FAILURE);
    }
}

bool Application::CreateWindow(unsigned width, unsigned height) {
    glfwWindowHint(GLFW_RESIZABLE, false);
    window_ = glfwCreateWindow(width, height, "TicTacToe", NULL, NULL);
    if (window_ != NULL) {
        glfwMakeContextCurrent(window_);

        SetupGL(width, height);
        return true;
    }

    return false;
}

void Application::SetupGL(unsigned width, unsigned height) {
    glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

    glViewport(0, 0, width, height);

    glMatrixMode(GL_PROJECTION);
    glLoadIdentity();
    gluOrtho2D(0, 10, 0, 10);

    glMatrixMode(GL_MODELVIEW);
    glLoadIdentity();
}

void Application::Events() {
    glfwPollEvents();

    if (glfwWindowShouldClose(window_)) {
        Exit(EXIT_SUCCESS);
    }
}

void Application::Logic() {

}

void Application::Render() {
    glClear(GL_COLOR_BUFFER_BIT);

    glLoadIdentity();
    glBegin(GL_TRIANGLES);
        glVertex3f(0, 0, 0);
        glVertex3f(10, 0, 0);
        glVertex3f(5, 10, 0);
    glEnd();

    glfwSwapBuffers(window_);
}

void Application::Finalize() {
    glfwDestroyWindow(window_);
    glfwTerminate();
}

} /* namespace tictactoe */

