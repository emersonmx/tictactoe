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

#ifndef TICTACTOE_GAME_GAME_LISTENER_HPP_
#define TICTACTOE_GAME_GAME_LISTENER_HPP_

#include <tictactoe/game/player.hpp>

namespace tictactoe {

class GameEvent;

class GameListener {
    public:
        virtual void GameStarted(const GameEvent& event) = 0;

        virtual void GameOver(const GameEvent& event) = 0;

        virtual void Marked(const GameEvent& event) = 0;

        virtual void GameWinner(const GameEvent& event) = 0;

        virtual void GameDraw(const GameEvent& event) = 0;

        virtual void CurrentPlayerChanged(const GameEvent& event) = 0;

        virtual void InvalidPosition(const GameEvent& event) = 0;

        virtual void PositionIsNotEmpty(const GameEvent& event) = 0;

        virtual void InvalidConfiguration(const GameEvent& event) = 0;
};

} /* namespace tictactoe */
#endif /* TICTACTOE_GAME_GAME_LISTENER_HPP_ */

