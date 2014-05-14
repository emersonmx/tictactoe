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

#ifndef TICTACTOE_GAME_TICTACTOE_LISTENER_HPP_
#define TICTACTOE_GAME_TICTACTOE_LISTENER_HPP_

#include <tictactoe/game/player.hpp>

namespace tictactoe {

class TicTacToeEvent;

class TicTacToeListener {
    public:
        virtual void GameStarted(const TicTacToeEvent& event) = 0;

        virtual void GameOver(const TicTacToeEvent& event) = 0;

        virtual void Marked(const TicTacToeEvent& event) = 0;

        virtual void GameWinner(const TicTacToeEvent& event) = 0;

        virtual void GameDraw(const TicTacToeEvent& event) = 0;

        virtual void CurrentPlayerChanged(const TicTacToeEvent& event) = 0;

        virtual void InvalidPosition(const TicTacToeEvent& event) = 0;

        virtual void PositionIsNotEmpty(const TicTacToeEvent& event) = 0;

        virtual void InvalidConfiguration(const TicTacToeEvent& event) = 0;
};

} /* namespace tictactoe */
#endif /* TICTACTOE_GAME_TICTACTOE_LISTENER_HPP_ */

