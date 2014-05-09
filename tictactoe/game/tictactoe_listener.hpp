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

class TicTacToeListener {
    public:
        virtual void Marked(const Player& player, const unsigned i,
                            const unsigned j) = 0;

        virtual void GameWinner(const Player& player) = 0;

        virtual void GameDraw() = 0;

        virtual void CurrentPlayerChanged(const Player& player) = 0;

        virtual void InvalidPosition(const unsigned i, const unsigned j) = 0;

        virtual void PositionIsNotEmpty(const unsigned i, const unsigned j) = 0;

        virtual void InvalidConfiguration(const Player& player_1,
                                          const Player& player_2) = 0;
};

} /* namespace tictactoe */
#endif /* TICTACTOE_GAME_TICTACTOE_LISTENER_HPP_ */

