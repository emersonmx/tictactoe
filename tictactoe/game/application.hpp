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

#ifndef TICTACTOE_GAME_APPLICATION_HPP_
#define TICTACTOE_GAME_APPLICATION_HPP_

struct GLFWwindow;

namespace tictactoe {

class Application {
    public:
        Application();

        inline int error_code() const { return error_code_; }

        int Run(int argc, char* argv[]);

        inline void Exit(const int error_code) {
            error_code_ = error_code;
            running_ = false;
        }

    private:
        void Initialize(int argc, char* argv[]);

        bool CreateWindow(unsigned width, unsigned height);

        void SetupGL(unsigned width, unsigned height);

        void Events();

        void Logic();

        void Render();

        void Finalize();

        int error_code_;
        bool running_;

        GLFWwindow* window_;
};

} /* namespace tictactoe */
#endif /* TICTACTOE_GAME_APPLICATION_HPP_ */

